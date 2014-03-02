package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Rol;
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.core.service.messages.EliminarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpResponse;
import pe.cp.core.service.messages.ObtenerUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaRequest;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.core.service.messages.ValidarDatosUsuarioRequest;
import pe.cp.core.service.messages.ValidarDatosUsuarioResponse;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioDao usuariodao;
	
	@Autowired
	private ClienteDao clientedao;
	
	private final String ERR_ELIMINAR_USUARIO = "Error al eliminar el usuario";
	private final String EXITO_ELIMINAR_USUARIO = "Se eliminó al usuario satisfactoriamente";
	
	@Override
	public ActualizarUsuarioResponse actualizar(ActualizarUsuarioRequest request) {
		ActualizarUsuarioResponse response = new ActualizarUsuarioResponse();
		Usuario usuario = usuariodao.buscar(request.getIdUsuario());
		Usuario nuevoMod = new Usuario();
		
		nuevoMod.setApellidos(request.getApellidos());
		nuevoMod.setCargo(request.getCargo());
		nuevoMod.setEmail(request.getEmail());
		nuevoMod.setLogin(request.getLogin());
		nuevoMod.setNombres(request.getNombres());		
		
		if (validarUsuarioModificado(usuario, nuevoMod)){
			usuario.setApellidos(request.getApellidos());
			usuario.setCargo(request.getCargo());
			usuario.setEmail(request.getEmail());
			usuario.setLogin(request.getLogin());
			usuario.setNombres(request.getNombres());	
			
			usuariodao.actualizar(usuario);
			usuariodao.removerTodosRoles(usuario.getId());
			for (Integer idRol : request.getIdRoles()) {
				usuariodao.agregarRol(usuario.getId(), idRol);
			}
			
			response.setResultadoEjecucion(true);	
			response.setMensaje("Se modificó al usuario exitosamente");
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("Ocurrió un error al modificar al usuario");
		}		
		
		return response;
	}

	@Override
	public InsertarUsuarioResponse agregar(InsertarUsuarioRequest request) {
		InsertarUsuarioResponse response = new InsertarUsuarioResponse();
		
		Usuario usuario = new Usuario();
			usuario.setApellidos(request.getApellidos());
			usuario.setCargo(request.getCargo());
			usuario.setEmail(request.getEmail());
			usuario.setLogin(request.getLogin());
			usuario.setNombres(request.getNombres());
			if (request.getIdCliente() > 0)				
				usuario.setCliente(clientedao.buscar(request.getIdCliente()));
			else
				usuario.setCliente(null);
			usuario.setPassword(generarContrasenaTemporal());
		
		if (validarNuevoUsuario(usuario)){
			Integer idUsuario = usuariodao.agregar(usuario);
			if (idUsuario != null){
				for (Integer idRol : request.getIdRoles()) {
					usuariodao.agregarRol(idUsuario, idRol);
				}
				response.setResultadoEjecucion(true);
				response.setMensaje("Se insertó al usuario exitosamente");
			}	
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("Ocurrió un error al insertar al usuario");
		}		
		
		return response;		
	}

	@Override
	public void eliminar(int id) {
		usuariodao.eliminar(id);		
	}

	@Override
	public BuscarUsuarioResponse buscarOr(BuscarUsuarioRequest request) {
		BuscarUsuarioResponse response = new BuscarUsuarioResponse();
		
		UsuarioFilter filter = new UsuarioFilter();
		filter.setNombres(request.getNombresApellidos());
		filter.setApellidos(request.getNombresApellidos());
		
		List<Usuario> usuarios =  usuariodao.buscarOr(filter);
		List<UsuarioView> usuariosview = new ArrayList<UsuarioView>();
		if (usuarios.size() > 0){
			for(Usuario usuario:usuarios){
				UsuarioView usuarioview = WrapperDomain.ViewMapper(usuario);
				usuariosview.add(usuarioview);
			}
			response.setMensaje("Se encontraron " + usuarios.size() + " registros");		    
		}else{
			response.setMensaje("No se encontraron coincidencias");}
		
		response.setUsuariosEncontrados(usuariosview);
		response.setResultadoEjecucion(true);
		
		return response;
	}

	@Override
	public ObtenerUsuarioResponse buscar(ObtenerUsuarioRequest request) {
		ObtenerUsuarioResponse response = new ObtenerUsuarioResponse();
		Usuario usuario = usuariodao.buscar(request.getIdUsuario());
		
		if (usuario != null){
			response.setUsuarioView(WrapperDomain.ViewMapper(usuario));
			response.setRolesView(new ArrayList<RolView>());
			for (Rol rol : usuario.getRoles()) {
				response.getRolesView().add(WrapperDomain.ViewMapper(rol));
			}
			
			response.setMensaje("Se encontró un usuario");
			response.setResultadoEjecucion(true);
		}else{
			response.setMensaje("No se encontró un usuario");
			response.setResultadoEjecucion(false);
		}
		
		return response;
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		return usuariodao.buscarPorLogin(login);
	}

	@Override
	public boolean validarNuevoUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean validarUsuarioModificado(Usuario usuario, Usuario usuarioModificado) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String generarContrasenaTemporal() {
		// TODO Auto-generated method stub
		return "123";
	}

	@Override
	public ObtenerUsuariosSistemaResponse obtenerUsuariosSistema(ObtenerUsuariosSistemaRequest request) {
		ObtenerUsuariosSistemaResponse response = new ObtenerUsuariosSistemaResponse();
		List<Usuario> usuarios = usuariodao.obtenerUsuariosSistema(request.getRol());
		
		response.setUsuariosView(new ArrayList<UsuarioView>());
		for (Usuario usuario : usuarios) {
			response.getUsuariosView().add(WrapperDomain.ViewMapper(usuario));
		}
		
		response.setResultadoEjecucion(true);
		return response;
	}

	@Override
	public ObtenerUsuarioPorClienteResponse obtenerUsuariosPorCliente(ObtenerUsuarioPorClienteRequest request) {
		ObtenerUsuarioPorClienteResponse response = new ObtenerUsuarioPorClienteResponse();
		List<Usuario> usuarios = usuariodao.obtenerUsuariosPorCliente(request.getIdCliente());
		
		response.setUsuariosView(new ArrayList<UsuarioView>());
		for (Usuario usuario : usuarios) {
			response.getUsuariosView().add(WrapperDomain.ViewMapper(usuario));
		}
		
		response.setResultadoEjecucion(true);
		return response;
	}

	
	/**
	 * Realiza las validaciones de los datos que se deben comparar contra la base de datos
	 */
	@Override
	public ValidarDatosUsuarioResponse validarDatosUsuario(ValidarDatosUsuarioRequest request) {
		ValidarDatosUsuarioResponse response = new ValidarDatosUsuarioResponse();
		response.setResultadoEjecucion(true);
		
		Usuario usuario = buscarPorLogin(request.getLogin());
		if (usuario != null){
			//Si el id > 0, el usuario existe por lo que si el login ha cambiado, este no debe existir en otro usuario
			if (request.getIdUsuario() > 0){
				Usuario antiguoUsuario = usuariodao.buscar(request.getIdUsuario());
				if (antiguoUsuario == null){
					response.setResultadoEjecucion(false);
					response.setMensaje("No se encontro al usuario a editar, posiblemente fue eliminado");
				}else{
					if (antiguoUsuario.getId() != usuario.getId() && usuario.getLogin().equals(request.getLogin())){
						response.setResultadoEjecucion(false);
						response.setMensaje("Ya existe un usuario con el login ingresado");
					}
				}
			}else{
				//Si el id <= 0, es un nuevo usuario, no debe existir el login
				if (usuario.getLogin().equals(request.getLogin())){
					response.setResultadoEjecucion(false);
					response.setMensaje("Ya existe un usuario con el login ingresado");
				}
			}	
		}
		
		return response;
	}

	@Override
	public ObtenerUsuarioPorUnidadOpResponse obtenerUsuariosPorUnidadOP(
				ObtenerUsuarioPorUnidadOpRequest request) {
		ObtenerUsuarioPorUnidadOpResponse response = new ObtenerUsuarioPorUnidadOpResponse();
		
		List<Usuario> usuarios = 
				usuariodao.obtenerUsuariosPorUnidadOperativa(request.getIdUnidadOperativa(), request.getRol());
		
		response.setUsuariosView(new ArrayList<UsuarioView>());
		for (Usuario usuario : usuarios) {
			response.getUsuariosView().add(WrapperDomain.ViewMapper(usuario));
		}
		
		response.setResultadoEjecucion(true);
		return response;
	}
	
	@Override
	public Response eliminarUsuario(EliminarUsuarioRequest request) {
		Response response = new Response();
		
		try {
			usuariodao.eliminar(request.getIdUsuario());
			response.setResultadoEjecucion(true);
			response.setMensaje(EXITO_ELIMINAR_USUARIO);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_ELIMINAR_USUARIO);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
}
