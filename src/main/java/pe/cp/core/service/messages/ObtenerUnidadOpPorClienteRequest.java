package pe.cp.core.service.messages;

public class ObtenerUnidadOpPorClienteRequest {
	private int idCliente;
	
	public ObtenerUnidadOpPorClienteRequest(int idCliente){
		this.idCliente = idCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}
}
