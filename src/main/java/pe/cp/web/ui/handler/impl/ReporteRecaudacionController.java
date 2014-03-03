package pe.cp.web.ui.handler.impl;

import pe.cp.web.ui.handler.IReporteRecaudacionHandler;
import pe.cp.web.ui.view.IReportesRecaudacionView;

public class ReporteRecaudacionController implements IReporteRecaudacionHandler {

	IReportesRecaudacionView view;
	
	public ReporteRecaudacionController(IReportesRecaudacionView view) {
		this.view = view;
	}
	
	@Override
	public void cargar() {
		// TODO Auto-generated method stub

	}

}
