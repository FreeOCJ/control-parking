package pe.cp.web;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Cliente;
import pe.cp.core.service.ClienteService;

public class MainTest {	
	
	
	
	public static void main(String[] args) {
		//TipoEvento tipoEvento = new TipoEvento();
		//tipoEvento.setDescripcion("LOGIN");
		ApplicationContext context = new ClassPathXmlApplicationContext(
		                          "classpath:WEB-INF/spring/context.xml");
		ClienteService clienteservice = context.getBean(ClienteService.class);
		List<Cliente> lista = clienteservice.buscar("Free");
		System.out.println("Resultados : " + lista.size());
	}

}
