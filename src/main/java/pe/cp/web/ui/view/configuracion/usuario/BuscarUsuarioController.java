package pe.cp.web.ui.view.configuracion.usuario;

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

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;



import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;


@Component
@Scope("prototype")
@SuppressWarnings("unchecked")
public class BuscarUsuarioController implements IBuscarUsuarioViewHandler {
	ApplicationContext ac;
	private IBuscarUsuarioView view;
	private Container container;
	
	@Autowired
	private UsuarioService usuarioservice;
	
	public BuscarUsuarioController(IBuscarUsuarioView view){		
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuarioservice = ac.getBean(UsuarioService.class);		
		this.view = view;
	}

	@Override
	public void  buscarpornombre(String nombresApellidos) {		
		BuscarUsuarioRequest request = new BuscarUsuarioRequest();
		request.setNombresApellidos(nombresApellidos);	
		view.limpiarTabla();		
		        
        BuscarUsuarioResponse response = usuarioservice.buscarOr(request);
		try {
			List<UsuarioView> usuarios = response.getUsuariosEncontrados();
	        if(usuarios != null && usuarios.size() > 0){
	        	for(UsuarioView usuarioview:usuarios){
	        		 Item newItem = container.getItem(container.addItem());
	        		 newItem.getItemProperty("Código").setValue(usuarioview.getId());
	        		 newItem.getItemProperty("Nombre Completo").setValue(usuarioview.getNombres() + " " + usuarioview.getApellidos());  
	        		 newItem.getItemProperty("Usuario").setValue(usuarioview.getLogin());
	        		 newItem.getItemProperty("Roles").setValue(usuarioview.getRolesAsString());
	        		 newItem.getItemProperty("Cargo").setValue(usuarioview.getCargo());
	        	}        
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}      	
	}

	@Override
	public Container setHeaderTable() {
		System.out.println("setHeader");
		container = new IndexedContainer(); 
		container.addContainerProperty("Código",Integer.class, 0);
        container.addContainerProperty("Nombre Completo",String.class, "");
        container.addContainerProperty("Usuario",String.class, "");
        container.addContainerProperty("Roles",String.class, "");
        container.addContainerProperty("Cargo",String.class, "");
		return container;
	}

	@Override
	public void irAgregarNuevoUsuario() {
		NavegacionUtil.irAgregarUsuarioSistema();;		
	}

	@Override
	public void irEditarUsuario(int idUsuario) {
		NavegacionUtil.irEditarUsuario(0, idUsuario);	
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			NavegacionUtil.irLogin();
		}else{
			if (!currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				NavegacionUtil.irMain();
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
