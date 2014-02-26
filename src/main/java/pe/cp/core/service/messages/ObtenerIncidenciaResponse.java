package pe.cp.core.service.messages;

import pe.cp.core.service.domain.IncidenciaView;
import pe.cp.core.service.domain.TipoIncidenciaView;

public class ObtenerIncidenciaResponse extends Response {
    private IncidenciaView incidenciaView;
    private TipoIncidenciaView tipoView;

	/**
	 * @return the incidenciaView
	 */
	public IncidenciaView getIncidenciaView() {
		return incidenciaView;
	}

	/**
	 * @param incidenciaView the incidenciaView to set
	 */
	public void setIncidenciaView(IncidenciaView incidenciaView) {
		this.incidenciaView = incidenciaView;
	}

	/**
	 * @return the tipoView
	 */
	public TipoIncidenciaView getTipoView() {
		return tipoView;
	}

	/**
	 * @param tipoView the tipoView to set
	 */
	public void setTipoView(TipoIncidenciaView tipoView) {
		this.tipoView = tipoView;
	}
}
