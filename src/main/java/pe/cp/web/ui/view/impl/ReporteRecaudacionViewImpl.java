package pe.cp.web.ui.view.impl;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.handler.IReporteRecaudacionHandler;
import pe.cp.web.ui.handler.impl.ReporteRecaudacionController;
import pe.cp.web.ui.view.IReportesRecaudacionView;

@SuppressWarnings("serial")
public class ReporteRecaudacionViewImpl extends HorizontalLayout implements IReportesRecaudacionView {

	private CssLayout contenido;
	private IReporteRecaudacionHandler handler;
	
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
	    
	    return areaPrincipal;
	}
}
