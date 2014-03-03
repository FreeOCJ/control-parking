package pe.cp.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public interface INuevoClienteView extends View{
	void init();
	TextField getRazonSocial();
	TextField getRuc();
	TextField getNombreComercial();
	Notification getNotification();
	void setNotification(Notification notification);
}
