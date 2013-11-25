package pe.cp.core.service;

import java.util.List;

import pe.cp.core.domain.Cliente;

public interface ClienteService {
	int agregar(Cliente cliente);
	void actualizar(Cliente cliente);
	void eliminar(int idCliente);
	List<Cliente> buscar(String nombreComercial);
	int agregarUsuario(int idCliente, int idUsuario);
	int removerUsuario(int idCliente, int idUsuario);
	int agregarUnidadOperativa(int idCliente, int idUnidadOperativa);
	int removerUnidadOperativa(int idCliente, int idUnidadOperativa);
}
