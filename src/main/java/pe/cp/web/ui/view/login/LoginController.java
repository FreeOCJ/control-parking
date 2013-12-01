package pe.cp.web.ui.view.login;

import pe.cp.web.ui.view.login.ILoginView;
import pe.cp.web.ui.view.login.ILoginViewHandler;

public class LoginController implements ILoginViewHandler {

	private ILoginView view;
	
	public LoginController(ILoginView view){
		this.view = view;
	}
	
	@Override
	public void Login() {
		if (true){ 
			view.afterSuccessfulLogin();
		}
	}

}
