package pe.cp.core.service.messages;

public class ObtenerOperacionPorTarifaRequest {
   private int idOperacion;
   
   public ObtenerOperacionPorTarifaRequest(int idOperacion) {
	   this.idOperacion = idOperacion;
   }

	/**
	 * @return the idOperacion
	 */
	public int getIdOperacion() {
		return idOperacion;
	}   
}
