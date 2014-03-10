package pe.cp.web.ui.handler.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.io.InputStream;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.reportes.util.ReportesConexion;
import pe.cp.reportes.util.ReportesPrint;
import pe.cp.web.ui.handler.IReportesIncidenciasViewHandler;
import pe.cp.web.ui.view.IReportesIncidenciasView;

@Component
@Scope("prototype")
public class ReportesIncidenciasController implements IReportesIncidenciasViewHandler{
	ApplicationContext ac;
	@SuppressWarnings("unused")
	private IReportesIncidenciasView view;
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	
	public ReportesIncidenciasController(IReportesIncidenciasView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}


	@Override
	public String obtenerNombreCliente(int idCliente) {
		String nombreCliente = "";
		ObtenerClienteRequest request= new ObtenerClienteRequest(idCliente);
		ObtenerClienteResponse response = clienteservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreCliente = response.getClienteView().getNombreComercial();
		}
		return nombreCliente;
	}


	@Override
	public String obtenerNombreUnidadOperativa(int idUnidadOp) {
		String nombreUnidad = "";
		ObtenerUnidadOperativaRequest request= new ObtenerUnidadOperativaRequest(idUnidadOp);
		ObtenerUnidadOperativaResponse response = unidadopservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreUnidad = response.getUnidadOpView().getNombre();
		}
		return nombreUnidad;
	}


	@Override
	public void generarReportePDFMensual(int idUnidadOp, Date fecha,String ruta) {
		ReportesPrint rprint = new ReportesPrint();
		ReportesConexion rpconexion = new ReportesConexion();
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		System.out.println("idUnidad: " + idUnidadOp);
		System.out.println("month: " + cal.get(Calendar.MONTH));
		System.out.println("YEAR: " + cal.get(Calendar.YEAR));
		System.out.println("ruta: " + ruta);
		params.put("P_ID_UNIDAD",idUnidadOp);
		params.put("P_MES", cal.get(Calendar.MONTH) + 1);
		params.put("P_ANHO", cal.get(Calendar.YEAR));		
		try {
			InputStream is = getClass().getResourceAsStream("/pe/cp/reportes/cpr_incidencias_mensual.jrxml");
			if (is != null){
				System.out.println("id Not Null");
			}else{
				System.out.println("id Null");
			}
			rpconexion.conectar();
			System.out.println("Reporte a PDF " + ruta + ".pdf");
			rprint.reporteaPDF(ruta + ".pdf",is,params, rpconexion.getConexion());
			rpconexion.desconectar();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (!rpconexion.getConexion().isClosed()){
					rpconexion.desconectar();
				}
			} catch (Exception e) {e.printStackTrace();}
		}		
		
	}
	
	@Override
	public String generarRuta(){
		String rutaArchivo = "";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyyhhmmss");
		String tempfolder = System.getProperty("catalina.home").replace("\\", "/") + "/temp/";
		Subject currentUser = SecurityUtils.getSubject();
		String fecha = sdf.format(Calendar.getInstance().getTime());
		if (currentUser != null && currentUser.isAuthenticated()){
			if (currentUser.getSession().getAttribute("id_usuario") != null){
				rutaArchivo = tempfolder 
						      + currentUser.getSession().getAttribute("id_usuario").toString()						      
						      + "-" + fecha;
			}
		}
		
		return rutaArchivo;
	}


	@Override
	public void generarReporteXLSMensual(int idUnidadOp, Date fecha, String ruta) {
		ReportesPrint rprint = new ReportesPrint();
		ReportesConexion rpconexion = new ReportesConexion();
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		params.put("P_ID_UNIDAD",idUnidadOp);
		params.put("P_MES", cal.get(Calendar.MONTH));
		params.put("P_ANHO", cal.get(Calendar.YEAR));		
		try {
			InputStream is = getClass().getResourceAsStream("/pe/cp/reportes/cpr_incidencias_mensual.jrxml");
			rpconexion.conectar();
			rprint.reporteaExcel(ruta + ".xls",is,params, rpconexion.getConexion());
			rpconexion.desconectar();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (!rpconexion.getConexion().isClosed()){
					rpconexion.desconectar();
				}
			} catch (Exception e) {e.printStackTrace();}
		}	
		
	}

	
}
