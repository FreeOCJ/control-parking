package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.TipoIncidenciaView;

public class ObtenerTipoIncidenciasResponse extends Response {
    private List<TipoIncidenciaView> tiposView;

	/**
	 * @return the tiposView
	 */
	public List<TipoIncidenciaView> getTiposView() {
		return tiposView;
	}

	/**
	 * @param tiposView the tiposView to set
	 */
	public void setTiposView(List<TipoIncidenciaView> tiposView) {
		this.tiposView = tiposView;
	}
}
