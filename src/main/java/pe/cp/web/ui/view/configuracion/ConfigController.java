package pe.cp.web.ui.view.configuracion;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.core.domain.Rol;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.UI;

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
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);
			}
		}
	}

}
