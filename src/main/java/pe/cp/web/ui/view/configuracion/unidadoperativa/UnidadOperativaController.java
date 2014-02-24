package pe.cp.web.ui.view.configuracion.unidadoperativa;

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

import pe.cp.core.domain.Rol;
import pe.cp.core.service.ClienteService;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpRequest;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.UtilService;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.domain.TarifaConsolidadoView;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.messages.ActualizarUnidadOpRequest;
import pe.cp.core.service.messages.ActualizarUnidadOpResponse;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.AgregarUsuarioUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerConsTarifasRequest;
import pe.cp.core.service.messages.ObtenerConsTarifasResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpResponse;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaRequest;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
@Scope("prototype")
public class UnidadOperativaController implements IUnidadOperativaHandler {

	ApplicationContext ac;
	private IUnidadOperativaView view;
	private Container categoriasContainer;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private UsuarioService usuarioService;
	
	public UnidadOperativaController(IUnidadOperativaView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		utilService = ac.getBean(UtilService.class);
		clienteService = ac.getBean(ClienteService.class);
		usuarioService = ac.getBean(UsuarioService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {		
		cargarDepartamentos();
		cargarTwinColSelects();
		
		ObtenerClienteRequest requestCliente = new ObtenerClienteRequest(view.getIdCliente());
		ObtenerClienteResponse responseCliente = clienteService.obtenerPorId(requestCliente);
		if (responseCliente.isResultadoEjecucion()){
			view.getNombreComercialCliente().setValue(responseCliente.getClienteView().getNombreComercial());
		}else{
			//TODO
		}	
		
		//Cuando entra en modo edicion
		if (view.getIdUnidadOperativa() > 0){
			view.getTitulo().setValue("Editar Unidad Operativa");
			ObtenerUnidadOperativaRequest request = new ObtenerUnidadOperativaRequest(view.getIdUnidadOperativa());
			ObtenerUnidadOperativaResponse response  = unidadOpService.obtenerPorId(request);
			
			if (response.isResultadoEjecucion()){
				view.getDepartamento().setValue(response.getUnidadOpView().getDepartamento());
				view.getDireccion().setValue(response.getUnidadOpView().getDireccion());
				view.getProvincia().setValue(response.getUnidadOpView().getProvincia());				
				view.getHoraApertura().setValue(response.getUnidadOpView().getHoraApertura());
				view.getHoraCierre().setValue(response.getUnidadOpView().getHoraCierre());
				view.getNombre().setValue(response.getUnidadOpView().getNombre());
				view.getNumeroCajones().setValue(String.valueOf(response.getUnidadOpView().getNroCajones()));
				view.getDistrito().setValue(response.getUnidadOpView().getDistrito());
				
				//Cargar los valores seleccionados de usuarios
				precargarUsuariosSeleccionados(view.getIdUnidadOperativa(), Rol.APROBADOR);
				precargarUsuariosSeleccionados(view.getIdUnidadOperativa(), Rol.CLIENTE);
				precargarUsuariosSeleccionados(view.getIdUnidadOperativa(), Rol.OPERADOR);
				
				//Se cargar las categorias
				cargarCategorias();
			}else{
				Logger.getAnonymousLogger().log(Level.SEVERE, response.getMensaje());
			}
		}else{
			view.getTitulo().setValue("Nueva Unidad Operativa");
			view.getTablas().setVisible(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarCategorias() {
		ObtenerConsTarifasRequest request = new ObtenerConsTarifasRequest(view.getIdUnidadOperativa());
		ObtenerConsTarifasResponse response = unidadOpService.obtenerTarifas(request);
		
		if (response.isResultadoEjecucion()){
			List<TarifaConsolidadoView> tarifasView = response.getTarifas();
			
			for(TarifaConsolidadoView tarifaView : tarifasView){        		
        		 Item newItem = categoriasContainer.getItem(categoriasContainer.addItem());
        		 newItem.getItemProperty("Categoría").setValue(tarifaView.getCategoria());  
        		 newItem.getItemProperty("Tarifas").setValue(tarifaView.getMontos());
        	}   			
		}else{
			//TODO
		}
	}
	
	/***
	 * Permite precargar las listas TwinColSelect con los usuarios seleccionados
	 * ya guardados en base de datos.
	 * @param idUnidadOperativa		Id de la unidad operativa en edicion
	 * @param rol					Rol.OPERADOR, Rol.APROBADOR, Rol.CLIENTE
	 */
	private void precargarUsuariosSeleccionados(int idUnidadOperativa, String rol) {
	   ObtenerUsuarioPorUnidadOpRequest request = new ObtenerUsuarioPorUnidadOpRequest(idUnidadOperativa, rol);
	   ObtenerUsuarioPorUnidadOpResponse response = usuarioService.obtenerUsuariosPorUnidadOP(request);
	   
	   if (response.isResultadoEjecucion()) {
			HashSet<UsuarioView> preselectedUsuarios = new HashSet<UsuarioView>();
			
			for (UsuarioView usuarioView : response.getUsuariosView())				
				Collections.addAll(preselectedUsuarios, usuarioView);
			
			if (rol.equals(Rol.APROBADOR)) {
				view.getAprobadores().setValue(preselectedUsuarios);
				view.getAprobadores().setImmediate(true);
			} else if (rol.equals(Rol.CLIENTE)) {
				view.getUsuarios().setValue(preselectedUsuarios);
				view.getUsuarios().setImmediate(true);
			} else if (rol.equals(Rol.OPERADOR)) {
				view.getOperadores().setValue(preselectedUsuarios);
				view.getOperadores().setImmediate(true);
			}
		}else {
			//TODO Mostrar mensaje?
			Logger.getAnonymousLogger().log(Level.SEVERE, response.getMensaje());
		}
	}

	private void cargarTwinColSelects(){
		//Cargar usuarios aprobadores
		ObtenerUsuariosSistemaResponse responseAprobadores = 
				usuarioService.obtenerUsuariosSistema(new ObtenerUsuariosSistemaRequest(ObtenerUsuariosSistemaRequest.ROL_APROBADOR));
		
		if (responseAprobadores.isResultadoEjecucion()){			
			BeanItemContainer<UsuarioView> usuarioAprobadoresBeans  = new BeanItemContainer<UsuarioView>(UsuarioView.class);
			for (UsuarioView usuarioView : responseAprobadores.getUsuariosView())
				usuarioAprobadoresBeans.addBean(usuarioView);
			
			view.getAprobadores().setContainerDataSource(usuarioAprobadoresBeans);
		}
		
		//cargar usuarios operadores
		ObtenerUsuariosSistemaResponse responseOperadores = 
				usuarioService.obtenerUsuariosSistema(new ObtenerUsuariosSistemaRequest(ObtenerUsuariosSistemaRequest.ROL_OPERADOR));
		if (responseOperadores.isResultadoEjecucion()){			
			BeanItemContainer<UsuarioView> usuarioOperadoresBeans  = new BeanItemContainer<UsuarioView>(UsuarioView.class);
			for (UsuarioView usuarioView : responseOperadores.getUsuariosView())
				usuarioOperadoresBeans.addBean(usuarioView);
			
			view.getOperadores().setContainerDataSource(usuarioOperadoresBeans);
		}
		
		//cargar usuarios clientes
		ObtenerUsuarioPorClienteResponse responseUsuariosCliente =
				usuarioService.obtenerUsuariosPorCliente(new ObtenerUsuarioPorClienteRequest(view.getIdCliente()));
		if (responseUsuariosCliente.isResultadoEjecucion()){			
			BeanItemContainer<UsuarioView> usuarioUsuariosBeans  = new BeanItemContainer<UsuarioView>(UsuarioView.class);
			for (UsuarioView usuarioView : responseUsuariosCliente.getUsuariosView())
				usuarioUsuariosBeans.addBean(usuarioView);
			
			view.getUsuarios().setContainerDataSource(usuarioUsuariosBeans);
		}
	}
	
	@Override
	public void cargarDepartamentos() {
		List<String> departamentos = utilService.obtenerDepartamentos();
		view.getProvincia().removeAllItems();
		view.getDistrito().removeAllItems();
		
		for (String dpto : departamentos) {
			view.getDepartamento().addItem(dpto);
		}
	}

	@Override
	public void cargarProvincias() {
		List<String> provincias = utilService.obtenerProvincias((String) view.getDepartamento().getValue());
		view.getProvincia().removeAllItems();
		view.getDistrito().removeAllItems();
		for (String prov : provincias) {
			view.getProvincia().addItem(prov);
		}
	}

	@Override
	public void cargarDistritos() {
		List<String> distritos = utilService.obtenerDistritos((String) view.getDepartamento().getValue(), (String) view.getProvincia().getValue());
		view.getDistrito().removeAllItems();
		for (String dist : distritos) {
			view.getDistrito().addItem(dist);
		}
	}

	@Override
	public Container obtenerHeadersCategoriaContainer() {
		categoriasContainer = new IndexedContainer(); 
		categoriasContainer.addContainerProperty("Categoría",String.class, "");
		categoriasContainer.addContainerProperty("Tarifas",String.class, "");		
		return categoriasContainer;
	}

	@Override
	public void guardar() {
		if (view.getIdUnidadOperativa() == 0){ 		
			//Caso Nueva Unidad Operativa: Solo se ingresan datos principales. 
			//Tablas asociadas solo se ingresan por el modo de Edicion
			AgregarUnidadOperativaRequest request = new AgregarUnidadOperativaRequest();
			request.setDepartamento((String) view.getDepartamento().getValue());
			request.setDireccion(view.getDireccion().getValue());
			request.setDistrito((String) view.getDistrito().getValue());
			request.setNombre(view.getNombre().getValue());
			request.setProvincia((String) view.getProvincia().getValue());
			request.setIdCliente(view.getIdCliente());
			request.setHoraInicio(view.getHoraApertura().getValue());
			request.setHoraCierre(view.getHoraCierre().getValue());
			request.setNroCajones(Integer.valueOf(view.getNumeroCajones().getValue()));
			
			AgregarUnidadOperativaResponse response = unidadOpService.agregar(request);
			Subject currentUser = SecurityUtils.getSubject();
			if (response.isResultadoEjecucion()){
				currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + response.getIdUnidadOperativa());
			}else{
				Logger.getAnonymousLogger().log(Level.SEVERE, response.getMensaje());
				//TODO Mostrar mensaje de error al usuario
			}
		}else{		
			//Caso Actualizar Unidad Operativa
			actualizar();
		}
	}

	@Override
	public void actualizar() {
	   ActualizarUnidadOpRequest request = new ActualizarUnidadOpRequest();
	   request.setIdUnidadOperativa(view.getIdUnidadOperativa());
	   request.setDepartamento(view.getDepartamento().getValue().toString());
	   request.setDireccion(view.getDireccion().getValue());
	   request.setDistrito(view.getDistrito().getValue().toString());
	   request.setNombre(view.getNombre().getValue().toString());
	   request.setNroCajones(Integer.valueOf(view.getNumeroCajones().getValue()));
	   request.setProvincia(view.getProvincia().getValue().toString());
	   request.setHoraInicio(view.getHoraApertura().getValue());
	   request.setHoraCierre(view.getHoraCierre().getValue());
	   
	   ActualizarUnidadOpResponse response = unidadOpService.actualizar(request);
	   Subject currentUser = SecurityUtils.getSubject();
	   if (response.isResultadoEjecucion()) {
		   currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
		   UI.getCurrent().getNavigator().navigateTo(
				   ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + view.getIdUnidadOperativa());
	   } else {
		   //TODO
	   }
	}

	@Override
	public void cancelar() {
		NavegacionUtil.irEditarCliente(view.getIdCliente());
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

	@SuppressWarnings("unchecked")
	@Override
	public void guardarUsuarios() {
		guardarUsuariosPorRol((Collection<UsuarioView>) view.getUsuarios().getValue(), RolView.CLIENTE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void guardarAprobadores() {
		guardarUsuariosPorRol((Collection<UsuarioView>) view.getAprobadores().getValue(), RolView.APROBADOR);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public void guardarOperadores() {
		guardarUsuariosPorRol((Collection<UsuarioView>) view.getOperadores().getValue(), RolView.OPERADOR);
	}
	
	private void guardarUsuariosPorRol(Collection<UsuarioView> usuariosAgregarView, String rol) {
	    ArrayList<UsuarioView> usuariosView = new ArrayList<UsuarioView>(usuariosAgregarView);
		
		int[] idsUsuarios = null;
		if (usuariosView.size() > 0) {
		   idsUsuarios = new int[usuariosView.size()];
		   for (int i=0; i<usuariosView.size(); i++)
			   idsUsuarios[i] = usuariosView.get(i).getId();
		}
		
		AgregarUsuarioUnidadOperativaRequest request = 
				new AgregarUsuarioUnidadOperativaRequest(view.getIdUnidadOperativa(), rol, idsUsuarios);
		Response response = unidadOpService.agregarUsuario(request);
		Subject currentUser = SecurityUtils.getSubject();
		
		if (response.isResultadoEjecucion()) {
			currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
			UI.getCurrent().getNavigator().navigateTo(
					   ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + view.getIdUnidadOperativa());
		} else {
			//TODO
			Logger.getAnonymousLogger().log(Level.WARNING, "Error");
		}
		
	}

	
	@Override
	public void irMantenimientoTarifa(String categoria) {
		StringBuilder sb = new StringBuilder();
		sb.append(ControlParkingUI.TARIFA);
		sb.append("/");
		sb.append(view.getIdUnidadOperativa());
		sb.append("/");
		sb.append(view.getIdCliente());
		sb.append("/");
		if (categoria != null && !categoria.isEmpty()) sb.append(categoria);
		
		UI.getCurrent().getNavigator().navigateTo(sb.toString());
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
