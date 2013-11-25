package pe.cp.core.domain;

import java.util.Date;
import java.util.List;

public class UnidadOperativa {
	private int id;
	private int numeroCajones;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	private Date horaInicio;
	private Date horaFin;
	private Cliente cliente;
	private List<Usuario> operadores;
	private List<Usuario> aprobadores;
	private List<Usuario> usuarios;
	private List<Tarifa> tarifas;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumeroCajones() {
		return numeroCajones;
	}
	public void setNumeroCajones(int numeroCajones) {
		this.numeroCajones = numeroCajones;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Usuario> getOperadores() {
		return operadores;
	}
	public void setOperadores(List<Usuario> operadores) {
		this.operadores = operadores;
	}
	public List<Usuario> getAprobadores() {
		return aprobadores;
	}
	public void setAprobadores(List<Usuario> aprobadores) {
		this.aprobadores = aprobadores;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public List<Tarifa> getTarifas() {
		return tarifas;
	}
	public void setTarifas(List<Tarifa> tarifas) {
		this.tarifas = tarifas;
	}	
}
