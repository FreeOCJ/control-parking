package pe.cp.core.service.messages;

import java.util.Date;

public class BuscarOperacionesRequest {
   private int idUnidadOperativa;
   private Date fechaOperacion;
   private String estado;
   
   public BuscarOperacionesRequest(int idUnidadOp, Date fechaOp, String estado) {
	   this.idUnidadOperativa = idUnidadOp;
	   this.fechaOperacion = fechaOp;
	   this.estado = estado;
   }
   
   public int getIdUnidadOperativa() {
	   return idUnidadOperativa;
   }
   
   public Date getFechaOperacion() {
	   return fechaOperacion;
   }
   
   public String getEstado() {
	   return estado;
   }
}
