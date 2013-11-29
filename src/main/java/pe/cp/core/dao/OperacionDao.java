package pe.cp.core.dao;

import pe.cp.core.domain.Operacion;

public interface OperacionDao {
	int agregar(Operacion op);
	void modificar(Operacion op);
	void eliminar(int idOperacion);
	Operacion buscar(int idOperacion);
}
