package pe.cp.core.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Cliente;

public class ClienteDaoTest {
	ApplicationContext ac;
	
	@Autowired
	ClienteDao cdao;

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		cdao = ac.getBean(ClienteDao.class);
	}
	
	@Test
	//TestCase: Insertar un nuevo cliente. Antes de agregarle
	//usuarios o unidades operativas
	public void probarInsertarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNombreComercial("PLAZA VEA");
		cliente.setRazonSocial("SUPERMERCADOS PERUANOS S.A.");
		cliente.setRuc("09000234343");
		
		Integer idCliente = cdao.agregar(cliente);
		Assert.assertNotNull(idCliente);
		
		Cliente nuevoCliente = cdao.buscar(idCliente);
		Assert.assertEquals("PLAZA VEA", nuevoCliente.getNombreComercial());
		Assert.assertEquals("SUPERMERCADOS PERUANOS S.A.", nuevoCliente.getRazonSocial());
		Assert.assertEquals("09000234343", nuevoCliente.getRuc());
		Assert.assertEquals(idCliente.toString(), String.valueOf(nuevoCliente.getId()));
		
		return;
	}
}
