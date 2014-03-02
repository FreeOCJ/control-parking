package pe.cp.web.ui.view.login;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class RecuperarContrasenaViewImpl extends HorizontalLayout implements IRecuperarContrasenaView {

	private TextField txtLogin;
	private Button btnEnviar;
	private Button btnCancelar;
	private CssLayout contenido;
	
	private IRecuperarContrasenaHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		init();
		handler = new RecuperarContrasenaController(this);
		handler.cargar();
	}

	@Override
	public void init() {
		setSizeFull();		
		addStyleName("main-view");	
		contenido = new CssLayout();
        addComponent(contenido);
        
        VerticalLayout areaPrincipal = new VerticalLayout();
        areaPrincipal.setMargin(true);
        contenido.addComponent(areaPrincipal);
		areaPrincipal.addStyleName("transactions");
		
		//Header
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Recuperar Contraseña");
	    title.addStyleName("h1");  
	    header.addComponent(title); 
	    areaPrincipal.addComponent(header);
	    
	    //Formulario
	    FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		txtLogin = new TextField("Ingresar Usuario");
		txtLogin.setMaxLength(100);
		txtLogin.setWidth("250px");
		
		formulario.addComponent(txtLogin);
		
		HorizontalLayout buttons = new HorizontalLayout();
		btnEnviar = new Button("Enviar");
		btnEnviar.addStyleName("default");
		btnCancelar = new Button("Regresar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnEnviar);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnEnviar, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(buttons);
	}

	@Override
	public TextField getLogin() {
		return txtLogin;
	}

	@Override
	public Button getBtnEnviar() {
		return btnEnviar;
	}

	@Override
	public Button getBtnCancelar() {
		return btnCancelar;
	}

}
