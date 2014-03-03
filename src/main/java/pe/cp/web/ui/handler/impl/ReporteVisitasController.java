package pe.cp.web.ui.handler.impl;

import pe.cp.web.ui.handler.IReporteVisitasHandler;
import pe.cp.web.ui.view.IReportesVisitasView;

public class ReporteVisitasController implements IReporteVisitasHandler {

	IReportesVisitasView view;
	
	public ReporteVisitasController(IReportesVisitasView view) {
		this.view = view;
	}
	
	@Override
	public void cargar() {
		// TODO Auto-generated method stub

	}

}
