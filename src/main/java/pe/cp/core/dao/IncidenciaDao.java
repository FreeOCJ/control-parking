package pe.cp.core.dao;

import pe.cp.core.domain.Incidencia;

public interface IncidenciaDao {
	int agregar(Incidencia incidencia);
	void actualizar(Incidencia incidencia);
	void eliminar(int idIncidencia);
}
