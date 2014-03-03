package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.NativeButton;

public interface IReportesView extends View {

	void init();
	ComboBox getCbCliente();
	ComboBox getCbUnidadOp();
	NativeButton getBtnVerIncidencias();
	NativeButton getBtnVerIngresoSalida();
	NativeButton getBtnVerVisitas();
	NativeButton getBtnVerRecaudacion();
	NativeButton getBtnVerConsolidado();
}
