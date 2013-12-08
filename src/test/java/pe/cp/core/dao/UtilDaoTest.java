package pe.cp.core.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UtilDaoTest {

	ApplicationContext ac;
	
	@Autowired
	UtilDao utilDao;
	
	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		utilDao = ac.getBean(UtilDao.class);		
	}
	
	@Test
	public void probarObtenerDepartamentos(){
		List<String> departamentos = utilDao.obtenerDepartamentos();
		Assert.assertNotNull(departamentos);		
	}
}
