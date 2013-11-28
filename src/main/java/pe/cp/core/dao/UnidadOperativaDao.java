package pe.cp.core.dao;

import pe.cp.core.domain.UnidadOperativa;

public interface UnidadOperativaDao {
	int agregar(UnidadOperativa unidadOp);
	void actualizar(UnidadOperativa unidadOp);
	void eliminar(int idUnidadOp);
	UnidadOperativa buscar(int idUnidadOp);
}
