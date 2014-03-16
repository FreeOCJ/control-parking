package pe.cp.web.ui.view.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.handler.IReportesViewHandler;
import pe.cp.web.ui.handler.impl.ReportesController;
import pe.cp.web.ui.view.IReportesView;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesViewImpl extends HorizontalLayout implements IReportesView {

	private CssLayout contenido;
	private ComboBox cbUnidadOperativa;
	private ComboBox cbCliente;
	
	private NativeButton btnReportesIncidencias;
	private NativeButton btnVerIngresoSalida;
	private NativeButton btnVerVisitas;
	private NativeButton btnVerConsolidado;
	private NativeButton btnVerRecaudacion;
	
	@Autowired
	private IReportesViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new ReportesController(this);
		init();
		handler.cargar();
		handler.validarUsuario();
	}
	
		
	@Override
	public void init() {		
        setSizeFull();		
		addStyleName("main-view");		
		SideBarViewImpl barraControl = new SideBarViewImpl();
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
	    
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setMargin(true);
	    areaPrincipal.addComponent(menuLayout);
	    
	    btnReportesIncidencias = new NativeButton();
	    btnVerConsolidado = new NativeButton();
	    btnVerIngresoSalida = new NativeButton();
	    btnVerRecaudacion = new NativeButton();
	    btnVerVisitas = new NativeButton();
	    
	    String infoIngresoSalidas = "Visualizar la información de ingreso y salidas de una unidad operativa por día, semana y mes"; 
	    String infoIncidencias = "Visualizar todas las incidencias ocurridas en una unidad operativa por rango de fechas";
	    String infoVisitas = "Visualizar la información del estimado de visitas generado por una unidad operativa";
	    String infoConsolidado = "Generar un reporte consolidad mensual de una unidad operativa";
	    String infoRecaudacion = "Visualizar la información de la recaudación generada por una unidad operativa";
	    
	    menuLayout.addComponent(agregarReporte(btnVerIngresoSalida,"Ingresos & Salidas", infoIngresoSalidas, "icon-ingresosalidas"));
	    menuLayout.addComponent(agregarReporte(btnReportesIncidencias,"Incidencias", infoIncidencias, "icon-incidencias"));
	    menuLayout.addComponent(agregarReporte(btnVerVisitas,"Visitas", infoVisitas, "icon-visitas"));
	    menuLayout.addComponent(agregarReporte(btnVerRecaudacion,"Recaudación", infoRecaudacion, "icon-recaudacion"));
	    menuLayout.addComponent(agregarReporte(btnVerConsolidado,"Consolidado", infoConsolidado, "icon-consolidado"));
	    
	    cbCliente.addValueChangeListener(new ValueChangeListener(){
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.cargarUnidadesOperativaPorCliente();				
			}	    	
	    });
	    
		return areaPrincipal;
	}
	
	private HorizontalLayout agregarReporte(NativeButton btnReporte, String labelBoton, String texto, String estilo) {
		HorizontalLayout seccionLayout = new HorizontalLayout();
	    CssLayout divSeccion = new CssLayout();
	    CssLayout spanSeccion = new CssLayout();
	    HorizontalLayout divInformacionAdicional = new HorizontalLayout();
	    btnReporte.setCaption(labelBoton);
	    Label lblIncidencias = new Label(texto);
	    
	    divSeccion.setWidth("200px");
	    divSeccion.setHeight("60px");
	    divSeccion.addStyleName("sidebar");
	    spanSeccion.addStyleName("menu");
	    btnReporte.setStyleName(estilo);
	    divInformacionAdicional.setMargin(true);
	    divInformacionAdicional.setWidth("500px");
	    
	    seccionLayout.addComponent(divSeccion);
	    divSeccion.addComponent(spanSeccion);
	    spanSeccion.addComponent(btnReporte);
	    seccionLayout.addComponent(divInformacionAdicional);
	    divInformacionAdicional.addComponent(lblIncidencias);
	    
	    return seccionLayout;
	}

	@Override
	public ComboBox getCbCliente() {
		return cbCliente;
	}

	@Override
	public ComboBox getCbUnidadOp() {
		return cbUnidadOperativa;
	}


	@Override
	public NativeButton getBtnVerIncidencias() {
		return btnReportesIncidencias;
	}


	@Override
	public NativeButton getBtnVerIngresoSalida() {
		return btnVerIngresoSalida;
	}


	@Override
	public NativeButton getBtnVerVisitas() {
		return btnVerVisitas;
	}


	@Override
	public NativeButton getBtnVerRecaudacion() {
		return btnVerRecaudacion;
	}


	@Override
	public NativeButton getBtnVerConsolidado() {
		return btnVerConsolidado;
	}
}
