package pe.cp.core.service;

import pe.cp.core.service.messages.ActualizarUnidadOpRequest;
import pe.cp.core.service.messages.ActualizarUnidadOpResponse;
import pe.cp.core.service.messages.AgregarTarifaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.AgregarUsuarioUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerConsTarifasRequest;
import pe.cp.core.service.messages.ObtenerConsTarifasResponse;
import pe.cp.core.service.messages.ObtenerTarifasRequest;
import pe.cp.core.service.messages.ObtenerTarifasResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarResponse;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;
import pe.cp.core.service.messages.Response;

public interface UnidadOperativaService {
	AgregarUnidadOperativaResponse agregar(AgregarUnidadOperativaRequest request);
	ObtenerUnidadOperativaResponse obtenerPorId(ObtenerUnidadOperativaRequest request);
	ObtenerUnidadpOpPorClienteResponse obtenerUnidadesPorCliente(ObtenerUnidadOpPorClienteRequest request);
	ActualizarUnidadOpResponse actualizar(ActualizarUnidadOpRequest request);
	ObtenerTarifasResponse obtenerTarifas(ObtenerTarifasRequest request);
	Response agregarTarifa(AgregarTarifaRequest request);
	ObtenerConsTarifasResponse obtenerTarifas(ObtenerConsTarifasRequest request);
	ObtenerUnidadesOpProcesarResponse obtenerParaProcesar(ObtenerUnidadesOpProcesarRequest request);
	void eliminar(int idUnidadOperativa);
	int agregarTarifa(int idUnidadOp, int idTarifa);
	int removerTarifa(int idUnidadOperativa, int idTarifa);
	Response agregarUsuario(AgregarUsuarioUnidadOperativaRequest request);
	int agregarOperador(int idUnidadOp, int idUsuario);
	int removerOperador(int idUnidadOp, int idUsuario);
	int agregarAprobador(int idUnidadOp, int idUsuario);
	int removerAprobador(int idUnidadOp, int idUsuario);
	int agregarUsuarioCliente(int idUnidadOp, int idUsuario);
	int removerUsuarioCliente(int idUnidadOp, int idUsuario);
}
