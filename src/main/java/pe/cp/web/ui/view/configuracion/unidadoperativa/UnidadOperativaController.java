package pe.cp.web.ui.view.configuracion.unidadoperativa;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.UtilService;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerClienteRequest;
import pe.cp.core.service.messages.ObtenerClienteResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaResponse;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
public class UnidadOperativaController implements IUnidadOperativaHandler {

	ApplicationContext ac;
	private INuevaUnidadOperativaView view;
	private Container categoriasContainer;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private UsuarioService usuarioService;
	
	public UnidadOperativaController(INuevaUnidadOperativaView view){
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
			
		}	
		
		//Cuando entra en modo edicion
		if (view.getIdUnidadOperativa() > 0){
			ObtenerUnidadOperativaRequest request = new ObtenerUnidadOperativaRequest(view.getIdUnidadOperativa());
			ObtenerUnidadOperativaResponse response  = unidadOpService.obtenerPorId(request);
			
			if (response.isResultadoEjecucion()){
				view.getDepartamento().setValue(response.getUnidadOpView().getDepartamento());
				view.getDireccion().setValue(response.getUnidadOpView().getDireccion());
				view.getProvincia().setValue(response.getUnidadOpView().getProvincia());				
				//view.getHoraApertura().setValue( response.getUnidadOpView().getHoraApertura());
				view.getHoraApertura().setValue(new java.util.Date());
				view.getHoraCierre().setValue(new java.util.Date());
				view.getNombre().setValue(response.getUnidadOpView().getNombre());
				view.getNumeroCajones().setValue(String.valueOf(response.getUnidadOpView().getNroCajones()));
				view.getDistrito().setValue(response.getUnidadOpView().getDistrito());
			}else{
				System.out.println("Error cargar unidad operativa");
			}
		}
		
	}

	private void cargarTwinColSelects(){
		ObtenerUsuariosSistemaResponse response = usuarioService.obtenerUsuariosSistema();
		if (response.isResultadoEjecucion()){			
			BeanItemContainer<UsuarioView> usuarioBeans  = new BeanItemContainer<UsuarioView>(UsuarioView.class);
			
			for (UsuarioView usuarioView : response.getUsuariosView()) {
				usuarioBeans.addBean(usuarioView);
			}
			
			view.getAprobadores().setContainerDataSource(usuarioBeans);
			view.getOperadores().setContainerDataSource(usuarioBeans);
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
		categoriasContainer.addContainerProperty("Código",Integer.class, 0);
		categoriasContainer.addContainerProperty("Nombre Categoría",String.class, "");
		categoriasContainer.addContainerProperty("Tarifas",String.class, "");		
		return categoriasContainer;
	}

	@Override
	public void guardar() {
		if (view.getIdUnidadOperativa() == 0){ 		//Caso Nuevo
			AgregarUnidadOperativaRequest request = new AgregarUnidadOperativaRequest();
			request.setDepartamento((String) view.getDepartamento().getValue());
			request.setDireccion(view.getDireccion().getValue());
			request.setDistrito((String) view.getDistrito().getValue());
			request.setNombre(view.getNombre().getValue());
			request.setProvincia((String) view.getProvincia().getValue());
			request.setIdCliente(view.getIdCliente());
			request.setHoraInicio(view.getHoraApertura().getValue());
			request.setHoraCierre(view.getHoraCierre().getValue());
			
			AgregarUnidadOperativaResponse response = unidadOpService.agregar(request);
			if (response.isResultadoEjecucion()){
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + response.getIdUnidadOperativa());
			}else{
				System.out.println("Error");
			}
		}else{		//Caso Actualizar
			
		}
	}

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
		
	}
}
