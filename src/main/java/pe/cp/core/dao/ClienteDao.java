package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.filters.ClienteFilter;

public interface ClienteDao {
	int agregar(Cliente cliente);
	void actualizar(Cliente cliente);
	void eliminar(int idCliente);
	List<Cliente> buscar(String nombreComercial);
	List<Cliente> buscarOr(ClienteFilter filtro);
	Cliente buscar(int idCliente);
}
