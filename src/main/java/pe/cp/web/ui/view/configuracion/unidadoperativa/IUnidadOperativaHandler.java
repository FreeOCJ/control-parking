package pe.cp.web.ui.view.configuracion.unidadoperativa;

import com.vaadin.data.Container;

public interface IUnidadOperativaHandler {
	void cargar();
	void cargarDepartamentos();
	void cargarProvincias();
	void cargarDistritos();
	Container obtenerHeadersCategoriaContainer();
	void guardar();
	void actualizar();
	void cancelar();
	void validarUsuario();
	void guardarUsuarios();
	void guardarAprobadores();
	void guardarOperadores();
	void irMantenimientoTarifa(String categoria);
	void mostrarMensajeInicio();
}
