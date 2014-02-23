package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.UsuarioView;

public class ObtenerUsuarioPorUnidadOpResponse extends Response {
	private List<UsuarioView> usuariosView;

	public List<UsuarioView> getUsuariosView() {
		return usuariosView;
	}

	public void setUsuariosView(List<UsuarioView> usuariosView) {
		this.usuariosView = usuariosView;
	}
}
