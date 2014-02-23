package pe.cp.web.ui.view.configuracion.unidadoperativa;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;

public interface ITarifaView extends View {
	void init();
	int getIdUnidadOperativa();
	int getIdCliente();
	String getCategoriaTarifa();
	TextField getTxtMonto();
	TextField getTxtNombre();
	Label getLabelUnidadOperativa();
	Label getLabelTitulo();
	ListSelect getListaTarifas();
}
