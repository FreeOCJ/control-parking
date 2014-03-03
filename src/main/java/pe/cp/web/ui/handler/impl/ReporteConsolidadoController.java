package pe.cp.web.ui.handler.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.ui.UI;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.view.IReportesConsolidadoView;

public class ReporteConsolidadoController implements IReporteConsolidadoHandler {

	ApplicationContext ac;
	@Autowired
	private ClienteService clienteservice;
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	IReportesConsolidadoView view;
	int anhoInicio = 2014;
	int idCliente;
	int idUnidadOperativa;
	Date fechaActual;
	
	public ReporteConsolidadoController(IReportesConsolidadoView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
		
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOp = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOp);
		fechaActual = new Date();
	}
	
	@Override
	public void cargar() {
		cargarAnhos();
		cargarMeses();
		cargarFormatos();
		cargarDatos();
	}
	
	private void cargarDatos() {
		view.getLblCliente().setValue(obtenerNombreCliente(idCliente));
		view.getLblUnidadOp().setValue(obtenerNombreUnidadOperativa(idUnidadOperativa));
	}
	
	private void cargarAnhos() {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fechaActual);
	    int anhoActual = cal.get(Calendar.YEAR);
		
		view.getCbAnho().removeAllItems();
		for (int anho = anhoActual; anho >= anhoInicio; anho--) {
			view.getCbAnho().addItem(String.valueOf(anho));
		}
	}
	
	private void cargarMeses() {
		view.getCbMes().removeAllItems();
		for (int i = 1; i <= 12; i++) {
			view.getCbMes().addItem(String.valueOf(i));
		}
	}
	
	private void cargarFormatos() {
		view.getCbFormato().addItem("PDF");
		view.getCbFormato().addItem("XLS");
	}
	
	private String obtenerNombreCliente(int idCliente) {
		String nombreCliente = "";
		ObtenerClienteRequest request= new ObtenerClienteRequest(idCliente);
		ObtenerClienteResponse response = clienteservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreCliente = response.getClienteView().getNombreComercial();
		}
		return nombreCliente;
	}


	private String obtenerNombreUnidadOperativa(int idUnidadOp) {
		String nombreUnidad = "";
		ObtenerUnidadOperativaRequest request= new ObtenerUnidadOperativaRequest(idUnidadOp);
		ObtenerUnidadOperativaResponse response = unidadopservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreUnidad = response.getUnidadOpView().getNombre();
		}
		return nombreUnidad;
	}
}
