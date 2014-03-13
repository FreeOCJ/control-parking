package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteIngresosSalidasHandler;
import pe.cp.web.ui.handler.impl.ReporteIngresosSalidasController;
import pe.cp.web.ui.view.IReportesIngresosSalidasView;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class ReporteIngresosSalidasViewImpl extends HorizontalLayout implements IReportesIngresosSalidasView {

	private CssLayout contenido;
	private IReporteIngresosSalidasHandler handler;
	
	private DateField dfFechaFiltro;
	private BrowserFrame browserFrame;
	private Button btnExportarPDF;
	private Button btnExportarExcel;
	private ComboBox cbVista;
	private VerticalLayout reporteLayout;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler = new ReporteIngresosSalidasController(this);
		handler.cargar();
	}

	@Override
	public void init() {
		removeAllComponents();
		setSizeFull();		
		addStyleName("main-view");		
		addComponent(new SideBarViewImpl());
		
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
	        
	    Label title = new Label("Reporte de Ingreso & Salida Vehicular");
	    header.addComponent(title); 
	    title.addStyleName("h1");  
	    
	    cbVista = new ComboBox();
	    cbVista.setInputPrompt("Ver por");
	    dfFechaFiltro = new DateField();
	    dfFechaFiltro.setWidth("120px");
	    btnExportarPDF = new Button("PDF");
	    btnExportarPDF.addStyleName("default");
	    btnExportarExcel = new Button("Excel");
	    btnExportarExcel.addStyleName("default");
	    
	    HorizontalLayout filtersLayout = new HorizontalLayout();
	    header.addComponent(filtersLayout);
	    header.setComponentAlignment(filtersLayout, Alignment.MIDDLE_RIGHT);
	    
	    filtersLayout.addComponent(cbVista);
	    filtersLayout.addComponent(dfFechaFiltro);
	    filtersLayout.addComponent(new Label("   "));
	    filtersLayout.addComponent(btnExportarPDF);
	    filtersLayout.addComponent(btnExportarExcel);
	    
	    reporteLayout = new VerticalLayout();
	    reporteLayout.setSizeFull();
	    areaPrincipal.addComponent(reporteLayout);
	    
	    return areaPrincipal;
	}

	@Override
	public ComboBox getCbVista() {
		return cbVista;
	}

	@Override
	public DateField getDfFiltro() {
		return dfFechaFiltro;
	}

	@Override
	public Button getBtnExportarPdf() {
		return btnExportarPDF;
	}

	@Override
	public Button getBtnExportarExcel() {
		return btnExportarExcel;
	}

	@Override
	public BrowserFrame getBrowserFrame() {
		return browserFrame;
	}

	@Override
	public VerticalLayout getLayoutReporte() {
		return reporteLayout;
	}
}
