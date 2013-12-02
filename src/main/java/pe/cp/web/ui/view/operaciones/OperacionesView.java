package pe.cp.web.ui.view.operaciones;

import pe.cp.web.ui.view.main.SideBar;
import pe.cp.web.ui.view.operaciones.IOperacionesView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class OperacionesView extends VerticalLayout implements IOperacionesView {

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public OperacionesView(){		
		SideBar sideBar = new SideBar();
		addComponent(sideBar);
		addComponent(sideBar);		
	}

}
