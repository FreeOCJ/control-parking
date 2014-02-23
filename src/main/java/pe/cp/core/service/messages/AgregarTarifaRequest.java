package pe.cp.core.service.messages;

public class AgregarTarifaRequest {
   private int idUnidadOperativa;
   private double[] montos;
   private String categoria;
   
   public AgregarTarifaRequest(int idUnidadOp, double[] montos, String categoria) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.montos = montos;
	   this.categoria = categoria;
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
}
