package pe.cp.core.domain;

import java.util.List;

public class Cliente {
	private int id;
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private boolean eliminado;
	private List<Usuario> usuarios;
	private List<UnidadOperativa> unidadesOp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public List<UnidadOperativa> getUnidadesOp() {
		return unidadesOp;
	}
	public void setUnidadesOp(List<UnidadOperativa> unidadesOp) {
		this.unidadesOp = unidadesOp;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}	
}
