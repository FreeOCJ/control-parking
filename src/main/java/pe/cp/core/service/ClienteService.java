package pe.cp.core.service;

import pe.cp.core.domain.Cliente;
import pe.cp.core.service.messages.ActualizarClienteRequest;
import pe.cp.core.service.messages.ActualizarClienteResponse;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.core.service.messages.InsertarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteResponse;

public interface ClienteService {
	InsertarClienteResponse agregar(InsertarClienteRequest request);
	ActualizarClienteResponse actualizar(ActualizarClienteRequest request);
	void eliminar(int idCliente);
	BuscarClienteResponse buscar(BuscarClienteRequest request);
	boolean validarNuevoCliente(Cliente cliente);
	boolean validarModificarCliente(Cliente cliente, Cliente clienteMod);	
}
