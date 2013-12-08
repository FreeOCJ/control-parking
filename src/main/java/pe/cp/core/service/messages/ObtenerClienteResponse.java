package pe.cp.core.service.messages;

import pe.cp.core.service.domain.ClienteView;

public class ObtenerClienteResponse extends Response {
	private ClienteView clienteView;

	public ClienteView getClienteView() {
		return clienteView;
	}

	public void setClienteView(ClienteView clienteView) {
		this.clienteView = clienteView;
	}	
}
