package pe.cp.core.service.domain;

public class OperacionDetalleView {
   private int idOperacionDetalle;
   private String horario;
   private int cantidadIngreso;
   private int cantidadSalida;
   private int cantidadPersonas;
   
	public int getIdOperacionDetalle() {
		return idOperacionDetalle;
	}
	public void setIdOperacionDetalle(int idOperacionDetalle) {
		this.idOperacionDetalle = idOperacionDetalle;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public int getCantidadIngreso() {
		return cantidadIngreso;
	}
	public void setCantidadIngreso(int cantidadIngreso) {
		this.cantidadIngreso = cantidadIngreso;
	}
	public int getCantidadSalida() {
		return cantidadSalida;
	}
	public void setCantidadSalida(int cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
}
