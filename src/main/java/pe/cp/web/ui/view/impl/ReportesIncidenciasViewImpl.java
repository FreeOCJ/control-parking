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
	  
	    return areaPrincipal;
	}
		

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
