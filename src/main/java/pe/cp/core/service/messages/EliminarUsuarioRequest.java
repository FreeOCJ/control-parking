package pe.cp.core.service.messages;

public class EliminarUsuarioRequest {
    private int idUsuario;
    private int idUsuarioModificador;
    
    public EliminarUsuarioRequest(int idUsuario, int idUsuarioModificador) {
    	this.idUsuario = idUsuario;
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
    public int getIdUsuario() {
    	return idUsuario;
    }
    
    public int getIdUsuarioModificador() {
       	return idUsuarioModificador;
       }
}
