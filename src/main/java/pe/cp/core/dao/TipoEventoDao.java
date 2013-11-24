package pe.cp.core.dao;

import pe.cp.core.domain.TipoEvento;

public interface TipoEventoDao {
	int agregar(TipoEvento tipoevento);
	void actualizar(TipoEvento tipoevento);
	void eliminar(int idTipoEvento);
}
