package pe.cp.web.ui.view.configuracion.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.messages.InsertarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteResponse;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.data.Container;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
@Scope("prototype")
public class NuevoClienteController implements INuevoClienteHandler {

	private ApplicationContext ac;
	private INuevoClienteView view;
	
	@Autowired
	private ClienteService clienteService;
	
	public NuevoClienteController(INuevoClienteView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteService = ac.getBean(ClienteService.class);	
		this.view  = view;
	}
	
	@Override
	public void guardar() {
		InsertarClienteRequest request = new InsertarClienteRequest();
		request.setNombreComercial(view.getNombreComercial().getValue().trim());
		request.setRazonSocial(view.getNombreComercial().getValue().trim());
		request.setRuc(view.getRuc().getValue().trim());
		
		InsertarClienteResponse response = clienteService.agregar(request);
		if (response.isResultadoEjecucion()){
			UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARCLIENTE + "/" + String.valueOf(response.getIdCliente()));
		}else{
			view.setNotification(new Notification(response.getMensaje(),Type.WARNING_MESSAGE));
			view.getNotification().setPosition(Position.TOP_CENTER);
			view.getNotification().show(Page.getCurrent());
		}
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub

	}

}
