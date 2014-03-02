package pe.cp.core.service.messages;

public class ActualizarClienteRequest {
	private String razonSocial;
	private String nombreComercial;
	private String ruc;
	private int idCliente;
	private int idUsuario;
	
	public ActualizarClienteRequest(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdUsuario() {
		return idUsuario;
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
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}	
}
