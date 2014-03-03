package pe.cp.web.ui.handler;

import com.vaadin.data.Container;

public interface IBuscarOperacionesHandler {

	Container obtenerHeadersContainer();
	void cargarComboUnidadesOp();
	void cargarComboEstados();
	void irNuevaOperacion();
	void irEditarOperacion(int idOperacion);
	void validarUsuario();
	void cargar();
	void buscar();
}
