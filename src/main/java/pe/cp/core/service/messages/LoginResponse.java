package pe.cp.core.service.messages;

import pe.cp.core.domain.Usuario;

public class LoginResponse extends Response { 
	private boolean autorizado;
	private Usuario usuario;
	
	public boolean isAutorizado() {
		return autorizado;
	}
	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
}
