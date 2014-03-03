package pe.cp.web.ui.view;

import pe.cp.web.ui.handler.IBuscarUsuarioViewHandler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Table;

public interface IBuscarUsuarioView extends View {
	void init();	
	void setHandler(IBuscarUsuarioViewHandler handler);
	void limpiarTabla();
	Table getTblResultados();
}
