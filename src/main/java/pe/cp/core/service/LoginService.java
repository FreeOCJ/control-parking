package pe.cp.core.service;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;

public interface LoginService {
	LoginResponse login(LoginRequest request);
	void actualizarContrasena(Usuario usuario, String nuevaContrasena);
	void recuperarContrasena(String login, String contrasena);
	String generarContrasenaTemporal(String login);
	void enviarContrasena(String email, String nombreUsuario, String contrasena);	
}
