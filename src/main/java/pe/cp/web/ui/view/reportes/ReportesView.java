package pe.cp.web.ui.view.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.main.SideBar;
import pe.cp.web.ui.view.reportes.IReportesView;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesView extends HorizontalLayout implements IReportesView {

	private CssLayout contenido;
	private ComboBox cbUnidadOperativa;
	private ComboBox cbCliente;
	

	private Button btnReportesIncidencias;
	
	@Autowired
	private IReportesViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new ReportesController(this);
		init();
		handler.cargar();
	}
	
		
	@Override
	public void init() {		
        System.out.println("init reportes");
        setSizeFull();		
		addStyleName("main-view");		
		SideBar barraControl = new SideBar();
		addComponent(barraControl);
		
		// Content
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);
        
        //Agregar Default
        contenido.addComponent(cargarContenido());
	}
	
	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Reportes");
	    title.addStyleName("h1");         
	    header.addComponent(title); 
	    
	    //Combos
	    cbCliente = new ComboBox();
	    cbCliente.setInputPrompt("Cliente");
	    cbCliente.setWidth("400px");
	    cbCliente.setItemCaptionPropertyId("NOMBRE");
	    cbUnidadOperativa = new ComboBox();
	    cbUnidadOperativa.setInputPrompt("Unidad Operativa");
	    cbUnidadOperativa.setWidth("400px");
	    cbUnidadOperativa.setItemCaptionPropertyId("NOMBRE");
	    
	    HorizontalLayout toolbar = new HorizontalLayout();
	    toolbar.setWidth("100%");
	    toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        
        toolbar.addComponent(cbCliente);
        toolbar.setComponentAlignment(cbCliente, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(cbUnidadOperativa);
        toolbar.setComponentAlignment(cbUnidadOperativa, Alignment.MIDDLE_LEFT);
        toolbar.setExpandRatio(cbUnidadOperativa, 1);
	    
		
	    btnReportesIncidencias = new Button("Reporte de Incidencias");
	    VerticalLayout configLayout = new VerticalLayout();
	    configLayout.addComponent(btnReportesIncidencias);
	    
	    btnReportesIncidencias.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irReportesIncidencias();		
			}
		});
	    
	    cbCliente.addValueChangeListener(new ValueChangeListener(){
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.cargarUnidadesOperativaPorCliente();				
			}	    	
	    });
	    		
	    areaPrincipal.addComponent(configLayout);
	    areaPrincipal.setExpandRatio(configLayout, 1);
		return areaPrincipal;
	}

	@Override
	public ComboBox getCbCliente() {
		return cbCliente;
	}

	@Override
	public ComboBox getCbUnidadOp() {
		return cbUnidadOperativa;
	}
	
	

}
