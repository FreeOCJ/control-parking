package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.domain.UsuarioView;

public class BuscarUsuarioResponse extends Response {
	private List<UsuarioView> usuariosEncontrados;

	public List<UsuarioView> getUsuariosEncontrados() {
		return usuariosEncontrados;
	}

	public void setUsuariosEncontrados(List<UsuarioView> usuariosEncontrados) {
		this.usuariosEncontrados = usuariosEncontrados;
	}
}
