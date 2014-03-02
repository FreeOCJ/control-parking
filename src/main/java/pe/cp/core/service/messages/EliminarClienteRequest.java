package pe.cp.core.service.messages;

public class EliminarClienteRequest {
    private int idCliente;
    
    public EliminarClienteRequest(int idCliente) {
    	this.idCliente = idCliente;
    }
    
    public int getIdCliente() {
    	return idCliente;
    }
}
