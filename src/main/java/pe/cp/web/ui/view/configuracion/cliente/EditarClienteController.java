package pe.cp.web.ui.view.configuracion.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.web.ui.ControlParkingUI;

@Component
@Scope("prototype")
public class EditarClienteController implements IEditarClienteHandler {

	private ApplicationContext ac;
	private IEditarClienteView view;
	private Container usuariosContainer;
	private Container unidadesOpContainer;
	
	@Autowired
	private ClienteService clienteService;
	
	public EditarClienteController(IEditarClienteView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteService = ac.getBean(ClienteService.class);	
		this.view  = view;
	}
	
	@Override
	public void guardar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargar() {
		ObtenerClienteRequest request = new ObtenerClienteRequest(view.getIdCliente());
		ObtenerClienteResponse response = clienteService.obtenerPorId(request);
		
		if (response.isResultadoEjecucion()){
			view.getNombreComercial().setValue(response.getClienteView().getNombreComercial());
			view.getRazonSocial().setValue(response.getClienteView().getRazonSocial());
			view.getRuc().setValue(response.getClienteView().getRuc());
		}else{
           //TODO			
		}					
	}

	@Override
	public Container obtenerHeadersUsuariosContainer() {
		usuariosContainer = new IndexedContainer(); 
		usuariosContainer.addContainerProperty("Código",Integer.class, 0);
		usuariosContainer.addContainerProperty("Nombre Completo",String.class, "");
		usuariosContainer.addContainerProperty("Usuario",String.class, "");
		usuariosContainer.addContainerProperty("Cargo",String.class, "");        
		return usuariosContainer;
	}

	@Override
	public Container obtenerHeadersUnidadesOpContainer() {
		unidadesOpContainer = new IndexedContainer(); 
		unidadesOpContainer.addContainerProperty("Código",Integer.class, 0);
		unidadesOpContainer.addContainerProperty("Nombre",String.class, "");
		unidadesOpContainer.addContainerProperty("Dirección",String.class, "");
		unidadesOpContainer.addContainerProperty("Nro. Cajones",String.class, "");        
		return unidadesOpContainer;
	}

	@Override
	public void irAgregarNuevaUnidadOperativa() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA);		
	}

	@Override
	public void irAgregarNuevoUsuario() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA);		
	}

}
