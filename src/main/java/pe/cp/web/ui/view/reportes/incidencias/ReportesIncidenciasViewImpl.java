package pe.cp.web.ui.view.reportes.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import pe.cp.web.ui.view.main.SideBar;

@Component
@Scope("prototype")
@SuppressWarnings("serial")
@Theme("controlparking")
public class ReportesIncidenciasViewImpl extends HorizontalLayout implements IReportesIncidenciasView{

	private CssLayout contenido;
	private int idUnidadOperativa;
	private int idCliente;
	private Button btnExportToPdf;
	
	@Autowired
	private IReportesIncidenciasViewHandler handler;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();		
		handler = new ReportesIncidenciasController(this);
		getParamsUrl();
		init();
	}
	
	private void getParamsUrl(){
		String fragment = UI.getCurrent().getPage().getUriFragment();
		int firstSlash = fragment.indexOf('/');
		int lastSlash = fragment.lastIndexOf('/');
		
		String strIdCliente = fragment.substring(firstSlash + 1, lastSlash);
		String strIdUnidadOperativa = fragment.substring(lastSlash + 1);
		
		idCliente = Integer.valueOf(strIdCliente);
		idUnidadOperativa = Integer.valueOf(strIdUnidadOperativa);
	}
	


	@Override
	public void init() {
		System.out.println("init reportes incidencias");
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
	    
	    Label title = new Label("Reporte de Incidencias");
	    title.addStyleName("h1");         
	    header.addComponent(title);
	    
	    Label lblCliente = new Label("Cliente: " + handler.obtenerNombreCliente(idCliente));
	    Label lblUnidadOperativa = new Label("Unidad Operativa: " + handler.obtenerNombreUnidadOperativa(idUnidadOperativa));
	    
	    VerticalLayout datos = new VerticalLayout();
	    datos.setHeight("20%");
	    datos.setWidth("400px");
	    datos.setSpacing(true);
	    datos.addStyleName("toolbar");
	    areaPrincipal.addComponent(datos);
	   	    
	    datos.addComponent(lblCliente);
	    datos.addComponent(lblUnidadOperativa);
	    
	    VerticalLayout content = new VerticalLayout();
	    content.setSizeFull();
	    content.setHeight("60%");
	    content.setWidth("100%");
	    content.setSpacing(true);
	    
	    Label lblContent = new Label("Contenido PDF : ");
	    content.addComponent(lblContent);
	    
	    areaPrincipal.addComponent(content);
	    
	    HorizontalLayout footer = new HorizontalLayout();
	    footer.setHeight("20%");
	    footer.setWidth("100%");
	    footer.setSpacing(true);
	    areaPrincipal.addComponent(footer);
	        
	    btnExportToPdf = new Button("PDF");
	    btnExportToPdf.addStyleName("default");
	    footer.addComponent(btnExportToPdf);
	    footer.setComponentAlignment(btnExportToPdf, Alignment.BOTTOM_RIGHT);
	    
	    header.addComponent(title);
	    return areaPrincipal;
	}

}
