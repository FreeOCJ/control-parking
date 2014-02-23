package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.OperacionDetalleView;

public class ObtenerDetalleOperacionResponse extends Response {
   private List<OperacionDetalleView> detallesView;

   public List<OperacionDetalleView> getDetallesView() {
	  return detallesView;
   }
	
   public void setDetallesView(List<OperacionDetalleView> detallesView) {
      this.detallesView = detallesView;
   }
}
