package pe.cp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Usuario;
import pe.cp.core.service.ClienteService;
import pe.cp.core.service.UsuarioService;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{    
	@Autowired
    private transient ApplicationContext applicationContext;
	
	@Autowired
    private UsuarioService usuarioservice;
	
	@Autowired
	private ClienteService clienteservice;
	
    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	/**Usuario usuario = new Usuario();
            	usuario.setNombres("Juan");
            	usuario.setApellidos("Trelles");
            	usuario.setCargo("Consultor");
            	usuario.setEmail("jtrelles@correo.com");
            	usuario.setLogin("jtrelles");
            	usuario.setPassword("jtrelles");
            	usuarioservice.nuevo(usuario);**/
            	/*List<Cliente> clientes = clienteservice.buscar("Free");
            	if (clientes!=null && clientes.size()>0){
            		for(Cliente cliente:clientes){
            			System.out.println("nombre: " + cliente.getRazonSocial());
            		}
            	}else{
            		System.out.println("null");
            	}
                layout.addComponent(new Label("Thank you for clicking"));*/
            }
        });
        layout.addComponent(button);
    }
}
