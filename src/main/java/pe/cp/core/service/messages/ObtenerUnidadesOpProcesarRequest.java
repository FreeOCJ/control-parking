package pe.cp.core.service.messages;

public class ObtenerUnidadesOpProcesarRequest {
   private String login;
   private boolean paraOperador;
   private boolean paraAprobador;
   
   public ObtenerUnidadesOpProcesarRequest(String login, boolean paraAprobador, boolean paraRevisor) {
	   this.login = login;
	   this.paraAprobador = paraAprobador;
	   this.paraOperador = paraRevisor;
   }

   public String getLogin() {
      return login;
   } 
   
   public boolean getIsParaAprobador() {
	   return paraAprobador;
   }
   
   public boolean getIsParaOperador() {
	   return paraOperador;
   }
}
