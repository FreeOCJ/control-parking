package pe.cp.web.ui.view.configuracion;

import com.vaadin.navigator.View;

public interface IConfigView extends View {

	void init();	
	void setHandler(IConfigViewHandler handler);
}
