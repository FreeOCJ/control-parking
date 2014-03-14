package pe.cp.web.ui.view.impl;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteRecaudacionHandler;
import pe.cp.web.ui.handler.impl.ReporteRecaudacionController;
import pe.cp.web.ui.view.IReportesRecaudacionView;

@SuppressWarnings("serial")
public class ReporteRecaudacionViewImpl extends HorizontalLayout implements IReportesRecaudacionView {

	private CssLayout contenido;
	private IReporteRecaudacionHandler handler;
	private Button btnExportToPdf;
	private Button btnExportToXls;
	private DateField dfFecha;
	private VerticalLayout reporteLayout;
	private ComboBox cbVista;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
        handler = new ReporteRecaudacionController(this);
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
	        
	    Label title = new Label("Reporte de Recaudación");
	    header.addComponent(title); 
	    title.addStyleName("h1");  
	    
	    cbVista = new ComboBox();
	    cbVista.setInputPrompt("Ver por");
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
	    
	    filtersLayout.addComponent(cbVista);
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
	public ComboBox getCbVista() {
		return cbVista;
	}
}
