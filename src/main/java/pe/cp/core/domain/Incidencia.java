package pe.cp.core.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidencia implements IAuditInfo {
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
	@Override
	public String getAuditInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm");
		String audit = String.format("Incidencia [ID=%s, TIPO=%s, DESCRIPCION=%s, FECHA=%s, IDOPERACION=%s]", 
                String.valueOf(id), tipoIncidencia, descripcion, sdf.format(fechaIncidencia), String.valueOf(idOperacion));
        return audit;
	}	
}
