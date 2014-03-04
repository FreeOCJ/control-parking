package pe.cp.web.ui.handler.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.vaadin.ui.UI;

import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.ISideBarHandler;
import pe.cp.web.ui.view.ISideBarView;

public class SideBarController implements ISideBarHandler {
	
	ISideBarView view;
	
	public SideBarController(ISideBarView view){
		this.view = view;
		cargarDatos();
	}
	
	@Override
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isAuthenticated()) {
			currentUser.logout();
			UI.getCurrent().getSession().close();
			UI.getCurrent().getPage().setLocation("/control-parking");
		}
		//NavegacionUtil.irLogin();
	}

	@Override
	public void cargarDatos() {
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser.isAuthenticated()){
			Logger.getAnonymousLogger().log(Level.INFO, currentUser.getSession().getAttribute("nombre_completo").toString());
			view.getLabelUsuario().setCaption(currentUser.getSession().getAttribute("nombre_completo").toString());	
		} else {
			Logger.getAnonymousLogger().log(Level.INFO, "usuario no autenticado");
		}
	}

	@Override
	public void irConfiguracion() {
		NavegacionUtil.irConfiguracionUsuario();
	}

	
}
