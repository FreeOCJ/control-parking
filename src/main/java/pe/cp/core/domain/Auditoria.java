package pe.cp.core.domain;

import java.util.Date;

public class Auditoria {
	private int id;
	private Date fechaCreacion;
	private String evento;
	private String nombreUsuario;
	private String loginUsuario;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}	
}
