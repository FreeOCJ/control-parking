package pe.cp.web.ui.view.auditoria;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.auditoria.IAuditoriaView;
import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class AuditoriaViewImpl extends HorizontalLayout implements IAuditoriaView {

	private CssLayout contenido;
	private Button btnBuscar;
	private TextField txtUsuario;
	private DateField dfFechaInicio;
	private DateField dfFechaFin;
	private ComboBox cbTipoEvento;
	private Table tblResultados;
	
	private IAuditHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new AuditoriaController(this);
		init();
		handler.cargar();
	}
	
	@Override
	public void init() {		
		construirBase();
	}
	
	private void construirBase(){		
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
        
        contenido.addComponent(cargarContenido());
	}
	
	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Auditoría");
	    title.addStyleName("h1");         
	    header.addComponent(title); 
		
        //Crear Tools Busqueda
	    txtUsuario = new TextField();
	    txtUsuario.setInputPrompt("Usuario");	    	    
        cbTipoEvento = new ComboBox();
        cbTipoEvento.setInputPrompt("Tipo Evento");
        cbTipoEvento.setWidth("150px");        
        dfFechaInicio = new DateField();
        dfFechaInicio.setWidth("80px");
        dfFechaFin = new DateField();
        dfFechaFin.setWidth("80px");        
        btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");
                
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        
        toolbar.addComponent(txtUsuario);
        toolbar.setComponentAlignment(txtUsuario, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(cbTipoEvento);
        toolbar.setComponentAlignment(cbTipoEvento, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(dfFechaInicio);
        toolbar.setComponentAlignment(dfFechaInicio, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(new Label());
        toolbar.addComponent(dfFechaFin);
        toolbar.setComponentAlignment(dfFechaFin, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(new Label());
        toolbar.addComponent(btnBuscar);
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
        
        //Tabla resultados
        tblResultados = new Table();
        areaPrincipal.addComponent(tblResultados);
        
		return areaPrincipal;
	}

	@Override
	public Table getTblResultados() {
		return tblResultados;
	}

	@Override
	public TextField getTxtUsuari() {
		return txtUsuario;
	}

	@Override
	public DateField getDfFechaInicio() {
		return dfFechaInicio;
	}

	@Override
	public DateField getDfFechaFin() {
		return dfFechaFin;
	}

	@Override
	public Button getBtnBuscar() {
		return btnBuscar;
	}

	@Override
	public ComboBox getCbTipoEvento() {
		return cbTipoEvento;
	}
}
