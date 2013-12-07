package pe.cp.web.ui.view.configuracion.cliente;

import com.vaadin.navigator.View;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface IBuscarClientesView extends View {
	void init();
	TextField getFiltroNombreComercial();
	Table getResultados();
	void limpiarTabla();
}
