package pe.cp.core.domain;

public class DetallePorOperacion {
	private int id;
	private String horaInicio;
	private String horaFin;
	private int cantidadPersonas;
	private int cantidadIngresos;
	private int cantidadSalidas;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
	public int getCantidadIngresos() {
		return cantidadIngresos;
	}
	public void setCantidadIngresos(int cantidadIngresos) {
		this.cantidadIngresos = cantidadIngresos;
	}
	public int getCantidadSalidas() {
		return cantidadSalidas;
	}
	public void setCantidadSalidas(int cantidadSalidas) {
		this.cantidadSalidas = cantidadSalidas;
	}	
}
