package pe.cp.web.ui.view.configuracion.unidadoperativa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.RolService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.UtilService;
import pe.cp.web.ui.view.configuracion.usuario.IEditarUsuarioView;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

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
	
	public UnidadOperativaController(INuevaUnidadOperativaView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		utilService = ac.getBean(UtilService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {		
		cargarDepartamentos();
	}

	@Override
	public void cargarDepartamentos() {
		List<String> departamentos = utilService.obtenerDepartamentos();
		
		for (String dpto : departamentos) {
			view.getDepartamento().addItem(dpto);
		}
	}

	@Override
	public void cargarProvincias() {
		List<String> provincias = utilService.obtenerProvincias((String) view.getDepartamento().getValue());
		for (String prov : provincias) {
			view.getProvincia().addItem(prov);
		}
	}

	@Override
	public void cargarDistritos() {
		List<String> distritos = utilService.obtenerDistritos((String) view.getDepartamento().getValue(), (String) view.getProvincia().getValue());
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
}
