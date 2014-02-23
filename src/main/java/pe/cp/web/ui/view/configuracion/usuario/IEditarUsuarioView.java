package pe.cp.web.ui.view.configuracion.usuario;

import com.vaadin.navigator.View;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public interface IEditarUsuarioView extends View {
	void init();	
	
	int getIdUsuario();
	int getIdCliente();
	TextField getLogin();
	TextField getNombres();
	TextField getApellidos();
	TextField getCargo();
	TextField getCorreoElectronico();
	TwinColSelect getRoles();
	Notification getNotification();
	void setNotification(Notification notification);
}
