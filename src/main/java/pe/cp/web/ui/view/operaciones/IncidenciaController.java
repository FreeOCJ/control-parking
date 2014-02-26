package pe.cp.web.ui.view.operaciones;

import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.OperacionService;
import pe.cp.core.service.domain.TipoIncidenciaView;
import pe.cp.core.service.messages.AgregarIncidenciaRequest;
import pe.cp.core.service.messages.AgregarIncidenciaResponse;
import pe.cp.core.service.messages.ActualizarIncidenciaRequest;
import pe.cp.core.service.messages.ObtenerIncidenciaRequest;
import pe.cp.core.service.messages.ObtenerIncidenciaResponse;
import pe.cp.core.service.messages.ObtenerTipoIncidenciasResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.web.ui.NavegacionUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.TextField;

public class IncidenciaController implements IIncidenciaHandler {

	private ApplicationContext ac;
	private IIncidenciaView view;
	@Autowired
	private OperacionService opService;
	private Container cbTiposContainer;
	
	private static final String ID_TIPO_INC = "ID";
	private static final String TIPO_INC = "TIPO";
	
	public IncidenciaController(IIncidenciaView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		opService = ac.getBean(OperacionService.class);
		this.view = view;
	}
	
	@Override
	public void guardar() {
	       if (view.getIdIncidencia() > 0) {
				actualizarIncidencia();
			} else {
				agregarNuevaIncidencia();	
			}
	}
	
	private void actualizarIncidencia() {
		if (validar()) {
			String descripcion = view.getTxtDescripcion().getValue().trim();
			Date fecha = view.getTxtHora().getValue();
			Object tipoSelectId = view.getCbTipo().getValue();
			int idTipo = Integer.valueOf(view.getCbTipo().getItem(tipoSelectId).getItemProperty(ID_TIPO_INC).toString());
			
			ActualizarIncidenciaRequest request = new ActualizarIncidenciaRequest();
			request.setDescripcion(descripcion);
			request.setHora(fecha);
			request.setIdIncidencia(view.getIdIncidencia());
			request.setIdTipo(idTipo);
			Response response = opService.actualizarIncidencia(request);
			
			if (response.isResultadoEjecucion()) {
				NavegacionUtil.irEditarOperacion(view.getIdOperacion());
			} else {
				//TODO
			}
		}
	}
	
	private void agregarNuevaIncidencia() {
		if (validar()) {
			String descripcion = view.getTxtDescripcion().getValue().trim();
			Date fecha = view.getTxtHora().getValue();
			Object tipoSelectId = view.getCbTipo().getValue();
			int idTipo = 
					Integer.valueOf(view.getCbTipo().getItem(tipoSelectId).getItemProperty(ID_TIPO_INC).toString());
			String tipo = view.getCbTipo().getItem(tipoSelectId).getItemProperty(TIPO_INC).toString();
			
	        AgregarIncidenciaRequest request = 
	    		    new AgregarIncidenciaRequest(view.getIdOperacion(), descripcion, fecha, idTipo, tipo);
	        AgregarIncidenciaResponse response = opService.agregarIncidencia(request);
	        
	        if (response.isResultadoEjecucion()) {
	        	NavegacionUtil.irEditarOperacion(view.getIdOperacion());
	        } else {
	        	//TODO
	        }
		} else {
			//En el validar?
		}
	}
	
	private boolean validar() {
		if (view.getCbTipo().getValue() != null && view.getTxtHora().getValue() != null 
				&& !view.getTxtDescripcion().getValue().isEmpty()) {
			return true;
		} else {
			//TODO
			
			return false;
		}
	}

	@Override
	public void cargar() {
		cargarComboTipo();
		
		if (view.getIdIncidencia() > 0) {
			view.getLblTitulo().setValue("Editar Incidencia");
			ObtenerIncidenciaResponse response = opService.obtenerIncidencia(new ObtenerIncidenciaRequest(view.getIdIncidencia()));
			if (response.isResultadoEjecucion()) {
			    view.getTxtDescripcion().setValue(response.getIncidenciaView().getDetalle());
			    view.getTxtHora().setValue(response.getIncidenciaView().getFechaIncidencia());
			    
			    for (@SuppressWarnings("rawtypes")
				Iterator i = view.getCbTipo().getItemIds().iterator(); i.hasNext();) {
					int iid = (Integer) i.next();
					Item item = view.getCbTipo().getItem(iid);
					
					int idTipo = (Integer) (item.getItemProperty(ID_TIPO_INC).getValue());
					if (idTipo == response.getTipoView().getId()) {
					    view.getCbTipo().select(iid);
					    break;
					}
				}
			} else {
				//TODO
			}
		} else view.getLblTitulo().setValue("Nueva Incidencia");
	}
	
	@SuppressWarnings("unchecked")
	private void cargarComboTipo() {
		view.getCbTipo().setContainerDataSource(prepararContainerCombo());	
		ObtenerTipoIncidenciasResponse response = opService.obtenerTipoIncidencias();
		
		if (response.isResultadoEjecucion()) {
			for (TipoIncidenciaView tipoView : response.getTiposView()) {
				Item nuevoItemCombo = cbTiposContainer.getItem(cbTiposContainer.addItem());
				nuevoItemCombo.getItemProperty(ID_TIPO_INC).setValue(tipoView.getId());  
				nuevoItemCombo.getItemProperty(TIPO_INC).setValue(tipoView.getTipo());
			}
		} else {
			//TODO
		}
	}
	
	private Container prepararContainerCombo() {
		cbTiposContainer = new IndexedContainer(); 
		cbTiposContainer.addContainerProperty(ID_TIPO_INC, Integer.class, "");
		cbTiposContainer.addContainerProperty(TIPO_INC, String.class, "");
		return cbTiposContainer;
    }

	@Override
	public void cancelar() {
		NavegacionUtil.irEditarOperacion(view.getIdOperacion());
	}
}
