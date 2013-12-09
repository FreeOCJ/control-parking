package pe.cp.web.ui.view.configuracion.usuario;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.configuracion.ConfigController;
import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class NuevoUsuarioViewImpl extends HorizontalLayout implements INuevoUsuarioView {

	private CssLayout contenido;
	private TextField txtLogin;
	private TextField txtNombres;
	private TextField txtApellidos;
	private TextField txtCargo;
	private TextField txtCorreoElectronico;
	private TwinColSelect selectRoles;
	private ComboBox cbClientes;
	
	private INuevoUsuarioViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		init();
		
		System.out.println("nuevo usuario view");
		handler = new NuevoUsuarioController(this);
		
		
	}

	//public NuevoUsuarioViewImpl(){		
		
	//}
	
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
        
        contenido.addComponent(cargarFormularioNuevoUsuario());       
	}

	private VerticalLayout cargarFormularioNuevoUsuario(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Nuevo Usuario");
	    title.addStyleName("h1");  	    
	    header.addComponent(title);
	    
		
		FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		
		txtLogin = new TextField();
		txtLogin.setCaption("Login");
		txtLogin.setWidth("300px");
		txtNombres = new TextField();
		txtNombres.setCaption("Nombres");
		txtNombres.setWidth("300px");
		txtApellidos = new TextField();
		txtApellidos.setCaption("Apellidos");
		txtApellidos.setWidth("300px");
		txtCargo = new TextField();
		txtCargo.setCaption("Cargo");
		txtCargo.setWidth("300px");
		txtCorreoElectronico = new TextField();
		txtCorreoElectronico.setCaption("Correo Electrónico");
		txtCorreoElectronico.setWidth("300px");
		selectRoles = new TwinColSelect();
		selectRoles.setCaption("Roles");
		selectRoles.setLeftColumnCaption("Disponibles");
		selectRoles.setRightColumnCaption("Asignados");
		selectRoles.setItemCaptionPropertyId("nombre");	
		cbClientes = new ComboBox();
		cbClientes.setCaption("Cliente");
		
		HorizontalLayout buttons = new HorizontalLayout();
		Button btnGuardarUsuario = new Button("Guardar");
		btnGuardarUsuario.addStyleName("default");
		Button btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardarUsuario);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnGuardarUsuario, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(txtLogin);
		formulario.addComponent(txtNombres);
		formulario.addComponent(txtApellidos);
		formulario.addComponent(txtCargo);
		formulario.addComponent(txtCorreoElectronico);		
		formulario.addComponent(selectRoles);
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
		
		return areaPrincipal;
	}

	@Override
	public TextField getLogin() {
		return txtLogin;
	}

	@Override
	public TextField getNombres() {
		return txtNombres;
	}

	@Override
	public TextField getApellidos() {
		return txtApellidos;
	}

	@Override
	public TextField getCargo() {
		return txtCargo;
	}

	@Override
	public TextField getCorreoElectronico() {
		return txtCorreoElectronico;
	}

	@Override
	public TwinColSelect getRoles() {
		return selectRoles;
	}

}
