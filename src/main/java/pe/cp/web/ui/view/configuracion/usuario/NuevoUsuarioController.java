package pe.cp.web.ui.view.configuracion.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.RolService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;
import pe.cp.core.service.messages.ValidarDatosUsuarioRequest;
import pe.cp.core.service.messages.ValidarDatosUsuarioResponse;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

@Component
@Scope("prototype")
public class NuevoUsuarioController implements INuevoUsuarioViewHandler {
	ApplicationContext ac;
	private INuevoUsuarioView view;
	
	@Autowired
	private UsuarioService usuarioservice;
	@Autowired
	private RolService rolservice;
	
	public NuevoUsuarioController(INuevoUsuarioView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuarioservice = ac.getBean(UsuarioService.class);
		rolservice = ac.getBean(RolService.class);	
		this.view = view;
		
		cargarRoles();
	}
	
	@Override
	public void cancelar() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);
	}

	public boolean validarDatosEntrada(){
		
				
		//Primero se validan los campos obligatorios
		if(view.getLogin().getValue().trim().isEmpty() || view.getNombres().getValue().trim().isEmpty() ||
		   view.getApellidos().getValue().trim().isEmpty() || view.getCargo().getValue().trim().isEmpty() ||
		   view.getCorreoElectronico().getValue().isEmpty()){
			Notification.show("Se debe llenar todos los datos del formulario.", Notification.Type.WARNING_MESSAGE);
			return false;
		}else{
			//Luego se valida el formato del correo electronico
			EmailValidator emailValidator = new EmailValidator("");
			view.getCorreoElectronico().addValidator(emailValidator);
			
			if (!view.getCorreoElectronico().isValid())
			{
				Notification.show("El correo electrónico no tiene un formato valido;\n\t", Notification.Type.WARNING_MESSAGE);
				return false;
			}
			else
			{
				//Se valida que el login del usuario sea unico
				ValidarDatosUsuarioRequest request = new ValidarDatosUsuarioRequest(0, view.getLogin().getValue().trim());
				ValidarDatosUsuarioResponse response = usuarioservice.validarDatosUsuario(request);
				if (!response.isResultadoEjecucion()){
					Notification.show(response.getMensaje(), Notification.Type.WARNING_MESSAGE);
					return false;
				}
			}		
			
	}
		
		return true;
		
	}
	
	@Override
	public void guardar() {
		Notification notification = null;		
		
		if (!validarDatosEntrada()) return;
		else{
			InsertarUsuarioRequest request = new InsertarUsuarioRequest();
			request.setApellidos(view.getApellidos().getValue().trim());
			request.setCargo(view.getCargo().getValue().trim());
			request.setEmail(view.getCorreoElectronico().getValue().trim());
			request.setLogin(view.getLogin().getValue().trim().toLowerCase());
			request.setNombres(view.getNombres().getValue().trim());
			request.setIdRoles(new ArrayList<Integer>());
			request.setIdCliente(view.getIdCliente());
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayList<RolView> rolesView = new ArrayList<RolView>((Collection)view.getRoles().getValue());
			for (RolView rolView : rolesView) {
				request.getIdRoles().add(rolView.getId());
			}
					
			InsertarUsuarioResponse response = usuarioservice.agregar(request);
			Subject currentUser = SecurityUtils.getSubject();
			if (response.isResultadoEjecucion()){
				currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
				if (view.getIdCliente()==0){
					UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);}
				else{
					NavegacionUtil.irEditarCliente(view.getIdCliente());
				}			
			}else{
				notification = new Notification(response.getMensaje(),Type.WARNING_MESSAGE);
				notification.setPosition(Position.TOP_CENTER);
				notification.show(Page.getCurrent());	
			}
		}
	}

	@Override
	public void cargarRoles() {
		List<RolView> rolesView = rolservice.obtenerTodos();
		BeanItemContainer<RolView> rolBeans  = new BeanItemContainer<RolView>(RolView.class);
		
		if (view.getIdCliente() == 0){
			for (RolView rolView : rolesView)
			   if (!rolView.getNombre().toUpperCase().equals(Rol.CLIENTE))
			      rolBeans.addBean(rolView);
			view.getRoles().setContainerDataSource(rolBeans);	
		}else{
			HashSet<RolView> preselectedRoles = new HashSet<RolView>();
			for (RolView rolView : rolesView) 
				if (rolView.getNombre().toUpperCase().equals(Rol.CLIENTE))
					Collections.addAll(preselectedRoles, rolView);
										
			view.getRoles().setValue(preselectedRoles);
			view.getRoles().setImmediate(true);
			view.getRoles().setVisible(false);
		}
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
