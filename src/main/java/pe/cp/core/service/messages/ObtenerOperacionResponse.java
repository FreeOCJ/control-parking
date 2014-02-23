package pe.cp.core.service.messages;

import pe.cp.core.service.domain.OperacionView;

public class ObtenerOperacionResponse extends Response {
    private OperacionView operacionView;

	public OperacionView getOperacionView() {
		return operacionView;
	}
	
	public void setOperacionView(OperacionView operacionView) {
		this.operacionView = operacionView;
	}
}
