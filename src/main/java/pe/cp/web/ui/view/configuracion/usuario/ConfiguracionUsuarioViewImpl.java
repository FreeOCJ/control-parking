package pe.cp.web.ui.view.configuracion.usuario;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ConfiguracionUsuarioViewImpl extends HorizontalLayout implements IConfiguracionUsuarioView {

	private TextField txtEmail;
	private PasswordField txtContrasenaActual;
	private PasswordField txtNuevaContrasena;
	private PasswordField txtValidarContrasena;
	private Button btnGuardarCorreo;
	private Button btnGuardarContrasena;
	
	private CssLayout contenido;
	private IConfigurarUsuarioHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		init();
		handler = new ConfigurarUsuarioController(this);
		handler.cargar();
	}

	@Override
	public TextField getTxtEmail() {
		return txtEmail;
	}

	@Override
	public PasswordField getTxtContrasena() {
		return txtContrasenaActual;
	}

	@Override
	public PasswordField getTxtNuevaContrasena() {
		return txtNuevaContrasena;
	}

	@Override
	public PasswordField getTxtValidarContrasena() {
		return txtValidarContrasena;
	}

	@Override
	public Button getBtnGuardarContrasena() {
		return btnGuardarContrasena;
	}

	@Override
	public Button getBtnGuardarEmail() {
		return btnGuardarCorreo;
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
	        
	    Label title = new Label("Configurar Información Usuario");
	    title.addStyleName("h1");  	    
	    header.addComponent(title);
	    
		FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		
		txtEmail = new TextField();
		txtEmail.setCaption("Correo Electrónico");
		txtEmail.setWidth("300px");
		txtContrasenaActual = new PasswordField();
		txtContrasenaActual.setCaption("Contraseña");
		txtContrasenaActual.setWidth("300px");
		txtNuevaContrasena = new PasswordField();
		txtNuevaContrasena.setCaption("Nueva Contraseña");
		txtNuevaContrasena.setWidth("300px");
		txtValidarContrasena = new PasswordField();
		txtValidarContrasena.setCaption("Repetir Nueva Contraseña");
		txtValidarContrasena.setWidth("300px");
		btnGuardarCorreo = new Button("Actualizar Correo");
		btnGuardarContrasena = new Button("Actualizar Contraseña");
		btnGuardarContrasena.setStyleName("default");
		btnGuardarCorreo.setStyleName("default");
		
		formulario.addComponent(txtEmail);
		formulario.addComponent(btnGuardarCorreo);
		formulario.addComponent(new Label());
		formulario.addComponent(txtContrasenaActual);
		formulario.addComponent(txtNuevaContrasena);
		formulario.addComponent(txtValidarContrasena);
		formulario.addComponent(btnGuardarContrasena);
		
		return areaPrincipal;
	}
}
