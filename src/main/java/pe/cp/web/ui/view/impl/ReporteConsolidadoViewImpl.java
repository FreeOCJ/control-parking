package pe.cp.web.ui.view.impl;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.handler.IReporteConsolidadoHandler;
import pe.cp.web.ui.handler.impl.ReporteConsolidadoController;
import pe.cp.web.ui.view.IReportesConsolidadoView;

@SuppressWarnings("serial")
public class ReporteConsolidadoViewImpl extends HorizontalLayout implements IReportesConsolidadoView {

	private CssLayout contenido;
	private IReporteConsolidadoHandler handler;
	private Label lblCliente;
	private Label lblUnidadOperativa;
	private ComboBox cbAnho;
	private ComboBox cbMes;
	private ComboBox cbFormato;
	private CheckBox chkIngresosSalidas;
	private CheckBox chkVisitas;
	private CheckBox chkIncidencias;
	private CheckBox chkRecaudacion;
	private Button btnGenerar;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler = new ReporteConsolidadoController(this);
		handler.cargar();
	}
	
	@Override
	public void init() {
		removeAllComponents();
		setSizeFull();
		addStyleName("main-view");		
		addComponent(new SideBarViewImpl());
		
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);        
        contenido.addComponent(cargarContenido());
	}
	
	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Reporte Consolidado");
	    header.addComponent(title); 
	    title.addStyleName("h1");  
	    
	    FormLayout formulario = new FormLayout();
	    formulario.setMargin(true);
	    lblCliente = new Label();
	    lblCliente.setCaption("Cliente");
	    lblUnidadOperativa = new Label();
	    lblUnidadOperativa.setCaption("Unidad Operativa");
	    cbAnho = new ComboBox();
	    cbAnho.setCaption("Año");
	    cbMes = new ComboBox();
	    cbMes.setCaption("Mes");
	    cbFormato = new ComboBox();
	    cbFormato.setCaption("Exportar como");
	    
	    VerticalLayout chkLayout = new VerticalLayout();
	    chkLayout.setCaption("Reportes");
	    chkIngresosSalidas = new CheckBox("Ingresos & Salidas");
	    chkVisitas = new CheckBox("Visitas");
	    chkIncidencias = new CheckBox("Incidencias");
	    chkRecaudacion = new CheckBox("Recaudación");
	    
	    btnGenerar = new Button("Generar");
	    btnGenerar.addStyleName("default");
	    
	    chkLayout.addComponent(chkIngresosSalidas);
	    chkLayout.addComponent(chkVisitas);
	    chkLayout.addComponent(chkIncidencias);
	    chkLayout.addComponent(chkRecaudacion);
	    
	    formulario.addComponent(lblCliente);
	    formulario.addComponent(lblUnidadOperativa);
	    formulario.addComponent(cbAnho);
	    formulario.addComponent(cbMes);
	    formulario.addComponent(cbFormato);
	    formulario.addComponent(chkLayout);
	    formulario.addComponent(btnGenerar);
	   
	    areaPrincipal.addComponent(formulario);
	    
	    return areaPrincipal;
	}

	@Override
	public Label getLblCliente() {
		return lblCliente;
	}

	@Override
	public Label getLblUnidadOp() {
		return lblUnidadOperativa;
	}

	@Override
	public ComboBox getCbAnho() {
		return cbAnho;
	}

	@Override
	public ComboBox getCbMes() {
		return cbMes;
	}

	@Override
	public ComboBox getCbFormato() {
		return cbFormato;
	}

	@Override
	public CheckBox getChbIngresosSalidas() {
		return chkIngresosSalidas;
	}

	@Override
	public CheckBox getChbVisitas() {
		return chkVisitas;
	}

	@Override
	public CheckBox getChbIncidencias() {
		return chkIncidencias;
	}

	@Override
	public CheckBox getChbRecaudacion() {
		return chkRecaudacion;
	}

	@Override
	public Button getBtnGenerar() {
		return btnGenerar;
	}

}
