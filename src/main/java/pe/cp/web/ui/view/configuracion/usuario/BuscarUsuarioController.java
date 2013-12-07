package pe.cp.web.ui.view.configuracion.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;



import com.vaadin.ui.UI;

import pe.cp.core.service.UsuarioService;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.web.ui.ControlParkingUI;


@Component
@Scope("prototype")
@SuppressWarnings("unchecked")
public class BuscarUsuarioController implements IBuscarUsuarioViewHandler {
	ApplicationContext ac;
	private IBuscarUsuarioView view;
	private Container container;
	
	@Autowired
	private UsuarioService usuarioservice;
	
	public BuscarUsuarioController(IBuscarUsuarioView view){		
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuarioservice = ac.getBean(UsuarioService.class);		
		this.view = view;
	}

	@Override
	public void  buscarpornombre(String nombresApellidos) {		
		BuscarUsuarioRequest request = new BuscarUsuarioRequest();
		request.setNombresApellidos(nombresApellidos);	
		view.limpiarTabla();		
		        
        BuscarUsuarioResponse response = usuarioservice.buscarOr(request);
		try {
			List<UsuarioView> usuarios = response.getUsuariosEncontrados();
	        if(usuarios != null && usuarios.size() > 0){
	        	for(UsuarioView usuarioview:usuarios){
	        		System.out.println(usuarioview.getNombres());
	        		 Item newItem = container.getItem(container.addItem());
	        		 newItem.getItemProperty("Código").setValue(usuarioview.getId());
	        		 newItem.getItemProperty("Nombre Completo").setValue(usuarioview.getNombres() + " " + usuarioview.getApellidos());  
	        		 newItem.getItemProperty("Usuario").setValue(usuarioview.getLogin());
	        		 newItem.getItemProperty("Roles").setValue(usuarioview.getRolesAsString());
	        		 newItem.getItemProperty("Cargo").setValue(usuarioview.getCargo());
	        	}        
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}      	
	}

	@Override
	public Container setHeaderTable() {
		System.out.println("setHeader");
		container = new IndexedContainer(); 
		container.addContainerProperty("Código",Integer.class, 0);
        container.addContainerProperty("Nombre Completo",String.class, "");
        container.addContainerProperty("Usuario",String.class, "");
        container.addContainerProperty("Roles",String.class, "");
        container.addContainerProperty("Cargo",String.class, "");
		return container;
	}

	@Override
	public void irAgregarNuevoUsuario() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVOUSUARIO);			
	}

	@Override
	public void irEditarUsuario(int idUsuario) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARUSUARIO + "/" + String.valueOf(idUsuario));	
	}
	
	
}
