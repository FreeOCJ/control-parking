package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.handler.IReporteIngresosSalidasHandler;
import pe.cp.web.ui.handler.impl.ReporteConsolidadoController;
import pe.cp.web.ui.handler.impl.ReporteIngresosSalidasController;
import pe.cp.web.ui.view.IReportesIngresosSalidasView;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class ReporteIngresosSalidasViewImpl extends HorizontalLayout implements IReportesIngresosSalidasView {

	private CssLayout contenido;
	private IReporteIngresosSalidasHandler handler;
	
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
	    
	    return areaPrincipal;
	}
}
