package pe.cp.core.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Operacion representa a la operacion diaria de una unidad operativa.
 * La carga se realiza de manera lazy loading.
 * @author juan
 *
 */
public class Operacion implements IAuditInfo {
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
	private List<DetallePorOperacion> detalles;
	private List<OperacionPorTarifa> operacionesTarifa;
	private boolean eliminado;
	private int idUnidadOperativa;
	private String auxNombreUnidadOperativa;
	private String auxNombreCliente;
	private UnidadOperativa unidadoperativa;
	
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
	public List<DetallePorOperacion> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetallePorOperacion> detalles) {
		this.detalles = detalles;
	}
	public List<OperacionPorTarifa> getOperacionesTarifa() {
		return operacionesTarifa;
	}
	public void setOperacionesTarifa(List<OperacionPorTarifa> operacionesTarifa) {
		this.operacionesTarifa = operacionesTarifa;
	}	
	
	public int getTotalIngresos(){
		//TODO
		return 0;
	}
	public int getTotalSalidas(){
		//TODO
		return 0;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	public UnidadOperativa getUnidadoperativa() {
		return unidadoperativa;
	}
	public void setUnidadoperativa(UnidadOperativa unidadoperativa) {
		this.unidadoperativa = unidadoperativa;
	}
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(int idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}
	public String getAuxNombreUnidadOperativa() {
		return auxNombreUnidadOperativa;
	}
	public void setAuxNombreUnidadOperativa(String auxNombreUnidadOperativa) {
		this.auxNombreUnidadOperativa = auxNombreUnidadOperativa;
	}
	public String getAuxNombreCliente() {
		return auxNombreCliente;
	}
	public void setAuxNombreCliente(String auxNombreCliente) {
		this.auxNombreCliente = auxNombreCliente;
	}
	@Override
	public String getAuditInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm");
		String audit = String.format("Operacion [ID=%s, UNIDAD OP=%s, CLIENTE=%s, FECHA TRANS=%s]", 
                String.valueOf(id), auxNombreUnidadOperativa, auxNombreCliente, sdf.format(fechaTransaccion));
        return audit;
	}
}
