package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Incidencia;
import pe.cp.core.domain.TipoIncidencia;

public interface IncidenciaDao {
	int agregar(Incidencia incidencia);
	void actualizar(Incidencia incidencia);
	void eliminar(int idIncidencia);
	List<Incidencia> obtenerIncidencias(int idOperacion);
	List<TipoIncidencia> obtenerTipos();
	Incidencia buscar(int idIncidencia);
}
