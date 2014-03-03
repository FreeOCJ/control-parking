package pe.cp.web.ui.view.impl;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteVisitasHandler;
import pe.cp.web.ui.handler.impl.ReporteVisitasController;
import pe.cp.web.ui.view.IReportesVisitasView;

@SuppressWarnings("serial")
public class ReporteVisitasViewImpl extends HorizontalLayout  implements IReportesVisitasView {

	private CssLayout contenido;
	private IReporteVisitasHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler = new ReporteVisitasController(this);
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
	        
	    Label title = new Label("Reporte de Visitas");
	    header.addComponent(title); 
	    title.addStyleName("h1");  
	    
	    return areaPrincipal;
	}
}
