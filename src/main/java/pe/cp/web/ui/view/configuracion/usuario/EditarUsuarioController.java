package pe.cp.web.ui.view.configuracion.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.service.RolService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioResponse;
import pe.cp.web.ui.ControlParkingUI;

@Component
@Scope("prototype")
public class EditarUsuarioController implements IEditarUsuarioViewHandler {
	ApplicationContext ac;
	private IEditarUsuarioView view;
	
	@Autowired
	private UsuarioService usuarioservice;
	@Autowired
	private RolService rolservice;
	
	public EditarUsuarioController(IEditarUsuarioView view){		
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuarioservice = ac.getBean(UsuarioService.class);
		rolservice = ac.getBean(RolService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {
		cargarRoles();
		
		ObtenerUsuarioRequest request = new ObtenerUsuarioRequest(view.getIdUsuario());
		ObtenerUsuarioResponse response = usuarioservice.buscar(request);
		
		if (response.isResultadoEjecucion()){
			view.getApellidos().setValue(response.getUsuarioView().getApellidos());
			view.getNombres().setValue(response.getUsuarioView().getNombres());
			view.getCargo().setValue(response.getUsuarioView().getCargo());
			view.getLogin().setValue(response.getUsuarioView().getLogin());
			view.getCorreoElectronico().setValue(response.getUsuarioView().getEmail());
			
			HashSet<RolView> preselected = new HashSet<RolView>();
			for (RolView rolView : response.getRolesView()) {				
				Collections.addAll(preselected, rolView);
			}
										
			view.getRoles().setValue(preselected);
			view.getRoles().setImmediate(true);
		}
	}
	
	@Override
	public void cargarRoles() {
		List<RolView> rolesView = rolservice.obtenerTodos();
		BeanItemContainer<RolView> rolBeans  = new BeanItemContainer<RolView>(RolView.class);
		
		for (RolView rolView : rolesView) {
			rolBeans.addBean(rolView);
		}
		
		view.getRoles().setContainerDataSource(rolBeans);		
	}

	@Override
	public void actualizar() {
		ActualizarUsuarioRequest request = new ActualizarUsuarioRequest();
		request.setIdUsuario(view.getIdUsuario());
		request.setApellidos(view.getApellidos().getValue().trim());
		request.setCargo(view.getCargo().getValue().trim());
		request.setEmail(view.getCorreoElectronico().getValue().trim());
		request.setLogin(view.getLogin().getValue().trim().toLowerCase());
		request.setNombres(view.getNombres().getValue().trim());
		request.setIdRoles(new ArrayList<Integer>());
		
		ArrayList<RolView> rolesView = new ArrayList<RolView>((Collection)view.getRoles().getValue());
		for (RolView rolView : rolesView) {
			request.getIdRoles().add(rolView.getId());
		}
		
		ActualizarUsuarioResponse response = usuarioservice.actualizar(request);
		if (response.isResultadoEjecucion()){
			UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);
		}else{
			view.setNotification(new Notification(response.getMensaje(),Type.WARNING_MESSAGE));
			view.getNotification().setPosition(Position.TOP_CENTER);
			view.getNotification().show(Page.getCurrent());	
		}
	}

	@Override
	public void cancelar() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);		
	}

}
