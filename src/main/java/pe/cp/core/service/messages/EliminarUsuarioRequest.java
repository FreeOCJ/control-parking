package pe.cp.core.service.messages;

public class EliminarUsuarioRequest {
    private int idUsuario;
    
    public EliminarUsuarioRequest(int idUsuario) {
    	this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario() {
    	return idUsuario;
    }
}
