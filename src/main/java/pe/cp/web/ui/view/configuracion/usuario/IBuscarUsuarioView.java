package pe.cp.web.ui.view.configuracion.usuario;

import com.vaadin.navigator.View;

public interface IBuscarUsuarioView extends View {
	void init();	
	void setHandler(IBuscarUsuarioViewHandler handler);
	void limpiarTabla();
}
