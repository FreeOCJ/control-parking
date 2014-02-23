package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.TarifaConsolidadoView;

public class ObtenerConsTarifasResponse extends Response{
   private List<TarifaConsolidadoView> tarifas;

   public List<TarifaConsolidadoView> getTarifas() {
    return tarifas;
   }
	
   public void setTarifas(List<TarifaConsolidadoView> tarifas) {
	this.tarifas = tarifas;
   }
}
