package pe.cp.web.ui.view.configuracion.unidadoperativa;

import javax.swing.text.html.ListView;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSException;

import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class TarifaViewImpl extends HorizontalLayout  implements ITarifaView {

	private CssLayout contenido;
	private Label lblUnidadOperativa;
	private TextField txtMonto;
	private TextField txtNombre;
	private Button btnAnadir;
	private Button btnRetirar;
	private Button btnGuardar;
	private Button btnCancelar;
	private Label lblTitulo;
	private int idUnidadOperativa;
	private int idCliente;
	private String categoriaTarifa;
	private ListSelect lTarifas;
	private ITarifaHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		getParams();
		handler = new TarifaController(this);
		init();
		handler.cargar();
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
        
        contenido.addComponent(configurarContenido()); 

	}

	private void getParams(){
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int secondSlash = fragment.indexOf("/", firstSlash + 1);
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdUnidadOperativa = fragment.substring(firstSlash + 1, secondSlash);
		String strIdCliente = fragment.substring(secondSlash + 1, lastSlash);
		categoriaTarifa = fragment.substring(lastSlash + 1);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOperativa);
		idCliente = Integer.valueOf(strIdCliente);
	}
	
	@SuppressWarnings("serial")
	private VerticalLayout configurarContenido() {
		VerticalLayout contenido = new VerticalLayout();
		contenido.addStyleName("transactions");
		
		//Configurar el header
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    contenido.addComponent(header);
	        
	    lblTitulo = new Label("Nueva Tarifa");
	    lblTitulo.addStyleName("h1");  	    
	    header.addComponent(lblTitulo);
	    
	    //Configurar el cuerpo de la pagina
	    FormLayout formulario = new FormLayout();
		contenido.addComponent(formulario);
	    
		lblUnidadOperativa = new Label();
		lblUnidadOperativa.setCaption("Unidad Operativa");
		txtNombre = new TextField("Nombre");
		
		//Montos
		HorizontalLayout layoutMontos = new HorizontalLayout();
		layoutMontos.setCaption("Tarifas (S/.)");
		txtMonto = new TextField();
		btnAnadir = new Button("+");
		btnRetirar = new Button("-");
		layoutMontos.addComponent(txtMonto);
		layoutMontos.addComponent(btnAnadir);
		layoutMontos.addComponent(btnRetirar);
		
		lTarifas = new ListSelect();
		lTarifas.setWidth("220px");
		
		HorizontalLayout buttons = new HorizontalLayout();
		btnGuardar = new Button("Guardar");
		btnGuardar.addStyleName("default");
		btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardar);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(lblUnidadOperativa);
		formulario.addComponent(txtNombre);
		formulario.addComponent(layoutMontos);
	    formulario.addComponent(lTarifas);
	    formulario.addComponent(buttons);
		
	    btnAnadir.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.anadir();
			}
		});
	    
	    btnRetirar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				handler.retirar();
			}
		});
	    
	    btnGuardar.addClickListener(new ClickListener() {
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
	    
	    return contenido;
	}
	
	@Override
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}

	@Override
	public TextField getTxtMonto() {
		return txtMonto;
	}

	@Override
	public Label getLabelUnidadOperativa() {
		return lblUnidadOperativa;
	}

	@Override
	public Label getLabelTitulo() {
		return lblTitulo;
	}

	@Override
	public String getCategoriaTarifa() {
		return categoriaTarifa;
	}

	@Override
	public TextField getTxtNombre() {
		return txtNombre;
	}

	@Override
	public ListSelect getListaTarifas() {
		return lTarifas;
	}

	@Override
	public int getIdCliente() {
		return idCliente;
	}

}
