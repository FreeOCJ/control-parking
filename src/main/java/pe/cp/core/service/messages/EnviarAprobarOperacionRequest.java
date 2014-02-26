package pe.cp.core.service.messages;

public class EnviarAprobarOperacionRequest {
    private int idOperacion;

    public EnviarAprobarOperacionRequest(int idOperacion) {
    	this.idOperacion = idOperacion;
    }
    
	public int getIdOperacion() {
		return idOperacion;
	}
}
