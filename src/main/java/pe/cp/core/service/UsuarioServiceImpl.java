package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioDao usuariodao;
	
	@Autowired
	private ClienteDao clientedao;
	
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
			usuario.setCliente(clientedao.buscar(request.getIdCliente()));
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
	public Usuario buscar(int idUsuario) {
		return usuariodao.buscar(idUsuario);
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		return usuariodao.buscarPorLogin(login);
	}

	@Override
	public boolean validarNuevoUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarUsuarioModificado(Usuario usuario, Usuario usuarioModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String generarContrasenaTemporal() {
		// TODO Auto-generated method stub
		return "123";
	}
}
