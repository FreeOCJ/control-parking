package pe.cp.web.ui.view.operaciones;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
	private Table tblControlTarifas;
	private Table tblIncidencias;
	private Button btnNuevaIncidencia;
	
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
		areaPrincipal.setMargin(true);
		
		//Header
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Editar Operación");
	    title.addStyleName("h1");  
	    header.addComponent(title); 
	    areaPrincipal.addComponent(header);
	    
	    //Configurar datos de labels
		FormLayout labels1 = new FormLayout();
		FormLayout labels2 = new FormLayout();
		HorizontalLayout cabeceraInformacion = new HorizontalLayout();
		cabeceraInformacion.addComponent(labels1);
		cabeceraInformacion.addComponent(labels2);
		areaPrincipal.addComponent(cabeceraInformacion);
		
		lblNombreUnidad = new Label();
		lblNombreUnidad.setCaption("Unidad Operativa");
		lblCreador = new Label();
		lblCreador.setCaption("Registrado Por");
		labels1.addComponent(lblNombreUnidad);
		labels1.addComponent(lblCreador);
		
		lblEstado = new Label("Estado");
		lblEstado.setCaption("Estado");
		lblFechaRegistro = new Label("Fecha Registro");
		lblFechaRegistro.setCaption("Fecha Registro");
		labels2.addComponent(lblEstado);
		labels2.addComponent(lblFechaRegistro);
		
		//Header Control de Ingreso y Salida
		HorizontalLayout headerIngresoSalidas = new HorizontalLayout();
		areaPrincipal.addComponent(headerIngresoSalidas);
		Label tituloControl = new Label("Control de Ingreso y Salida");
	    title.addStyleName("h2");  
	    headerIngresoSalidas.addComponent(tituloControl);
	    
	    //Control Ingreso y Salida
	  	VerticalLayout controlIngresoLayout = new VerticalLayout();
	  	FormLayout formularioOferta = new FormLayout();
	  	HorizontalLayout ingresoDiaLayout = new HorizontalLayout();
	  	areaPrincipal.addComponent(controlIngresoLayout);
	  	controlIngresoLayout.addComponent(formularioOferta);
	  	controlIngresoLayout.addComponent(ingresoDiaLayout);
	 
		txtOferta = new TextField("Oferta del día");
		txtOferta.setWidth("100%");
		formularioOferta.addComponent(txtOferta);
		
		tblOperacionesPorHorario = new Table();
		FormLayout adicionalesControlLayout = new FormLayout();
		
	    ingresoDiaLayout.addComponent(tblOperacionesPorHorario);
	    ingresoDiaLayout.addComponent(adicionalesControlLayout);
		
		txtTicketInicial = new TextField("Ticket Inicial");
		txtTicketFinal = new TextField("Ticket Final");
		txtTotalDia = new TextField("Total Día");
		txtAjuste = new TextField("Ajuste");
		
		txtPernoctadosAyer = new TextField("Pernoctados Acumulados");
		txtPernoctadosHoy = new TextField("Pernoctados Hoy");
		txtTotalIngresos = new TextField("Total Ingresos");
		txtTotalSalidas = new TextField("Total Salidas");
		txtTotalPersonas = new TextField("Total Personas");
		txtTotalDia.setEnabled(false);
		txtPernoctadosAyer.setEnabled(false);
		txtPernoctadosHoy.setEnabled(false);
		txtTotalIngresos.setEnabled(false);
		txtTotalSalidas.setEnabled(false);
		txtTotalPersonas.setEnabled(false);
		
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
		
		//Control de Tarifas
		HorizontalLayout headerTarifas = new HorizontalLayout();
		areaPrincipal.addComponent(headerTarifas);
		Label tituloTarifas = new Label("Control de Tarifas");
	    title.addStyleName("h2");  
	    headerTarifas.addComponent(tituloTarifas);
		
	    tblControlTarifas = new Table();
		areaPrincipal.addComponent(tblControlTarifas);
		
		//Incidencias del dia
		HorizontalLayout headerIncidencias = new HorizontalLayout();
		areaPrincipal.addComponent(headerIncidencias);
		Label tituloIncidencias = new Label("Incidencias del Día");
	    title.addStyleName("h2");  
	    btnNuevaIncidencia = new Button("+");
	    btnNuevaIncidencia.addStyleName("default");
	    headerIncidencias.addComponent(tituloIncidencias); 
	    headerIncidencias.addComponent(btnNuevaIncidencia);
	    headerIncidencias.setComponentAlignment(btnNuevaIncidencia, Alignment.MIDDLE_RIGHT);
		
	    tblIncidencias = new Table();
	    areaPrincipal.addComponent(tblIncidencias);
		
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
}
