package pe.cp.core.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Tarifa;
import pe.cp.core.domain.UnidadOperativa;

public class TarifaDaoTest {
ApplicationContext ac;
	
	@Autowired
	TarifaDao tdao;
	@Autowired
	UnidadOperativaDao udao;

	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		tdao = ac.getBean(TarifaDao.class);
		udao = ac.getBean(UnidadOperativaDao.class);
	}
	
	@Test
	//TestCase: Insertar una nueva tarifa a una unidad operativa
	public void probarInsertarTarifa() {
		UnidadOperativa unidadOp = udao.buscar(1);
		
		Tarifa tarifa = new Tarifa();
		tarifa.setCategoria("INTERBANK");
		tarifa.setMonto(2.00f);
		
		
		tdao.agregar(tarifa);
	}
}
