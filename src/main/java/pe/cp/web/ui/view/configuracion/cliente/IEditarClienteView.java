package pe.cp.web.ui.view.configuracion.cliente;

import com.vaadin.navigator.View;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface IEditarClienteView extends View {
	void init();
	TextField getRazonSocial();
	TextField getRuc();
	TextField getNombreComercial();
	Table getUsuarios();
	Table getUnidadesOperativas();
}
