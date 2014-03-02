package pe.cp.web.ui.view.auditoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.AuditoriaService;

public class AuditoriaController implements IAuditHandler {

	ApplicationContext ac;
	private IAuditoriaView view;
	
	@Autowired
	private AuditoriaService auditService;
	
	public AuditoriaController(IAuditoriaView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		auditService = ac.getBean(AuditoriaService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepararTabla() {
		// TODO Auto-generated method stub

	}

}
