package pe.cp.core.service;

import org.springframework.stereotype.Service;

import pe.cp.core.domain.Usuario;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public boolean login(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recuperarContrasena(String login, String contrasena) {
		// TODO Auto-generated method stub

	}

	@Override
	public String generarContrasenaTemporal(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enviarContrasena(String email, String nombreUsuario,
			String contrasena) {
		// TODO Auto-generated method stub

	}

}
