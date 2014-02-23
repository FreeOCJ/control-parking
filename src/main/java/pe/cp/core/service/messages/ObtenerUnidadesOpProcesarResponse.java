package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.UnidadOperativaView;

public class ObtenerUnidadesOpProcesarResponse extends Response {
    private List<UnidadOperativaView> unidadesOpViews;

	public List<UnidadOperativaView> getUnidadesOpViews() {
		return unidadesOpViews;
	}
	
	public void setUnidadesOpViews(List<UnidadOperativaView> unidadesOpViews) {
		this.unidadesOpViews = unidadesOpViews;
	} 
}
