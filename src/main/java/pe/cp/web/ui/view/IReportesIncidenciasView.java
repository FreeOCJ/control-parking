package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FileDownloader;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;

public interface IReportesIncidenciasView extends View {
	void init();
	VerticalLayout getLayoutReporte();
	DateField getDfFiltro();
    Button getBtnExportarPdf();
    Button getBtnExportarExcel();
    BrowserFrame getBrowserFrame();
    FileDownloader getFileDownloader();
    Button getBtnDownload();
}
