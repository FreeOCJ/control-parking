package pe.cp.web.ui.handler.impl;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.OperacionService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.EliminarOperacionRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.IBuscarOperacionesHandler;
import pe.cp.web.ui.view.IBuscarOperacionesView;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.ColumnGenerator;

@Scope("prototype")
public class BuscarOperacionesController implements IBuscarOperacionesHandler {
   
	private ApplicationContext ac;
	private IBuscarOperacionesView view;
	private Container container;
	private Container cbUnidadesContainers;
	private Notification notification;
	
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
	private final String MSG_CONFIRMACION_ELIMINACION = "¿Estás seguro que deseas eliminar la operación?";
	private final String HEADER_POP_UP_ELIMINAR = "Confirmar Acción";
   
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
	private final String NO_RESULTADOS = "No se encontraron resultados";
	
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
		prepararTabla();
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
			if (response.getOperacionesView().size() > 0)
				for (OperacionView opView : response.getOperacionesView()) {
					Item itemOperacion = container.getItem(container.addItem());
					itemOperacion.getItemProperty(CODIGO).setValue(opView.getId());  
					itemOperacion.getItemProperty(UNIDAD_OPERATIVA).setValue(opView.getNombreUnidadOperativa());
					itemOperacion.getItemProperty(CLIENTE).setValue(opView.getNombreCliente());
					itemOperacion.getItemProperty(FECHA).setValue(opView.getFechaTransaccion());
					itemOperacion.getItemProperty(REGISTRADO_POR).setValue(opView.getCreador());
					itemOperacion.getItemProperty(ESTADO).setValue(opView.getEstado());
				}
			else {
	        	notification = new Notification(NO_RESULTADOS);
	        	notification.setPosition(Position.TOP_CENTER);
	        	notification.show(Page.getCurrent());
	        }
		} else {
			//TODO
		}
		
	}

	@Override
	public void irEditarOperacion(int idOperacion) {
		NavegacionUtil.irEditarOperacion(idOperacion);
	}
	
	@SuppressWarnings("serial")
	private void prepararTabla() {
		Table tblOperaciones = view.getResultados();
		tblOperaciones.setContainerDataSource(obtenerHeadersContainer());
		
		tblOperaciones.addGeneratedColumn(BOTONES, new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button();
				btnEditar.setIcon(new ThemeResource("icons/18/edit.png"));
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        Integer idOperacion = (Integer) source.getContainerDataSource().getContainerProperty(itemId, CODIGO).getValue();
			        irEditarOperacion(idOperacion);
			      }
			    });
				
				Button btnEliminar = new Button();
				btnEliminar.setIcon(new ThemeResource("icons/18/delete.png"));
				btnEliminar.addClickListener(new ClickListener() {			 
			      @Override 
			      public void buttonClick(ClickEvent event) {				    	  
			    	  ConfirmDialog.show(UI.getCurrent(), HEADER_POP_UP_ELIMINAR, MSG_CONFIRMACION_ELIMINACION, "Si", "No", 
				        new ConfirmDialog.Listener() {
				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	Integer idOperacion = (Integer) source.getContainerDataSource().getContainerProperty(itemId, CODIGO).getValue();
				                	eliminar(idOperacion);
				                }
				            }
				        });			        			      
			      }
			    });
			 
				botonesAccion.addComponent(btnEditar);
				botonesAccion.addComponent(btnEliminar);
			    return botonesAccion;
			}
		} ); 
		
        tblOperaciones.setColumnWidth(BOTONES, 130);
        tblOperaciones.setVisibleColumns((Object []) BuscarOperacionesController.obtenerColumnasVisibles());
	}

	@Override
	public void eliminar(int idOperacion) {
		Subject currentUser = SecurityUtils.getSubject();
		String login = currentUser.getSession().getAttribute("login").toString();
		
		Response response = opService.eliminarOperacion(new EliminarOperacionRequest(idOperacion, login));
		if (response.isResultadoEjecucion()) {
			currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje()));
			NavegacionUtil.irOperaciones();
		} else {
			notification = new Notification(response.getMensaje());
			notification.show(Page.getCurrent());
		}
		
	}
}
