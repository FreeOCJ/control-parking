package pe.cp.web.ui.handler.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.core.domain.Rol;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.handler.IConfigViewHandler;
import pe.cp.web.ui.view.IConfigView;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
@Scope("prototype")
@Theme("controlparking")
public class ConfigController implements IConfigViewHandler {

	private IConfigView view;
	
	public ConfigController(IConfigView view){
		this.view = view;
	}
	
	@Override
	public void irConfiguracionUsuarios() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);	
	}

	@Override
	public void irConfiguracionClientes() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARCLIENTES);	
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			UI.getCurrent().getNavigator().navigateTo("");
		}else{
			if (!currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);
			}
		}
	}

}
