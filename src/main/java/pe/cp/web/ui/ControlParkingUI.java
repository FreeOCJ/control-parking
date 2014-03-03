package pe.cp.web.ui;



import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.auditoria.IAuditoriaView;
import pe.cp.web.ui.view.auditoria.AuditoriaViewImpl;
import pe.cp.web.ui.view.configuracion.IConfigView;
import pe.cp.web.ui.view.configuracion.ConfigViewImpl;
import pe.cp.web.ui.view.configuracion.cliente.BuscarClienteViewImpl;
import pe.cp.web.ui.view.configuracion.cliente.EditarClienteViewImpl;
import pe.cp.web.ui.view.configuracion.cliente.IBuscarClientesView;
import pe.cp.web.ui.view.configuracion.cliente.IEditarClienteView;
import pe.cp.web.ui.view.configuracion.cliente.INuevoClienteView;
import pe.cp.web.ui.view.configuracion.cliente.NuevoClienteViewImpl;
import pe.cp.web.ui.view.configuracion.unidadoperativa.IUnidadOperativaView;
import pe.cp.web.ui.view.configuracion.unidadoperativa.ITarifaView;
import pe.cp.web.ui.view.configuracion.unidadoperativa.NuevaUnidadOperativaViewImpl;
import pe.cp.web.ui.view.configuracion.unidadoperativa.TarifaViewImpl;
import pe.cp.web.ui.view.configuracion.usuario.BuscarUsuarioViewImpl;
import pe.cp.web.ui.view.configuracion.usuario.ConfiguracionUsuarioViewImpl;
import pe.cp.web.ui.view.configuracion.usuario.EditarUsuarioViewImpl;
import pe.cp.web.ui.view.configuracion.usuario.IBuscarUsuarioView;
import pe.cp.web.ui.view.configuracion.usuario.IConfiguracionUsuarioView;
import pe.cp.web.ui.view.configuracion.usuario.IEditarUsuarioView;
import pe.cp.web.ui.view.configuracion.usuario.INuevoUsuarioView;
import pe.cp.web.ui.view.configuracion.usuario.NuevoUsuarioViewImpl;
import pe.cp.web.ui.view.login.ILoginView;
import pe.cp.web.ui.view.login.IRecuperarContrasenaView;
import pe.cp.web.ui.view.login.LoginController;
import pe.cp.web.ui.view.login.LoginViewImpl;
import pe.cp.web.ui.view.login.RecuperarContrasenaViewImpl;
import pe.cp.web.ui.view.main.IMain;
import pe.cp.web.ui.view.main.MainViewImpl;
import pe.cp.web.ui.view.operaciones.EditarOperacionViewImpl;
import pe.cp.web.ui.view.operaciones.IBuscarOperacionesView;
import pe.cp.web.ui.view.operaciones.BuscarOperacionesViewImpl;
import pe.cp.web.ui.view.operaciones.IEditarOperacionView;
import pe.cp.web.ui.view.operaciones.IIncidenciaView;
import pe.cp.web.ui.view.operaciones.INuevaOperacionView;
import pe.cp.web.ui.view.operaciones.IncidenciaViewImpl;
import pe.cp.web.ui.view.operaciones.NuevaOperacionViewImpl;
import pe.cp.web.ui.view.reportes.IReportesView;
import pe.cp.web.ui.view.reportes.ReportesView;
import pe.cp.web.ui.view.reportes.incidencias.IReportesIncidenciasView;
import pe.cp.web.ui.view.reportes.incidencias.ReportesIncidenciasViewImpl;

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
	public static final String REPORTES_INCIDENCIAS = "reportes_incidencias";
	public static final String CONFIGURACION = "configuracion";
	public static final String AUDITORIA = "auditoria";
	public static final String MAIN = "main";
	public static final String BUSCARUSUARIOS = "buscar_usuarios";
	public static final String BUSCARCLIENTES = "buscar_clientes";
	public static final String NUEVOUSUARIO = "nuevo_usuario";
	public static final String EDITARUSUARIO = "editar_usuario";
	public static final String NUEVOCLIENTE = "nuevo_cliente";
	public static final String EDITARCLIENTE = "editar_cliente";
	public static final String UNIDADOPERATIVA = "unidad_operativa";
	public static final String TARIFA = "tarifa";
	public static final String NUEVA_OPERACION = "nueva_operacion";
	public static final String EDITAR_OPERACION = "editar_operacion";
	public static final String INCIDENCIA = "incidencia";
	public static final String RECUPERAR_CONTRASENA = "recuperar_contrasena";
	public static final String CONFIGURAR_USUARIO = "conf";
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ControlParkingUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
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
		IReportesIncidenciasView reportesIncidenciasView = new ReportesIncidenciasViewImpl();
		IBuscarOperacionesView operacionesView = new BuscarOperacionesViewImpl();
		IConfigView configuracionView = new ConfigViewImpl();
		IAuditoriaView auditoriaView = new AuditoriaViewImpl();
		IBuscarUsuarioView buscarUsuarioView = new BuscarUsuarioViewImpl();
		INuevoUsuarioView nuevoUsuarioView = new NuevoUsuarioViewImpl();
		IEditarUsuarioView editarUsuarioView = new EditarUsuarioViewImpl();
		IBuscarClientesView buscarClientesView = new BuscarClienteViewImpl();
		INuevoClienteView nuevoClienteView = new NuevoClienteViewImpl();
		IEditarClienteView editarClienteView = new EditarClienteViewImpl();
		IUnidadOperativaView unidadOperativaView = new NuevaUnidadOperativaViewImpl(); 
		ITarifaView tarifaView = new TarifaViewImpl();
		INuevaOperacionView nuevaOperacionView = new NuevaOperacionViewImpl();
		IEditarOperacionView editarOperacionView = new EditarOperacionViewImpl();
		IIncidenciaView incidenciaView = new IncidenciaViewImpl();
		IRecuperarContrasenaView recuperarPwdView = new RecuperarContrasenaViewImpl();
		IConfiguracionUsuarioView configurarUsuarioView = new ConfiguracionUsuarioViewImpl();
		
		navigator.addView(MAIN, mainView);
		navigator.addView(OPERACIONES, operacionesView);
		navigator.addView(REPORTES, reportesView);
		navigator.addView(REPORTES_INCIDENCIAS, reportesIncidenciasView);
		navigator.addView(CONFIGURACION, configuracionView);
		navigator.addView(AUDITORIA, auditoriaView);
		navigator.addView(BUSCARUSUARIOS, buscarUsuarioView);
		navigator.addView(BUSCARCLIENTES, buscarClientesView);
		navigator.addView(NUEVOUSUARIO, nuevoUsuarioView);
		navigator.addView(EDITARUSUARIO, editarUsuarioView);
		navigator.addView(NUEVOCLIENTE, nuevoClienteView);
		navigator.addView(EDITARCLIENTE, editarClienteView);
		navigator.addView(UNIDADOPERATIVA, unidadOperativaView);
		navigator.addView(TARIFA, tarifaView);
		navigator.addView(NUEVA_OPERACION, nuevaOperacionView);
		navigator.addView(EDITAR_OPERACION, editarOperacionView);
		navigator.addView(INCIDENCIA, incidenciaView);
		navigator.addView(RECUPERAR_CONTRASENA, recuperarPwdView);
		navigator.addView(CONFIGURAR_USUARIO, configurarUsuarioView);
		
		setNavigator(navigator);
		navigator.navigateTo("");
    }
}