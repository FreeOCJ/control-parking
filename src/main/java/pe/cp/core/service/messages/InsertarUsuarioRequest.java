package pe.cp.core.service.messages;

import java.util.List;

public class InsertarUsuarioRequest {
	private String login;
	private String nombres;
	private String apellidos;
	private String cargo;
	private String email;
	private int idCliente;
	private List<Integer> idRoles;
	private int idUsuarioModificador;
	
	public InsertarUsuarioRequest(int idUsuarioModificador) {
		this.idUsuarioModificador = idUsuarioModificador;
	}
	
	public int getIdUsuarioModificador() {
    	return idUsuarioModificador;
    }
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public List<Integer> getIdRoles() {
		return idRoles;
	}
	public void setIdRoles(List<Integer> idRoles) {
		this.idRoles = idRoles;
	}
}
