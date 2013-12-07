package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.domain.UsuarioView;

public class ObtenerUsuarioResponse extends Response {
	private UsuarioView usuarioView;
	private List<RolView> rolesView;

	public UsuarioView getUsuarioView() {
		return usuarioView;
	}

	public void setUsuarioView(UsuarioView usuarioView) {
		this.usuarioView = usuarioView;
	}

	public List<RolView> getRolesView() {
		return rolesView;
	}

	public void setRolesView(List<RolView> rolesView) {
		this.rolesView = rolesView;
	}	
}
