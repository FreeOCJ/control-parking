package pe.cp.web.ui.view.configuracion.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
public class BuscarClienteController implements IBuscarClienteHandler {

	private ApplicationContext ac;
	private IBuscarClientesView view;
	private Container container;
	
	@Autowired
	private ClienteService clienteService;
	
	public BuscarClienteController(IBuscarClientesView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteService = ac.getBean(ClienteService.class);	
		this.view  = view;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
		BuscarClienteRequest request = new BuscarClienteRequest();
		request.setNombreComercial(view.getFiltroNombreComercial().getValue());
		view.limpiarTabla();
		
		BuscarClienteResponse response = clienteService.buscar(request);
		if (response.isResultadoEjecucion()){
			List<ClienteView> clientesView = response.getClientesEncontrados();
			for(ClienteView clienteView : clientesView){        		
        		 Item newItem = container.getItem(container.addItem());
        		 newItem.getItemProperty("Código").setValue(clienteView.getId());
        		 newItem.getItemProperty("Nombre Comercial").setValue(clienteView.getNombreComercial());  
        		 newItem.getItemProperty("Razón Social").setValue(clienteView.getRazonSocial());
        		 newItem.getItemProperty("RUC").setValue(clienteView.getRuc());        		 
        	}   			
		}else{
			
		}		
	}

	@Override
	public void irAgregarNuevoCliente() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVOCLIENTE);	
	}

	@Override
	public void irEditarCliente(int idCliente) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARCLIENTE + "/" + String.valueOf(idCliente));
	}

	@Override
	public void eliminarCliente(int idCliente) {
		// TODO Auto-generated method stub

	}

	@Override
	public Container obtenerHeadersContainer() {
		container = new IndexedContainer(); 
		container.addContainerProperty("Código",Integer.class, 0);
        container.addContainerProperty("Nombre Comercial",String.class, "");
        container.addContainerProperty("Razón Social",String.class, "");
        container.addContainerProperty("RUC",String.class, "");        
		return container;
	}

}
