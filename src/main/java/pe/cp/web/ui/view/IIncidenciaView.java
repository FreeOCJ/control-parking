package pe.cp.web.ui.view;

import org.vaadin.thomas.timefield.TimeField;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public interface IIncidenciaView extends View {
    void init();
    TextArea getTxtDescripcion();
    TextArea getTxtAccion();
    ComboBox getCbTipo();
    TimeField getTxtHora();
    Label getLblTitulo();
    int getIdIncidencia();
    int getIdOperacion();
}
