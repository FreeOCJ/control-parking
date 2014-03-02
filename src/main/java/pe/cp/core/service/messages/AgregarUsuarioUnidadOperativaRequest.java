package pe.cp.core.service.messages;

public class AgregarUsuarioUnidadOperativaRequest {
   private int idUnidadOperativa;
   private String rol;
   private int[] idsUsuarios;
   private int idUsuarioModificador;
   
   public AgregarUsuarioUnidadOperativaRequest(int idUnidadOp, String rol, int[] idsUsuarios, int idUsuarioModificador) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.rol = rol;
	   this.idsUsuarios = idsUsuarios;
	   this.idUsuarioModificador = idUsuarioModificador;
   }
   
   public int getIdUsuarioModificador() {
	   return idUsuarioModificador;
   }
   public int getIdUnidadOperativa() {
	return idUnidadOperativa;
   }
   public String getRol() {
    return rol;
   }
   public int[] getIdsUsuarios() {
	return idsUsuarios;
   }

   public static String CLIENTE = "CLIENTE";
   public static String OPERADOR = "OPERADOR";
   public static String APROBADOR = "APROBADOR";
}
