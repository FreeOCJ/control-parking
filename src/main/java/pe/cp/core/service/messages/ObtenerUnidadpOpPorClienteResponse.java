package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.UnidadOperativaView;

public class ObtenerUnidadpOpPorClienteResponse extends Response {
	private List<UnidadOperativaView> unidadesOpView;

	public List<UnidadOperativaView> getUnidadesOpView() {
		return unidadesOpView;
	}

	public void setUnidadesOpView(List<UnidadOperativaView> unidadesOpView) {
		this.unidadesOpView = unidadesOpView;
	}
}
