package pe.cp.core.service.domain;

import java.util.Date;

public class IncidenciaView {
    private int id;
    private String tipo;
    private String hora;
    private String detalle;
    private Date fechaIncidencia;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Date getFechaIncidencia() {
		return fechaIncidencia;
	}
	public void setFechaIncidencia(Date fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}
}
