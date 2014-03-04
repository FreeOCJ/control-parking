package pe.cp.web.ui.view.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.handler.IConfigViewHandler;
import pe.cp.web.ui.handler.impl.ConfigController;
import pe.cp.web.ui.view.IConfigView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ConfigViewImpl extends HorizontalLayout implements IConfigView {

	@Autowired
	private IConfigViewHandler handler;
	
	private CssLayout contenido;
	private NativeButton btnConfigUsuarios;
	private NativeButton btnConfigClientes;
	
	@Override
	public void enter(ViewChangeEvent event) {
		init();
		handler = new ConfigController(this);
		handler.validarUsuario();
	}
	
	@Override
	public void init() {		
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
	        
	    Label title = new Label("Configuración");
	    title.addStyleName("h1");         
	    header.addComponent(title); 
		
	    VerticalLayout menuLayout = new VerticalLayout();
	    areaPrincipal.addComponent(menuLayout);
	    menuLayout.setMargin(true);
	    
	    //Layout usuarios
	    HorizontalLayout seccionUsuariosLayout = new HorizontalLayout();
	    CssLayout divBtnUsuario = new CssLayout();
	    CssLayout spanBtnUsuario = new CssLayout();
	    HorizontalLayout divInfoUsuario = new HorizontalLayout();
	    btnConfigUsuarios = new NativeButton("Configurar Usuarios");
	    Label lblInfoUsuario = new Label("La configuración de usuarios permite agregar nuevos usuarios " +
	              						 "operadores, aprobadores o administradores. Asimismo, permite  " +
	              						 "el mantenimiento de los datos existentes.");
	    
	    divBtnUsuario.setHeight("100%");
	    divBtnUsuario.addStyleName("sidebar");
	    spanBtnUsuario.addStyleName("menu");
	    btnConfigUsuarios.setStyleName("icon-usuarios");
	    divInfoUsuario.setMargin(true);
	    divInfoUsuario.setWidth("500px");
	    
	    menuLayout.addComponent(seccionUsuariosLayout);
	    seccionUsuariosLayout.addComponent(divBtnUsuario);
	    divBtnUsuario.addComponent(spanBtnUsuario);
	    spanBtnUsuario.addComponent(btnConfigUsuarios);
	    seccionUsuariosLayout.addComponent(divInfoUsuario);
	    divInfoUsuario.addComponent(lblInfoUsuario);
	    
	    menuLayout.addComponent(new Label());
	    
	    //Layout clientes
	    HorizontalLayout seccionClientesLayout = new HorizontalLayout();
	    CssLayout divBtnCliente = new CssLayout();
	    CssLayout spanBtnCliente = new CssLayout();
	    HorizontalLayout divInfoCliente = new HorizontalLayout();
	    btnConfigClientes = new NativeButton("Configurar Clientes");
	    Label lblInfoCliente = new Label("Permite agregar nuevos clientes, configurar sus " +
					 					 "operadores y aprobadores, agregar usuarios de consulta, unidades operativas  " +
					 					 " y tarifas");
	    
	    divBtnCliente.setHeight("100%");
	    divBtnCliente.addStyleName("sidebar");
	    spanBtnCliente.addStyleName("menu");
	    btnConfigClientes.setStyleName("icon-clientes");
	    divInfoCliente.setMargin(true);
	    divInfoCliente.setWidth("500px");
	    
	    menuLayout.addComponent(seccionClientesLayout);
	    seccionClientesLayout.addComponent(divBtnCliente);
	    divBtnCliente.addComponent(spanBtnCliente);
	    spanBtnCliente.addComponent(btnConfigClientes);
	    seccionClientesLayout.addComponent(divInfoCliente);
	    divInfoCliente.addComponent(lblInfoCliente);
	    
	    btnConfigUsuarios.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irConfiguracionUsuarios();		
			}
		});
	    
	    btnConfigClientes.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irConfiguracionClientes();		
			}
		});
		
		return areaPrincipal;
	}

	@Override
	public void setHandler(IConfigViewHandler handler) {
		this.handler = handler;		
	}

}
