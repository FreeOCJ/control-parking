package pe.cp.web.ui.view.configuracion.unidadoperativa;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.web.ui.view.configuracion.cliente.EditarClienteController;
import pe.cp.web.ui.view.main.SideBar;

import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.vaadin.annotations.Theme;
import com.vaadin.event.Transferable;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnGenerator;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class NuevaUnidadOperativaViewImpl extends HorizontalLayout implements INuevaUnidadOperativaView {

	private CssLayout contenido;
	private TextField txtNombre;
	private TextField txtDireccion;
	private TextField txtNombreComercialCliente;
	private ComboBox cbDepartamento;
	private ComboBox cbProvincia;
	private ComboBox cbDistrito;
	private TextField txtNumeroCajones;
	private TextField txtHoraApertura;
	private TextField txtHoraCierre;	
	private Table tblCategorias;
	private TwinColSelect tcsOperadores;
	private TwinColSelect tcsAprobadores;
	private TwinColSelect tcsUsuarios;
	private TabSheet tsTablas;
	private VerticalLayout tabCategorias;
	private VerticalLayout tabOperadores;
	private VerticalLayout tabAprobadores;
	private VerticalLayout tabUsuarios;
	
	IUnidadOperativaHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {		
		removeAllComponents();
		handler = new UnidadOperativaController(this);				
				
		init();					
	}

	@Override
	public void init() {
		setSizeFull();		
		addStyleName("main-view");
		
		SideBar barraControl = new SideBar();
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
	        
	    Label title = new Label("Nueva Unidad Operativa");
	    title.addStyleName("h1");  	    
	    header.addComponent(title);
			    
	    HorizontalLayout data = new HorizontalLayout();	  
	    data.setSizeFull();
		contenido.addComponent(data);
		
		data.addComponent(cargarFormularioUnidadOperativa());
		data.addComponent(configurarTablas());
		
		return contenido;
	}
	
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
		cbProvincia = new ComboBox();
		cbProvincia.setCaption("Provincia");
		cbProvincia.setWidth("300px");	
		cbDistrito = new ComboBox();
		cbDistrito.setCaption("Distrito");
		cbDistrito.setWidth("300px");	
		txtNumeroCajones = new TextField();
		txtNumeroCajones.setCaption("Nro. Cajones");
		txtNumeroCajones.setWidth("50px");
		txtHoraApertura = new TextField();
		txtHoraApertura.setCaption("Hora Apertura");
		txtHoraApertura.setWidth("200px");
		txtHoraCierre= new TextField();
		txtHoraCierre.setCaption("Hora Cierre");
		txtHoraCierre.setWidth("200px");			
				
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
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
		formulario.addComponent(buttons);
		
		btnGuardarUsuario.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				//handler.guardar();				
			}
		});
		
		btnCancelar.addClickListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				//handler.cancelar();				
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
        tblCategorias.setContainerDataSource(handler.obtenerHeadersCategoriaContainer());
        tblCategorias.setSelectable(true);
        tblCategorias.addGeneratedColumn("", new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button("Editar");				 
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        
			        
			      }
			    });
				
				Button btnEliminar = new Button("Eliminar");				 
				btnEliminar.addClickListener(new ClickListener() {			 
			      @Override 
			      public void buttonClick(ClickEvent event) {				    	  
			    	  ConfirmDialog.show(UI.getCurrent(), "Confirmar Acción", "¿Estás seguro que deseas eliminar al usuario?", "Si", "No", 
				        new ConfirmDialog.Listener() {
				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                    // Confirmed to continue
				                	Integer idCliente = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Código").getValue();
				                	System.out.println("eliminar");
				                } else {
				                    // User did not confirm
				                	System.out.println("cancelar");
				                }
				            }
				        });			        			      
			      }
			    });
			 
				botonesAccion.addComponent(btnEditar);
				botonesAccion.addComponent(btnEliminar);
			    return botonesAccion;
			}
		} ); 
		
        tabCategorias.addComponent(toolbar);
        tabCategorias.addComponent(tblCategorias);
	}
	
	private void configurarTabOperadores(){
		tabOperadores = new VerticalLayout();	
		
		tcsOperadores = new TwinColSelect();
		tcsOperadores.setLeftColumnCaption("Disponibles");
		tcsOperadores.setRightColumnCaption("Seleccionados");
		tcsOperadores.setItemCaptionPropertyId("nombrecompleto");
		
		tabOperadores.addComponent(tcsOperadores);
	}
	
	private void configurarTabAprobadores(){
		tabAprobadores = new VerticalLayout();
		
		tcsAprobadores = new TwinColSelect();
		tcsAprobadores.setLeftColumnCaption("Disponibles");
		tcsAprobadores.setRightColumnCaption("Seleccionados");
		tcsAprobadores.setItemCaptionPropertyId("nombrecompleto");
		
		tabAprobadores.addComponent(tcsAprobadores);
	}
	
	private void configurarTabUsuarios(){
		tabUsuarios = new VerticalLayout();	
		
		tcsUsuarios = new TwinColSelect();
		tcsUsuarios.setLeftColumnCaption("Disponibles");
		tcsUsuarios.setRightColumnCaption("Seleccionados");
		tcsUsuarios.setItemCaptionPropertyId("nombrecompleto");
		
		tabUsuarios.addComponent(tcsUsuarios);
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
	public TextField getHoraApertura() {
		return txtHoraApertura;
	}

	@Override
	public TextField getHoraCierre() {
		return txtHoraCierre;
	}

	@Override
	public TextField getNombreComercialCliente() {
		return txtNombreComercialCliente;
	}
}
