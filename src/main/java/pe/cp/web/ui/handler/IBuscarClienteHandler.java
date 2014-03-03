package pe.cp.web.ui.handler;

import com.vaadin.data.Container;

public interface IBuscarClienteHandler {
	void cargar();
	void buscar();
	void irAgregarNuevoCliente();
	void irEditarCliente(int idCliente);
	void eliminarCliente(int idCliente);
	Container obtenerHeadersContainer();
	void validarUsuario();
	void mostrarMensajeInicio();
}
