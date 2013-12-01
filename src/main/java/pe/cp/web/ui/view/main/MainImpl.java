package pe.cp.web.ui.view.main;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class MainImpl extends HorizontalLayout implements IMain {

	private CssLayout base;
	private CssLayout contenido;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		construirBase();
		addComponent(base);
	}
	
	private void construirBase(){
		base = new CssLayout();
		base.setSizeFull();		
		base.addStyleName("main-view");
		
		SideBar barraControl = new SideBar();
		base.addComponent(barraControl);
		
		// Content
		contenido = new CssLayout();
        base.addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");
        setExpandRatio(contenido, 1);		
	}

}
