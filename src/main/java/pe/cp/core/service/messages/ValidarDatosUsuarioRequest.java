package pe.cp.core.service.messages;

public class ValidarDatosUsuarioRequest {
	private int idUsuario;
	private String login;
	
	public ValidarDatosUsuarioRequest(int idUsuario, String login){
		this.idUsuario = idUsuario;
		this.login = login; 
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public String getLogin() {
		return login;
	}
}
