package pe.cp.core.dao;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.UnidadOperativa;

public class UnidadOperativaDaoTest {
	ApplicationContext ac;
	
	@Autowired
	UnidadOperativaDao uoDao;
	@Autowired
	ClienteDao cDao;

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		uoDao = ac.getBean(UnidadOperativaDao.class);
		cDao = ac.getBean(ClienteDao.class);
	}
	
	@Test
	public void probarInsertarUnidadOperativa() {
		Cliente cliente = cDao.buscar(4);		
		
		Date horaInicio = new GregorianCalendar(2013, 12, 01, 8, 00, 00).getTime();
		Date horaFin = new GregorianCalendar(2013, 12, 01, 23, 00, 00).getTime();
		
		UnidadOperativa uop = new UnidadOperativa();
		uop.setDepartamento("Lima");
		uop.setProvincia("Lima");
		uop.setDistrito("San Borja");		
		uop.setHoraFin(horaFin);
		uop.setHoraInicio(horaInicio);
		uop.setNumeroCajones(20);
		uop.setCliente(cliente);
		uop.setDireccion("Av Angamos 2013");
		
		Integer idUnidad = uoDao.agregar(uop);
		Assert.assertNotNull(idUnidad);
				
		UnidadOperativa nuevaUnidadOp = uoDao.buscar(idUnidad);						
		Assert.assertEquals("Lima", nuevaUnidadOp.getDepartamento());
		Assert.assertEquals("Lima", nuevaUnidadOp.getProvincia());
		Assert.assertEquals("San Borja", nuevaUnidadOp.getDistrito());
		Assert.assertEquals("Av Angamos 2013", nuevaUnidadOp.getDireccion());
		/*Assert.assertEquals(horaInicio, nuevaUnidadOp.getHoraInicio());
		Assert.assertEquals(horaFin, nuevaUnidadOp.getHoraFin());*/
		Assert.assertEquals(20, nuevaUnidadOp.getNumeroCajones());
		Assert.assertEquals(idUnidad.toString(), String.valueOf(nuevaUnidadOp.getId()));
		
		return;
	}
}
