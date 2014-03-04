package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public interface ILoginView extends View {
	void init();
	
	TextField getTxtUsername();
	PasswordField getTxtPassword();
	Button getBtnLogin();
	Button getBtnRecuperarContrasena();
	
	void afterSuccessfulLogin();
}
