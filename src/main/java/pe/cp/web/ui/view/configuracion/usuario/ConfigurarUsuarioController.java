package pe.cp.web.ui.view.configuracion.usuario;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;

import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.messages.ActualizarContrasenaRequest;
import pe.cp.core.service.messages.ActualizarEmailUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioResponse;
import pe.cp.core.service.messages.Response;

public class ConfigurarUsuarioController implements IConfigurarUsuarioHandler {

	private ApplicationContext ac;
	private IConfiguracionUsuarioView view;
	private Notification notification;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public ConfigurarUsuarioController(IConfiguracionUsuarioView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuarioService = ac.getBean(UsuarioService.class);
		this.view = view;
	}

	@SuppressWarnings("serial")
	@Override
	public void cargar() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		
        ObtenerUsuarioResponse response = usuarioService.buscar(new ObtenerUsuarioRequest(idUsuario));
        if (response.isResultadoEjecucion()) {
        	view.getTxtEmail().setValue(response.getUsuarioView().getEmail());
        } else {
        	notification = new Notification(response.getMensaje());
        	notification.show(Page.getCurrent());
        }
        
        view.getBtnGuardarContrasena().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				guardarContrasena();
			}
		});
        
        view.getBtnGuardarEmail().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				guardarEmail();
			}
		});
        
	}
	
	private void guardarEmail() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		String email = view.getTxtEmail().getValue();
		
		EmailValidator emailValidator = new EmailValidator("");
		view.getTxtEmail().addValidator(emailValidator);
		
		if (!view.getTxtEmail().isValid()) {
				Notification.show("El correo electrónico no tiene un formato valido;\n\t", Notification.Type.WARNING_MESSAGE);					
		} else {
			Response response = usuarioService.actualizarCorreo(new ActualizarEmailUsuarioRequest(idUsuario, email));
			notification = new Notification(response.getMensaje());
			notification.show(Page.getCurrent());
		}			
	}
	
	private void guardarContrasena() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		String nuevaContrasena = view.getTxtNuevaContrasena().getValue().toString();
		String validarContrasena = view.getTxtValidarContrasena().getValue().toString();
		String contrasenaAntigua = view.getTxtContrasena().getValue().toString();
		
		ActualizarContrasenaRequest request = new ActualizarContrasenaRequest(idUsuario, 
									nuevaContrasena, validarContrasena, contrasenaAntigua);
		Response response = usuarioService.actualizarContrasena(request);
		notification = new Notification(response.getMensaje());
		notification.show(Page.getCurrent());
	}

}
