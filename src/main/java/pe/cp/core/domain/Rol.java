package pe.cp.core.domain;

public class Rol {
	public static String ADMINISTRADOR = "ADMINISTRADOR";
	public static String CLIENTE = "CLIENTE";
	public static String OPERADOR = "OPERADOR";
	public static String APROBADOR = "APROBADOR";
	
	private int id;
	private String descripcion;
	private boolean eliminado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
		
}
