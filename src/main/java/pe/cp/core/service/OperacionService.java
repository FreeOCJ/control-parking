package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Operacion;

public interface OperacionService {
	int agregar(Operacion op);
	void editar(Operacion op);
	void eliminar(int idOperacion);
	int agregarIncidencia(int idOperacion, int idIncidencia);
	int removerIncidencia(int idOperacion, int idIncidencia);
	List<Operacion> buscar(String nombreUnidadOp, Date fechaOp, String estadoOp);
}
