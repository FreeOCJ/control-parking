package pe.cp.core.service;

import java.util.Random;
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
	
	private static final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM     = "0123456789";
    private static final String SPL_CHARS   = "!@#$%^&*_=+-/";
	
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
	public String generarContrasenaTemporal() {
		String contrasena = generatePswd(5, 9, 4, 2, 1).toString();
		return contrasena;
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
			String contrasena = generatePswd(5, 9, 4, 2, 1).toString();
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

	
	private char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha,
            int noOfDigits, int noOfSplChars) {
        if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
            throw new IllegalArgumentException
            ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for(int i = 0; i < len; i++) {
            if(pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }
 
    private int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }
}
