package pe.cp.web.ui.view.login;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;

public interface IRecuperarContrasenaView extends View {
    void init();
    TextField getCorreoElectronico();
    Button getBtnEnviar();
    Button getBtnCancelar();
}
