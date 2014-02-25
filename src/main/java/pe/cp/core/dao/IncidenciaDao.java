package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Incidencia;

public interface IncidenciaDao {
	int agregar(Incidencia incidencia);
	void actualizar(Incidencia incidencia);
	void eliminar(int idIncidencia);
	List<Incidencia> obtenerIncidencias(int idOperacion);
}
