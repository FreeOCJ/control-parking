package pe.cp.core.service.messages;

public class ObtenerDetalleOperacionRequest {
   private int idOperacion;
   
   public ObtenerDetalleOperacionRequest(int idOperacion) {
	   this.idOperacion = idOperacion;
   }
   
   public int getIdOperacion() {
	   return idOperacion;
   }
}
