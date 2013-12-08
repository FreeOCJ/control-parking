package pe.cp.core.service.messages;

public class ObtenerClienteRequest {
	private int idCliente;
	
	public ObtenerClienteRequest(int idCliente){
		this.idCliente = (idCliente);
	}

	public int getIdCliente() {
		return idCliente;
	}	
}
