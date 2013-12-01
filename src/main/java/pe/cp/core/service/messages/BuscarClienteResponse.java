package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.domain.Cliente;

public class BuscarClienteResponse extends Response {
	private List<Cliente> clientesEncontrados;

	public List<Cliente> getClientesEncontrados() {
		return clientesEncontrados;
	}

	public void setClientesEncontrados(List<Cliente> clientesEncontrados) {
		this.clientesEncontrados = clientesEncontrados;
	}
}
