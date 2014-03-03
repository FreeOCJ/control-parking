package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.handler.INuevoClienteHandler;
import pe.cp.web.ui.handler.impl.NuevoClienteController;
import pe.cp.web.ui.view.INuevoClienteView;

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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class NuevoClienteViewImpl extends HorizontalLayout implements INuevoClienteView {

	private CssLayout contenido;
	private TextField txtRazonSocial;
	private TextField txtNombreComercial;
	private TextField txtRuc;
	private Notification notificacion;
	
	private INuevoClienteHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new NuevoClienteController(this);
		init();
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
        
        contenido.addComponent(cargarFormularioNuevoCliente()); 
	}

	private VerticalLayout cargarFormularioNuevoCliente(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Nuevo Cliente");
	    title.addStyleName("h1");  	    
	    header.addComponent(title);
	    
		
		FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		
		txtRazonSocial = new TextField();
		txtRazonSocial.setCaption("Razón Social");
		txtRazonSocial.setWidth("300px");
		txtRazonSocial.setMaxLength(50);
		txtNombreComercial = new TextField();
		txtNombreComercial.setCaption("Nombre Comercial");
		txtNombreComercial.setWidth("300px");
		txtNombreComercial.setMaxLength(100);
		txtRuc = new TextField();
		txtRuc.setCaption("RUC");
		txtRuc.setWidth("300px");		
		txtRuc.setMaxLength(11);
		
		HorizontalLayout buttons = new HorizontalLayout();
		Button btnGuardarUsuario = new Button("Guardar");
		btnGuardarUsuario.addStyleName("default");
		Button btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardarUsuario);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnGuardarUsuario, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(txtRazonSocial);
		formulario.addComponent(txtNombreComercial);
		formulario.addComponent(txtRuc);		
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
	public TextField getRazonSocial() {
		return txtRazonSocial;
	}

	@Override
	public TextField getRuc() {
		return txtRuc;
	}

	@Override
	public TextField getNombreComercial() {
		return txtNombreComercial;
	}

	@Override
	public Notification getNotification() {
		return notificacion;
	}

	@Override
	public void setNotification(Notification notification) {
		this.notificacion = notification;		
	}

}
