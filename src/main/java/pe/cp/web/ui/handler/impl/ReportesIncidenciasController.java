package pe.cp.web.ui.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.web.ui.handler.IReportesIncidenciasViewHandler;
import pe.cp.web.ui.view.IReportesIncidenciasView;

@Component
@Scope("prototype")
public class ReportesIncidenciasController implements IReportesIncidenciasViewHandler{
	ApplicationContext ac;
	@SuppressWarnings("unused")
	private IReportesIncidenciasView view;
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	
	public ReportesIncidenciasController(IReportesIncidenciasView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}


	@Override
	public String obtenerNombreCliente(int idCliente) {
		String nombreCliente = "";
		ObtenerClienteRequest request= new ObtenerClienteRequest(idCliente);
		ObtenerClienteResponse response = clienteservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreCliente = response.getClienteView().getNombreComercial();
		}
		return nombreCliente;
	}


	@Override
	public String obtenerNombreUnidadOperativa(int idUnidadOp) {
		String nombreUnidad = "";
		ObtenerUnidadOperativaRequest request= new ObtenerUnidadOperativaRequest(idUnidadOp);
		ObtenerUnidadOperativaResponse response = unidadopservice.obtenerPorId(request);
		if (response.isResultadoEjecucion()){
			nombreUnidad = response.getUnidadOpView().getNombre();
		}
		return nombreUnidad;
	}
}
