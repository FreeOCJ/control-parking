package pe.cp.core.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;

public class LoginServiceTest {
	ApplicationContext ac;
	
	@Autowired
	LoginService loginservice;
	
	
	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		loginservice = ac.getBean(LoginService.class);
	}
	
	@Test
	public void probarLogin() {
		System.out.println("test");
		LoginRequest request = new LoginRequest("oespinoza", "123456789");
		LoginResponse response = loginservice.login(request);
		Assert.assertNotNull(response);
	}
}
