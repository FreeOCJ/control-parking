package pe.cp.web.ui.view.login;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public interface ILoginView extends View {
	void init();
	
	TextField getTxtUsername();
	PasswordField getTxtPassword();
	Button getBtnLogin();
	
	void afterSuccessfulLogin();
	void setHandler(ILoginViewHandler handler);
}
