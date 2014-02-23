package pe.cp.core.service.messages;

public class ObtenerTarifasRequest {
   private int idUnidadOperativa;
   private String categoriaTarifa;
   
   public ObtenerTarifasRequest(int idUnidadOp, String categoriaTarifa) { 
	   this.idUnidadOperativa = idUnidadOp;
	   this.categoriaTarifa = categoriaTarifa;
   }

   public int getIdUnidadOperativa() {
      return idUnidadOperativa;
   }   
   
   public String getCategoriaTarifa() {
	   return categoriaTarifa;
   }
}
