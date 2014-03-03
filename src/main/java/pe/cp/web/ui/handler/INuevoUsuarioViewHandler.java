package pe.cp.web.ui.handler;

public interface INuevoUsuarioViewHandler {
	void cancelar();
	void guardar();
	void cargarRoles();
	void validarUsuario();
	boolean validarDatosEntrada();
	//void enviarMail(String from, String pwd);
}
