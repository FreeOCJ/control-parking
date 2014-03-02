package pe.cp.core.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.vaadin.example.shiro.SecureView;






import com.vaadin.server.VaadinService;

import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;
import pe.cp.core.service.messages.RecuperarContrasenaRequest;
import pe.cp.core.service.messages.Response;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Override
	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		response.setAutorizado(false);
		response.setResultadoEjecucion(false);
		/*UsuarioFilter filtro = new UsuarioFilter();
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
		}		*/
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				request.getLoginName(), request.getPassword());
		try {
			currentUser.login(token);
			Usuario usuario = usuarioDao.buscarPorLogin(request.getLoginName());
			currentUser.getSession().setAttribute("nombre_completo", String.format("%s\n%s", usuario.getNombres(), usuario.getApellidos()));
			currentUser.getSession().setAttribute("login", usuario.getLogin());
			currentUser.getSession().setAttribute("id_usuario", usuario.getId());
			response.setAutorizado(true);
			response.setResultadoEjecucion(true);
			
		}catch(UnknownAccountException ex) {
			Logger.getAnonymousLogger().log(Level.INFO, ex.getMessage());
			response.setMensaje("No existe la cuenta ingresada");
		}catch (IncorrectCredentialsException ex) {
			Logger.getAnonymousLogger().log(Level.INFO, ex.getMessage());
			response.setMensaje("Los credenciales ingresados son incorrectos");
		}catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
			response.setMensaje("Ocurrio un error al realizar la transaccion");
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

	@Override
	public Response recuperaContrasena(RecuperarContrasenaRequest request) {
		Response response = new Response();
		
		try {
			//IMPLEMENTAR ESTO OMAR
			
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error !!");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

}
