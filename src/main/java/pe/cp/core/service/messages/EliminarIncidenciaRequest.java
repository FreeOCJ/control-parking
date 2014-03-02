package pe.cp.core.service.messages;

public class EliminarIncidenciaRequest {
   private int idIncidencia;
   private int idUsuarioModificador;
   
   public EliminarIncidenciaRequest(int idIncidencia, int idUsuarioModificador) {
	   this.idIncidencia = idIncidencia;
	   this.idUsuarioModificador = idUsuarioModificador;
   }

   public int getIdIncidencia() {
       return idIncidencia;
   }
   
   public int getIdUsuarioModificador() {
      return idUsuarioModificador;
   }
}
