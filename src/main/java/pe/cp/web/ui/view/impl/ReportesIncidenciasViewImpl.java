package pe.cp.web.ui.view.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReportesIncidenciasViewHandler;
import pe.cp.web.ui.handler.impl.ReportesIncidenciasController;
import pe.cp.web.ui.view.IReportesIncidenciasView;

@Component
@Scope("prototype")
@SuppressWarnings({"serial"})
@Theme("controlparking")
public class ReportesIncidenciasViewImpl extends HorizontalLayout implements IReportesIncidenciasView{

	private CssLayout contenido;
	private Button btnExportToPdf;
	private Button btnExportToXls;
	private DateField dfFecha;
	private VerticalLayout reporteLayout;
	private BrowserFrame browserFrame;
	
	@Autowired
	private IReportesIncidenciasViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();		
		handler = new ReportesIncidenciasController(this);
		handler.cargar();
	}
	
	@Override
	public void init() {
		removeAllComponents();		
		setSizeFull();		
		addStyleName("main-view");		
		SideBarViewImpl barraControl = new SideBarViewImpl();
		addComponent(barraControl);
		
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
	    
	    dfFecha = new DateField();
	    dfFecha.setWidth("120px");
	    btnExportToPdf = new Button("PDF");
	    btnExportToPdf.addStyleName("default");
	    btnExportToXls = new Button("Excel");
	    btnExportToXls.addStyleName("default");
	    
	    HorizontalLayout filtersLayout = new HorizontalLayout();
	    filtersLayout.setSpacing(true);
	    header.addComponent(filtersLayout);
	    header.setComponentAlignment(filtersLayout, Alignment.MIDDLE_RIGHT);
	    
	    filtersLayout.addComponent(dfFecha);
	    filtersLayout.addComponent(new Label("   "));
	    filtersLayout.addComponent(btnExportToPdf);
	    filtersLayout.addComponent(btnExportToXls);
	    
	    reporteLayout = new VerticalLayout();
	    reporteLayout.setSizeFull();
	    areaPrincipal.addComponent(reporteLayout);
	    
	    /**content = new HorizontalLayout();
	    content.setSizeFull();
	    content.setHeight("100%");
	    content.setWidth("100%");
	    content.setSpacing(true);
	    
	    StreamResource.StreamSource streamSource = new PdfSource();
		StreamResource resource = new StreamResource(streamSource, rutaArchivo + ".pdf");
		
		BrowserFrame browser = new BrowserFrame("",resource);
		browser.setHeight("700px");
		browser.setWidth("1200px");
		content.addComponent(browser);
		content.setComponentAlignment(browser, Alignment.TOP_CENTER);
	    
	    areaPrincipal.addComponent(content);
	    
	    HorizontalLayout footer = new HorizontalLayout();
	    footer.setHeight("20%");
	    footer.setWidth("100%");
	    footer.setSpacing(true);
	    footer.addStyleName("toolbar");
	    areaPrincipal.addComponent(footer);
	    
	    
	    dfFecha = new DateField();
        dfFecha.setWidth("120px");
        
        footer.addComponent(dfFecha);
        footer.setComponentAlignment(dfFecha, Alignment.MIDDLE_RIGHT);
        
	        
	    
	    footer.addComponent(new Label("    "));
	    footer.addComponent(btnExportToPdf);
	    
	    
	    footer.addComponent(btnExportToXls);
	    footer.setComponentAlignment(btnExportToXls, Alignment.MIDDLE_LEFT);
	    
	    footer.setExpandRatio(dfFecha, 1);
	    footer.setExpandRatio(btnExportToXls, 1);
	   
	   
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
	    
	    header.addComponent(title);**/
	    return areaPrincipal;
	}
	
	/**private void getParamsUrl(){
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOperativa = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOperativa);
	}**/
	
	

	@Override
	public VerticalLayout getLayoutReporte() {
		return reporteLayout;
	}

	@Override
	public DateField getDfFiltro() {
		return dfFecha;
	}



	@Override
	public Button getBtnExportarPdf() {
		return btnExportToPdf;
	}



	@Override
	public Button getBtnExportarExcel() {
		return btnExportToXls;
	}



	@Override
	public BrowserFrame getBrowserFrame() {
		return browserFrame;
	}

}
