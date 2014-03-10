package pe.cp.core.service.messages;

import java.util.Date;

public class AgregarIncidenciaRequest {
    private int idOperacion;
    private String descripcion;
    private String accion;
    private Date fecha;
    private int idTipo;
    private String tipoIncidencia;
    private int idUsuarioModificador;
    
    
    public AgregarIncidenciaRequest(int idOperacion, String descripcion, String accion, Date fecha, int idTipo, 
    		String tipoIncidencia, int idUsuarioModificador) {
    	this.idOperacion = idOperacion;
    	this.descripcion = descripcion;
    	this.fecha = fecha;
    	this.idTipo = idTipo;
    	this.tipoIncidencia = tipoIncidencia;
    	this.idUsuarioModificador = idUsuarioModificador;
    	this.accion = accion;
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
	
	public int getIdUsuarioModificador() {
		return idUsuarioModificador;
	}
    
	public String getAccion() {
		return accion;
	}
	
}
