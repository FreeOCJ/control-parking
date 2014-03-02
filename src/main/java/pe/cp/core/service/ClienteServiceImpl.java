package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.AuditoriaDao;
import pe.cp.core.dao.ClienteDao;
import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Usuario;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.ActualizarClienteRequest;
import pe.cp.core.service.messages.ActualizarClienteResponse;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.core.service.messages.EliminarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteResponse;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.Response;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteDao cdao;
	@Autowired
	private AuditoriaDao auditDao;
	@Autowired
	private UsuarioDao usuarioDao;
	
	private final String ERR_ELIMINAR_CLIENTE = "Error al eliminar al cliente";
	private final String ERR_VALIDAR_CLIENTE = "Error al validar al cliente";
	private final String EXITO_ELIMINAR_CLIENTE = "Se eliminó al cliente de manera satisfactoria";
	private final String EXITO_INSERTAR_CLIENTE = "Se insertó al cliente exitosamente";
	private final String ERR_MODIFICAR_CLIENTE = "Error al modificar al cliente";
	
	@Override
	public InsertarClienteResponse agregar(InsertarClienteRequest request) {
		InsertarClienteResponse response = new InsertarClienteResponse();
		
		Usuario modificador = usuarioDao.buscar(request.getIdUsuarioModificador());
		Cliente cliente = new Cliente();
		cliente.setNombreComercial(request.getNombreComercial());
		cliente.setRazonSocial(request.getRazonSocial());
		cliente.setRuc(request.getRuc());
		
		if (validarNuevoCliente(cliente)){
			Integer idCliente = cdao.agregar(cliente);
			if (idCliente != null){
				cliente.setId(idCliente);
				auditDao.agregarAuditoria(modificador, cliente, AuditoriaServiceImpl.INSERTAR_CLIENTE);
				response.setIdCliente(idCliente);
				response.setMensaje(EXITO_INSERTAR_CLIENTE);
				response.setResultadoEjecucion(true);
			}						
		}else{
			response.setMensaje(ERR_VALIDAR_CLIENTE);
			response.setResultadoEjecucion(false);
		}
				
		return response;
	}

	@Override
	public ActualizarClienteResponse actualizar(ActualizarClienteRequest request) {
		ActualizarClienteResponse response = new ActualizarClienteResponse();
				
		try {
			Usuario modificador = usuarioDao.buscar(request.getIdUsuario());
			Cliente cliente = cdao.buscar(request.getIdCliente());
			Cliente clienteMod = new Cliente();
			clienteMod.setNombreComercial(request.getNombreComercial());
			clienteMod.setRazonSocial(request.getRazonSocial());
			clienteMod.setRuc(request.getRuc());
			
			if (validarModificarCliente(cliente, clienteMod)){
				cliente.setNombreComercial(request.getNombreComercial());
				cliente.setRazonSocial(request.getRazonSocial());
				cliente.setRuc(request.getRuc());
				cdao.actualizar(cliente);	
				
				auditDao.agregarAuditoria(modificador, cliente, AuditoriaServiceImpl.MODIFICAR_CLIENTE);
				response.setResultadoEjecucion(true);
				response.setMensaje("Se actualizaron los datos del cliente exitosamente");
			}else{
				response.setResultadoEjecucion(false);
				response.setMensaje("Error al validar los datos del cliente");
			}	
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_MODIFICAR_CLIENTE);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public void eliminar(int idCliente) {
		cdao.eliminar(idCliente);
	}

	@Override
	public BuscarClienteResponse buscar(BuscarClienteRequest request) {
		BuscarClienteResponse response = new BuscarClienteResponse();
		
		List<Cliente> clientes = cdao.buscar(request.getNombreComercial());
		
		if (clientes.size() > 0)
			response.setMensaje("Se encontraron " + clientes.size() + " registros");
		else
			response.setMensaje("No se encontraron coincidencias");
		
		response.setClientesEncontrados(new ArrayList<ClienteView>());
		for (Cliente cliente : clientes) {
			response.getClientesEncontrados().add(WrapperDomain.ViewMapper(cliente));
		}		
		response.setResultadoEjecucion(true);
				
		return response;
	}

	@Override
	public boolean validarNuevoCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean validarModificarCliente(Cliente cliente, Cliente clienteMod) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ObtenerClienteResponse obtenerPorId(ObtenerClienteRequest request) {
		ObtenerClienteResponse response = new ObtenerClienteResponse();
		Cliente cliente = cdao.buscar(request.getIdCliente());
		
		if (cliente != null){
			response.setResultadoEjecucion(true);
			response.setClienteView(WrapperDomain.ViewMapper(cliente));
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("No se encontro un cliente con el ID dado");
		}
		
		return response;
	}
	
	@Override
	public Response eliminarCliente(EliminarClienteRequest request) {
		Response response = new Response();
		
		try {
			Usuario modificador = usuarioDao.buscar(request.getIdUsuarioModificador());
			Cliente cliente = cdao.buscar(request.getIdCliente());
			
			cdao.eliminar(request.getIdCliente());
			auditDao.agregarAuditoria(modificador, cliente, AuditoriaServiceImpl.MODIFICAR_CLIENTE);
			response.setResultadoEjecucion(true);
			response.setMensaje(EXITO_ELIMINAR_CLIENTE);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_ELIMINAR_CLIENTE);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

}
