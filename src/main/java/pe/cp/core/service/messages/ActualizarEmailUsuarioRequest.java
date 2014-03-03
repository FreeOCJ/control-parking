package pe.cp.core.service.messages;

public class ActualizarEmailUsuarioRequest {
    private int idUsuario;
    private String correo;
    
    public ActualizarEmailUsuarioRequest(int idUsuario, String correo) {
    	this.idUsuario = idUsuario;
    	this.correo = correo;
    }
    
    public int getIdUsuario() {
    	return idUsuario;
    }
    
    public String getCorreo() {
    	return correo;
    }
}
