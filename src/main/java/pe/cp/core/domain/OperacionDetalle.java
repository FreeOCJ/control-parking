package pe.cp.core.domain;

import java.util.Date;

public class OperacionDetalle {
   private int idOpDetalle;
   private int idOperacion;
   private Date horaInicio;
   private Date horaFin;
   private int cantidadPersonas;
   private int cantidadIngresos;
   private int cantidadSalidas;
   
	public int getIdOpDetalle() {
		return idOpDetalle;
	}
	public void setIdOpDetalle(int idOpDetalle) {
		this.idOpDetalle = idOpDetalle;
	}
	public int getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
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
