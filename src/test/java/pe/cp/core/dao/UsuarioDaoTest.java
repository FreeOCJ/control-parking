package pe.cp.core.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Usuario;

public class UsuarioDaoTest {
	
	ApplicationContext ac;
	
	@Autowired
	UsuarioDao usuariodao;

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuariodao = ac.getBean(UsuarioDao.class);
	}
	
	@Test
	public void probarBuscarUsuarios() {
		List<Usuario> usuarios = usuariodao.buscar("Juan");
		System.out.println("usuarios: " + usuarios.size());
		Assert.assertEquals(1,usuarios.size());
	}
	
	@Test
	public void probarBuscarUsuarioxId() {
		Usuario usuario = usuariodao.buscar(3);
		System.out.println("Nombre: " + usuario.getNombres());
		Assert.assertEquals("Juan",usuario.getNombres());
	}
	
	@Test
	public void probarEliminarUsuarioxId() {
		usuariodao.eliminar(3);
		System.out.println("eliminado");
	}
}
