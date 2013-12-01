package pe.cp.web.ui.view.operaciones;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class OperacionesComponent extends VerticalLayout {

	public OperacionesComponent(){
		setSizeFull();
        addStyleName("timeline");

        Label header = new Label("Operaciones");
        header.addStyleName("h1");
        addComponent(header);
	}
	
}
