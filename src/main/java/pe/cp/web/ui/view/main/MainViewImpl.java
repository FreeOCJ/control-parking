package pe.cp.web.ui.view.main;

import java.util.HashMap;

import pe.cp.web.ui.view.auditoria.AuditoriaView;
import pe.cp.web.ui.view.configuracion.ConfigViewImpl;
import pe.cp.web.ui.view.operaciones.OperacionesComponent;
import pe.cp.web.ui.view.reportes.ReportesView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class MainViewImpl extends HorizontalLayout implements IMain {
	
	private CssLayout contenido;	
	
	public MainViewImpl(){
		init();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void init() {		
        System.out.println("init operaciones");
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