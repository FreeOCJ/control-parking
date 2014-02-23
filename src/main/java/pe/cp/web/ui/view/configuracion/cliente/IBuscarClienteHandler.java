package pe.cp.web.ui.view.configuracion.cliente;

import com.vaadin.data.Container;

public interface IBuscarClienteHandler {
	void buscar();
	void irAgregarNuevoCliente();
	void irEditarCliente(int idCliente);
	void eliminarCliente(int idCliente);
	Container obtenerHeadersContainer();
	void validarUsuario();
}
