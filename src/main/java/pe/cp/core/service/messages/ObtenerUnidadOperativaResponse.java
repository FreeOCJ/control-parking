package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.domain.UsuarioView;

public class ObtenerUnidadOperativaResponse extends Response {
	private UnidadOperativaView unidadOpView;
	private List<UsuarioView> usuariosUnidadOpView;

	public UnidadOperativaView getUnidadOpView() {
		return unidadOpView;
	}

	public void setUnidadOpView(UnidadOperativaView unidadOpView) {
		this.unidadOpView = unidadOpView;
	}

	public List<UsuarioView> getUsuariosUnidadOpView() {
		return usuariosUnidadOpView;
	}

	public void setUsuariosUnidadOpView(List<UsuarioView> usuariosUnidadOpView) {
		this.usuariosUnidadOpView = usuariosUnidadOpView;
	}	
}
