package pe.cp.core.dao;

import pe.cp.core.domain.TipoIncidencia;

public interface TipoIncidenciaDao {
	int agregar(TipoIncidencia tipoIncidencia);
	void modificar(TipoIncidencia tipoIncidencia);
	void eliminar(int idTipoIncidencia);
}
