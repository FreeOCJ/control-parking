package pe.cp.core.service.messages;

public class RemoverTarifaRequest {
    private int idUnidadOp;
    private String categoria;
    private int idUsuarioModificador;
    
    public RemoverTarifaRequest(int idUnidadOp, String categoria, int idUsuarioModificador) {
    	this.idUnidadOp = idUnidadOp;
    	this.categoria = categoria;
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
    public int getIdUnidadOp() {
    	return idUnidadOp;
    }
    
    public String getCategoria() {
    	return categoria;
    }
    
    public int getIdUsuarioModificador() {
    	return idUsuarioModificador;
    }
}
