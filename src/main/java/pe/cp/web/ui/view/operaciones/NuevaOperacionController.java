package pe.cp.web.ui.view.operaciones;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.OperacionService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarResponse;
import pe.cp.web.ui.NavegacionUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

public class NuevaOperacionController implements INuevaOperacionHandler {
    
	private ApplicationContext ac;
	private INuevaOperacionView view;
	private Container cbUnidadesContainers;
	
	public final static String ID_UNIDAD = "ID";
	public final static String NOMBRE_UNIDAD = "NOMBRE";
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
	public NuevaOperacionController(INuevaOperacionView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		opService = ac.getBean(OperacionService.class);
		this.view = view;
	}
	
	@Override
	public void guardar() {
		Subject currentUser = SecurityUtils.getSubject();
		String login = currentUser.getSession().getAttribute("login").toString();
		
		if (view.getUnidadOperativa().getValue() != null && view.getFechaOperacion().getValue() != null) {
			int idUnidadSeleccionada = Integer.valueOf(view.getUnidadOperativa().getValue().toString());
			AgregarOperacionRequest request = 
					new AgregarOperacionRequest(idUnidadSeleccionada, view.getFechaOperacion().getValue(), login);
			AgregarOperacionResponse response = opService.agregar(request);
			if (response.isResultadoEjecucion()) {
				int idNuevaOperacion = response.getIdNuevaOperacion();
				NavegacionUtil.irEditarOperacion(idNuevaOperacion);
			} else {
				//TODO
			}
		} else {
			//TODO
		}
	}

	@Override
	public void validarUsuario() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void cargar() {
		//Cargar combo unidades
		Subject currentUser = SecurityUtils.getSubject();
		String login = currentUser.getSession().getAttribute("login").toString();
		
        view.getUnidadOperativa().setContainerDataSource(prepararContainerComboUnidad());	
		ObtenerUnidadesOpProcesarRequest request = new ObtenerUnidadesOpProcesarRequest(login, false, true);
		ObtenerUnidadesOpProcesarResponse response = unidadOpService.obtenerParaProcesar(request);
		
		if (response.isResultadoEjecucion()) {
			for (UnidadOperativaView unidadOpView : response.getUnidadesOpViews()) {
				Item nuevoItemCombo = cbUnidadesContainers.getItem(cbUnidadesContainers.addItem());
				nuevoItemCombo.getItemProperty(ID_UNIDAD).setValue(unidadOpView.getId());  
				nuevoItemCombo.getItemProperty(NOMBRE_UNIDAD).setValue(unidadOpView.getNombre());
			}
		} else {
			//TODO
		}
	}
	
	private Container prepararContainerComboUnidad() {
    	cbUnidadesContainers = new IndexedContainer(); 
    	cbUnidadesContainers.addContainerProperty(ID_UNIDAD, Integer.class, "");
    	cbUnidadesContainers.addContainerProperty(NOMBRE_UNIDAD, String.class, "");
		return cbUnidadesContainers;
    }

	@Override
	public void cancelar() {
		//TODO
	}

}