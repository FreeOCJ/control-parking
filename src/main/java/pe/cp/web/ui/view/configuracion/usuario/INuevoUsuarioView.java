package pe.cp.web.ui.view.configuracion.usuario;

import com.vaadin.navigator.View;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public interface INuevoUsuarioView extends View {
	void init();	
	void setHandler();
	
	TextField getLogin();
	TextField getNombres();
	TextField getApellidos();
	TextField getCargo();
	TextField getCorreoElectronico();
	TwinColSelect getRoles();
}
