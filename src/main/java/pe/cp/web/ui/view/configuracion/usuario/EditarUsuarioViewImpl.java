package pe.cp.web.ui.view.configuracion.usuario;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class EditarUsuarioViewImpl extends HorizontalLayout implements IEditarUsuarioView {
	
	private int idUsuario;
	private int idCliente;
	private CssLayout contenido;
	private TextField txtLogin;
	private TextField txtNombres;
	private TextField txtApellidos;
	private TextField txtCargo;
	private TextField txtCorreoElectronico;
	private TwinColSelect selectRoles;
	private IEditarUsuarioViewHandler handler;
	private Notification notification = null;	
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		handler = new EditarUsuarioController(this);
		
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUsuario = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUsuario = Integer.valueOf(strIdUsuario);
		
		init();		
		handler.cargar();
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
        
        contenido.addComponent(cargarFormularioEditarUsuario());  
	}

	private VerticalLayout cargarFormularioEditarUsuario(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Editar Usuario");
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
				handler.actualizar();				
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
	public int getIdUsuario() {
		return idUsuario;
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

	@Override
	public Notification getNotification() {
		return notification;
	}

	@Override
	public void setNotification(Notification notification) {
		this.notification  = notification;		
	}

	@Override
	public int getIdCliente() {
		return idCliente;
	}

}
