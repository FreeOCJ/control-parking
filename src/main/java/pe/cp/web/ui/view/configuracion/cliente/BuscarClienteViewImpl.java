package pe.cp.web.ui.view.configuracion.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.web.ui.view.configuracion.usuario.BuscarUsuarioController;
import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnGenerator;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class BuscarClienteViewImpl extends HorizontalLayout implements IBuscarClientesView {

	private CssLayout contenido;
	private Table tblClientes;
	private TextField txtFiltroCliente;
	
	@Autowired
	private IBuscarClienteHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new BuscarClienteController(this);
		init();		
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
	        
	    Label title = new Label("Configuración de Clientes");
	    title.addStyleName("h1");  
	    Button btnNuevoUsuario = new Button("+");
	    btnNuevoUsuario.addStyleName("default");
	    header.addComponent(title); 
	    header.addComponent(btnNuevoUsuario);
	    header.setComponentAlignment(btnNuevoUsuario, Alignment.MIDDLE_RIGHT);
		
        //Crear Tools Busqueda
	    txtFiltroCliente = new TextField();
	    txtFiltroCliente.setInputPrompt("Nombre Comercial");
	    txtFiltroCliente.setWidth("300px");
	    txtFiltroCliente.setMaxLength(50);
        Button btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");
        
        //Crear Tabla
        tblClientes = new Table();
        tblClientes.setSizeFull();    

                
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        areaPrincipal.addComponent(tblClientes);
                
        toolbar.addComponent(txtFiltroCliente);
        toolbar.addComponent(btnBuscar);        
        toolbar.setComponentAlignment(txtFiltroCliente, Alignment.MIDDLE_LEFT);        
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
        
        btnBuscar.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {				
				handler.buscar();			
			}
		});
        
        btnNuevoUsuario.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irAgregarNuevoCliente();			
			}
		});      
        
		return areaPrincipal;
	}

	@Override
	public TextField getFiltroNombreComercial() {
		return txtFiltroCliente;
	}

	@Override
	public Table getResultados() {
		return tblClientes;
	}

	@Override
	public void limpiarTabla() {
		tblClientes.removeAllItems();		
	}

}
