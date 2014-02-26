package pe.cp.core.service.messages;

public class AprobarOperacionRequest {
    private int idOperacion;
    
    public AprobarOperacionRequest(int idOperacion) {
    	this.idOperacion = idOperacion;
    }

	public int getIdOperacion() {
		return idOperacion;
	}
}
