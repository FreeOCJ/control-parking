package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.thomas.timefield.TimeField;

import pe.cp.web.ui.handler.IUnidadOperativaHandler;
import pe.cp.web.ui.handler.impl.EditarClienteController;
import pe.cp.web.ui.handler.impl.UnidadOperativaController;
import pe.cp.web.ui.view.IUnidadOperativaView;

import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Transferable;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnGenerator;

@Component
@Scope("prototype")
@Theme("controlparking")
public class NuevaUnidadOperativaViewImpl extends HorizontalLayout implements IUnidadOperativaView {

	private CssLayout contenido;
	private Label lblTitulo;
	private TextField txtNombre;
	private TextField txtDireccion;
	private TextField txtNombreComercialCliente;
	private ComboBox cbDepartamento;
	private ComboBox cbProvincia;
	private ComboBox cbDistrito;
	private TextField txtNumeroCajones;
	private TimeField txtHoraApertura;
	private TimeField txtHoraCierre;	
	private Table tblCategorias;
	private TwinColSelect tcsOperadores;
	private TwinColSelect tcsAprobadores;
	private TwinColSelect tcsUsuarios;
	private TabSheet tsTablas;
	private VerticalLayout tabCategorias;
	private VerticalLayout tabOperadores;
	private VerticalLayout tabAprobadores;
	private VerticalLayout tabUsuarios;
	private int idUnidadOperativa;
	private int idCliente;
	
	IUnidadOperativaHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {		
		removeAllComponents();
		handler = new UnidadOperativaController(this);				
		getParams();			
		init();	
		handler.cargar();
		handler.mostrarMensajeInicio();
	}

	private void getParams(){
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOperativa = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		if (strIdUnidadOperativa.equals("")) idUnidadOperativa = 0;
		else idUnidadOperativa = Integer.valueOf(strIdUnidadOperativa);
	}
	
	@Override
	public void init() {
		setSizeFull();		
		addStyleName("main-view");
		
		SideBarViewImpl barraControl = new SideBarViewImpl();
		addComponent(barraControl);
		
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);
        
        contenido.addComponent(configurarContenido()); 

	}
	
	private VerticalLayout configurarContenido(){
		VerticalLayout contenido = new VerticalLayout();
		contenido.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    contenido.addComponent(header);
	        
	    lblTitulo = new Label();
	    lblTitulo.addStyleName("h1");  	    
	    header.addComponent(lblTitulo);
			    
	    HorizontalLayout data = new HorizontalLayout();	  
	    data.setSizeFull();
		contenido.addComponent(data);
		
		data.addComponent(cargarFormularioUnidadOperativa());
		data.addComponent(configurarTablas());
		
		return contenido;
	}
	
	@SuppressWarnings("serial")
	private VerticalLayout cargarFormularioUnidadOperativa(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.setMargin(true);
	    		
		FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		
		txtNombreComercialCliente = new TextField();
		txtNombreComercialCliente.setCaption("Cliente");
		txtNombreComercialCliente.setWidth("300px");
		txtNombreComercialCliente.setEnabled(false);
		txtNombre = new TextField();
		txtNombre.setCaption("Nombre");
		txtNombre.setWidth("300px");
		txtDireccion = new TextField();
		txtDireccion.setCaption("Dirección");
		txtDireccion.setWidth("300px");
		cbDepartamento = new ComboBox();
		cbDepartamento.setCaption("Departamento");
		cbDepartamento.setWidth("300px");
		cbDepartamento.setItemCaptionMode(ItemCaptionMode.ID);
		cbProvincia = new ComboBox();
		cbProvincia.setCaption("Provincia");
		cbProvincia.setWidth("300px");
		cbProvincia.setItemCaptionMode(ItemCaptionMode.ID);
		cbDistrito = new ComboBox();
		cbDistrito.setCaption("Distrito");
		cbDistrito.setWidth("300px");	
		cbDistrito.setItemCaptionMode(ItemCaptionMode.ID);
		txtNumeroCajones = new TextField();
		txtNumeroCajones.setCaption("Nro. Cajones");
		txtNumeroCajones.setWidth("50px");
		txtHoraApertura = new TimeField();
		txtHoraApertura.setCaption("Hora Apertura");
		txtHoraApertura.setWidth("200px");
		txtHoraCierre= new TimeField();
		txtHoraCierre.setCaption("Hora Cierre");
		txtHoraCierre.setWidth("200px");			
				
		HorizontalLayout buttons = new HorizontalLayout();
		Button btnGuardarUsuario = new Button("Guardar");
		btnGuardarUsuario.addStyleName("default");
		Button btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardarUsuario);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnGuardarUsuario, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(txtNombreComercialCliente);
		formulario.addComponent(txtNombre);
		formulario.addComponent(txtDireccion);
		formulario.addComponent(cbDepartamento);
		formulario.addComponent(cbProvincia);
		formulario.addComponent(cbDistrito);
		formulario.addComponent(txtHoraApertura);
		formulario.addComponent(txtHoraCierre);	
		formulario.addComponent(txtNumeroCajones);
		formulario.addComponent(buttons);
		
		btnGuardarUsuario.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				handler.guardar();				
			}
		});
		
		btnCancelar.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				handler.cancelar();				
			}
		});
		
		cbDepartamento.addValueChangeListener( new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.cargarProvincias();				
			}
		});
		
		cbProvincia.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.cargarDistritos();				
			}
		});
		
		return areaPrincipal;
	}
	
	private VerticalLayout configurarTablas(){
		configurarTabCategorias();
		configurarTabOperadores();
		configurarTabAprobadores();
		configurarTabUsuarios();
		
		VerticalLayout contenido = new VerticalLayout();
		contenido.setMargin(true);
		
		tsTablas = new TabSheet();		
		tsTablas.setSizeFull();
		tsTablas.addStyleName("borderless");
		
		tsTablas.addTab(tabCategorias, "Categorías");
		tsTablas.addTab(tabOperadores, "Operadores");
		tsTablas.addTab(tabAprobadores, "Aprobadores");
		tsTablas.addTab(tabUsuarios, "Usuarios");
		
		contenido.addComponent(tsTablas);
		return contenido;
	}
	
	@SuppressWarnings("serial")
	private void configurarTabCategorias(){
		tabCategorias = new VerticalLayout();
		
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setWidth("100%");
		Button btnNuevaCategoria = new Button();
		btnNuevaCategoria.addStyleName("default");
		btnNuevaCategoria.setCaption("+");
		toolbar.addComponent(btnNuevaCategoria);
		toolbar.setComponentAlignment(btnNuevaCategoria, Alignment.MIDDLE_RIGHT);
		
        tblCategorias = new Table();
        tblCategorias.setSizeFull();      
		
        tabCategorias.addComponent(toolbar);
        tabCategorias.addComponent(tblCategorias);
        
        btnNuevaCategoria.addClickListener(new ClickListener() {			 
		    @Override 
		    public void buttonClick(ClickEvent event) {			    	  
			   handler.irMantenimientoTarifa(null);   
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void configurarTabOperadores(){
		tabOperadores = new VerticalLayout();	
		
		tcsOperadores = new TwinColSelect();
		tcsOperadores.setLeftColumnCaption("Disponibles");
		tcsOperadores.setRightColumnCaption("Seleccionados");
		tcsOperadores.setItemCaptionPropertyId("nombreCompleto");
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		Button btnGuardarOperadores= new Button("Guardar Operadores");
		btnGuardarOperadores.addStyleName("default");
		buttons.addComponent(btnGuardarOperadores);
		
		tabOperadores.addComponent(tcsOperadores);
		tabOperadores.addComponent(buttons);
		
		btnGuardarOperadores.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				handler.guardarOperadores();				
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void configurarTabAprobadores(){
		tabAprobadores = new VerticalLayout();
		
		tcsAprobadores = new TwinColSelect();
		tcsAprobadores.setLeftColumnCaption("Disponibles");
		tcsAprobadores.setRightColumnCaption("Seleccionados");
		tcsAprobadores.setItemCaptionPropertyId("nombreCompleto");
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		Button btnGuardarAprobadores= new Button("Guardar Aprobadores");
		btnGuardarAprobadores.addStyleName("default");
		buttons.addComponent(btnGuardarAprobadores);
		
		tabAprobadores.addComponent(tcsAprobadores);
		tabAprobadores.addComponent(buttons);
		
		btnGuardarAprobadores.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				handler.guardarAprobadores();				
			}
		});
	}
	
	@SuppressWarnings("serial")
	private void configurarTabUsuarios(){
		tabUsuarios = new VerticalLayout();	
		
		tcsUsuarios = new TwinColSelect();
		tcsUsuarios.setLeftColumnCaption("Disponibles");
		tcsUsuarios.setRightColumnCaption("Seleccionados");
		tcsUsuarios.setItemCaptionPropertyId("nombreCompleto");
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		Button btnGuardarUsuarios = new Button("Guardar Usuarios");
		btnGuardarUsuarios.addStyleName("default");
		buttons.addComponent(btnGuardarUsuarios);
		
		tabUsuarios.addComponent(tcsUsuarios);
		tabUsuarios.addComponent(buttons);
		
		btnGuardarUsuarios.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				handler.guardarUsuarios();				
			}
		});
	}
	
	@Override
	public TextField getNombre() {
		return txtNombre;
	}

	@Override
	public TextField getDireccion() {
		return txtDireccion;
	}

	@Override
	public ComboBox getDepartamento() {
		return cbDepartamento;
	}

	@Override
	public ComboBox getProvincia() {
		return cbProvincia;
	}

	@Override
	public ComboBox getDistrito() {
		return cbDistrito;
	}

	@Override
	public TextField getNumeroCajones() {
		return txtNumeroCajones;
	}

	@Override
	public Table getCategorias() {
		return tblCategorias;
	}

	@Override
	public TwinColSelect getOperadores() {
		return tcsOperadores;
	}

	@Override
	public TwinColSelect getAprobadores() {
		return tcsAprobadores;
	}

	@Override
	public TwinColSelect getUsuarios() {
		return tcsUsuarios;
	}

	@Override
	public TimeField getHoraApertura() {
		return txtHoraApertura;
	}

	@Override
	public TimeField getHoraCierre() {
		return txtHoraCierre;
	}

	@Override
	public TextField getNombreComercialCliente() {
		return txtNombreComercialCliente;
	}

	@Override
	public int getIdCliente() {
		return idCliente;
	}

	@Override
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}

	@Override
	public TabSheet getTablas() {
		return tsTablas;
	}

	@Override
	public Label getTitulo() {
		return lblTitulo;
	}
}
