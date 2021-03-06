package pe.cp.web.ui.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;





import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.web.util.PdfUtil;
import pe.cp.reportes.util.ReportesConexion;
import pe.cp.reportes.util.ReportesPrint;
import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.view.IReportesConsolidadoView;

public class ReporteConsolidadoController implements IReporteConsolidadoHandler {

	ApplicationContext ac;
	@Autowired
	private ClienteService clienteservice;
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	IReportesConsolidadoView view;
	int anhoInicio = 2014;
	int idCliente;
	int idUnidadOperativa;
	Date fechaActual;

	private String rutaArchivoConsolidado;
	private List<String>  files;
	private Notification notification;
	
	private final String PDF = "PDF";
	private final String PARAM_ID_UNIDAD = "P_ID_UNIDAD";
	private final String PARAM_MES = "P_MES";
	private final String PARAM_ANHO = "P_ANHO";
	private final String PARAM_LOGO= "P_LOGO";
	private final String PARAM_FECHA_OPERACION = "P_FECHA_OPERACION";
	private final String PARAM_REPORT_LOCALE = "REPORT_LOCALE";
	private final String REP_INCIDENCIAS= "/pe/cp/reportes/cpr_incidencias_mensual.jrxml";
	private final String REP_VISITAS_DIARIOS = "/pe/cp/reportes/cpr_visitas_diario.jrxml";
	private final String REP_VISITAS_MENSUAL = "/pe/cp/reportes/cpr_visitas_mensual.jrxml";
	private final String REP_ING_SAL_MENSUAL = "/pe/cp/reportes/cpr_ingresos_salidas_mensual.jrxml";
	private final String REP_ING_SAL_DIARIOS = "/pe/cp/reportes/cpr_ingresos_salidas_diario.jrxml";
	private final String REP_RECAUDACION_MENSUAL = "/pe/cp/reportes/cpr_recaudacion_mensual.jrxml";
	private final String REP_RECAUDACION_DIARIOS = "/pe/cp/reportes/cpr_recaudacion_diario.jrxml";
	private final String ERR_DATOS_DISPONIBLES = "No existe datos disponibles";
	private final String LOGO = "/pe/cp/reportes/LogoCP.jpg";
	
	public ReporteConsolidadoController(IReportesConsolidadoView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
		
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOp = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOp);
		fechaActual = new Date();
	}
	
	@SuppressWarnings("serial")
	@Override
	public void cargar() {
		FileDownloader filedownloader = new FileDownloader(new StreamResource(new StreamSource() {
		 	@Override
		 	public InputStream getStream () {
		 		try {
		 			Calendar fromDate = Calendar.getInstance();
					fromDate.setTime(getFromDate());
					Date toDate = getToDate();
					files = new ArrayList<String>();
					if (view.getChbIncidencias().getValue()){			
						generarReporteIncidencias(view.getCbFormato().getValue().toString());
					}
					if (view.getChbVisitas().getValue()){
						while (fromDate.before(toDate)) {
							generarReporteDiarioVisitas(view.getCbFormato().getValue().toString(), 
														fromDate.getTime());
							fromDate.add(Calendar.DATE, 1);
						}
						generarReporteMensualVisitas(view.getCbFormato().getValue().toString());
					}
					if (view.getChbIngresosSalidas().getValue()){
						while (fromDate.before(toDate)) {
							generarReporteDiarioIngresoSalidas(view.getCbFormato().getValue().toString(), 
														fromDate.getTime());
							fromDate.add(Calendar.DATE, 1);
						}
						generarReporteMensualIngresoSalidas(view.getCbFormato().getValue().toString());
					}
					if (view.getChbRecaudacion().getValue()){
						while (fromDate.before(toDate)) {
							generarReporteDiarioRecaudacion(view.getCbFormato().getValue().toString(), 
														fromDate.getTime());
							fromDate.add(Calendar.DATE, 1);
						}
						generarReporteMensualRecaudacion(view.getCbFormato().getValue().toString());
					}
					generarReporteConsolidado();	 		
					return new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(rutaArchivoConsolidado)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
		 	}
		}, "reporteconsolidado.pdf"));
		filedownloader.extend(view.getBtnGenerar());
		
		/**view.getBtnGenerar().addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar fromDate = Calendar.getInstance();
				fromDate.setTime(getFromDate());
				Date toDate = getToDate();
				files = new ArrayList<String>();
				if (view.getChbIncidencias().getValue()){			
					generarReporteIncidencias(view.getCbFormato().getValue().toString());
				}
				if (view.getChbVisitas().getValue()){
					while (fromDate.before(toDate)) {
						generarReporteDiarioVisitas(view.getCbFormato().getValue().toString(), 
													fromDate.getTime());
						fromDate.add(Calendar.DATE, 1);
					}
					generarReporteMensualVisitas(view.getCbFormato().getValue().toString());
				}
				if (view.getChbIngresosSalidas().getValue()){
					while (fromDate.before(toDate)) {
						generarReporteDiarioIngresoSalidas(view.getCbFormato().getValue().toString(), 
													fromDate.getTime());
						fromDate.add(Calendar.DATE, 1);
					}
					generarReporteMensualIngresoSalidas(view.getCbFormato().getValue().toString());
				}
				if (view.getChbRecaudacion().getValue()){
					while (fromDate.before(toDate)) {
						generarReporteDiarioRecaudacion(view.getCbFormato().getValue().toString(), 
													fromDate.getTime());
						fromDate.add(Calendar.DATE, 1);
					}
					generarReporteMensualRecaudacion(view.getCbFormato().getValue().toString());
				}
				generarReporteConsolidado();
			}		
		});**/				
		cargarAnhos();
		cargarMeses();
		cargarFormatos();
		cargarDatos();
	}
	
	private Date getFromDate(){
		int anho = Integer.valueOf(view.getCbAnho().getValue().toString());
		int mes = Integer.valueOf(view.getCbMes().getValue().toString());
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(anho, mes-1,1);
		cal1.add(Calendar.DAY_OF_MONTH, -1);		
		
		return cal1.getTime();
	}
	
	private Date getToDate(){
		int anho = Integer.valueOf(view.getCbAnho().getValue().toString());
		int mes = Integer.valueOf(view.getCbMes().getValue().toString());
		
		Calendar cal1 = Calendar.getInstance();
		
		cal1.set(anho, mes-1,1);
		cal1.add(Calendar.MONTH, 1);
		cal1.add(Calendar.DAY_OF_MONTH, -1);		
		
		return cal1.getTime();
	}
	
	private void generarReporteConsolidado(){
		if (files != null && files.size()>0){
			rutaArchivoConsolidado = generarRuta() + ".pdf";
			PdfUtil pdfutil = new PdfUtil();
			String[] filesArray = new String[ files.size() ];
			files.toArray(filesArray);
			pdfutil.concatenarPDFs(rutaArchivoConsolidado, filesArray);
		}else{
			notification = new Notification(ERR_DATOS_DISPONIBLES);
			notification.show(Page.getCurrent());
		}
	}
	
	private void cargarDatos() {
		view.getLblCliente().setValue(obtenerNombreCliente(idCliente));
		view.getLblUnidadOp().setValue(obtenerNombreUnidadOperativa(idUnidadOperativa));
	}
	
	private void cargarAnhos() {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fechaActual);
	    int anhoActual = cal.get(Calendar.YEAR);
		
		view.getCbAnho().removeAllItems();
		for (int anho = anhoActual; anho >= anhoInicio; anho--) {
			view.getCbAnho().addItem(String.valueOf(anho));
			view.getCbAnho().setValue(String.valueOf(anho));
		}
	}
	
	private void cargarMeses() {
		Calendar cal = Calendar.getInstance();
		view.getCbMes().removeAllItems();
		for (int i = 1; i <= 12; i++) {
			view.getCbMes().addItem(String.valueOf(i));			
		}
		view.getCbMes().setValue(String.valueOf(cal.get(Calendar.MONTH) + 1));
	}
	
	private void cargarFormatos() {
		view.getCbFormato().addItem("PDF");
		view.getCbFormato().setValue("PDF");
	}
	
	private String obtenerNombreCliente(int idCliente) {
		String nombreCliente = "";
		ObtenerClienteRequest request= new ObtenerClienteRequest(idCliente);
		ObtenerClienteResponse response = clienteservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreCliente = response.getClienteView().getNombreComercial();
		}
		return nombreCliente;
	}


	private String obtenerNombreUnidadOperativa(int idUnidadOp) {
		String nombreUnidad = "";
		ObtenerUnidadOperativaRequest request= new ObtenerUnidadOperativaRequest(idUnidadOp);
		ObtenerUnidadOperativaResponse response = unidadopservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreUnidad = response.getUnidadOpView().getNombre();
		}
		return nombreUnidad;
	}
	
	private void generarReporteIncidencias(String formato){
		HashMap<String, Object> params= new HashMap<String, Object>();
			
		params.put(PARAM_ID_UNIDAD,idUnidadOperativa);
		params.put(PARAM_MES,Integer.valueOf(view.getCbMes().getValue().toString()));
		params.put(PARAM_ANHO, Integer.valueOf(view.getCbAnho().getValue().toString()));	
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_INCIDENCIAS);		
	}
	
	private void generarReporteMensualVisitas(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
			
		params.put(PARAM_ID_UNIDAD, idUnidadOperativa);
		params.put(PARAM_MES,Integer.valueOf(view.getCbMes().getValue().toString()));
		params.put(PARAM_ANHO, Integer.valueOf(view.getCbAnho().getValue().toString()));	
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_VISITAS_MENSUAL);
	}
	
	private void generarReporteDiarioVisitas(String formato,Date fecha) {
		HashMap<String, Object> params= new HashMap<String, Object>();
			
		params.put(PARAM_ID_UNIDAD, idUnidadOperativa);
		params.put(PARAM_FECHA_OPERACION,fecha);
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_VISITAS_DIARIOS);
	}
	
	public void generarReporteMensualIngresoSalidas(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
			
		params.put(PARAM_ID_UNIDAD, idUnidadOperativa);
		params.put(PARAM_MES,Integer.valueOf(view.getCbMes().getValue().toString()));
		params.put(PARAM_ANHO, Integer.valueOf(view.getCbAnho().getValue().toString()));	
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_ING_SAL_MENSUAL);		
	}
	
	public void generarReporteDiarioIngresoSalidas(String formato,Date fecha) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		
		params.put(PARAM_FECHA_OPERACION, fecha);
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_ING_SAL_DIARIOS);
	}
	
	public void generarReporteMensualRecaudacion(String formato) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		
		params.put(PARAM_ID_UNIDAD, idUnidadOperativa);
		params.put(PARAM_MES,Integer.valueOf(view.getCbMes().getValue().toString()));
		params.put(PARAM_ANHO, Integer.valueOf(view.getCbAnho().getValue().toString()));	
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_RECAUDACION_MENSUAL);				
	}
	
	public void generarReporteDiarioRecaudacion(String formato,Date fecha) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		
		params.put(PARAM_ID_UNIDAD, idUnidadOperativa);
		params.put(PARAM_FECHA_OPERACION, fecha);
		params.put(PARAM_REPORT_LOCALE, new Locale("es", "ES"));
		params.put(PARAM_LOGO,this.getClass().getResource(LOGO));
			
		obtenerReporte(formato, params, REP_RECAUDACION_DIARIOS);		
	}
	
	private void obtenerReporte(String formato,HashMap<String, Object> params, String rutaReporte) {
		ReportesPrint rprint = new ReportesPrint();
		ReportesConexion rpconexion = new ReportesConexion();
		
		try {
			InputStream iStream = getClass().getResourceAsStream(rutaReporte);
			rpconexion.conectar();
			
			if (formato.equals(PDF)){
				String ruta = generarRuta();
				rprint.reporteaPDF(ruta + ".pdf", iStream, params, rpconexion.getConexion());
				File f = new File(ruta + ".pdf");
				if (f.exists()){files.add(ruta + ".pdf");}				
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
	
	private String generarRuta(){
		String rutaArchivo = "";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyyhhmmssSSS");
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
	

}
