package pe.cp.core.service.messages;

import java.util.List;

public class ActualizarUsuarioRequest {
	private Integer idUsuario;
	private String login;
	private String nombres;
	private String apellidos;
	private String cargo;
	private String email;
	private List<Integer> idRoles;
	private int idUsuarioModificador;
	
	public ActualizarUsuarioRequest(int idUsuarioModificador) {
		this.idUsuarioModificador = idUsuarioModificador;
	}
	
	public int getIdUsuarioModificador() {
		return idUsuarioModificador;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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
	public List<Integer> getIdRoles() {
		return idRoles;
	}
	public void setIdRoles(List<Integer> idRoles) {
		this.idRoles = idRoles;
	}
}
