package pe.cp.web.ui.handler.impl;

import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.view.IReportesConsolidadoView;

public class ReporteConsolidadoController implements IReporteConsolidadoHandler {

	IReportesConsolidadoView view;
	
	public ReporteConsolidadoController(IReportesConsolidadoView view) {
		this.view = view;
	}
	
	@Override
	public void cargar() {
		// TODO Auto-generated method stub

	}

}
