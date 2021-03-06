package pe.cp.web.ui.view;

import org.vaadin.thomas.timefield.TimeField;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public interface IUnidadOperativaView extends View {
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
	TabSheet getTablas();
	int getIdCliente();
	int getIdUnidadOperativa();
	Label getTitulo();
}
