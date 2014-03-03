package pe.cp.web.ui.handler.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.ISideBarHandler;
import pe.cp.web.ui.view.ISideBarView;

import com.vaadin.ui.UI;

public class SideBarController implements ISideBarHandler {
	
	ISideBarView view;
	
	public SideBarController(ISideBarView view){
		this.view = view;
	}
	
	@Override
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		UI.getCurrent().getNavigator().navigateTo("");

	}

	@Override
	public void cargarDatos() {
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser.isAuthenticated()){
			Logger.getAnonymousLogger().log(Level.INFO, currentUser.getSession().getAttribute("nombre_completo").toString());
			view.getLabelUsuario().setCaption(currentUser.getSession().getAttribute("nombre_completo").toString());	
		}
	}

	@Override
	public void irConfiguracion() {
		NavegacionUtil.irConfiguracionUsuario();
	}

}
