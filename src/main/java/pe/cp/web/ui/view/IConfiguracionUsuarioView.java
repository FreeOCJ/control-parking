package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public interface IConfiguracionUsuarioView extends View {
    TextField getTxtEmail();
    PasswordField getTxtContrasena();
    PasswordField getTxtNuevaContrasena();
    PasswordField getTxtValidarContrasena();
    Button getBtnGuardarContrasena();
    Button getBtnGuardarEmail();
    void init();
}
