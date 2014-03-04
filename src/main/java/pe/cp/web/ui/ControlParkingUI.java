package pe.cp.web.ui;



import javax.servlet.annotation.WebServlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.handler.impl.LoginController;
import pe.cp.web.ui.view.IAuditoriaView;
import pe.cp.web.ui.view.IBuscarClientesView;
import pe.cp.web.ui.view.IBuscarOperacionesView;
import pe.cp.web.ui.view.IBuscarUsuarioView;
import pe.cp.web.ui.view.IConfigView;
import pe.cp.web.ui.view.IConfiguracionUsuarioView;
import pe.cp.web.ui.view.IEditarClienteView;
import pe.cp.web.ui.view.IEditarOperacionView;
import pe.cp.web.ui.view.IEditarUsuarioView;
import pe.cp.web.ui.view.IIncidenciaView;
import pe.cp.web.ui.view.ILoginView;
import pe.cp.web.ui.view.IMainView;
import pe.cp.web.ui.view.INuevaOperacionView;
import pe.cp.web.ui.view.INuevoClienteView;
import pe.cp.web.ui.view.INuevoUsuarioView;
import pe.cp.web.ui.view.IRecuperarContrasenaView;
import pe.cp.web.ui.view.IReportesConsolidadoView;
import pe.cp.web.ui.view.IReportesIncidenciasView;
import pe.cp.web.ui.view.IReportesIngresosSalidasView;
import pe.cp.web.ui.view.IReportesRecaudacionView;
import pe.cp.web.ui.view.IReportesView;
import pe.cp.web.ui.view.IReportesVisitasView;
import pe.cp.web.ui.view.ITarifaView;
import pe.cp.web.ui.view.IUnidadOperativaView;
import pe.cp.web.ui.view.impl.AuditoriaViewImpl;
import pe.cp.web.ui.view.impl.BuscarClienteViewImpl;
import pe.cp.web.ui.view.impl.BuscarOperacionesViewImpl;
import pe.cp.web.ui.view.impl.BuscarUsuarioViewImpl;
import pe.cp.web.ui.view.impl.ConfigViewImpl;
import pe.cp.web.ui.view.impl.ConfiguracionUsuarioViewImpl;
import pe.cp.web.ui.view.impl.EditarClienteViewImpl;
import pe.cp.web.ui.view.impl.EditarOperacionViewImpl;
import pe.cp.web.ui.view.impl.EditarUsuarioViewImpl;
import pe.cp.web.ui.view.impl.IncidenciaViewImpl;
import pe.cp.web.ui.view.impl.LoginViewImpl;
import pe.cp.web.ui.view.impl.MainViewImpl;
import pe.cp.web.ui.view.impl.NuevaOperacionViewImpl;
import pe.cp.web.ui.view.impl.NuevaUnidadOperativaViewImpl;
import pe.cp.web.ui.view.impl.NuevoClienteViewImpl;
import pe.cp.web.ui.view.impl.NuevoUsuarioViewImpl;
import pe.cp.web.ui.view.impl.RecuperarContrasenaViewImpl;
import pe.cp.web.ui.view.impl.ReporteConsolidadoViewImpl;
import pe.cp.web.ui.view.impl.ReporteIngresosSalidasViewImpl;
import pe.cp.web.ui.view.impl.ReporteRecaudacionViewImpl;
import pe.cp.web.ui.view.impl.ReporteVisitasViewImpl;
import pe.cp.web.ui.view.impl.ReportesIncidenciasViewImpl;
import pe.cp.web.ui.view.impl.ReportesViewImpl;
import pe.cp.web.ui.view.impl.TarifaViewImpl;

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
	public static final String REPORTE_VISITAS = "repvisitas";
	public static final String REPORTE_INGRESOS_SALIDAS = "repoingsal";
	public static final String REPORTE_RECAUDACION = "reprecaud";
	public static final String REPORTE_CONSOLIDADO = "repcons";
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
		Subject currentUser = SecurityUtils.getSubject();
		Navigator navigator = new Navigator(this, this);
		configurarNavigator(navigator);
		
		if (!currentUser.isAuthenticated()) {
			NavegacionUtil.irLogin();
		} else {
			NavegacionUtil.irMain();
		}
    }
	
	private void configurarNavigator(Navigator navigator) {
		ILoginView loginView = new LoginViewImpl();
		IMainView mainView = new MainViewImpl();
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
		IReportesView reportesView = new ReportesViewImpl();
		IReportesIncidenciasView reportesIncidenciasView = new ReportesIncidenciasViewImpl();
		IReportesConsolidadoView reporteConsolidadView = new ReporteConsolidadoViewImpl();
		IReportesIngresosSalidasView reporteIngresosSalidasView = new ReporteIngresosSalidasViewImpl();
		IReportesRecaudacionView reporteRecaudacionView = new ReporteRecaudacionViewImpl();
		IReportesVisitasView reporteVisitas = new ReporteVisitasViewImpl();
		
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
		navigator.addView(REPORTE_CONSOLIDADO, reporteConsolidadView);
		navigator.addView(REPORTE_INGRESOS_SALIDAS, reporteIngresosSalidasView);
		navigator.addView(REPORTE_RECAUDACION, reporteRecaudacionView);
		navigator.addView(REPORTE_VISITAS, reporteVisitas);
		navigator.addView("", loginView);
		
		setNavigator(navigator);
	}
}