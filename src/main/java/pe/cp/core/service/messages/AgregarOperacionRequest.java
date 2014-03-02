package pe.cp.core.service.messages;

import java.util.Date;

public class AgregarOperacionRequest {
   private int idUnidadOperativa;
   private Date fechaOperacion;
   private String loginCreador;
   private int idUsuarioModificador;
   
   public AgregarOperacionRequest(int idUnidadOp, Date fechaOp, String login, int idUsuarioModificador) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.fechaOperacion = fechaOp;
	   this.loginCreador = login;
	   this.idUsuarioModificador = idUsuarioModificador;
   }
   
   public int getIdUnidadOperativa() {
	   return idUnidadOperativa;
   }
   
   public Date getFechaOperacion() {
	   return fechaOperacion;
   }
   
   public String getLogin() {
	   return loginCreador;
   }
   
   public int getIdUsuarioModificador() {
	   return idUsuarioModificador;
   }
}
