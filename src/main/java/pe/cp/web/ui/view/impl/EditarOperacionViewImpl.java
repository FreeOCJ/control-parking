package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import pe.cp.web.ui.handler.IEditarOperacionHandler;
import pe.cp.web.ui.handler.impl.EditarOperacionController;
import pe.cp.web.ui.view.IEditarOperacionView;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
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
public class EditarOperacionViewImpl extends HorizontalLayout implements IEditarOperacionView {

	private CssLayout contenido;
	private Label lblNombreUnidad;
	private Label lblCreador;
	private Label lblEstado;
	private Label lblFechaRegistro;
	private TextField txtOferta;
	private Table tblOperacionesPorHorario;
	private TextField txtTicketInicial;
	private TextField txtTicketFinal;
	private TextField txtTotalDia;
	private TextField txtAjuste;
	private TextField txtPernoctadosAyer;
	private TextField txtPernoctadosHoy;
	private TextField txtTotalIngresos;
	private TextField txtTotalSalidas;
	private TextField txtTotalPersonas;
	private TextField txtTotalCarrosTarifa;
	private TextField txtTotalRecaudacion;
	private Table tblControlTarifas;
	private Table tblIncidencias;
	private Button btnNuevaIncidencia;
	private Button btnAprobar;
	private Button btnEnviarAprobacion;
	private Button btnGuardar;
	private TabSheet tsAcciones;
	
	IEditarOperacionHandler handler;
	int idOperacion;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		init();
		handler = new EditarOperacionController(this);
		String fragment = UI.getCurrent().getPage().getUriFragment();
		String id = fragment.substring(fragment.indexOf("/") + 1);
		idOperacion = Integer.valueOf(id);
		handler.cargar();
	}

	@Override
	public void init() {
		setSizeFull();		
		addStyleName("main-view");		
		SideBarViewImpl barraControl = new SideBarViewImpl();
		addComponent(barraControl);
		
		// Content
		contenido = new CssLayout();
        addComponent(contenido);
        contenido.setSizeFull();
        contenido.addStyleName("view-content");       
        setExpandRatio(contenido, 1);        
        contenido.addComponent(cargarContenido());	
		
	}

	private HorizontalLayout crearCabeceraPagina() {
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	        
	    Label title = new Label("Editar Operación");
	    HorizontalLayout buttonBarLayout = new HorizontalLayout();
	    
	    header.addComponent(title);
	    header.addComponent(buttonBarLayout);
	    
	    title.addStyleName("h1"); 
	    btnGuardar = new Button("Guardar");
	    btnGuardar.addStyleName("default");
	    btnEnviarAprobacion = new Button("Enviar a Aprobar");
	    btnEnviarAprobacion.addStyleName("default");
	    btnAprobar = new Button("Aprobar");
	    btnAprobar.addStyleName("default");
	    buttonBarLayout.addComponent(btnGuardar);
	    buttonBarLayout.addComponent(btnEnviarAprobacion);
	    buttonBarLayout.addComponent(btnAprobar);
	    header.setComponentAlignment(buttonBarLayout, Alignment.MIDDLE_RIGHT);
	    
	    btnAprobar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.aprobar();
			}
		});
	    
	    btnGuardar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.guardar();
			}
		});
	    
	    btnEnviarAprobacion.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.enviarAprobar();
			}
		});
	    
	    return header;
	}
	
	private VerticalLayout cargarContenido(){
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		areaPrincipal.setMargin(true);
		
		//Header
		areaPrincipal.addComponent(crearCabeceraPagina());
	    
	    //Configurar datos de labels
	    HorizontalLayout cabeceraInformacion = new HorizontalLayout();
	    cabeceraInformacion.setWidth("100%");
		FormLayout labels1 = new FormLayout();
		FormLayout labels2 = new FormLayout();
		FormLayout labels3 = new FormLayout();
		FormLayout labels4 = new FormLayout();
		cabeceraInformacion.addComponent(labels1);
		cabeceraInformacion.addComponent(labels2);
		cabeceraInformacion.addComponent(labels3);
		cabeceraInformacion.addComponent(labels4);
		areaPrincipal.addComponent(cabeceraInformacion);
		
		lblNombreUnidad = new Label();
		lblNombreUnidad.setCaption("Unidad Operativa");
		lblCreador = new Label();
		lblCreador.setCaption("Registrado Por");
		lblEstado = new Label("Estado");
		lblEstado.setCaption("Estado");
		lblFechaRegistro = new Label("Fecha Registro");
		lblFechaRegistro.setCaption("Fecha Registro");
		
		labels1.addComponent(lblNombreUnidad);
		labels2.addComponent(lblCreador);
		labels3.addComponent(lblEstado);
		labels4.addComponent(lblFechaRegistro);
		
		txtOferta = new TextField("Oferta del día");
		txtOferta.setWidth("100%");
		txtOferta.setMaxLength(100);
		areaPrincipal.addComponent(txtOferta);
		
		//Configurar de Tabs
		tsAcciones = new TabSheet();
		tsAcciones.setSizeFull();
		tsAcciones.addStyleName("borderless");
		areaPrincipal.addComponent(tsAcciones);
		VerticalLayout tabIngresosSalidas = new VerticalLayout();
		VerticalLayout tabIncidencias = new VerticalLayout();
		
		tsAcciones.addTab(tabIngresosSalidas, "Control de Ingresos, Salidas y Tarifas");
		tsAcciones.addTab(tabIncidencias, "Incidencias del Día");
		
		//TAB INGRESOS Y SALIDAS
		HorizontalLayout headerIngresoSalidas = new HorizontalLayout();
		//tabIngresosSalidas.addComponent(headerIngresoSalidas);
		Label tituloControl = new Label("");
		tituloControl.addStyleName("h2");  
	    headerIngresoSalidas.addComponent(tituloControl);
	    
	    //Control Ingreso y Salida
	  	VerticalLayout controlIngresoLayout = new VerticalLayout();
	  	FormLayout formularioOferta = new FormLayout();
	  	HorizontalLayout ingresoDiaLayout = new HorizontalLayout();
	  	tabIngresosSalidas.addComponent(controlIngresoLayout);
	  	controlIngresoLayout.addComponent(formularioOferta);
	  	controlIngresoLayout.addComponent(ingresoDiaLayout);
		
		tblOperacionesPorHorario = new Table();
		tblControlTarifas = new Table();
		FormLayout adicionalesControlLayout = new FormLayout();
		adicionalesControlLayout.setMargin(true);
		
	    ingresoDiaLayout.addComponent(tblOperacionesPorHorario);
	    ingresoDiaLayout.addComponent(adicionalesControlLayout);
	    ingresoDiaLayout.addComponent(tblControlTarifas);
		
		txtTicketInicial = new TextField("Ticket Inicial");
		txtTicketInicial.setWidth("80px");
		txtTicketFinal = new TextField("Ticket Final");
		txtTicketFinal.setWidth("80px");
		txtTotalDia = new TextField("Total Día");
		txtTotalDia.setWidth("80px");
		txtAjuste = new TextField("Ajuste");
		txtAjuste.setWidth("80px");
		
		txtPernoctadosAyer = new TextField("Pernoctados Acumulados");
		txtPernoctadosAyer.setWidth("80px");
		txtPernoctadosHoy = new TextField("Pernoctados Hoy");
		txtPernoctadosHoy.setWidth("80px");
		txtTotalIngresos = new TextField("Total Ingresos");
		txtTotalIngresos.setWidth("80px");
		txtTotalSalidas = new TextField("Total Salidas");
		txtTotalSalidas.setWidth("80px");
		txtTotalPersonas = new TextField("Total Personas");
		txtTotalPersonas.setWidth("80px");
		txtTotalCarrosTarifa = new TextField("Total Carros");
		txtTotalCarrosTarifa.setWidth("80px");
	    txtTotalRecaudacion = new TextField("Total Recaudación");
	    txtTotalRecaudacion.setWidth("80px");
		txtTotalDia.setEnabled(false);
		txtPernoctadosHoy.setEnabled(false);
		txtTotalIngresos.setEnabled(false);
		txtTotalSalidas.setEnabled(false);
		txtTotalPersonas.setEnabled(false);
		txtTotalCarrosTarifa.setEnabled(false);
		txtTotalRecaudacion.setEnabled(false);
		
		adicionalesControlLayout.addComponent(txtTicketInicial);
		adicionalesControlLayout.addComponent(txtTicketFinal);
		adicionalesControlLayout.addComponent(txtTotalDia);
		adicionalesControlLayout.addComponent(txtAjuste);
		adicionalesControlLayout.addComponent(new Label());
		adicionalesControlLayout.addComponent(txtPernoctadosAyer);
		adicionalesControlLayout.addComponent(txtPernoctadosHoy);
		adicionalesControlLayout.addComponent(txtTotalIngresos);
		adicionalesControlLayout.addComponent(txtTotalSalidas);
		adicionalesControlLayout.addComponent(txtTotalPersonas);
		adicionalesControlLayout.addComponent(new Label("<hr />", ContentMode.HTML));
		adicionalesControlLayout.addComponent(txtTotalCarrosTarifa);
		adicionalesControlLayout.addComponent(txtTotalRecaudacion);
		
		//TAB INCIDENCIAS
		HorizontalLayout headerIncidencias = new HorizontalLayout();
		tabIncidencias.addComponent(headerIncidencias);
		
		headerIncidencias.setSizeFull();
		Label tituloIncidencias = new Label("");
		tituloIncidencias.addStyleName("h2");  
	    btnNuevaIncidencia = new Button("+");
	    btnNuevaIncidencia.addStyleName("default");
	    headerIncidencias.addComponent(tituloIncidencias); 
	    headerIncidencias.addComponent(btnNuevaIncidencia);
	    headerIncidencias.setComponentAlignment(btnNuevaIncidencia, Alignment.MIDDLE_RIGHT);
		
	    tblIncidencias = new Table();
	    tblIncidencias.setSizeFull();
	    tabIncidencias.addComponent(tblIncidencias);
	    
	    btnNuevaIncidencia.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.agregarIncidencia();
			}
		});

	    tblIncidencias.addGeneratedColumn("", new ColumnGenerator() {			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout botonesAccion = new HorizontalLayout();
				
				Button btnEditar = new Button();	
				btnEditar.setIcon(new ThemeResource("icons/18/edit.png"));
				btnEditar.addClickListener(new ClickListener() {			 
			      @Override public void buttonClick(ClickEvent event) {			    	  
			        Integer idIncidencia = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Id").getValue();
			        handler.editarIncidencia(idIncidencia);
			      }
			    });
				
				Button btnEliminar = new Button();	
				btnEliminar.setIcon(new ThemeResource("icons/18/delete.png"));
				btnEliminar.addClickListener(new ClickListener() {			 
			      @Override 
			      public void buttonClick(ClickEvent event) {	
			    	  Integer idIncidencia = (Integer) source.getContainerDataSource().getContainerProperty(itemId, "Id").getValue();
			    	  handler.eliminarIncidencia(idIncidencia);		        			      
			      }
			    });
			 
				botonesAccion.addComponent(btnEditar);
				botonesAccion.addComponent(btnEliminar);
			    return botonesAccion;
			}
		} );
		
	    //Eventos
	    txtTicketInicial.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.calcularTotalTickets();
			}
		});
        txtTicketFinal.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				handler.calcularTotalTickets();
			}
		});
        txtTicketInicial.setImmediate(true);
        txtTicketFinal.setImmediate(true);
	    
	    return areaPrincipal;
	}

	@Override
	public int getIdOperacion() {
		return idOperacion;
	}

	@Override
	public TextField getTxtTotalRecaudacion() {
		return txtTotalRecaudacion;
	}

	@Override
	public TextField getTxtTotalCarrosTarifa() {
		return txtTotalCarrosTarifa;
	}

	@Override
	public Button getBtnAprobar() {
		return btnAprobar;
	}

	@Override
	public Button getBtnGuardar() {
		return btnGuardar;
	}

	@Override
	public Button getBtnEnviarAprobar() {
		return btnEnviarAprobacion;
	}
	
	@Override
	public Label getLblNombreUnidadOp() {
		return lblNombreUnidad;
	}

	@Override
	public Label getLblCreador() {
		return lblCreador;
	}

	@Override
	public Label getLblEstado() {
		return lblEstado;
	}

	@Override
	public Label getFechaRegistro() {
		return lblFechaRegistro;
	}

	@Override
	public TextField getTxtOferta() {
		return txtOferta;
	}

	@Override
	public Table getTblOperacionesPorHorario() {
		return tblOperacionesPorHorario;
	}

	@Override
	public TextField getTxtTicketInicial() {
		return txtTicketInicial;
	}

	@Override
	public TextField getTxtTicketFinal() {
		return txtTicketFinal;
	}

	@Override
	public TextField getTxtTotalDia() {
		return txtTotalDia;
	}

	@Override
	public TextField getTxtAjuste() {
		return txtAjuste;
	}

	@Override
	public TextField getTxtPernoctadosAnteriores() {
		return txtPernoctadosAyer;
	}

	@Override
	public TextField getTxtPernoctadosHoy() {
		return txtPernoctadosHoy;
	}

	@Override
	public TextField gdtTxtTotalIngresos() {
		return txtTotalIngresos;
	}

	@Override
	public TextField getTxtTotalSalidas() {
		return txtTotalSalidas;
	}
	
	@Override
	public TextField getTxtTotalPersonas() {
		return txtTotalPersonas;
	}

	@Override
	public Table getTblControlTarifas() {
		return tblControlTarifas;
	}

	@Override
	public Table getTblIncidencias() {
		return tblIncidencias;
	}

	@Override
	public Button getBtnAgregarIncidencia() {
		return btnNuevaIncidencia;
	}
}
