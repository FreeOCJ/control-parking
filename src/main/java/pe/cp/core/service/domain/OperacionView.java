package pe.cp.core.service.domain;

import java.util.Date;

public class OperacionView {
	private int id;
	private Date fechaTransaccion;
	private String oferta;
	private int cantidadVehiculosEntrada;
	private int cantidadVehiculosSalida;
	private int numeroTicketInicio;
	private int numeroTicketFin;
	private int cantidadRaudos;
	private int cantidadPersonas;
	private int cantidadPernoctadosInicio;
	private int cantidadPernoctadosFin;
	private String creador;
	private String ultimoModificador;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private String estado;
	private int ajuste;
	private boolean eliminado;
	private int idUnidadOperativa;
	private String nombreUnidadOperativa;
	private String nombreCliente;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}
	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public int getCantidadVehiculosEntrada() {
		return cantidadVehiculosEntrada;
	}
	public void setCantidadVehiculosEntrada(int cantidadVehiculosEntrada) {
		this.cantidadVehiculosEntrada = cantidadVehiculosEntrada;
	}
	public int getCantidadVehiculosSalida() {
		return cantidadVehiculosSalida;
	}
	public void setCantidadVehiculosSalida(int cantidadVehiculosSalida) {
		this.cantidadVehiculosSalida = cantidadVehiculosSalida;
	}
	public int getNumeroTicketInicio() {
		return numeroTicketInicio;
	}
	public void setNumeroTicketInicio(int numeroTicketInicio) {
		this.numeroTicketInicio = numeroTicketInicio;
	}
	public int getNumeroTicketFin() {
		return numeroTicketFin;
	}
	public void setNumeroTicketFin(int numeroTicketFin) {
		this.numeroTicketFin = numeroTicketFin;
	}
	public int getCantidadRaudos() {
		return cantidadRaudos;
	}
	public void setCantidadRaudos(int cantidadRaudos) {
		this.cantidadRaudos = cantidadRaudos;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
	public int getCantidadPernoctadosInicio() {
		return cantidadPernoctadosInicio;
	}
	public void setCantidadPernoctadosInicio(int cantidadPernoctadosInicio) {
		this.cantidadPernoctadosInicio = cantidadPernoctadosInicio;
	}
	public int getCantidadPernoctadosFin() {
		return cantidadPernoctadosFin;
	}
	public void setCantidadPernoctadosFin(int cantidadPernoctadosFin) {
		this.cantidadPernoctadosFin = cantidadPernoctadosFin;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getUltimoModificador() {
		return ultimoModificador;
	}
	public void setUltimoModificador(String ultimoModificador) {
		this.ultimoModificador = ultimoModificador;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getAjuste() {
		return ajuste;
	}
	public void setAjuste(int ajuste) {
		this.ajuste = ajuste;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(int idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}
	public String getNombreUnidadOperativa() {
		return nombreUnidadOperativa;
	}
	public void setNombreUnidadOperativa(String nombreUnidadOperativa) {
		this.nombreUnidadOperativa = nombreUnidadOperativa;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
}
