package pe.cp.web.ui.view.reportes.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import pe.cp.web.ui.view.main.SideBar;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesIncidenciasViewImpl extends HorizontalLayout implements IReportesIncidenciasView{

	private CssLayout contenido;
	
	@Autowired
	private IReportesIncidenciasViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		System.out.println("init reportes incidencias");
		construirBase();
	}
	
	private void construirBase(){		
		setSizeFull();		
		addStyleName("main-view");
		
		SideBar barraControl = new SideBar();
		addComponent(barraControl);
		
		// Content
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);
        
        //Agregar Default
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
		
	    Button btnReportesIncidencias = new Button("Reporte de Incidencias");
	    VerticalLayout configLayout = new VerticalLayout();
	    configLayout.addComponent(btnReportesIncidencias);
	    
	    btnReportesIncidencias.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				//handler.irReportesIncidencias();		
			}
		});
	    		
	    areaPrincipal.addComponent(configLayout);
	    areaPrincipal.setExpandRatio(configLayout, 1);
	    return areaPrincipal;
	}

}
