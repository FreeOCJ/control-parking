package pe.cp.web.ui.view.auditoria;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface IAuditoriaView extends View {
	void init();
    Table getTblResultados();
    TextField getTxtUsuari();
    DateField getDfFechaInicio();
    DateField getDfFechaFin();
    Button getBtnBuscar();
    ComboBox getCbTipoEvento();
}
