package pe.cp.core.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Rol;

public class RolDaoTest {
	ApplicationContext ac;
	
	@Autowired
	RolDao rolDao;

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		rolDao = ac.getBean(RolDao.class);
	}
	
	@Test
	public void probarInsertarRol() {
		Rol rol = new Rol();
		rol.setDescripcion("Revisor");
		
		Integer idRol = rolDao.agregar(rol);
		Assert.assertNotNull(idRol);
		
		Rol nuevoRol = rolDao.buscar(idRol);
		Assert.assertEquals("Revisor", nuevoRol.getDescripcion());
		Assert.assertEquals(idRol.toString(), String.valueOf(nuevoRol.getId()));
		
		return;
	}
	
	@Test
	public void probarObtenerTodos() {
		List<Rol> roles = rolDao.obtenerTodos();				
		Assert.assertNotNull(roles);				
		
		return;
	}
}
