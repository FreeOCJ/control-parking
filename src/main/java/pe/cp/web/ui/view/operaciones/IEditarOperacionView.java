package pe.cp.web.ui.view.operaciones;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public interface IEditarOperacionView extends View {
   void init();
   Label getLblNombreUnidadOp();
   Label getLblCreador();
   Label getLblEstado();
   Label getFechaRegistro();
   TextField getTxtOferta();
   Table getTblOperacionesPorHorario();
   TextField getTxtTicketInicial();
   TextField getTxtTicketFinal();
   TextField getTxtTotalDia();
   TextField getTxtAjuste();
   TextField getTxtPernoctadosAnteriores();
   TextField getTxtPernoctadosHoy();
   TextField gdtTxtTotalIngresos();
   TextField getTxtTotalSalidas();
   TextField getTxtTotalPersonas();
   TextField getTxtTotalRecaudacion();
   TextField getTxtTotalCarrosTarifa();
   Table getTblControlTarifas();
   Table getTblIncidencias();
   Button getBtnAprobar();
   Button getBtnGuardar();
   Button getBtnEnviarAprobar();
   Button getBtnAgregarIncidencia();
   int getIdOperacion();
}
