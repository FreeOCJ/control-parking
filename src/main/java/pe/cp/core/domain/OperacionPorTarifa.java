package pe.cp.core.domain;

public class OperacionPorTarifa {
	private int cantidadTickets;
	private double monto;
	
	public int getCantidadTickets() {
		return cantidadTickets;
	}
	public void setCantidadTickets(int cantidadTickets) {
		this.cantidadTickets = cantidadTickets;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}	
}
