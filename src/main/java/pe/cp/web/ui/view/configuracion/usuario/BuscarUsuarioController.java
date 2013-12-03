package pe.cp.web.ui.view.configuracion.usuario;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class BuscarUsuarioController implements IBuscarUsuarioViewHandler {
	ApplicationContext ac;
	private IBuscarUsuarioView view;	
	
	public BuscarUsuarioController(IBuscarUsuarioView view){		
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
				
		this.view = view;
	}
}
