package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.TarifaView;

public class ObtenerTarifasResponse extends Response {
   private List<TarifaView> tarifasView;

   public List<TarifaView> getTarifasView() {
      return tarifasView;
   }

   public void setTarifasView(List<TarifaView> tarifasView) {
      this.tarifasView = tarifasView;
   }
}
