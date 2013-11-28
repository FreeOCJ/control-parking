package pe.cp.core.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Cliente;



public class ClienteServiceTest {
	ApplicationContext ac;

	@Autowired
	ClienteService clienteservice;
	
	
	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		clienteservice = ac.getBean(ClienteService.class);
	}
	
	@Test
	public void probarbuscar() {
		System.out.println("prueba junit");
		List<Cliente> lista = clienteservice.buscar("Free");
		System.out.println("lista " + lista.size());
		Assert.assertEquals(1,lista.size());
		/*Operacion op = new Operacion();
        member.setEmail("jane.doe@mailinator.com");
        member.setName("Jane Doe");
        member.setPhoneNumber("2125552121");

        memberDao.register(member);
        Long id = member.getId();
        Assert.assertNotNull(id);

        Assert.assertEquals(2, memberDao.findAllOrderedByName().size());
        Member newMember = memberDao.findById(id);

        Assert.assertEquals("Jane Doe", newMember.getName());
        Assert.assertEquals("jane.doe@mailinator.com", newMember.getEmail());
        Assert.assertEquals("2125552121", newMember.getPhoneNumber());
        return;*/
	}
}
