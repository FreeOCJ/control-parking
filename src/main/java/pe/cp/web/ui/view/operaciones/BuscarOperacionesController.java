package pe.cp.web.ui.view.operaciones;

import java.util.Date;
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

import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.domain.Rol;
import pe.cp.core.service.ClienteService;
import pe.cp.core.service.OperacionService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.UtilService;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarResponse;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Reindeer;


@Scope("prototype")
public class BuscarOperacionesController implements IBuscarOperacionesHandler {
   
	private ApplicationContext ac;
	private IBuscarOperacionesView view;
	private Container container;
	private Container cbUnidadesContainers;
	
	private final static String CODIGO = "Codigo";
	private final static String UNIDAD_OPERATIVA = "Unidad Operativa";
	private final static String CLIENTE = "Cliente";
	private final static String FECHA = "Fecha";
	private final static String REGISTRADO_POR = "Registrado Por";
	private final static String ESTADO = "Estado";
	private final static String BOTONES = "";
	
	private final String EN_PROCESO = "EN PROCESO";
	private final String POR_APROBAR = "POR APROBAR";
	private final String APROBADA = "APROBADA";
   
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
    public BuscarOperacionesController(IBuscarOperacionesView view) {
    	ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		opService = ac.getBean(OperacionService.class);
    	this.view = view;
    }
    
    public Container obtenerHeadersContainer() {
    	container = new IndexedContainer(); 
    	container.addContainerProperty(CODIGO, Integer.class, "");
		container.addContainerProperty(UNIDAD_OPERATIVA, String.class, "");
        container.addContainerProperty(CLIENTE, String.class, "");
        container.addContainerProperty(FECHA, Date.class, new Date());
        container.addContainerProperty(REGISTRADO_POR, String.class, ""); 
        container.addContainerProperty(ESTADO, String.class, ""); 
		return container;
    }
    
    public Container prepararContainerComboUnidad() {
    	cbUnidadesContainers = new IndexedContainer(); 
    	cbUnidadesContainers.addContainerProperty("ID", Integer.class, "");
    	cbUnidadesContainers.addContainerProperty("NOMBRE", String.class, "");
		return cbUnidadesContainers;
    }
    
    public static String[] obtenerColumnasVisibles(){
		String[] visibles = {UNIDAD_OPERATIVA, CLIENTE, FECHA, REGISTRADO_POR, ESTADO, BOTONES};
		return visibles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cargarComboUnidadesOp() {
		Subject currentUser = SecurityUtils.getSubject();
		String login = currentUser.getSession().getAttribute("login").toString();
		
        view.getCbUnidadOp().setContainerDataSource(prepararContainerComboUnidad());	
		ObtenerUnidadesOpProcesarRequest request = new ObtenerUnidadesOpProcesarRequest(login, true, true);
		ObtenerUnidadesOpProcesarResponse response = unidadOpService.obtenerParaProcesar(request);
		
		if (response.isResultadoEjecucion()) {
			for (UnidadOperativaView unidadOpView : response.getUnidadesOpViews()) {
				Item nuevoItemCombo = cbUnidadesContainers.getItem(cbUnidadesContainers.addItem());
				nuevoItemCombo.getItemProperty("ID").setValue(unidadOpView.getId());  
				nuevoItemCombo.getItemProperty("NOMBRE").setValue(unidadOpView.getNombre());
			}
		} else {
			//TODO
		}
	}

	@Override
	public void cargarComboEstados() {
		view.getCbEstado().addItem(EN_PROCESO);
		view.getCbEstado().addItem(POR_APROBAR);
		view.getCbEstado().addItem(APROBADA);
	}

	@Override
	public void irNuevaOperacion() {
		NavegacionUtil.irNuevaOperacion();
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			NavegacionUtil.irLogin();
		}else{
			if (!currentUser.hasRole(Rol.OPERADOR) || !currentUser.hasRole(Rol.APROBADOR) || 
					!currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				NavegacionUtil.irMain();
			}
		}
	}

	@Override
	public void cargar() {
		cargarComboUnidadesOp();
		cargarComboEstados();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
		int idUnidadSeleccionada = 0;
		Date fechaSeleccionada = null;
		String estadoSeleccionado = null;
		
		view.getResultados().removeAllItems();
		
		Object idElemUnidadSeleccionado = view.getCbUnidadOp().getValue();
		if (idElemUnidadSeleccionado != null) {
			int idUnidad = Integer.valueOf(view.getCbUnidadOp().getItem(idElemUnidadSeleccionado).getItemProperty("ID").toString());
			if (idUnidad > 0) idUnidadSeleccionada = idUnidad;
		}
		if (view.getDfFechaOp().getValue() != null) fechaSeleccionada = view.getDfFechaOp().getValue();
		if (view.getCbEstado().getValue() != null && !view.getCbEstado().getValue().toString().isEmpty())
			estadoSeleccionado = view.getCbEstado().getValue().toString();
		
		BuscarOperacionesRequest request = 
				new BuscarOperacionesRequest(idUnidadSeleccionada, fechaSeleccionada, estadoSeleccionado);
		BuscarOperacionResponse response = opService.buscar(request);
		
		if (response.isResultadoEjecucion()) {
			for (OperacionView opView : response.getOperacionesView()) {
				Item itemOperacion = container.getItem(container.addItem());
				itemOperacion.getItemProperty(CODIGO).setValue(opView.getId());  
				itemOperacion.getItemProperty(UNIDAD_OPERATIVA).setValue(opView.getNombreUnidadOperativa());
				itemOperacion.getItemProperty(CLIENTE).setValue(opView.getNombreCliente());
				itemOperacion.getItemProperty(FECHA).setValue(opView.getFechaTransaccion());
				itemOperacion.getItemProperty(REGISTRADO_POR).setValue(opView.getCreador());
				itemOperacion.getItemProperty(ESTADO).setValue(opView.getEstado());
			}
		} else {
			//TODO
		}
		
	}

	@Override
	public void irEditarOperacion(int idOperacion) {
		NavegacionUtil.irEditarOperacion(idOperacion);
	}
}
