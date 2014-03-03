package pe.cp.core.service.messages;

public class ActualizarContrasenaRequest {
    private int idUsuario;
    private String nuevaContrasena;
    private String confirmacionContrasena;
    private String contrasenaAntigua;
    
    public ActualizarContrasenaRequest(int idUsuario, String nuevaContrasena, 
    		String confirmacionContrasena, String contrasenaAntigua) {
    	this.idUsuario = idUsuario;
    	this.nuevaContrasena = nuevaContrasena;
    	this.confirmacionContrasena = confirmacionContrasena;
    	this.contrasenaAntigua = contrasenaAntigua;
    }

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getNuevaContrasena() {
		return nuevaContrasena;
	}

	public String getConfirmacionContrasena() {
		return confirmacionContrasena;
	}

	public String getContrasenaAntigua() {
		return contrasenaAntigua;
	}
}
