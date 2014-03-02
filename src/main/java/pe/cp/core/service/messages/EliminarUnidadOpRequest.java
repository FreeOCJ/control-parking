package pe.cp.core.service.messages;

public class EliminarUnidadOpRequest {
    private int idUnidadOp;
    private int idUsuarioModificador;
    
    public EliminarUnidadOpRequest(int idUnidadOperativa, int idUsuarioModificador) {
    	this.idUnidadOp = idUnidadOperativa;
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
    public int getIdUnidadOperativa() {
    	return idUnidadOp;
    }
    
    public int getIdUsuarioModificador() {
       	return idUsuarioModificador;
       }
}
