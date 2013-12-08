package pe.cp.web.ui.view.configuracion.unidadoperativa;

import org.vaadin.thomas.timefield.TimeField;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public interface INuevaUnidadOperativaView extends View {
	void init();
	TextField getNombre();
	TextField getDireccion();
	TextField getNombreComercialCliente();	
	ComboBox getDepartamento();
	ComboBox getProvincia();
	ComboBox getDistrito();
	TextField getNumeroCajones();
	TimeField getHoraApertura();
	TimeField getHoraCierre();	
	Table getCategorias();
	TwinColSelect getOperadores();
	TwinColSelect getAprobadores();
	TwinColSelect getUsuarios();
	int getIdCliente();
	int getIdUnidadOperativa();
}
