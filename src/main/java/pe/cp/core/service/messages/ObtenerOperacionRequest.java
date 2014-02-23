package pe.cp.core.service.messages;

public class ObtenerOperacionRequest {
   private int idOperacion;
   
   public ObtenerOperacionRequest(int idOperacion) {
	   this.idOperacion = idOperacion;
   }
   
   public int getIdOperacion() {
	   return idOperacion;
   }
}
