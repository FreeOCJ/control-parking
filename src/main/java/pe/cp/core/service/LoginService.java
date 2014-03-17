package pe.cp.core.service;

import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;
import pe.cp.core.service.messages.RecuperarContrasenaRequest;
import pe.cp.core.service.messages.Response;

public interface LoginService {
	LoginResponse login(LoginRequest request);
	String generarContrasenaTemporal();	
	Response recuperaContrasena(RecuperarContrasenaRequest request);
}
