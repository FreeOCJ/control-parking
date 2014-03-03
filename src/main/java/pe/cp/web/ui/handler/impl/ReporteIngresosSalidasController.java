package pe.cp.web.ui.handler.impl;

import pe.cp.web.ui.handler.IReporteIngresosSalidasHandler;
import pe.cp.web.ui.view.IReportesIngresosSalidasView;

public class ReporteIngresosSalidasController implements
		IReporteIngresosSalidasHandler {

	IReportesIngresosSalidasView view;
	
	public ReporteIngresosSalidasController(IReportesIngresosSalidasView view) {
		this.view = view;
	}
	
	@Override
	public void cargar() {
		// TODO Auto-generated method stub

	}

}
