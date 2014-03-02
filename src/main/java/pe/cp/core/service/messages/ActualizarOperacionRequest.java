package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionPorTarifaView;

public class ActualizarOperacionRequest {
    private int idOperacion;
    private int ticketInicial;
    private int ticketFinal;
    private int totalDia;
    private int pernoctadosHoy;
    private int pernoctadosAyer;
    private int totalIngresos;
    private int totalSalidas;
    private int totalPersonas;
    private int totalCarros;
    private float recaudacion;
    private String oferta;
    private List<OperacionDetalleView> detalles;
    private List<OperacionPorTarifaView> tarifas;
    private String loginModificador;
    private int ajuste;
    private int idUsuarioModificador;
    
    public ActualizarOperacionRequest(int idUsuarioModificador) {
    	this.idUsuarioModificador = idUsuarioModificador;
    }
    
    public int getIdUsuarioModificador() {
    	return idUsuarioModificador;
    }
	public int getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}
	public int getTicketInicial() {
		return ticketInicial;
	}
	public void setTicketInicial(int ticketInicial) {
		this.ticketInicial = ticketInicial;
	}
	public int getTicketFinal() {
		return ticketFinal;
	}
	public void setTicketFinal(int ticketFinal) {
		this.ticketFinal = ticketFinal;
	}
	public int getTotalDia() {
		return totalDia;
	}
	public void setTotalDia(int totalDia) {
		this.totalDia = totalDia;
	}
	public int getPernoctadosHoy() {
		return pernoctadosHoy;
	}
	public void setPernoctadosHoy(int pernoctadosHoy) {
		this.pernoctadosHoy = pernoctadosHoy;
	}
	public int getTotalIngresos() {
		return totalIngresos;
	}
	public void setTotalIngresos(int totalIngresos) {
		this.totalIngresos = totalIngresos;
	}
	public int getTotalSalidas() {
		return totalSalidas;
	}
	public void setTotalSalidas(int totalSalidas) {
		this.totalSalidas = totalSalidas;
	}
	public int getTotalPersonas() {
		return totalPersonas;
	}
	public void setTotalPersonas(int totalPersonas) {
		this.totalPersonas = totalPersonas;
	}
	public int getTotalCarros() {
		return totalCarros;
	}
	public void setTotalCarros(int totalCarros) {
		this.totalCarros = totalCarros;
	}
	public float getRecaudacion() {
		return recaudacion;
	}
	public void setRecaudacion(float recaudacion) {
		this.recaudacion = recaudacion;
	}
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public List<OperacionDetalleView> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<OperacionDetalleView> detalles) {
		this.detalles = detalles;
	}
	public List<OperacionPorTarifaView> getTarifas() {
		return tarifas;
	}
	public void setTarifas(List<OperacionPorTarifaView> tarifas) {
		this.tarifas = tarifas;
	}
	public String getLoginModificador() {
		return loginModificador;
	}
	public void setLoginModificador(String loginModificador) {
		this.loginModificador = loginModificador;
	}
	public int getAjuste() {
		return ajuste;
	}
	public void setAjuste(int ajuste) {
		this.ajuste = ajuste;
	}
	public int getPernoctadosAyer() {
		return pernoctadosAyer;
	}
	public void setPernoctadosAyer(int pernoctadosAyer) {
		this.pernoctadosAyer = pernoctadosAyer;
	}
}
