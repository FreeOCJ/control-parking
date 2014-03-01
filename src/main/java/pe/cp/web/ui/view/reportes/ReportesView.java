package pe.cp.web.ui.view.reportes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.main.SideBar;
import pe.cp.web.ui.view.operaciones.OperacionesComponent;
import pe.cp.web.ui.view.reportes.IReportesView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesView extends HorizontalLayout implements IReportesView {

	private CssLayout contenido;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public ReportesView(){
		init();
	}

	
	@Override
	public void init() {		
        System.out.println("init reportes");
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
        OperacionesComponent op = new OperacionesComponent();
        contenido.addComponent(op);
	}

}
