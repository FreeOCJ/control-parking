package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.domain.Usuario;

public class BuscarUsuarioResponse extends Response {
	private List<Usuario> usuariosEncontrados;

	public List<Usuario> getUsuariosEncontrados() {
		return usuariosEncontrados;
	}

	public void setUsuariosEncontrados(List<Usuario> usuariosEncontrados) {
		this.usuariosEncontrados = usuariosEncontrados;
	}
}
