package pe.cp.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.TipoEvento;
import pe.cp.core.service.TipoEventoService;

public class MainTest {	
	
	public static void main(String[] args) {
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setDescripcion("LOGIN");
						
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:WEB-INF/spring/context.xml");
		TipoEventoService tipoEventoService = context.getBean(TipoEventoService.class);
		int id = tipoEventoService.agregar(tipoEvento);
		System.out.println("Se insertó tipo evento " + id);
	}

}
