package pe.cp.core.service.messages;

public class EliminarIncidenciaRequest {
   private int idIncidencia;
   
   public EliminarIncidenciaRequest(int idIncidencia) {
	   this.idIncidencia = idIncidencia;
   }

   public int getIdIncidencia() {
       return idIncidencia;
   }
}
