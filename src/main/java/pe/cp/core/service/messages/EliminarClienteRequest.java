package pe.cp.core.service.messages;

public class EliminarClienteRequest {
    private int idCliente;
    private int idUsuarioModificador;
    
    public EliminarClienteRequest(int idCliente, int idUsuarioModificador) {
    	this.idCliente = idCliente;
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
    public int getIdUsuarioModificador() {
    	return idUsuarioModificador;
    }
    
    public int getIdCliente() {
    	return idCliente;
    }
}
