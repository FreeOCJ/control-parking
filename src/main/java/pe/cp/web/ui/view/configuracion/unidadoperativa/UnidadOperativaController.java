package pe.cp.web.ui.view.configuracion.unidadoperativa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.RolService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.UsuarioService;
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
	
	public UnidadOperativaController(INuevaUnidadOperativaView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);		
		this.view = view;
	}
	
	@Override
	public void cargar() {		

	}

	@Override
	public void cargarDepartamentos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargarProvincias() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargarDistritos() {
		// TODO Auto-generated method stub

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
