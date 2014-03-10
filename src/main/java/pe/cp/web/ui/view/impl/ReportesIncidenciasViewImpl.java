package pe.cp.web.ui.view.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

import pe.cp.web.ui.handler.IReportesIncidenciasViewHandler;
import pe.cp.web.ui.handler.impl.ReportesIncidenciasController;
import pe.cp.web.ui.view.IReportesIncidenciasView;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesIncidenciasViewImpl extends HorizontalLayout implements IReportesIncidenciasView{

	private CssLayout contenido;
	private int idUnidadOperativa;
	private int idCliente;
	private Button btnExportToPdf;
	private Button btnExportToXls;
	private VerticalLayout content;
	private String rutaArchivo;
	private DateField dfFecha;
	
	@Autowired
	private IReportesIncidenciasViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();		
		handler = new ReportesIncidenciasController(this);
		rutaArchivo = handler.generarRuta();
		getParamsUrl();
		generarReporteInicio();
		init();
	}
	
	
	
	private void getParamsUrl(){
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOperativa = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOperativa);
	}
	
	private void generarReporteInicio(){
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		handler.generarReportePDFMensual(idUnidadOperativa, date, rutaArchivo);
	}
	
	@Override
	public void init() {
		System.out.println("init reportes incidencias");
		setSizeFull();		
		addStyleName("main-view");		
		SideBarViewImpl barraControl = new SideBarViewImpl();
		addComponent(barraControl);
		
		// Content
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);        
        contenido.addComponent(cargarContenido());
	}
	
	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");	    
	    areaPrincipal.addComponent(header);
	    
	    Label title = new Label("Reporte de Incidencias");
	    title.addStyleName("h1");         
	    header.addComponent(title);	    	    
	    
	    content = new VerticalLayout();
	    content.setSizeFull();
	    content.setHeight("80%");
	    content.setWidth("100%");
	    content.setSpacing(true);
	    
	    StreamResource.StreamSource streamSource = new PdfSource();
		StreamResource resource = new StreamResource(streamSource, rutaArchivo + ".pdf");
		
		BrowserFrame browser = new BrowserFrame("",resource);
		browser.setHeight("800px");
		browser.setWidth("1200px");
		content.addComponent(browser);
		content.setComponentAlignment(browser, Alignment.TOP_CENTER);
	    
	    areaPrincipal.addComponent(content);
	    
	    HorizontalLayout footer = new HorizontalLayout();
	    footer.setHeight("20%");
	    footer.setWidth("100%");
	    footer.setSpacing(true);
	    areaPrincipal.addComponent(footer);
	    
	    dfFecha = new DateField();
        dfFecha.setWidth("120px");
        footer.addComponent(dfFecha);
        footer.setComponentAlignment(dfFecha, Alignment.BOTTOM_LEFT);
	        
	    btnExportToPdf = new Button("PDF");
	    btnExportToPdf.addStyleName("default");
	    footer.addComponent(btnExportToPdf);
	    footer.setComponentAlignment(btnExportToPdf, Alignment.BOTTOM_CENTER);
	    
	    btnExportToXls = new Button("Excel");
	    btnExportToXls.addStyleName("default");
	    footer.addComponent(btnExportToXls);
	    footer.setComponentAlignment(btnExportToXls, Alignment.BOTTOM_CENTER);
	   
	    btnExportToPdf.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				Date fecha = new Date(Calendar.getInstance().getTimeInMillis());
				if (dfFecha.getValue() != null){
					fecha = new java.sql.Date(dfFecha.getValue().getTime());
				}
				rutaArchivo = handler.generarRuta();
				handler.generarReportePDFMensual(idUnidadOperativa, fecha, rutaArchivo);
			}		
	    });
	    
	    btnExportToXls.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				Date fecha = new Date(Calendar.getInstance().getTimeInMillis());
				if (dfFecha.getValue() != null){
					fecha = new java.sql.Date(dfFecha.getValue().getTime());
				}
				rutaArchivo = handler.generarRuta();
				handler.generarReporteXLSMensual(idUnidadOperativa, fecha, rutaArchivo);							
			}		
	    });
	    
	    header.addComponent(title);
	    return areaPrincipal;
	}
	
	class PdfSource implements StreamResource.StreamSource {
		@Override
		public InputStream getStream() {
			try {
				return new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(rutaArchivo + ".pdf")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}					
		}		
	}

}
