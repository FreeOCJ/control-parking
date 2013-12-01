package pe.cp.web.ui.view.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import pe.cp.core.service.LoginService;
import pe.cp.core.service.messages.LoginRequest;
import pe.cp.web.ui.view.login.ILoginView;
import pe.cp.web.ui.view.login.ILoginViewHandler;

@Component
public class LoginController implements ILoginViewHandler {

	private ILoginView view;
	
	
	@Autowired
	private LoginService loginservice;
	
	
	public LoginController(ILoginView view){
		this.view = view;
	}
	
	@Override
	public void Login(String username,String password) {		
		if (loginservice.login(new LoginRequest(username, password)).isAutorizado()){ 
			System.out.println("login Successfull");
			view.afterSuccessfulLogin();
		}else{
			System.out.println("Password o Usuario Incorrecto");
		}
	}

}
