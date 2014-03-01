package pe.cp.core.service.messages;

public class RemoverTarifaRequest {
    private int idUnidadOp;
    private String categoria;
    
    public RemoverTarifaRequest(int idUnidadOp, String categoria) {
    	this.idUnidadOp = idUnidadOp;
    	this.categoria = categoria;
    }
    
    public int getIdUnidadOp() {
    	return idUnidadOp;
    }
    
    public String getCategoria() {
    	return categoria;
    }
}
