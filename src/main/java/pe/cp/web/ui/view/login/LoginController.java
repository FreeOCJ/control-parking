package pe.cp.web.ui.view.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.LoginService;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.core.service.messages.LoginResponse;
import pe.cp.web.ui.view.login.ILoginView;
import pe.cp.web.ui.view.login.ILoginViewHandler;

@Component
@Scope("prototype")
public class LoginController implements ILoginViewHandler {
	ApplicationContext ac;
	private ILoginView view;
		
	@Autowired
	private LoginService loginservice;
	
	public LoginController(ILoginView view){		
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		
		loginservice = ac.getBean(LoginService.class);
		this.view = view;
	}
	
	@Override
	public void Login(String username,String password) {	
		LoginRequest request = new LoginRequest(username, password);
		LoginResponse response = loginservice.login(request);
		
		if (response.isResultadoEjecucion()){
			if (response.isAutorizado()){ 
				System.out.println("login Successfull");
				view.afterSuccessfulLogin();
			}else{
				System.out.println("Password o Usuario Incorrecto");
			}	
		}		
	}

}
