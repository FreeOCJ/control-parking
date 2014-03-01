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

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.messages.ActualizarClienteRequest;
import pe.cp.core.service.messages.ActualizarClienteResponse;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteResponse;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

@Component
@Scope("prototype")
public class EditarClienteController implements IEditarClienteHandler {

	private ApplicationContext ac;
	private IEditarClienteView view;
	private Container usuariosContainer;
	private Container unidadesOpContainer;
	
	private final static String CODIGO_UNIDAD = "Código";
	private final static String NOMBRE_UNIDAD = "Nombre";
	private final static String DIRECCION_UNIDAD = "Dirección";
	private final static String NUMERO_CAJONES_UNIDAD = "Nro. Cajones";
	private final static String BOTONES_UNIDAD = "";
	
	private final static String CODIGO_USUARIO = "Código";
	private final static String NOMBRE_USUARIO = "Nombre Completo";
	private final static String LOGIN_USUARIO = "Usuario";
	private final static String ROLES_USUARIO = "Roles";
	private final static String CARGO_USUARIO = "Cargo";
	private final static String BOTONES_USUARIO = "";
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public EditarClienteController(IEditarClienteView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteService = ac.getBean(ClienteService.class);
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		usuarioService = ac.getBean(UsuarioService.class);
		this.view  = view;
	}
	
	public static String[] obtenerColumnasVisiblesUnidadOp(){
		String[] visibles = {NOMBRE_UNIDAD, DIRECCION_UNIDAD, NUMERO_CAJONES_UNIDAD, BOTONES_UNIDAD};
		return visibles;
	}
	
	public static String[] obtenerColumnasVisiblesUsuario(){
		String[] visibles = {NOMBRE_USUARIO, LOGIN_USUARIO, ROLES_USUARIO, CARGO_USUARIO, BOTONES_USUARIO};
		return visibles;
	}
	
	@Override
	public void guardar() {
		ActualizarClienteRequest request = new ActualizarClienteRequest();
		request.setIdCliente(view.getIdCliente());
		request.setNombreComercial(view.getNombreComercial().getValue().trim());
		request.setRazonSocial(view.getRazonSocial().getValue().trim());
		request.setRuc(view.getRuc().getValue().trim());
		
		ActualizarClienteResponse response = new ActualizarClienteResponse();
		Subject currentUser = SecurityUtils.getSubject();
		
		if (response.isResultadoEjecucion()){		
			currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
			UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARCLIENTES);			
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

	@SuppressWarnings("unchecked")
	@Override
	public void cargar() {
		ObtenerClienteRequest request = new ObtenerClienteRequest(view.getIdCliente());
		ObtenerClienteResponse response = clienteService.obtenerPorId(request);
		
		if (response.isResultadoEjecucion()){
			view.getNombreComercial().setValue(response.getClienteView().getNombreComercial());
			view.getRazonSocial().setValue(response.getClienteView().getRazonSocial());
			view.getRuc().setValue(response.getClienteView().getRuc());
			
			ObtenerUnidadOpPorClienteRequest requestUnidades = new ObtenerUnidadOpPorClienteRequest(view.getIdCliente());
			ObtenerUnidadpOpPorClienteResponse responseUnidades = unidadOpService.obtenerUnidadesPorCliente(requestUnidades);
			
			if (responseUnidades.isResultadoEjecucion()){				
				for(UnidadOperativaView unidadOpView : responseUnidades.getUnidadesOpView()){        		
	        		 Item newItem = unidadesOpContainer.getItem(unidadesOpContainer.addItem());
	        		 newItem.getItemProperty(CODIGO_UNIDAD).setValue(unidadOpView.getId());
	        		 newItem.getItemProperty(NOMBRE_UNIDAD).setValue(unidadOpView.getNombre());  
	        		 newItem.getItemProperty(DIRECCION_UNIDAD).setValue(unidadOpView.getDireccion());
	        		 newItem.getItemProperty(NUMERO_CAJONES_UNIDAD).setValue(unidadOpView.getNroCajones());        		 
	        	}
			}
			
			ObtenerUsuarioPorClienteRequest requestClientes = new ObtenerUsuarioPorClienteRequest(view.getIdCliente());
			ObtenerUsuarioPorClienteResponse responseClientes = usuarioService.obtenerUsuariosPorCliente(requestClientes);
			
			if (responseClientes.isResultadoEjecucion()){
		        if(responseClientes.getUsuariosView() != null && responseClientes.getUsuariosView().size() > 0){
		        	for(UsuarioView usuarioview:responseClientes.getUsuariosView()){
		        		 Item usuario = usuariosContainer.getItem(usuariosContainer.addItem());
		        		 usuario.getItemProperty(CODIGO_USUARIO).setValue(usuarioview.getId());
		        		 usuario.getItemProperty(NOMBRE_USUARIO).setValue(usuarioview.getNombres() + " " + usuarioview.getApellidos());  
		        		 usuario.getItemProperty(LOGIN_USUARIO).setValue(usuarioview.getLogin());
		        		 usuario.getItemProperty(ROLES_USUARIO).setValue(usuarioview.getRolesAsString());
		        		 usuario.getItemProperty(CARGO_USUARIO).setValue(usuarioview.getCargo());
		        	}        
		        }
			}
		}else{
           Logger.getAnonymousLogger().log(Level.WARNING, "No se pudo obtener al cliente");		
		}					
	}

	@Override
	public Container obtenerHeadersUsuariosContainer() {
		usuariosContainer = new IndexedContainer(); 
		usuariosContainer.addContainerProperty(CODIGO_USUARIO,Integer.class, 0);
		usuariosContainer.addContainerProperty(NOMBRE_USUARIO,String.class, "");
		usuariosContainer.addContainerProperty(LOGIN_USUARIO,String.class, "");
		usuariosContainer.addContainerProperty(ROLES_USUARIO,String.class, "");
		usuariosContainer.addContainerProperty(CARGO_USUARIO,String.class, "");        
		return usuariosContainer;
	}

	@Override
	public Container obtenerHeadersUnidadesOpContainer() {
		unidadesOpContainer = new IndexedContainer(); 
		unidadesOpContainer.addContainerProperty(CODIGO_UNIDAD,Integer.class, 0);
		unidadesOpContainer.addContainerProperty(NOMBRE_UNIDAD,String.class, "");
		unidadesOpContainer.addContainerProperty(DIRECCION_UNIDAD,String.class, "");
		unidadesOpContainer.addContainerProperty(NUMERO_CAJONES_UNIDAD,Integer.class, 0);        
		return unidadesOpContainer;
	}

	@Override
	public void irAgregarNuevaUnidadOperativa() {
		NavegacionUtil.irAgregarUnidadOperativa(view.getIdCliente());		
	}

	@Override
	public void irAgregarNuevoUsuario() {
		NavegacionUtil.irAgregarUsuarioCliente(view.getIdCliente());	
	}

	@Override
	public void irEditarUnidadOperativa(int idUnidadOperativa) {
		NavegacionUtil.irEditarUnidadOperativa(view.getIdCliente(), idUnidadOperativa);		
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
	public void irEditarUsuario(int idUsuario,int idCliente) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARUSUARIO + "/" + idCliente + "/" + idUsuario);
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

	@Override
	public void elimiarUsuario() {
		
		
		
	}

}
