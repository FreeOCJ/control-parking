package pe.cp.web.ui.view.operaciones;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.web.ui.view.main.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Component
@Scope("prototype")
@Theme("controlparking")
@SuppressWarnings("serial")
public class NuevaOperacionViewImpl extends HorizontalLayout implements INuevaOperacionView {

	private CssLayout contenido;
	ComboBox cbUnidadOperativa;
	DateField dfFechaOperacion;
	Button btnGuardar;
	Button btnCancelar;
	INuevaOperacionHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		init();
		handler = new NuevaOperacionController(this);
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
		
		//Header
		HorizontalLayout header = new HorizontalLayout();
	    header.setWidth("100%");
	    areaPrincipal.addComponent(header);
	        
	    Label title = new Label("Nueva Operación");
	    title.addStyleName("h1");  
	    header.addComponent(title); 
	    areaPrincipal.addComponent(header);
	    
	    FormLayout formulario = new FormLayout();
		areaPrincipal.addComponent(formulario);
		cbUnidadOperativa = new ComboBox();
		cbUnidadOperativa.setCaption("Unidad Operativa");
		cbUnidadOperativa.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		cbUnidadOperativa.setItemCaptionPropertyId("NOMBRE");
		dfFechaOperacion = new DateField();
		dfFechaOperacion.setValue(new Date());
		dfFechaOperacion.setCaption("Fecha Operación");
		
		formulario.addComponent(cbUnidadOperativa);
		formulario.addComponent(dfFechaOperacion);
		
		HorizontalLayout buttons = new HorizontalLayout();
		btnGuardar = new Button("Guardar");
		btnGuardar.addStyleName("default");
		btnCancelar = new Button("Cancelar");
		btnCancelar.addStyleName("default");
		
		buttons.addComponent(btnGuardar);		
		buttons.addComponent(btnCancelar);
		buttons.setComponentAlignment(btnGuardar, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_LEFT);
		
		formulario.addComponent(buttons);
		
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
	public ComboBox getUnidadOperativa() {
		return cbUnidadOperativa;
	}

	@Override
	public DateField getFechaOperacion() {
		return dfFechaOperacion;
	}
}
