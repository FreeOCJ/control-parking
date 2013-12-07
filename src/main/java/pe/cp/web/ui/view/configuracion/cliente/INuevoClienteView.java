package pe.cp.web.ui.view.configuracion.cliente;

import com.vaadin.navigator.View;
import com.vaadin.ui.TextField;

public interface INuevoClienteView extends View{
	void init();
	TextField getRazonSocial();
	TextField getRuc();
	TextField getNombreComercial();
}
