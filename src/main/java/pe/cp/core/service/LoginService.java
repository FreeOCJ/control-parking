package pe.cp.core.service;

import pe.cp.core.domain.Usuario;

public interface LoginService {
	boolean login(Usuario usuario);
	void actualizarContrasena(Usuario usuario, String nuevaContrasena);
	void recuperarContrasena(String login, String contrasena);
	String generarContrasenaTemporal(String login);
	void enviarContrasena(String email, String nombreUsuario, String contrasena);	
}
