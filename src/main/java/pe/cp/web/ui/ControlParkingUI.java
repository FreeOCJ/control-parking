package pe.cp.web.ui;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.auditoria.IAuditoriaView;
import pe.cp.web.ui.view.auditoria.AuditoriaView;
import pe.cp.web.ui.view.configuracion.IConfigView;
import pe.cp.web.ui.view.configuracion.ConfigViewImpl;
import pe.cp.web.ui.view.login.ILoginView;
import pe.cp.web.ui.view.login.LoginController;
import pe.cp.web.ui.view.login.LoginViewImpl;
import pe.cp.web.ui.view.main.IMain;
import pe.cp.web.ui.view.main.MainViewImpl;
import pe.cp.web.ui.view.operaciones.IOperacionesView;
import pe.cp.web.ui.view.operaciones.OperacionesView;
import pe.cp.web.ui.view.reportes.IReportesView;
import pe.cp.web.ui.view.reportes.ReportesView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")

public class ControlParkingUI extends UI {

	@Autowired
    private transient ApplicationContext applicationContext;	
	
	public static final String OPERACIONES = "operaciones";
	public static final String REPORTES = "reportes";
	public static final String CONFIGURACION = "configuracion";
	public static final String AUDITORIA = "auditoria";
	public static final String MAIN = "main";
	/*@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ControlParkingUI.class)
	public static class Servlet extends VaadinServlet {
	}*/
	
	@Override
    protected void init(VaadinRequest request) {		
		Navigator navigator = new Navigator(this, this);
		
		ILoginView loginView = new LoginViewImpl();
		LoginController loginController = new LoginController(loginView);
		loginView.setHandler(loginController);
		loginView.init();
		navigator.addView("", loginView);
		
		//Create navigation
		IMain mainView = new MainViewImpl();
		IReportesView reportesView = new ReportesView();
		IConfigView configuracionView = new ConfigViewImpl();
		IAuditoriaView auditoriaView = new AuditoriaView();
		
		navigator.addView(OPERACIONES, mainView);
		navigator.addView(REPORTES, reportesView);
		navigator.addView(CONFIGURACION, configuracionView);
		navigator.addView(AUDITORIA, auditoriaView);
		setNavigator(navigator);
		navigator.navigateTo("");
    }
}