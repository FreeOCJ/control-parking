package pe.cp.web.ui.view.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
@Theme("controlparking")

public class ReportesController  implements IReportesViewHandler{
	
	private IReportesView view;
	private Container cbClientesContainers;
	private Container cbUnidadOpContainers;
	private ApplicationContext ac;
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	public ReportesController(IReportesView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}

	@Override
	public void irReportesIncidencias() {
		Object clienteSelectId = view.getCbCliente().getValue();
		Object unidadSelectId = view.getCbUnidadOp().getValue();
		int idCliente = 0,idUnidadOperativa = 0;
		if (clienteSelectId != null){
			if (view.getCbCliente().getItem(clienteSelectId).getItemProperty("ID").getValue() != null){
				idCliente = Integer.valueOf(view.getCbCliente().getItem(clienteSelectId).getItemProperty("ID").getValue().toString());
			}
		}
		if (unidadSelectId != null){
			if (view.getCbUnidadOp().getItem(unidadSelectId).getItemProperty("ID").getValue() != null){
				idUnidadOperativa = Integer.valueOf(view.getCbUnidadOp().getItem(unidadSelectId).getItemProperty("ID").getValue().toString());
			}
		}
		
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTES_INCIDENCIAS + "/" + idCliente + "/" + idUnidadOperativa);		
	}

	@Override
	public void cargar() {
		cargarComboClientes();		
	}
	
	@SuppressWarnings("unchecked")
	private void cargarComboClientes(){
		view.getCbCliente().setContainerDataSource(prepararContainerComboCliente());
		BuscarClienteRequest request = new  BuscarClienteRequest();
		request.setNombreComercial("");
		BuscarClienteResponse response = clienteservice.buscar(request);
		
		if (response.isResultadoEjecucion()){
			for(ClienteView clienteview:response.getClientesEncontrados()){
				Item nuevoItemCombo = cbClientesContainers.getItem(cbClientesContainers.addItem());
				nuevoItemCombo.getItemProperty("ID").setValue(clienteview.getId());  
				nuevoItemCombo.getItemProperty("NOMBRE").setValue(clienteview.getNombreComercial());
			}			
		}		
	}
	
	private Container prepararContainerComboCliente() {
		cbClientesContainers = new IndexedContainer(); 
		cbClientesContainers.addContainerProperty("ID", Integer.class, "");
		cbClientesContainers.addContainerProperty("NOMBRE", String.class, "");
		return cbClientesContainers;
    }
	
	private Container prepararContainerComboUnidadOperativa() {
		cbUnidadOpContainers = new IndexedContainer(); 
		cbUnidadOpContainers.addContainerProperty("ID", Integer.class, "");
		cbUnidadOpContainers.addContainerProperty("NOMBRE", String.class, "");
		return cbUnidadOpContainers;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void cargarUnidadesOperativaPorCliente() {
		view.getCbUnidadOp().setContainerDataSource(prepararContainerComboUnidadOperativa());
		Object clienteSelectId = view.getCbCliente().getValue();
		Object valor = view.getCbCliente().getItem(clienteSelectId).getItemProperty("ID").getValue();
		int idCliente = Integer.valueOf(valor.toString());
		ObtenerUnidadOpPorClienteRequest request = new ObtenerUnidadOpPorClienteRequest(idCliente);
		ObtenerUnidadpOpPorClienteResponse response = unidadopservice.obtenerUnidadesPorCliente(request);
		
		if (response.isResultadoEjecucion()){
			for(UnidadOperativaView unidadOpView : response.getUnidadesOpView()){
				Item nuevoItemCombo = cbUnidadOpContainers.getItem(cbUnidadOpContainers.addItem());
				nuevoItemCombo.getItemProperty("ID").setValue(unidadOpView.getId());  
				nuevoItemCombo.getItemProperty("NOMBRE").setValue(unidadOpView.getNombre());
			}
		}
	}
}
