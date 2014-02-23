package pe.cp.web.ui.view.configuracion.usuario;

public interface INuevoUsuarioViewHandler {
	void cancelar();
	void guardar();
	void cargarRoles();
	void validarUsuario();
	boolean validarDatosEntrada();
}
