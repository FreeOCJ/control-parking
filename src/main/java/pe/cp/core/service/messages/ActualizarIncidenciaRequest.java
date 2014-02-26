package pe.cp.core.service.messages;

import java.util.Date;

public class ActualizarIncidenciaRequest {
    private int idIncidencia;
    private String descripcion;
    private int idTipo;
    private Date hora;
    
	public int getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
}
