package pe.cp.web.ui.handler;

import com.vaadin.data.Container;

public interface IEditarClienteHandler {
	void guardar();
	void cancelar();
	void cargar();
	Container obtenerHeadersUsuariosContainer();
	Container obtenerHeadersUnidadesOpContainer();
	void irAgregarNuevaUnidadOperativa();
	void irAgregarNuevoUsuario();
	void irEditarUnidadOperativa(int idUnidadOperativa);
	void irEditarUsuario(int idUsuario,int idCliente);
	void validarUsuario();
	void mostrarMensajeInicio();
	void elimiarUsuario();
	void prepararTablaUsuarios();
}
