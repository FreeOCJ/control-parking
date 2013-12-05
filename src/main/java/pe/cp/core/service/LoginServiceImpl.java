package pe.cp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Override
	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		UsuarioFilter filtro = new UsuarioFilter();
		filtro.setLogin(request.getLoginName());
		
		Usuario usuario = usuarioDao.buscarPorLogin(request.getLoginName());
		if (usuario != null){
			if (usuario.getPassword().equals(request.getPassword())){
				response.setResultadoEjecucion(true);
				response.setAutorizado(true);
				response.setUsuario(usuario);			
			}else{
				response.setResultadoEjecucion(false);
				response.setAutorizado(false);	
				response.setMensaje("El password ingresado es incorrecto");
			}
		}else{
			response.setResultadoEjecucion(false);
			response.setAutorizado(false);	
			response.setMensaje("El usuario ingresado no existe");
		}		
		
		return response;
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
