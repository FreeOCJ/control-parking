package pe.cp.web.ui.view.configuracion.usuario;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BuscarUsuarioViewImpl extends HorizontalLayout implements IBuscarUsuarioView {

	private CssLayout contenido;
	
	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("buscar usuario view");

	}

	public BuscarUsuarioViewImpl(){
		init();
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
	    header.addComponent(title); 
		
        //Crear Tools Busqueda
	    TextField txtUsuario = new TextField();
	    txtUsuario.setInputPrompt("Nombres/Apellidos");
	    txtUsuario.setWidth("300px");               
        Button btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");
                
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        
        toolbar.addComponent(txtUsuario);
        toolbar.addComponent(btnBuscar);        
        toolbar.setComponentAlignment(txtUsuario, Alignment.MIDDLE_LEFT);        
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
        
		return areaPrincipal;
	}

	@Override
	public void setHandler() {
		// TODO Auto-generated method stub

	}

}
