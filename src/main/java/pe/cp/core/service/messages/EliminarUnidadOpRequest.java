package pe.cp.core.service.messages;

public class EliminarUnidadOpRequest {
    private int idUnidadOp;
    
    public EliminarUnidadOpRequest(int idUnidadOperativa) {
    	this.idUnidadOp = idUnidadOperativa;
    }
    
    public int getIdUnidadOperativa() {
    	return idUnidadOp;
    }
}
