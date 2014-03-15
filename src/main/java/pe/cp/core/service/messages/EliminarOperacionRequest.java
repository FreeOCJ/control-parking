package pe.cp.core.service.messages;

public class EliminarOperacionRequest {
    private int idOperacion;
    private String loginModificador;
    
    public EliminarOperacionRequest(int idOperacion, String loginModificador) {
    	this.idOperacion = idOperacion;
    	this.loginModificador = loginModificador;
    }
    
    public int getIdOperacion() {
    	return idOperacion;
    }
    
    public String getLoginModificador() {
    	return loginModificador;
    }
}
