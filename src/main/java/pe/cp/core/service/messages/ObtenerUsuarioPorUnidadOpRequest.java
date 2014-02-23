package pe.cp.core.service.messages;

public class ObtenerUsuarioPorUnidadOpRequest {
   private int idUnidadOperativa;
   private String rol;
   
   public ObtenerUsuarioPorUnidadOpRequest(int idUnidadOp, String rol){
	   this.idUnidadOperativa = idUnidadOp;
	   this.rol = rol;
   }

   public int getIdUnidadOperativa() {
      return idUnidadOperativa;
   }

   public String getRol() {
     return rol;
   }
   
   public static String CLIENTE = "CLIENTE";
   public static String OPERADOR = "OPERADOR";
   public static String APROBADOR = "APROBADOR";
}
