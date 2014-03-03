package pe.cp.web.ui.view;

import pe.cp.web.ui.handler.IConfigViewHandler;

import com.vaadin.navigator.View;

public interface IConfigView extends View {

	void init();	
	void setHandler(IConfigViewHandler handler);
}
