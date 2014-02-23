package pe.cp.web.ui.view.operaciones;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.web.ui.view.configuracion.cliente.BuscarClienteController;
import pe.cp.web.ui.view.main.SideBar;
import pe.cp.web.ui.view.operaciones.IBuscarOperacionesView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.ColumnGenerator;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class BuscarOperacionesViewImpl extends HorizontalLayout implements IBuscarOperacionesView {

	private CssLayout contenido;
	private Table tblOperaciones;
	private ComboBox cbUnidadOperativa;
	private ComboBox cbEstado;
	private DateField dfFechaOp;
	private Button btnBuscar;
	private Button btnNuevaOperacion;
	
	@Autowired
	private IBuscarOperacionesHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new BuscarOperacionesController(this);
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

	@Override
	public void limpiarTabla() {
		
	}

	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Operaciones");
	    title.addStyleName("h1");  
	    btnNuevaOperacion = new Button("Procesar");
	    btnNuevaOperacion.addStyleName("default");
	    header.addComponent(title); 
	    header.addComponent(btnNuevaOperacion);
	    header.setComponentAlignment(btnNuevaOperacion, Alignment.MIDDLE_RIGHT);
		
        //Crear Tools
	    cbUnidadOperativa = new ComboBox();
	    cbUnidadOperativa.setInputPrompt("Unidad Operativa");
	    cbUnidadOperativa.setWidth("200px");
	    cbUnidadOperativa.setItemCaptionPropertyId("NOMBRE");
        dfFechaOp = new DateField();
        dfFechaOp.setWidth("200px");
        cbEstado = new ComboBox();
        cbEstado.setInputPrompt("Estado");
        cbEstado.setWidth("200px");
        btnBuscar = new Button("Buscar");
        btnBuscar.addStyleName("default");

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        areaPrincipal.addComponent(toolbar);
        
        toolbar.addComponent(cbUnidadOperativa);
        toolbar.setComponentAlignment(cbUnidadOperativa, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(dfFechaOp);
        toolbar.setComponentAlignment(dfFechaOp, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(cbEstado);
        toolbar.setComponentAlignment(cbEstado, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(btnBuscar);
        toolbar.setComponentAlignment(btnBuscar, Alignment.BOTTOM_LEFT);
        toolbar.setExpandRatio(btnBuscar, 1);
	    
        //Crear Tabla
        tblOperaciones = new Table();
        tblOperaciones.setSizeFull();    
        tblOperaciones.setContainerDataSource(handler.obtenerHeadersContainer());
        tblOperaciones.setSelectable(true);
        tblOperaciones.addGeneratedColumn("", new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button();
				btnEditar.setIcon(new ThemeResource("icons/18/edit.png"));
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        Integer idOperacion = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Codigo").getValue();
			        handler.irEditarOperacion(idOperacion);
			      }
			    });
				
				Button btnEliminar = new Button();
				btnEliminar.setIcon(new ThemeResource("icons/18/delete.png"));
				btnEliminar.addClickListener(new ClickListener() {			 
			      @Override 
			      public void buttonClick(ClickEvent event) {				    	  
			    	  ConfirmDialog.show(UI.getCurrent(), "Confirmar Acción", "¿Estás seguro que deseas eliminar al usuario?", "Si", "No", 
				        new ConfirmDialog.Listener() {
				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                    // Confirmed to continue
				                	Integer idCliente = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Código").getValue();
				                	System.out.println("eliminar");
				                } else {
				                    // User did not confirm
				                	System.out.println("cancelar");
				                }
				            }
				        });			        			      
			      }
			    });
			 
				botonesAccion.addComponent(btnEditar);
				botonesAccion.addComponent(btnEliminar);
			    return botonesAccion;
			}
		} ); 
        tblOperaciones.setVisibleColumns((Object []) BuscarOperacionesController.obtenerColumnasVisibles());
        areaPrincipal.addComponent(tblOperaciones); 
        
        btnBuscar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.buscar();
			}
		});
        
        btnNuevaOperacion.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irNuevaOperacion();
			}
		});
        
		return areaPrincipal;
	}
	
	@Override
	public ComboBox getCbUnidadOp() {
		return cbUnidadOperativa;
	}

	@Override
	public ComboBox getCbEstado() {
		return cbEstado;
	}

	@Override
	public DateField getDfFechaOp() {
		return dfFechaOp;
	}

	@Override
	public Table getResultados() {
		return tblOperaciones;
	}

}
