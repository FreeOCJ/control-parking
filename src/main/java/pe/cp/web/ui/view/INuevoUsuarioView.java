package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public interface INuevoUsuarioView extends View {
	void init();	
	
	TextField getLogin();
	TextField getNombres();
	TextField getApellidos();
	TextField getCargo();
	TextField getCorreoElectronico();
	TwinColSelect getRoles();
	int getIdCliente();
}
