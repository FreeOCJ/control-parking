package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Cliente;

public interface ClienteDao {
	int agregar(Cliente cliente);
	void actualizar(Cliente cliente);
	void eliminar(int idCliente);
	List<Cliente> buscar(String nombreComercial);
	Cliente buscar(int idCliente);
}
