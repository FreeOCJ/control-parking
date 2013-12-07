package pe.cp.core.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Rol;
import pe.cp.core.domain.Usuario;

public class UsuarioDaoTest {
	
	ApplicationContext ac;
	
	@Autowired
	UsuarioDao usuariodao;
	
	@Autowired
	ClienteDao clienteDao;
	

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		usuariodao = ac.getBean(UsuarioDao.class);
		clienteDao = ac.getBean(ClienteDao.class);
	}
	
/*	@Test
	public void probarAgregarNuevoUsuario(){
		Cliente cliente = clienteDao.buscar(2);
		
		Usuario usuario = new Usuario();
		usuario.setApellidos("Espinoza Lozada");
		usuario.setCargo("Soporte");
		usuario.setCliente(cliente);
		usuario.setEmail("oomares@gmail.com");
		usuario.setLogin("oespinoza");
		usuario.setNombres("Omar Dante");
		usuario.setPassword("123456789");		
		
		Integer idUsuario = usuariodao.agregar(usuario);
		Assert.assertNotNull(idUsuario);
		
		Usuario nuevoUsuario = usuariodao.buscar(idUsuario);
		Assert.assertEquals("Espinoza Lozada", nuevoUsuario.getApellidos());
		Assert.assertEquals("Soporte", nuevoUsuario.getCargo());
		Assert.assertEquals("oomares@gmail.com", nuevoUsuario.getEmail());
		Assert.assertEquals("Omar Dante", nuevoUsuario.getNombres());
		Assert.assertEquals("123456789", nuevoUsuario.getPassword());		
		Assert.assertEquals(idUsuario.toString(), String.valueOf(nuevoUsuario.getId()));
		Assert.assertEquals(String.valueOf(cliente.getId()), String.valueOf(nuevoUsuario.getCliente().getId()));
		
		return;
	}*/
	
	/*@Test
	public void probarAgregarRolPorUsuario(){
		Usuario usuario = usuariodao.buscar(1);
		int idRol = 2;
		usuariodao.agregarRol(usuario.getId(), idRol);
	}
	
	@Test
	public void probarBuscarUsuarios() {
		List<Usuario> usuarios = usuariodao.buscar("Juan");
		System.out.println("usuarios: " + usuarios.size());
		Assert.assertEquals(1,usuarios.size());
	}*/
	
	@Test
	public void probarBuscarUsuarioxId() {
		Usuario usuario = usuariodao.buscar(6);
		System.out.println("Nombre: " + usuario.getNombres());
		Assert.assertEquals("Juan",usuario.getNombres());
	}
	
	/*@Test
	public void probarEliminarUsuarioxId() {
		usuariodao.eliminar(3);
		System.out.println("eliminado");
	}*/
}
