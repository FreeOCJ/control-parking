package pe.cp.web.ui.view.configuracion.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.service.RolService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;
import pe.cp.web.ui.ControlParkingUI;

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

	@Override
	public void guardar() {
		Notification notification = null;		
		
		InsertarUsuarioRequest request = new InsertarUsuarioRequest();
		request.setApellidos(view.getApellidos().getValue().trim());
		request.setCargo(view.getCargo().getValue().trim());
		request.setEmail(view.getCorreoElectronico().getValue().trim());
		request.setLogin(view.getLogin().getValue().trim().toLowerCase());
		request.setNombres(view.getNombres().getValue().trim());
		request.setIdRoles(new ArrayList<Integer>());
				
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<RolView> rolesView = new ArrayList<RolView>((Collection)view.getRoles().getValue());
		for (RolView rolView : rolesView) {
			request.getIdRoles().add(rolView.getId());
		}
				
		InsertarUsuarioResponse response = usuarioservice.agregar(request);
		if (response.isResultadoEjecucion()){
			UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);
		}else{
			notification = new Notification(response.getMensaje(),Type.WARNING_MESSAGE);
			notification.setPosition(Position.TOP_CENTER);
			notification.show(Page.getCurrent());	
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

}