package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;

public interface IReportesIngresosSalidasView extends View {
    void init();
    VerticalLayout getLayoutReporte();
    ComboBox getCbVista();
    DateField getDfFiltro();
    Button getBtnExportarPdf();
    Button getBtnExportarExcel();
    BrowserFrame getBrowserFrame();
}
