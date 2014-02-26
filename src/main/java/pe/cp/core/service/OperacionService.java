package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Operacion;
import pe.cp.core.service.messages.ActualizarOperacionRequest;
import pe.cp.core.service.messages.AgregarIncidenciaRequest;
import pe.cp.core.service.messages.AgregarIncidenciaResponse;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.AprobarOperacionRequest;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ActualizarIncidenciaRequest;
import pe.cp.core.service.messages.EliminarIncidenciaRequest;
import pe.cp.core.service.messages.EnviarAprobarOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerIncidenciaRequest;
import pe.cp.core.service.messages.ObtenerIncidenciaResponse;
import pe.cp.core.service.messages.ObtenerIncidenciasRequest;
import pe.cp.core.service.messages.ObtenerIncidenciasResponse;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaRequest;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.messages.ObtenerTipoIncidenciasResponse;
import pe.cp.core.service.messages.Response;

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
	ObtenerOperacionPorTarifaResponse obtenerOperacionesPorTarifa(ObtenerOperacionPorTarifaRequest request);
	ObtenerIncidenciasResponse obtenerIncidencias(ObtenerIncidenciasRequest request);
	AgregarIncidenciaResponse agregarIncidencia(AgregarIncidenciaRequest request);
	ObtenerTipoIncidenciasResponse obtenerTipoIncidencias();
	Response eliminarIncidencia(EliminarIncidenciaRequest request);
	Response actualizarIncidencia(ActualizarIncidenciaRequest request);
	ObtenerIncidenciaResponse obtenerIncidencia(ObtenerIncidenciaRequest request);
	Response actualizarOperacion(ActualizarOperacionRequest request);
	Response enviarAprobarOperacion(EnviarAprobarOperacionRequest request);
	Response aprobarOperacion(AprobarOperacionRequest request);
	String getConstanteEstadoEnProceso();
	String getConstanteEstadoPorAprobar();
	String getConstanteEstadoAprobada();
}
