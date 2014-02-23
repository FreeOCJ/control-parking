package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Operacion;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;

public interface OperacionService {
	int agregar(Operacion op);
	void editar(Operacion op);
	void eliminar(int idOperacion);
	int agregarIncidencia(int idOperacion, int idIncidencia);
	int removerIncidencia(int idOperacion, int idIncidencia);
	
	boolean validarNuevaOperacion(int idUnidad, Date fechaOp);
	List<Operacion> buscar(String nombreUnidadOp, Date fechaOp, String estadoOp);
	BuscarOperacionResponse buscar(BuscarOperacionesRequest request);
	AgregarOperacionResponse agregar(AgregarOperacionRequest request);
	ObtenerOperacionResponse obtener(ObtenerOperacionRequest request);
	ObtenerDetalleOperacionResponse obtenerDetalles(ObtenerDetalleOperacionRequest request);
}
