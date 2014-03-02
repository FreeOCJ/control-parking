package pe.cp.core.service.messages;

public class EnviarAprobarOperacionRequest {
    private int idOperacion;
    private int idUsuarioModificador;

    public EnviarAprobarOperacionRequest(int idOperacion, int idUsuarioModificador) {
    	this.idOperacion = idOperacion;
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
	public int getIdOperacion() {
		return idOperacion;
	}
	
	public int getIdUsuarioModificador() {
	    return idUsuarioModificador;
	}
}
