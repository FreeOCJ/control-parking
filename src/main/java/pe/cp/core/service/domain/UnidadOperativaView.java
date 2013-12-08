package pe.cp.core.service.domain;

import java.util.Date;

public class UnidadOperativaView {
	private int id;
	private String nombre;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	private Date horaApertura;
	private Date horaCierre;
	private int nroCajones;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public Date getHoraApertura() {
		return horaApertura;
	}
	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}
	public Date getHoraCierre() {
		return horaCierre;
	}
	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}
	public int getNroCajones() {
		return nroCajones;
	}
	public void setNroCajones(int nroCajones) {
		this.nroCajones = nroCajones;
	}	
}
