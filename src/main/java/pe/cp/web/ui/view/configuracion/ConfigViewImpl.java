package pe.cp.web.ui.view.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.view.configuracion.IConfigView;
import pe.cp.web.ui.view.login.ILoginViewHandler;
import pe.cp.web.ui.view.login.LoginController;
import pe.cp.web.ui.view.main.SideBar;
import pe.cp.web.ui.view.operaciones.OperacionesComponent;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
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
	
	@Override
	public void enter(ViewChangeEvent event) {
		handler = new ConfigController(this);
	}

	public ConfigViewImpl(){		
		init();
	}
	
	@Override
	public void init() {		
        System.out.println("init configuracion");
		construirBase();
	}
	
	private void construirBase(){		
		setSizeFull();		
		addStyleName("main-view");
		
		SideBar barraControl = new SideBar();
		addComponent(barraControl);
		
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
		
	    Button btnConfigUsuarios = new Button("Configurar Usuarios");
	    Button btnConfigClientes = new Button("Configurar Clientes");
	    VerticalLayout configLayout = new VerticalLayout();
	    configLayout.addComponent(btnConfigUsuarios);
	    configLayout.addComponent(btnConfigClientes);
	    
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
		
	    areaPrincipal.addComponent(configLayout);
	    areaPrincipal.setExpandRatio(configLayout, 1);
		return areaPrincipal;
	}

	@Override
	public void setHandler(IConfigViewHandler handler) {
		this.handler = handler;		
	}
}
