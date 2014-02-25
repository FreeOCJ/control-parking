package pe.cp.core.service.domain;

public class OperacionPorTarifaView {
    private int idOpTarifa;
    private String descripcion;
    private int cantidad;
    private float monto;
    private float precioTarifa;
    
	public int getIdOpTarifa() {
		return idOpTarifa;
	}
	public void setIdOpTarifa(int idOpTarifa) {
		this.idOpTarifa = idOpTarifa;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public float getPrecioTarifa() {
		return precioTarifa;
	}
	public void setPrecioTarifa(float precioTarifa) {
		this.precioTarifa = precioTarifa;
	}
}
