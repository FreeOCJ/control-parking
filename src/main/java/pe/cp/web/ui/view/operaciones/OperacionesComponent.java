package pe.cp.web.ui.view.operaciones;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class OperacionesComponent extends VerticalLayout {

	Table t;
	
	public OperacionesComponent(){		
		setSizeFull();		
        addStyleName("transactions");
        
        //Configurar Header
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        addComponent(header);
        
        Label title = new Label("Operaciones");
        title.addStyleName("h1");  
        
        Button btnNuevaOperacion = new Button("+");
        btnNuevaOperacion.setStyleName("default");
        
        header.addComponent(title);
        header.addComponent(btnNuevaOperacion);
        header.setComponentAlignment(btnNuevaOperacion, Alignment.MIDDLE_RIGHT);
        header.setMargin(true);
                
        //Crear Tools
        ComboBox cbUnidadOp = new ComboBox();
        cbUnidadOp.setInputPrompt("Unidad Operativa");
        cbUnidadOp.setWidth("200px");
        DateField dfFechaOperacion = new DateField();
        dfFechaOperacion.setWidth("200px");
        ComboBox cbEstado = new ComboBox();
        cbEstado.setInputPrompt("Estado");
        cbEstado.setWidth("200px");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");
                
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        addComponent(toolbar);
        
        toolbar.addComponent(cbUnidadOp);
        toolbar.setComponentAlignment(cbUnidadOp, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(dfFechaOperacion);
        toolbar.setComponentAlignment(dfFechaOperacion, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(cbEstado);
        toolbar.setComponentAlignment(cbEstado, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(btnBuscar);
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
        
        /*FormLayout unidadForm = new FormLayout();
        unidadForm.addComponent(cbUnidadOp);
        toolbar.addComponent(unidadForm);
        
        FormLayout fechaForm = new FormLayout();
        fechaForm.addComponent(dfFechaOperacion);
        toolbar.addComponent(fechaForm);
        
        FormLayout estadoForm = new FormLayout();
        estadoForm.addComponent(cbEstado);
        toolbar.addComponent(estadoForm);*/             
        
        //Tabla
        HorizontalLayout tablaLayout = new HorizontalLayout();
        tablaLayout.setSizeFull();
        addComponent(tablaLayout);
        
        setExpandRatio(tablaLayout, 1);
        
        t = new Table() {
/*            @Override
            protected String formatPropertyValue(Object rowId, Object colId,
                    Property<?> property) {
                if (colId.equals("Time")) {
                    SimpleDateFormat df = new SimpleDateFormat();
                    df.applyPattern("MM/dd/yyyy hh:mm:ss a");
                    return df
                            .format(((Calendar) property.getValue()).getTime());
                } else if (colId.equals("Price")) {
                    if (property != null && property.getValue() != null) {
                        String ret = new DecimalFormat("#.##").format(property
                                .getValue());
                        return "$" + ret;
                    } else {
                        return "";
                    }
                }
                return super.formatPropertyValue(rowId, colId, property);
            }*/
        };
        t.setSizeFull();
        t.addStyleName("borderless");
        t.setSelectable(true);
        t.setColumnCollapsingAllowed(true);
        t.setColumnReorderingAllowed(true);                               

        /*t.setVisibleColumns(new Object[] { "Unidad Operativa", "Cliente", "Fecha",
                							"Registrado Por", "Estado"});*/

        t.setFooterVisible(true);        

        // Allow dragging items to the reports menu
        t.setDragMode(TableDragMode.ROW);
        t.setMultiSelect(true);
	}
	
}
