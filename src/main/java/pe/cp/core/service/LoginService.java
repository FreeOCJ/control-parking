package pe.cp.core.service;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;
import pe.cp.core.service.messages.RecuperarContrasenaRequest;
import pe.cp.core.service.messages.Response;

public interface LoginService {
	LoginResponse login(LoginRequest request);
	void actualizarContrasena(Usuario usuario, String nuevaContrasena);
	void recuperarContrasena(String login, String contrasena);
	String generarContrasenaTemporal(String login);
	void enviarContrasena(String email, String nombreUsuario, String contrasena);	
	Response recuperaContrasena(RecuperarContrasenaRequest request);
}
