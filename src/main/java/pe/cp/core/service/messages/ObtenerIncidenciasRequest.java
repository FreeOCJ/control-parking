package pe.cp.core.service.messages;

public class ObtenerIncidenciasRequest {
    private int idOperacion;
    
    public ObtenerIncidenciasRequest(int idOperacion) {
    	this.idOperacion = idOperacion;
    }

	public int getIdOperacion() {
		return idOperacion;
	}
}
