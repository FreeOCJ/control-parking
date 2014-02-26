package pe.cp.core.service.messages;

public class ObtenerIncidenciaRequest {
    private int idIncidencia;
    
    public ObtenerIncidenciaRequest(int idIncidencia) {
    	this.idIncidencia = idIncidencia;
    }

	public int getIdIncidencia() {
		return idIncidencia;
	}
}
