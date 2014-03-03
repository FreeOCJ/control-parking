package pe.cp.web.ui.handler.impl;

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
import pe.cp.core.service.messages.InsertarClienteRequest;
import pe.cp.core.service.messages.InsertarClienteResponse;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.INuevoClienteHandler;
import pe.cp.web.ui.view.INuevoClienteView;

import com.vaadin.data.validator.RegexpValidator;
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
	
	public boolean validarDatosEntrada(){
		
		//1. Validar campos obligatorios
		
		if (view.getRuc().getValue().trim().isEmpty() || view.getNombreComercial().getValue().trim().isEmpty() || view.getRazonSocial().getValue().trim().isEmpty() )
		{
			Notification.show("Se debe llenar todos los datos del formulario nuevo cliente.", Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		//2. Validar que el campo RUC tiene el formato válido.
						
		view.getRuc().addValidator(new RegexpValidator("\\d{11}", ""));
		
		
		if (!view.getRuc().isValid())
		{
			Notification.show("El formato del campo RUC no es correcto, debe tener 11 números", Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		
		//3. Validar el tamaño de los campos
		if (view.getRazonSocial().getValue().trim().length() > 50 )
		{
			Notification.show("Se ha excedido el tamaño del campo Razón Social (máximo 50).", Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		//3. Validar el tamaño de los campos
		if (view.getNombreComercial().getValue().trim().length() > 100)
		{
			Notification.show("Se ha excedido el tamaño del campo Nombre Comercial (máximo 100).", Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		//4. No debe permitir ingresar el mismo RUC para dos empresas.
		
		
		if (clienteService.existeCliente(view.getRuc().getValue())) {
			
			Notification.show("El RUC " + view.getRuc().getValue() + " ya fue ingresado");
			return false;
			
		}
		
		return true;
				
	}
	
	@Override
	public void guardar() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		Notification notification = null;
		
		System.out.println("1");
		if(!validarDatosEntrada()) return;
		System.out.println("2");
		
		InsertarClienteRequest request = new InsertarClienteRequest(idUsuario);
		request.setNombreComercial(view.getNombreComercial().getValue().trim());
		request.setRazonSocial(view.getRazonSocial().getValue().trim());
		request.setRuc(view.getRuc().getValue().trim());
		
		InsertarClienteResponse response = clienteService.agregar(request);
		if (response.isResultadoEjecucion()){
			currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
			UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARCLIENTE + "/" + String.valueOf(response.getIdCliente()));
		}else{
			view.setNotification(new Notification(response.getMensaje(),Type.WARNING_MESSAGE));
			view.getNotification().setPosition(Position.TOP_CENTER);
			view.getNotification().show(Page.getCurrent());
		}
	}

	@Override
	public void cancelar() {
		NavegacionUtil.irBuscarCliente();
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

}
