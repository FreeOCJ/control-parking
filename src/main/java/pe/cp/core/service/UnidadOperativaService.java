package pe.cp.core.service;

import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;

public interface UnidadOperativaService {
	AgregarUnidadOperativaResponse agregar(AgregarUnidadOperativaRequest request);
	ObtenerUnidadOperativaResponse obtenerPorId(ObtenerUnidadOperativaRequest request);
	ObtenerUnidadpOpPorClienteResponse obtenerUnidadesPorCliente(ObtenerUnidadOpPorClienteRequest request);
	void actualizar(UnidadOperativa unidadOp);
	void eliminar(int idUnidadOperativa);
	int agregarTarifa(int idUnidadOp, int idTarifa);
	int removerTarifa(int idUnidadOperativa, int idTarifa);
	int agregarOperador(int idUnidadOp, int idUsuario);
	int removerOperador(int idUnidadOp, int idUsuario);
	int agregarAprobador(int idUnidadOp, int idUsuario);
	int removerAprobador(int idUnidadOp, int idUsuario);
	int agregarUsuarioCliente(int idUnidadOp, int idUsuario);
	int removerUsuarioCliente(int idUnidadOp, int idUsuario);
}
