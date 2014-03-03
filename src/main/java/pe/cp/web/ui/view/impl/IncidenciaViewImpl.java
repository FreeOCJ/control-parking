package pe.cp.web.ui.view.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.thomas.timefield.TimeField;

import pe.cp.web.ui.handler.IIncidenciaHandler;
import pe.cp.web.ui.handler.impl.IncidenciaController;
import pe.cp.web.ui.view.IIncidenciaView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class IncidenciaViewImpl extends HorizontalLayout implements IIncidenciaView {

	private CssLayout contenido;
	private TextArea txtDescripcion;
	private TimeField txtHora;
	private ComboBox cbTipo;
	private Label lblTitulo;
	private Button btnGuardar;
	private Button btnCancelar;
	private int idIncidencia;
	private int idOperacion;
	
	IIncidenciaHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		getParams();
		removeAllComponents();
		handler = new IncidenciaController(this);
		init();	
		handler.cargar();
	}
	
	private void getParams() {
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdOperacion = fragment.substring(firstSlash + 1, lastSlash);
		String strIdIncidencia = fragment.substring(lastSlash + 1);
		
		idOperacion = Integer.valueOf(strIdOperacion);
		if (strIdIncidencia.equals("")) idIncidencia = 0;
		else idIncidencia = Integer.valueOf(strIdIncidencia);
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

	private VerticalLayout cargarContenido() {
		VerticalLayout areaPrincipal = new VerticalLayout();
		areaPrincipal.addStyleName("transactions");
		
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	    lblTitulo = new Label();
	    lblTitulo.addStyleName("h1");  
	    header.addComponent(lblTitulo);
	    
	    //Datos incidencia
	    FormLayout formularioLayout = new FormLayout();
	    areaPrincipal.addComponent(formularioLayout);
	    
	    cbTipo = new ComboBox("Tipo Incidencia");
	    cbTipo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
	    cbTipo.setItemCaptionPropertyId("TIPO");
	    cbTipo.setImmediate(true);
	    txtHora = new TimeField("Hora Evento");
	    txtDescripcion = new TextArea("Descripción");
	    txtDescripcion.setWidth("200px");
	    txtDescripcion.setHeight("200px");
	    formularioLayout.addComponent(cbTipo);
	    formularioLayout.addComponent(txtHora);
	    formularioLayout.addComponent(txtDescripcion);
	    
	    HorizontalLayout buttons = new HorizontalLayout();
	    formularioLayout.addComponent(buttons);
		btnGuardar = new Button("Guardar");
		btnGuardar.addStyleName("default");
		btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardar);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnGuardar, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
	    
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
		
	    return areaPrincipal;
	}
	
	@Override
	public TextArea getTxtDescripcion() {
		return txtDescripcion;
	}

	@Override
	public ComboBox getCbTipo() {
		return cbTipo;
	}

	@Override
	public TimeField getTxtHora() {
		return txtHora;
	}

	@Override
	public Label getLblTitulo() {
		return lblTitulo;
	}

	@Override
	public int getIdIncidencia() {
		return idIncidencia;
	}

	@Override
	public int getIdOperacion() {
		return idOperacion;
	}

}
