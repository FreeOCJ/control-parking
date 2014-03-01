package pe.cp.web.ui.view.configuracion.cliente;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnGenerator;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class EditarClienteViewImpl extends HorizontalLayout implements IEditarClienteView {

	private CssLayout contenido;
	private TextField txtRazonSocial;
	private TextField txtNombreComercial;
	private TextField txtRuc;
	private Notification notificacion;
	private int idCliente;
	private Table tblUsuarios;
	private Table tblUnidadesOperativas;
	
	private IEditarClienteHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		handler = new EditarClienteController(this);				
		String fragment = UI.getCurrent().getPage().getUriFragment();
		String id = fragment.substring(fragment.indexOf("/") + 1);
		idCliente = Integer.valueOf(id);
		init();		
		handler.cargar();
		handler.mostrarMensajeInicio();
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
        
        contenido.addComponent(cargarFormularioNuevoCliente()); 
	}

	private VerticalLayout cargarFormularioNuevoCliente(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Editar Cliente");
	    title.addStyleName("h1");  	    
	    header.addComponent(title);
	    		
		FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		
		txtRazonSocial = new TextField();
		txtRazonSocial.setCaption("Razón Social");
		txtRazonSocial.setWidth("300px");
		txtNombreComercial = new TextField();
		txtNombreComercial.setCaption("Nombre Comercial");
		txtNombreComercial.setWidth("300px");
		txtRuc = new TextField();
		txtRuc.setCaption("RUC");
		txtRuc.setWidth("300px");		
		
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
		
		//Area Usuarios
		HorizontalLayout barraTituloUsuarios = new HorizontalLayout();
		barraTituloUsuarios.setWidth("100%");
		Label tituloUsuarios = new Label("Usuarios");
		tituloUsuarios.addStyleName("h2");
	    Button btnAgregarUsuario = new Button("+");
	    btnAgregarUsuario.addStyleName("default");
		barraTituloUsuarios.addComponent(tituloUsuarios);
		barraTituloUsuarios.addComponent(btnAgregarUsuario);
		barraTituloUsuarios.setComponentAlignment(tituloUsuarios, Alignment.MIDDLE_LEFT);
		barraTituloUsuarios.setComponentAlignment(btnAgregarUsuario, Alignment.MIDDLE_RIGHT);
		
		tblUsuarios = new Table();
		tblUsuarios.setWidth("100%");  
		tblUsuarios.setHeight("150px");
		tblUsuarios.setContainerDataSource(handler.obtenerHeadersUsuariosContainer());
		tblUsuarios.setSelectable(true);
		
		tblUsuarios.addGeneratedColumn("", new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button();
				btnEditar.setIcon(new ThemeResource("icons/18/edit.png"));
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        Integer idUsuario = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Código").getValue();
			        handler.irEditarUsuario(idUsuario,idCliente);
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
				                	Integer idUsuario = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Código").getValue();
				                	System.out.println("eliminar");
				                	handler.elimiarUsuario();
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
	    
		tblUsuarios.setVisibleColumns((Object[]) EditarClienteController.obtenerColumnasVisiblesUsuario());
		
		areaPrincipal.addComponent(barraTituloUsuarios);
		areaPrincipal.addComponent(tblUsuarios);
		
		//Area Unidades Operativas
		HorizontalLayout barraTituloUnidadesOp = new HorizontalLayout();
		barraTituloUnidadesOp.setWidth("100%");
		Label tituloUnidadesOp = new Label("Unidades Operativas");
		tituloUnidadesOp.addStyleName("h2");
	    Button btnAgregarUnidadOp = new Button("+");	    
	    btnAgregarUnidadOp.addStyleName("default");
	    barraTituloUnidadesOp.addComponent(tituloUnidadesOp);
	    barraTituloUnidadesOp.addComponent(btnAgregarUnidadOp);
	    barraTituloUnidadesOp.setComponentAlignment(tituloUnidadesOp, Alignment.MIDDLE_LEFT);
	    barraTituloUnidadesOp.setComponentAlignment(btnAgregarUnidadOp, Alignment.MIDDLE_RIGHT);
		
	    tblUnidadesOperativas = new Table();
	    tblUnidadesOperativas.setWidth("100%");
	    tblUnidadesOperativas.setHeight("150px");
	    tblUnidadesOperativas.setContainerDataSource(handler.obtenerHeadersUnidadesOpContainer());
	    tblUnidadesOperativas.setSelectable(true);
	    
	    tblUnidadesOperativas.addGeneratedColumn("", new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button();
				btnEditar.setIcon(new ThemeResource("icons/18/edit.png"));
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        Integer idUnidadOperativa = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Código").getValue();
			        handler.irEditarUnidadOperativa(idUnidadOperativa);
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
	    
	    tblUnidadesOperativas.setVisibleColumns((Object[]) EditarClienteController.obtenerColumnasVisiblesUnidadOp());
             			    
		areaPrincipal.addComponent(barraTituloUnidadesOp);
		areaPrincipal.addComponent(tblUnidadesOperativas);
		
		btnAgregarUnidadOp.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irAgregarNuevaUnidadOperativa();				
			}
		});
		
		btnAgregarUsuario.addClickListener(new ClickListener() {	
			@Override
			public void buttonClick(ClickEvent event) {
				handler.irAgregarNuevoUsuario();				
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

	@Override
	public Table getUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getUnidadesOperativas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdCliente() {
		return idCliente;
	}

}
