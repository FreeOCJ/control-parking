package pe.cp.core.domain;

public class OperacionPorTarifa {
	private int idOperacionPorTarifa;
	private int cantidadTickets;
	private float monto;
	private int idTarifa;
	private int idOperacion;
	private String categoria;
	private float precioTarifa;
	
	public int getCantidadTickets() {
		return cantidadTickets;
	}
	public void setCantidadTickets(int cantidadTickets) {
		this.cantidadTickets = cantidadTickets;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public int getIdOperacionPorTarifa() {
		return idOperacionPorTarifa;
	}
	public void setIdOperacionPorTarifa(int idOperacionPorTarifa) {
		this.idOperacionPorTarifa = idOperacionPorTarifa;
	}
	public int getIdTarifa() {
		return idTarifa;
	}
	public void setIdTarifa(int idTarifa) {
		this.idTarifa = idTarifa;
	}
	public int getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}
	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the precioTarifa
	 */
	public float getPrecioTarifa() {
		return precioTarifa;
	}
	/**
	 * @param precioTarifa the precioTarifa to set
	 */
	public void setPrecioTarifa(float precioTarifa) {
		this.precioTarifa = precioTarifa;
	}	
}
