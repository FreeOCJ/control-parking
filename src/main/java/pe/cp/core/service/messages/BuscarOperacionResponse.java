package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.OperacionView;

public class BuscarOperacionResponse extends Response{
   private List<OperacionView> operacionesView;

   public List<OperacionView> getOperacionesView() {
	return operacionesView;
   }

   public void setOperacionesView(List<OperacionView> operacionesView) {
	this.operacionesView = operacionesView;
   }
}
