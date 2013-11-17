package pe.cp.core.domain;

import java.util.Date;

public class Incidencia {
	private int id;
	private String descripcion;
	private Date fechaIncidencia;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaIncidencia() {
		return fechaIncidencia;
	}
	public void setFechaIncidencia(Date fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}	
}
