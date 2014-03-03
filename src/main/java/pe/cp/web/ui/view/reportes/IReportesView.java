package pe.cp.web.ui.view.reportes;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;

public interface IReportesView extends View {

	void init();
	ComboBox getCbCliente();
	ComboBox getCbUnidadOp();

}
