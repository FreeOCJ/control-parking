package pe.cp.core.service.messages;

import java.util.Date;

public class AgregarOperacionRequest {
   private int idUnidadOperativa;
   private Date fechaOperacion;
   private String loginCreador;
   
   public AgregarOperacionRequest(int idUnidadOp, Date fechaOp, String login) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.fechaOperacion = fechaOp;
	   this.loginCreador = login;
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
}
