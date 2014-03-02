package pe.cp.web.ui.view.configuracion.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class BuscarUsuarioViewImpl extends HorizontalLayout implements IBuscarUsuarioView {

	ApplicationContext ac;
	
	@Autowired
	private IBuscarUsuarioViewHandler handler;
	
	private CssLayout contenido;
	private Table tblusuarios;
	private TextField txtUsuario;
	
	@Override
	public void enter(ViewChangeEvent event) {	
		this.removeAllComponents();
		handler = new BuscarUsuarioController(this);
		handler.validarUsuario();
		init();
		handler.mostrarMensajeInicio();
		handler.cargar();		
	}
		
	@Override
	public void init() {
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
	        
	    Label title = new Label("Configuración de Usuarios");
	    title.addStyleName("h1");  
	    Button btnNuevoUsuario = new Button("+");
	    btnNuevoUsuario.addStyleName("default");
	    header.addComponent(title); 
	    header.addComponent(btnNuevoUsuario);
	    header.setComponentAlignment(btnNuevoUsuario, Alignment.MIDDLE_RIGHT);
		
        //Crear Tools Busqueda
	    txtUsuario = new TextField();
	    txtUsuario.setInputPrompt("Nombres/Apellidos");
	    txtUsuario.setWidth("300px");               
        Button btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");
        
        //Crear Tabla
        tblusuarios = new Table();
        tblusuarios.setSizeFull();      
                
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        areaPrincipal.addComponent(tblusuarios);
                
        toolbar.addComponent(txtUsuario);
        toolbar.addComponent(btnBuscar);        
        toolbar.setComponentAlignment(txtUsuario, Alignment.MIDDLE_LEFT);        
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
        
        btnBuscar.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {				
				handler.buscarpornombre(txtUsuario.getValue());			
			}
		});
        
        btnNuevoUsuario.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irAgregarNuevoUsuario();			
			}
		});      
        
		return areaPrincipal;
	}

	
	public void setHandler(IBuscarUsuarioViewHandler handler) {
		this.handler = handler;
	}

	@Override
	public void limpiarTabla() {
		tblusuarios.removeAllItems();
	}

	@Override
	public Table getTblResultados() {
		return tblusuarios;
	}
	
	



}
