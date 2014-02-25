package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.IncidenciaView;

public class ObtenerIncidenciasResponse extends Response {
    private List<IncidenciaView> incidenciasView;

	/**
	 * @return the incidenciasView
	 */
	public List<IncidenciaView> getIncidenciasView() {
		return incidenciasView;
	}

	/**
	 * @param incidenciasView the incidenciasView to set
	 */
	public void setIncidenciasView(List<IncidenciaView> incidenciasView) {
		this.incidenciasView = incidenciasView;
	}
    
    
}
