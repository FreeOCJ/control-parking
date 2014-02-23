package pe.cp.core.service.messages;

public class ObtenerConsTarifasRequest {
   private int idUnidadOperativa;
   
   public ObtenerConsTarifasRequest(int idUnidadOp) {
	   this.idUnidadOperativa = idUnidadOp;
   }

	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
}
