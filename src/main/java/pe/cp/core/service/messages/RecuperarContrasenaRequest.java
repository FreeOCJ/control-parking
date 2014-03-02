package pe.cp.core.service.messages;

public class RecuperarContrasenaRequest {
    private String email;
    
    public RecuperarContrasenaRequest(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return email;
    }
}
