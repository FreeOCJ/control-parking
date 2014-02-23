package pe.cp.web.ui.view.operaciones;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;

public interface INuevaOperacionView extends View {
   void init();
   ComboBox getUnidadOperativa();
   DateField getFechaOperacion();
}
