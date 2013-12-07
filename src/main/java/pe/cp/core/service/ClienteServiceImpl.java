package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.filters.ClienteFilter;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.ActualizarClienteRequest;
import pe.cp.core.service.messages.ActualizarClienteResponse;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.core.service.messages.InsertarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteResponse;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteDao cdao;
	
	@Override
	public InsertarClienteResponse agregar(InsertarClienteRequest request) {
		InsertarClienteResponse response = new InsertarClienteResponse();
		
		Cliente cliente = new Cliente();
		cliente.setNombreComercial(request.getNombreComercial());
		cliente.setRazonSocial(request.getRazonSocial());
		cliente.setRuc(request.getRuc());
		
		if (validarNuevoCliente(cliente)){
			Integer idCliente = cdao.agregar(cliente);
			if (idCliente != null){
				response.setMensaje("Se insertó al usuario exitosamente");
				response.setResultadoEjecucion(true);
			}						
		}else{
			response.setMensaje("Error de validación");
			response.setResultadoEjecucion(false);
		}
				
		return response;
	}

	@Override
	public ActualizarClienteResponse actualizar(ActualizarClienteRequest request) {
		ActualizarClienteResponse response = new ActualizarClienteResponse();
				
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
			
			response.setResultadoEjecucion(true);
			response.setMensaje("Se actulizaron los datos del cliente exitosamente");
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al validar los datos del cliente");
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
		return false;
	}

	@Override
	public boolean validarModificarCliente(Cliente cliente, Cliente clienteMod) {
		// TODO Auto-generated method stub
		return false;
	}

}
