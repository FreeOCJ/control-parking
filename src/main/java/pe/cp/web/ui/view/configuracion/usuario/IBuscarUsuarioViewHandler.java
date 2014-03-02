package pe.cp.web.ui.view.configuracion.usuario;

import com.vaadin.data.Container;


public interface IBuscarUsuarioViewHandler {
	Container setHeaderTable();
	void  buscarpornombre(String nombre);
	void irAgregarNuevoUsuario();
	void irEditarUsuario(int idUsuario);
	void validarUsuario();
	void mostrarMensajeInicio();
	void cargar();
}
