package pe.cp.web.ui.view.auditoria;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import pe.cp.core.service.AuditoriaService;
import pe.cp.core.service.domain.AuditoriaView;
import pe.cp.core.service.domain.ClienteView;
import pe.cp.core.service.messages.BuscarAuditoriaRequest;
import pe.cp.core.service.messages.BuscarAuditoriaResponse;
import pe.cp.core.service.messages.ObtenerTipoEventosResponse;
import pe.cp.web.ui.view.configuracion.cliente.BuscarClienteController;

public class AuditoriaController implements IAuditHandler {

	private ApplicationContext ac;
	private IAuditoriaView view;
	private Container container;
	
	@Autowired
	private AuditoriaService auditService;
	
	private final String ID = "ID";
	private final String FECHA = "Fecha - Hora";
	private final String TIPOEVENTO = "Evento";
	private final String USUARIO = "Usuario";
	private final String DESCRIPCION = "Descripción";
	
	Notification notification;
	
	public AuditoriaController(IAuditoriaView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		auditService = ac.getBean(AuditoriaService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {
		prepararTabla();
		cargarComboTipoEvento();
	}

	@SuppressWarnings("unchecked")
	private void buscar() {
		String login = view.getTxtUsuari().getValue();
		Object otipoEvento = view.getCbTipoEvento().getValue();
		Date fechaInicio = view.getDfFechaInicio().getValue();
		Date fechaFin = view.getDfFechaFin().getValue();
		String tipoEvento;
		if (otipoEvento == null) tipoEvento = null;
		else tipoEvento = otipoEvento.toString();
		
		BuscarAuditoriaRequest request = new BuscarAuditoriaRequest(tipoEvento, login, fechaInicio, fechaFin);
		BuscarAuditoriaResponse response = auditService.buscar(request);
		
		if (response.isResultadoEjecucion()) {
			view.getTblResultados().removeAllItems();
			List<AuditoriaView> auditsView = response.getAuditViews();
			for(AuditoriaView auditView : auditsView){        		
        		 Item newItem = container.getItem(container.addItem());
        		 newItem.getItemProperty(ID).setValue(auditView.getId());
        		 newItem.getItemProperty(FECHA).setValue(auditView.getFechaHora());  
        		 newItem.getItemProperty(TIPOEVENTO).setValue(auditView.getTipoEvento());
        		 newItem.getItemProperty(USUARIO).setValue(auditView.getUsuario());   
        		 newItem.getItemProperty(DESCRIPCION).setValue(auditView.getDescripcion());  
        	} 
		} else {
			notification = new Notification(response.getMensaje());
			notification.setPosition(Position.TOP_CENTER);
			notification.show(Page.getCurrent());
		}
	}
	
	@SuppressWarnings("serial")
	@Override
	public void prepararTabla() {
		container = new IndexedContainer(); 
		container.addContainerProperty(ID,Integer.class, 0);
        container.addContainerProperty(FECHA,String.class, "");
        container.addContainerProperty(TIPOEVENTO,String.class, "");
        container.addContainerProperty(USUARIO,String.class, "");
        container.addContainerProperty(DESCRIPCION,String.class, "");
		
        String[] visibles = {FECHA, TIPOEVENTO, USUARIO, DESCRIPCION};
        view.getTblResultados().setContainerDataSource(container);
        view.getTblResultados().setSizeFull();
        view.getTblResultados().setVisibleColumns((Object []) visibles);
        view.getTblResultados().setColumnWidth(FECHA, 100);
        view.getTblResultados().setColumnWidth(TIPOEVENTO, 100);
        view.getTblResultados().setColumnWidth(USUARIO, 100);
        
        view.getBtnBuscar().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				buscar();
			}
		});
	}
	
	private void cargarComboTipoEvento() {
		ObtenerTipoEventosResponse response = auditService.obtenerTipoEventos();
		
		view.getCbTipoEvento().removeAllItems();
		if (response.isResultadoEjecucion()) {
			for (String tipoEvento : response.getTipoEventos()) {
				view.getCbTipoEvento().addItem(tipoEvento);
			}
		}
	}
}
