package pe.cp.core.service.domain;

import java.util.List;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Rol;

public class UsuarioView {
	private int id;
	private String nombres;
	private String apellidos;
	private String cargo;
	private String email;
	private String login;
	private String password;
	private Cliente cliente;
	private List<Rol> roles;
	private String nombreCompleto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Rol> getRoles() {
		return roles;
	}
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	public String getRolesAsString(){
		String rolesAsString = "";
		if(this.roles != null && this.roles.size()>0){
			for(Rol rol:roles){
				rolesAsString = rolesAsString + rol.getDescripcion() + ",";
			}
		}
		return rolesAsString;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	
}
