package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;

public interface IReportesConsolidadoView extends View {
    void init();
    Label getLblCliente();
    Label getLblUnidadOp();
    ComboBox getCbAnho();
    ComboBox getCbMes();
    ComboBox getCbFormato();
    CheckBox getChbIngresosSalidas();
    CheckBox getChbVisitas();
    CheckBox getChbIncidencias();
    CheckBox getChbRecaudacion();
    Button getBtnGenerar();
}
