package pe.cp.core.domain;

import java.util.Date;

public class Incidencia {
	private int id;
	private String descripcion;
	private TipoIncidencia tipoIncidencia;
	private Date fechaIncidencia;
	private int idOperacion;
	
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
	public TipoIncidencia getTipoIncidencia() {
		return tipoIncidencia;
	}
	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	public int getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}	
}
