package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.OperacionPorTarifaView;

public class ObtenerOperacionPorTarifaResponse extends Response{
    private List<OperacionPorTarifaView> opsPorTarifaView;
    private float totalRecaudacion;
    private int totalCarros;

	/**
	 * @return the opsPorTarifaView
	 */
	public List<OperacionPorTarifaView> getOpsPorTarifaView() {
		return opsPorTarifaView;
	}

	/**
	 * @param opsPorTarifaView the opsPorTarifaView to set
	 */
	public void setOpsPorTarifaView(List<OperacionPorTarifaView> opsPorTarifaView) {
		this.opsPorTarifaView = opsPorTarifaView;
	}

	/**
	 * @return the totalRecaudacion
	 */
	public float getTotalRecaudacion() {
		return totalRecaudacion;
	}

	/**
	 * @param totalRecaudacion the totalRecaudacion to set
	 */
	public void setTotalRecaudacion(float totalRecaudacion) {
		this.totalRecaudacion = totalRecaudacion;
	}

	/**
	 * @return the totalCarros
	 */
	public int getTotalCarros() {
		return totalCarros;
	}

	/**
	 * @param totalCarros the totalCarros to set
	 */
	public void setTotalCarros(int totalCarros) {
		this.totalCarros = totalCarros;
	}
}
