package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Table;

public interface IBuscarOperacionesView extends View {
   void init();
   void limpiarTabla();
   ComboBox getCbUnidadOp();
   ComboBox getCbEstado();
   DateField getDfFechaOp();
   Table getResultados();
}
