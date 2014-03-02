package pe.cp.core.service.messages;

public class InsertarClienteRequest {
	private String razonSocial;
	private String ruc;
	private String nombreComercial;
	private int idUsuarioModificador;
	
	public InsertarClienteRequest(int idUsuarioModificador) {
		this.idUsuarioModificador = idUsuarioModificador;
	}
	
	public int getIdUsuarioModificador() {
	      return idUsuarioModificador;
	   }
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}	
}
