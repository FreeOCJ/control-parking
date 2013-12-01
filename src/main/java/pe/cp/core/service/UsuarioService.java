package pe.cp.core.service;

import java.util.List;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;

public interface UsuarioService {
	ActualizarUsuarioResponse actualizar(ActualizarUsuarioRequest request);
	InsertarUsuarioResponse agregar(InsertarUsuarioRequest request);
	void eliminar(int id);
	List<Usuario> buscar(String nombre);
	Usuario buscar(int idUsuario);
	Usuario buscarPorLogin(String login);
	boolean validarNuevoUsuario(Usuario usuario);
	boolean validarUsuarioModificado(Usuario usuario, Usuario usuarioModificado);
	String generarContrasenaTemporal();
}
