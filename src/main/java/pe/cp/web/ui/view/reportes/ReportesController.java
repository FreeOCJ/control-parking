package pe.cp.web.ui.view.reportes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
@Theme("controlparking")
public class ReportesController  implements IReportesViewHandler{
	
	private IReportesView view;
	
	public ReportesController(IReportesView view){
		this.view = view;
	}

	@Override
	public void irReportesIncidencias() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTES_INCIDENCIAS);
		
	}

}
