package pe.cp.web.ui.view.configuracion;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
@Theme("controlparking")
public class ConfigController implements IConfigViewHandler {

	private IConfigView view;
	
	public ConfigController(IConfigView view){
		this.view = view;
	}
	
	@Override
	public void irConfiguracionUsuarios() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARUSUARIOS);	
	}

	@Override
	public void irConfiguracionClientes() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARCLIENTES);	
	}

}
