package pe.cp.core.service.messages;

public class ObtenerUsuariosSistemaRequest {
	public static String ROL_ADMINISTRADOR = "ADMINISTRADOR";
	public static String ROL_APROBADOR = "APROBADOR";
	public static String ROL_OPERADOR = "OPERADOR";
	private String rol;
	
	public ObtenerUsuariosSistemaRequest(String rol) {
		this.rol = rol;
	}

	public String getRol() {
		return rol;
	}
}
