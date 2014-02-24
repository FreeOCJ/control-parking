package pe.cp.web.ui.view.configuracion.cliente;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.ClienteService;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
@Scope("prototype")
public class BuscarClienteController implements IBuscarClienteHandler {

	private ApplicationContext ac;
	private IBuscarClientesView view;
	private Container container;
	
	private final static String CODIGO_CLIENTE = "Código";
	private final static String NOMBRE_CLIENTE = "Nombre Comercial";
	private final static String RAZONSOCIAL_CLIENTE = "Razón Social";
	private final static String RUC_CLIENTE = "RUC";
	private final static String BOTONES_CLIENTE = "";
	
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
			//TODO
		}		
	}

	@Override
	public void irAgregarNuevoCliente() {
		NavegacionUtil.irAgregarCliente();	
	}

	@Override
	public void irEditarCliente(int idCliente) {
		NavegacionUtil.irEditarCliente(idCliente);
	}

	@Override
	public void eliminarCliente(int idCliente) {
		// TODO Auto-generated method stub

	}
	
	public static String[] obtenerColumnasVisiblesCliente(){
		String[] visibles = {NOMBRE_CLIENTE, RAZONSOCIAL_CLIENTE, RUC_CLIENTE, BOTONES_CLIENTE};
		return visibles;
	}
	
	@Override
	public Container obtenerHeadersContainer() {
		container = new IndexedContainer(); 
		container.addContainerProperty(CODIGO_CLIENTE,Integer.class, 0);
        container.addContainerProperty(NOMBRE_CLIENTE,String.class, "");
        container.addContainerProperty(RAZONSOCIAL_CLIENTE,String.class, "");
        container.addContainerProperty(RUC_CLIENTE,String.class, "");        
		return container;
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			UI.getCurrent().getNavigator().navigateTo("");
		}else{
			if (!currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);
			}
		}
	}

	@Override
	public void mostrarMensajeInicio() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null && currentUser.isAuthenticated()){
			if (currentUser.getSession().getAttribute("mensaje") != null){
				Notification notification = (Notification) currentUser.getSession().getAttribute("mensaje");
				notification.setPosition(Position.TOP_CENTER);
				notification.show(Page.getCurrent());				
			}
		}		
	}

}
