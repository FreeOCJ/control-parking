package pe.cp.web.ui.view.impl;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.handler.impl.ReporteConsolidadoController;
import pe.cp.web.ui.view.IReportesConsolidadoView;

@SuppressWarnings("serial")
public class ReporteConsolidadoViewImpl extends HorizontalLayout implements IReportesConsolidadoView {

	IReporteConsolidadoHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler = new ReporteConsolidadoController(this);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
