package pe.cp.web.ui.view.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.LoginService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.messages.RecuperarContrasenaRequest;
import pe.cp.core.service.messages.Response;
import pe.cp.web.ui.NavegacionUtil;
import pe.cp.core.domain.Usuario;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;


public class RecuperarContrasenaController implements
		IRecuperarContrasenaHandler {

	private ApplicationContext ac;
	private IRecuperarContrasenaView view;
	private Notification notification;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Usuario usuario;
	
	
	private final String ERR_FORMATO_EMAIL = "Debe ingresar un email válido";
	
	public RecuperarContrasenaController(IRecuperarContrasenaView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		loginService = ac.getBean(LoginService.class);
		this.view = view;
	}
	
	@Override
	public void enviarContrasena() {
		String login = view.getLogin().getValue();
		
		
		if (login != null && !login.isEmpty()) {
			
			usuarioService = ac.getBean(UsuarioService.class);
			usuario = usuarioService.buscarPorLogin(login);
			
			if (usuario == null) {
				
				notification = new Notification("No existe el usuario ingresado.");
				notification.setPosition(Position.TOP_CENTER);
				notification.show(Page.getCurrent());
				return;
			} 
			
			
			Response response = loginService.recuperaContrasena(new RecuperarContrasenaRequest(login));
			
			notification = new Notification(response.getMensaje());
			if (response.isResultadoEjecucion()) {
				
				notification = new Notification("Se envió la contraseña al correo configurado.");
				notification.setPosition(Position.TOP_CENTER);
				notification.show(Page.getCurrent());
				
			    NavegacionUtil.irLogin();	
			} else {
				notification.setPosition(Position.TOP_CENTER);
				notification.show(Page.getCurrent());
				
			}
		} else {
			notification = new Notification("Usuario no válido.");
			notification.setPosition(Position.TOP_CENTER);
			notification.show(Page.getCurrent());
		}
	}

	@SuppressWarnings("serial")
	@Override
	public void cargar() {
		view.getBtnEnviar().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				enviarContrasena();
			}
		});
		
		view.getBtnCancelar().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NavegacionUtil.irLogin();
			}
		});
	}

}
