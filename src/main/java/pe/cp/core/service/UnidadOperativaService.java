package pe.cp.core.service;

import pe.cp.core.domain.UnidadOperativa;

public interface UnidadOperativaService {
	int agregar(UnidadOperativa unidadOp);
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
