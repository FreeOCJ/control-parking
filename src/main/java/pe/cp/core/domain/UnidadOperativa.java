package pe.cp.core.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UnidadOperativa implements IAuditInfo {
	private int id;
	private String nombre;
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
	private boolean eliminado;
	
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
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String getAuditInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		String audit = String.format("Unidad Operativa [ID=%s, NOMBRE=%s, HORA INICIO=%s, HORA FIN=%s]", 
                String.valueOf(id), nombre, sdf.format(horaInicio), sdf.format(horaFin));
        return audit;
	}	
}
