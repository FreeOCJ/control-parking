package pe.cp.core.service.messages;

import java.util.Date;

public class AgregarIncidenciaRequest {
    private int idOperacion;
    private String descripcion;
    private Date fecha;
    private int idTipo;
    private String tipoIncidencia;
    
    
    public AgregarIncidenciaRequest(int idOperacion, String descripcion, Date fecha, int idTipo, String tipoIncidencia) {
    	this.idOperacion = idOperacion;
    	this.descripcion = descripcion;
    	this.fecha = fecha;
    	this.idTipo = idTipo;
    	this.tipoIncidencia = tipoIncidencia;
    }

	public int getIdOperacion() {
		return idOperacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getIdTipo() {
		return idTipo;
	}

	public String getTipoIncidencia() {
		return tipoIncidencia;
	}
    
}
