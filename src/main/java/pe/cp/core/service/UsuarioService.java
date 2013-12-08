package pe.cp.core.service;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaResponse;

public interface UsuarioService {
	ActualizarUsuarioResponse actualizar(ActualizarUsuarioRequest request);
	InsertarUsuarioResponse agregar(InsertarUsuarioRequest request);
	void eliminar(int id);
	BuscarUsuarioResponse buscarOr(BuscarUsuarioRequest request);
	ObtenerUsuarioResponse buscar(ObtenerUsuarioRequest request);
	Usuario buscarPorLogin(String login);
	ObtenerUsuariosSistemaResponse obtenerUsuariosSistema();
	boolean validarNuevoUsuario(Usuario usuario);
	boolean validarUsuarioModificado(Usuario usuario, Usuario usuarioModificado);
	String generarContrasenaTemporal();
}
