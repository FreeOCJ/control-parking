package pe.cp.core.service.messages;

public class ObtenerUsuarioRequest {
	private int idUsuario;
	
	public ObtenerUsuarioRequest(int idUsuario){
		this.idUsuario = idUsuario;
	}

	public int getIdUsuario() {
		return idUsuario;
	}	
}
