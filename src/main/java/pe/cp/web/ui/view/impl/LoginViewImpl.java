package pe.cp.web.ui.view.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.handler.ILoginViewHandler;
import pe.cp.web.ui.view.ILoginView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class LoginViewImpl extends CssLayout implements ILoginView {
		
	@Autowired
	private ILoginViewHandler handler;
	
	VerticalLayout loginLayout;
	private TextField txtUsername;
	private PasswordField txtPassword;
	private Button btnLogin;
	private Button btnRecuperarContrasena;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler.cargar();
		Logger.getAnonymousLogger().log(Level.WARNING, "enter login view");
	}

	@Override
	public void init() {
		removeAllComponents();
		addStyleName("root");
        setSizeFull();
				
		Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        addComponent(bg);
		
		addStyleName("login");
        setSizeFull();   
        
        buildLoginView();       
	}
	
	public void buildLoginView(){				
		loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login-layout");
        
        addComponent(loginLayout);
                                
        //Panel que contiene la informacion del login
        final CssLayout loginPanel = new CssLayout();	
        loginPanel.addStyleName("login-panel");

        //Para dividir informacion de logueo e informacion de recuperacion de contrasena
        VerticalLayout panelDiv = new VerticalLayout();
                
        HorizontalLayout loginInfo = new HorizontalLayout();
        loginInfo.setWidth("100%");
        loginInfo.setMargin(true);
                        
        Image logo = new Image(null, new ThemeResource("img/logo-controlparking.png"));
        logo.setSizeFull();
        loginInfo.addComponent(logo);               
        
        FormLayout fields = new FormLayout();
        fields.setMargin(true);
        txtUsername = new TextField("Usuario: ");
        txtUsername.setWidth("100%");
        txtUsername.focus();
        fields.addComponent(txtUsername);
        
        txtPassword = new PasswordField("Contraseña: ");
        txtPassword.setWidth("100%");
        fields.addComponent(txtPassword);
        
        btnLogin = new Button("Ingresar");
        btnLogin.addStyleName("default");
        fields.addComponent(btnLogin);
        fields.setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
        
        btnLogin.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.Login(txtUsername.getValue().trim(),txtPassword.getValue().trim());			
			}
		});        
        
        VerticalLayout passwordUtilsContainer = new VerticalLayout();
        passwordUtilsContainer.setSizeFull();                
        btnRecuperarContrasena = new Button("Recuperar Contraseña");
        btnRecuperarContrasena.setStyleName("link");
        passwordUtilsContainer.addComponent(btnRecuperarContrasena);
        passwordUtilsContainer.setComponentAlignment(btnRecuperarContrasena, Alignment.TOP_RIGHT);        
        
        loginInfo.addComponent(fields);                  
        loginInfo.setExpandRatio(logo, 1);
        loginInfo.setExpandRatio(fields, 3);
        
        panelDiv.addComponent(loginInfo);        
        panelDiv.addComponent(passwordUtilsContainer);
        loginPanel.addComponent(panelDiv);
        
        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}

	@Override
	public TextField getTxtUsername() {
		return txtUsername;
	}

	@Override
	public PasswordField getTxtPassword() {
		return txtPassword;
	}

	@Override
	public Button getBtnLogin() {
		return btnLogin;
	}

	@Override
	public void afterSuccessfulLogin() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.MAIN);
	}

	@Override
	public void setHandler(ILoginViewHandler handler) {
		this.handler = handler;		
	}

	@Override
	public Button getBtnRecuperarContrasena() {
		return btnRecuperarContrasena;
	}

}
