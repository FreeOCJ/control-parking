package pe.cp.web.ui.view.configuracion.unidadoperativa;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class TarifaViewImpl implements ITarifaView {

	private Label lblUnidadOperativa;
	private TextField txtMonto;
	private Button btnAnadir;
	private Button btnRetirar;
	private Button btnGuardar;
	private int idUnidadOperativa;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}

	@Override
	public TextField getTxtMonto() {
		return txtMonto;
	}

	@Override
	public Label getLabelUnidadOperativa() {
		return lblUnidadOperativa;
	}

}
