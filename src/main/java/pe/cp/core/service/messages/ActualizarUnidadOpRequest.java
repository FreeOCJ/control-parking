package pe.cp.core.service.messages;

import java.util.Date;

public class ActualizarUnidadOpRequest {
	private int idUnidadOperativa;
	private String nombre;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	private Date horaInicio;
	private Date horaCierre;
	private int nroCajones;
	private int idUsuarioModificador;

	public ActualizarUnidadOpRequest(int idUsuarioModificador) {
		this.idUsuarioModificador = idUsuarioModificador;
	}
	
	public int getIdUsuarioModificador() {
		return idUsuarioModificador;
	}
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(int idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
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
	public int getNroCajones() {
		return nroCajones;
	}
	public void setNroCajones(int nroCajones) {
		this.nroCajones = nroCajones;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraCierre() {
		return horaCierre;
	}
	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}
}
