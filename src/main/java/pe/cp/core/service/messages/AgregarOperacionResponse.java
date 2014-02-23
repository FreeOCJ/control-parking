package pe.cp.core.service.messages;

public class AgregarOperacionResponse extends Response {
    private int idNuevaOperacion;

	public int getIdNuevaOperacion() {
		return idNuevaOperacion;
	}
	
	public void setIdNuevaOperacion(int idNuevaOperacion) {
		this.idNuevaOperacion = idNuevaOperacion;
	}  
}
