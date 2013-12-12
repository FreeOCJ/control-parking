package pe.cp.web.ui.view.configuracion.unidadoperativa;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public interface ITarifaView extends View {
	void init();
	int getIdUnidadOperativa();
	TextField getTxtMonto();
	Label getLabelUnidadOperativa();
}
