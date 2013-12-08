package pe.cp.web.ui.view.configuracion.unidadoperativa;

import com.vaadin.data.Container;

public interface IUnidadOperativaHandler {
	void cargar();
	void cargarDepartamentos();
	void cargarProvincias();
	void cargarDistritos();
	Container obtenerHeadersCategoriaContainer();
}
