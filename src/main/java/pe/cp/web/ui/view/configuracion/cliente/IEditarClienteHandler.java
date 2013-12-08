package pe.cp.web.ui.view.configuracion.cliente;

import com.vaadin.data.Container;

public interface IEditarClienteHandler {
	void guardar();
	void cancelar();
	void cargar();
	Container obtenerHeadersUsuariosContainer();
	Container obtenerHeadersUnidadesOpContainer();
	void irAgregarNuevaUnidadOperativa();
	void irAgregarNuevoUsuario();
}
