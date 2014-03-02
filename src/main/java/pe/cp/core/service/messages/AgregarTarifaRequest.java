package pe.cp.core.service.messages;

public class AgregarTarifaRequest {
   private int idUnidadOperativa;
   private double[] montos;
   private String categoria;
   private int idUsuarioModificador;
   
   public AgregarTarifaRequest(int idUnidadOp, double[] montos, String categoria, int idUsuarioModificador) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.montos = montos;
	   this.categoria = categoria;
	   this.idUsuarioModificador = idUsuarioModificador;
   }

   public int getIdUnidadOperativa() {
      return idUnidadOperativa;
   }

   public double[] getMontos() {
      return montos;
   }

   public String getCategoria() {
      return categoria;
   }
   
   public int getIdUsuarioModificador() {
	   return idUsuarioModificador;
   }
}
