package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.ClienteView;

public class BuscarClienteResponse extends Response {
	private List<ClienteView> clientesEncontrados;

	public List<ClienteView> getClientesEncontrados()  {
		return clientesEncontrados;
	}

	public void setClientesEncontrados(List<ClienteView> clientesEncontrados) {
		this.clientesEncontrados = clientesEncontrados; 
	}
}
