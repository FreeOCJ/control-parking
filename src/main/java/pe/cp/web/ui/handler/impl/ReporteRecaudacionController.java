package pe.cp.web.ui.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import pe.cp.core.web.util.PdfSource;
import pe.cp.reportes.util.ReportesConexion;
import pe.cp.reportes.util.ReportesPrint;
import pe.cp.web.ui.handler.IReporteRecaudacionHandler;
import pe.cp.web.ui.view.IReportesRecaudacionView;

public class ReporteRecaudacionController implements IReporteRecaudacionHandler {

	IReportesRecaudacionView view;
	Notification notification;
	PdfSource pdfSource;
	String rutaArchivo;
	int idCliente;
	int idUnidadOp;
	
	private final String PDF = "pdf";
	private final String XLS = "xls";
	private final String DIARIO = "Diario";
	private final String SEMANAL = "Semanal";
	private final String MENSUAL = "Mensual";
	private final String ANUAL = "Anual";
	
	private final String PARAM_ID_UNIDAD = "P_ID_UNIDAD";
	private final String PARAM_MES = "P_MES";
	private final String PARAM_ANHO = "P_ANHO";
	private final String PARAM_LOGO= "P_LOGO";
	private final String PARAM_FECHA_OPERACION = "P_FECHA_OPERACION";
	private final String PARAM_REPORT_LOCALE = "REPORT_LOCALE";
	private final String REP_RECAUDACION_DIARIOS = "/pe/cp/reportes/cpr_recaudacion_diario.jrxml";
	private final String REP_RECAUDACION_MENSUAL = "/pe/cp/reportes/cpr_recaudacion_mensual.jrxml";
	private final String REP_RECAUDACION_SEMANAL = "/pe/cp/reportes/cpr_recaudacion_semanal.jrxml";
	private final String REP_RECAUDACION_ANUAL = "/pe/cp/reportes/cpr_recaudacion_anual.jrxml";
	private final String LOGO = "/pe/cp/reportes/LogoCP.jpg";
	private final String ERR_DATOS_DISPONIBLES = "No existe datos disponibles";
	
	public ReporteRecaudacionController(IReportesRecaudacionView view) {
		this.view = view;
		getParamsUrl();
	}
	
	private void getParamsUrl() {		
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOp = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOp = Integer.valueOf(strIdUnidadOp);
	}
	
	@SuppressWarnings("serial")
	@Override
	public void cargar() {
		view.getBtnExportarPdf().addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				Object vista = view.getCbVista().getValue();
				if (vista != null) {
					if (vista.toString().equals(DIARIO)) 
						generarReporteDiario(PDF);
					else if (vista.toString().equals(SEMANAL)) 
						generarReporteSemanal(PDF);
					else if (vista.toString().equals(MENSUAL)) 
						generarReporteMensual(PDF);
					else if (vista.toString().equals(ANUAL)) 
						generarReporteAnual(PDF);
				}
			}		
		});
				
		FileDownloader filedownloader = new FileDownloader(new StreamResource(new StreamSource() {
		 	@Override
		 	public InputStream getStream () {
		 		try {
		 			Object vista = view.getCbVista().getValue();
					if (vista != null) {
						if (vista.toString().equals(DIARIO)) 
							generarReporteDiario(XLS);
						else if (vista.toString().equals(SEMANAL)) 
							generarReporteSemanal(XLS);
						else if (vista.toString().equals(MENSUAL)) 
							generarReporteMensual(XLS);
						else if (vista.toString().equals(ANUAL)) 
							generarReporteAnual(XLS);
					}	 		
					return new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(rutaArchivo + ".xls")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
		 	}
		}, "reporterecaudacion.xls"));
		filedownloader.extend(view.getBtnExportarExcel());	
		
		cargarComboVista();
	}
	
	@Override
	public void generarReporteDiario(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		if (view.getDfFiltro().getValue() != null){
			cal.setTime(view.getDfFiltro().getValue());
			
			params.put(PARAM_ID_UNIDAD, idUnidadOp);
			params.put(PARAM_FECHA_OPERACION, cal.getTime());
			params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
			params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
			obtenerReporte(formato, params, REP_RECAUDACION_DIARIOS);
		}else{
			notification = new Notification("Seleccionar fecha");
			notification.show(Page.getCurrent());
		}
		
	}

	@Override
	public void generarReporteSemanal(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		if (view.getDfFiltro().getValue() != null){
			cal.setTime(view.getDfFiltro().getValue());
			
			params.put(PARAM_ID_UNIDAD, idUnidadOp);
			params.put(PARAM_MES, cal.get(Calendar.MONTH) + 1);
			params.put(PARAM_ANHO, cal.get(Calendar.YEAR));
			params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
			params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
			obtenerReporte(formato, params, REP_RECAUDACION_SEMANAL);
		}else{
			notification = new Notification("Seleccionar fecha");
			notification.show(Page.getCurrent());
		}
		
	}

	@Override
	public void generarReporteMensual(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		if (view.getDfFiltro().getValue() != null){
			cal.setTime(view.getDfFiltro().getValue());
			
			params.put(PARAM_ID_UNIDAD, idUnidadOp);
			params.put(PARAM_MES, cal.get(Calendar.MONTH) + 1);
			params.put(PARAM_ANHO, cal.get(Calendar.YEAR));
			params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
			params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
			obtenerReporte(formato, params, REP_RECAUDACION_MENSUAL);
		}else{
			notification = new Notification("Seleccionar fecha");
			notification.show(Page.getCurrent());
		}		
	}

	@Override
	public void generarReporteAnual(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		if (view.getDfFiltro().getValue() != null){
			cal.setTime(view.getDfFiltro().getValue());
			
			params.put(PARAM_ID_UNIDAD, idUnidadOp);
			params.put(PARAM_ANHO, cal.get(Calendar.YEAR));
			params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
			params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
			obtenerReporte(formato, params, REP_RECAUDACION_ANUAL);
		}else{
			notification = new Notification("Seleccionar fecha");
			notification.show(Page.getCurrent());
		}			
	}
	
	private String generarRuta(){
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
	
	private void obtenerReporte(String formato,HashMap<String, Object> params, String rutaReporte) {
		ReportesPrint rprint = new ReportesPrint();
		ReportesConexion rpconexion = new ReportesConexion();
		
		try {
			InputStream iStream = getClass().getResourceAsStream(rutaReporte);
			rpconexion.conectar();
			
			if (formato.equals(PDF)){
				rutaArchivo = generarRuta();
				StreamSource streamSource = new PdfSource(rutaArchivo);
				StreamResource resource = new StreamResource(streamSource, rutaArchivo + ".pdf");
				BrowserFrame frameReporte = new BrowserFrame("",resource);
				frameReporte.setWidth("100%");
				frameReporte.setHeight("700px");
				view.getLayoutReporte().removeAllComponents();
				view.getLayoutReporte().addComponent(frameReporte);
				rprint.reporteaPDF(generarRuta() + ".pdf", iStream, params, rpconexion.getConexion());
			}else
				rprint.reporteaExcel(generarRuta() + ".xls", iStream, params, rpconexion.getConexion());
		} catch (FileNotFoundException fe) {
			notification = new Notification(ERR_DATOS_DISPONIBLES);
			notification.show(Page.getCurrent());
			fe.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (!rpconexion.getConexion().isClosed()){
					rpconexion.desconectar();
				}
			} catch (Exception e) {e.printStackTrace();}
		}
	}

	private void cargarComboVista() {
		view.getCbVista().removeAllItems();
		view.getCbVista().addItem(DIARIO);
		view.getCbVista().addItem(SEMANAL);
		view.getCbVista().addItem(MENSUAL);
		view.getCbVista().addItem(ANUAL);
	}
}
