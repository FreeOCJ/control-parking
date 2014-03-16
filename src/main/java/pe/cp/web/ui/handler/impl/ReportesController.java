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
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.messages.BuscarClienteRequest;
import pe.cp.core.service.messages.BuscarClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;
import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.IReportesViewHandler;
import pe.cp.web.ui.view.IReportesView;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

@Component
@Scope("prototype")
@Theme("controlparking")

public class ReportesController implements IReportesViewHandler{
	
	private IReportesView view;
	private Container cbClientesContainers;
	private Container cbUnidadOpContainers;
	private ApplicationContext ac;
	private Notification notificacion;
	
	private final String ERR_SELECCIONAR_COMBOS = "Debe seleccionar a un cliente y una unidad operativa";
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private UnidadOperativaService unidadopservice;
	
	public ReportesController(IReportesView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
		unidadopservice = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}

	@Override
	public void cargar() {
		cargarComboClientes();
		agregarEventosBotones();	
	}
	
	@SuppressWarnings("unchecked")
	private void cargarComboClientes(){
		view.getCbCliente().setContainerDataSource(prepararContainerComboCliente());
		BuscarClienteRequest request = new  BuscarClienteRequest();
		request.setNombreComercial("");
		BuscarClienteResponse response = clienteservice.buscar(request);
		
		if (response.isResultadoEjecucion()){
			for(ClienteView clienteview:response.getClientesEncontrados()){
				Item nuevoItemCombo = cbClientesContainers.getItem(cbClientesContainers.addItem());
				nuevoItemCombo.getItemProperty("ID").setValue(clienteview.getId());  
				nuevoItemCombo.getItemProperty("NOMBRE").setValue(clienteview.getNombreComercial());
			}			
		}		
	}
	
	private Container prepararContainerComboCliente() {
		cbClientesContainers = new IndexedContainer(); 
		cbClientesContainers.addContainerProperty("ID", Integer.class, "");
		cbClientesContainers.addContainerProperty("NOMBRE", String.class, "");
		return cbClientesContainers;
    }
	
	private Container prepararContainerComboUnidadOperativa() {
		cbUnidadOpContainers = new IndexedContainer(); 
		cbUnidadOpContainers.addContainerProperty("ID", Integer.class, "");
		cbUnidadOpContainers.addContainerProperty("NOMBRE", String.class, "");
		return cbUnidadOpContainers;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void cargarUnidadesOperativaPorCliente() {
		view.getCbUnidadOp().setContainerDataSource(prepararContainerComboUnidadOperativa());
		Object clienteSelectId = view.getCbCliente().getValue();
		Object valor = view.getCbCliente().getItem(clienteSelectId).getItemProperty("ID").getValue();
		int idCliente = Integer.valueOf(valor.toString());
		ObtenerUnidadOpPorClienteRequest request = new ObtenerUnidadOpPorClienteRequest(idCliente);
		ObtenerUnidadpOpPorClienteResponse response = unidadopservice.obtenerUnidadesPorCliente(request);
		
		if (response.isResultadoEjecucion()){
			for(UnidadOperativaView unidadOpView : response.getUnidadesOpView()){
				Item nuevoItemCombo = cbUnidadOpContainers.getItem(cbUnidadOpContainers.addItem());
				nuevoItemCombo.getItemProperty("ID").setValue(unidadOpView.getId());  
				nuevoItemCombo.getItemProperty("NOMBRE").setValue(unidadOpView.getNombre());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private int getIdClienteSeleccionado() {
		int idCliente = 0;
		Object clienteSelectId = view.getCbCliente().getValue();
		
		if (clienteSelectId != null){
			Property<Integer> property = view.getCbCliente().getItem(clienteSelectId).getItemProperty("ID");
			if (property != null)
				idCliente = Integer.valueOf(property.getValue().toString());
		}
		return idCliente;
	}
	
	@SuppressWarnings("unchecked")
	private int getIdUnidadOpSeleccionada() {
		int idUnidadOperativa = 0;
		Object unidadSelectId = view.getCbUnidadOp().getValue();
		
		if (unidadSelectId != null){
			Property<Integer> property = view.getCbUnidadOp().getItem(unidadSelectId).getItemProperty("ID");
			if (property != null)
				idUnidadOperativa = Integer.valueOf(property.getValue().toString());
		}
		
		return idUnidadOperativa;
	}
	
	@Override
	public void irReporteIncidencias() {
		int idCliente = getIdClienteSeleccionado();
		int idUnidadOperativa = getIdUnidadOpSeleccionada();
		
		if (idCliente + idUnidadOperativa > 1) {
			NavegacionUtil.irReporteIncidencias(idCliente, idUnidadOperativa);	
		} else {
			notificacion = new Notification(ERR_SELECCIONAR_COMBOS);
			notificacion.show(Page.getCurrent());
		}
	}

	@Override
	public void irReporteRecaudacion() {
		int idCliente = getIdClienteSeleccionado();
		int idUnidadOperativa = getIdUnidadOpSeleccionada();
		
		if (idCliente + idUnidadOperativa > 1) {
			NavegacionUtil.irReporteRecaudacion(idCliente, idUnidadOperativa);	
		} else {
			notificacion = new Notification(ERR_SELECCIONAR_COMBOS);
			notificacion.show(Page.getCurrent());
		}	
	}

	@Override
	public void irReporteIngresosSalidas() {
		int idCliente = getIdClienteSeleccionado();
		int idUnidadOperativa = getIdUnidadOpSeleccionada();
		
		if (idCliente + idUnidadOperativa > 1) {
			NavegacionUtil.irReporteIngresosSalidas(idCliente, idUnidadOperativa);
		} else {
			notificacion = new Notification(ERR_SELECCIONAR_COMBOS);
			notificacion.show(Page.getCurrent());
		}	
	}

	@Override
	public void irReporteVisitas() {
		int idCliente = getIdClienteSeleccionado();
		int idUnidadOperativa = getIdUnidadOpSeleccionada();
		
		if (idCliente + idUnidadOperativa > 1) {
			NavegacionUtil.irReporteVisitas(idCliente, idUnidadOperativa);
		} else {
			notificacion = new Notification(ERR_SELECCIONAR_COMBOS);
			notificacion.show(Page.getCurrent());
		}	
	}

	@Override
	public void irReporteConsolidado() {
		int idCliente = getIdClienteSeleccionado();
		int idUnidadOperativa = getIdUnidadOpSeleccionada();
		
		if (idCliente + idUnidadOperativa > 1) {
			NavegacionUtil.irReporteConsolidado(idCliente, idUnidadOperativa);
		} else {
			notificacion = new Notification(ERR_SELECCIONAR_COMBOS);
			notificacion.show(Page.getCurrent());
		}	
	}
	
	@SuppressWarnings("serial")
	private void agregarEventosBotones() {
		view.getBtnVerIncidencias().addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				irReporteIncidencias();		
			}
		});
		
		view.getBtnVerConsolidado().addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				irReporteConsolidado();		
			}
		});
		
		view.getBtnVerIngresoSalida().addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				irReporteIngresosSalidas();		
			}
		});
		
		view.getBtnVerRecaudacion().addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				irReporteRecaudacion();		
			}
		});
		
		view.getBtnVerVisitas().addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				irReporteVisitas();		
			}
		});
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			NavegacionUtil.irLogin();
		}else{
			if (!currentUser.hasRole(Rol.CLIENTE) && !currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				NavegacionUtil.irMain();
			}
		}
	}
}
