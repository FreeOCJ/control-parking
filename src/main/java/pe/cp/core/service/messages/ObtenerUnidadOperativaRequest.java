package pe.cp.core.service.messages;

public class ObtenerUnidadOperativaRequest {
	private int idUnidadOperativa;

	public ObtenerUnidadOperativaRequest(int idUnidadOpertiva){
		this.idUnidadOperativa = idUnidadOpertiva;
	}
	
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}	
}
