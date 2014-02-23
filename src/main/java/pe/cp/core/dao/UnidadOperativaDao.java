package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.UnidadOperativa;

public interface UnidadOperativaDao {
	int agregar(UnidadOperativa unidadOp);
	void actualizar(UnidadOperativa unidadOp);
	void eliminar(int idUnidadOp);
	UnidadOperativa buscar(int idUnidadOp);
	List<UnidadOperativa> obtenerPorCliente(int idCliente);
	List<UnidadOperativa> obtenerParaProcesar(String login, boolean esOperador, boolean esAprobador);
	void retirarUsuariosUnidadOp(int idUnidadOp, String rol);
	void agregarUsuariosUnidadOp(int idUnidadOp, int[] idsUsuarios, String rol);
}
