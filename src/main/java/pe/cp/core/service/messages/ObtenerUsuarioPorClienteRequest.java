package pe.cp.core.service.messages;

public class ObtenerUsuarioPorClienteRequest {
	private int idCliente;
	
	public ObtenerUsuarioPorClienteRequest(int idCliente){
		this.idCliente = idCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}
}
