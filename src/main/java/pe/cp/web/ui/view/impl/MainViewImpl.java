package pe.cp.web.ui.view.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import pe.cp.web.ui.view.IMainView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MainViewImpl extends HorizontalLayout implements IMainView {
	
	private CssLayout contenido;	
	
	public MainViewImpl(){
		init();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
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
	        
	    Label title = new Label("Bienvenido!");
	    header.addComponent(title); 
	    title.addStyleName("h1");  
	    
	    return areaPrincipal;
	}

}
