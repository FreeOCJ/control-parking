package pe.cp.web.ui.view.operaciones;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

import pe.cp.core.service.OperacionService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;

public class EditarOperacionController implements IEditarOperacionHandler {

	private ApplicationContext ac;
	private IEditarOperacionView view;
	private Container controlHorasContainer;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
	private static final String ID_DETALLE = "ID";
	private static final String INGRESO_DETALLE = "Ingreso";
	private static final String SALIDA_DETALLE = "Salida";
	private static final String PERSONAS_DETALLE = "Personas";
	private static final String HORARIO_DETALLE = "Horario";
	
	public EditarOperacionController(IEditarOperacionView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		opService = ac.getBean(OperacionService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {
		
		ObtenerOperacionResponse response = 
				opService.obtener(new ObtenerOperacionRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			cargarCabecera(response.getOperacionView());
			cargarIngresoSalidas();
		} else {
			Logger.getAnonymousLogger().log(Level.WARNING, "nada");
		}
	}

	private void cargarCabecera(OperacionView opView) {
		view.getLblCreador().setValue(opView.getCreador());
		view.getLblEstado().setValue(opView.getEstado());
		view.getFechaRegistro().setValue(opView.getFechaCreacion().toString());
		
		ObtenerUnidadOperativaResponse response = 
				unidadOpService.obtenerPorId(new ObtenerUnidadOperativaRequest(opView.getIdUnidadOperativa()));
		if (response.isResultadoEjecucion()) {
			view.getLblNombreUnidadOp().setValue(response.getUnidadOpView().getNombre());
		} else {
			//TODO
		}
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	private void cargarIngresoSalidas() {
		cargarContainerTablaControl();
		
		ObtenerDetalleOperacionResponse response = 
				opService.obtenerDetalles(new ObtenerDetalleOperacionRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			for (OperacionDetalleView detalleView : response.getDetallesView()) {
				Item detalle = controlHorasContainer.getItem(controlHorasContainer.addItem());
				detalle.getItemProperty(ID_DETALLE).setValue(detalleView.getIdOperacionDetalle());  
				detalle.getItemProperty(HORARIO_DETALLE).setValue(detalleView.getHorario());
				
				TextField txtCantidadIngresos = new TextField();
				TextField txtCantidadSalidas = new TextField();
				TextField txtCantidadPersonas = new TextField();
				
				txtCantidadIngresos.setValue(String.valueOf(detalleView.getCantidadIngreso()));
				txtCantidadSalidas.setValue(String.valueOf(detalleView.getCantidadSalida()));
				txtCantidadPersonas.setValue(String.valueOf(detalleView.getCantidadPersonas()));
				
				txtCantidadIngresos.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						calcularTotalIngresoSalidas();
					}
				});
				
				txtCantidadSalidas.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						calcularTotalIngresoSalidas();
					}
				});
				
				txtCantidadPersonas.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						calcularTotalIngresoSalidas();
					}
				});
				
				txtCantidadIngresos.setImmediate(true);
				txtCantidadSalidas.setImmediate(true);
				txtCantidadPersonas.setImmediate(true);
				
				detalle.getItemProperty(INGRESO_DETALLE).setValue(txtCantidadIngresos);
				detalle.getItemProperty(SALIDA_DETALLE).setValue(txtCantidadSalidas);
				detalle.getItemProperty(PERSONAS_DETALLE).setValue(txtCantidadPersonas);
			}
		} else {
			//TODO
		}
	}
	
	@Override
	public void guardar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enviarAprobar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarIncidencia() {
		// TODO Auto-generated method stub

	}

	@Override
	public void editarIncidencia(int idIncidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarIncidencia(int idIncidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aprobar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarUsuario() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validarEdicion() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void cargarContainerTablaControl() {
		controlHorasContainer = new IndexedContainer(); 
		controlHorasContainer.addContainerProperty(ID_DETALLE,Integer.class, 0);
		controlHorasContainer.addContainerProperty(HORARIO_DETALLE,String.class, "");
		controlHorasContainer.addContainerProperty(INGRESO_DETALLE,TextField.class, new TextField());
		controlHorasContainer.addContainerProperty(SALIDA_DETALLE,TextField.class, new TextField());
		controlHorasContainer.addContainerProperty(PERSONAS_DETALLE,TextField.class, new TextField());
		
		view.getTblOperacionesPorHorario().setContainerDataSource(controlHorasContainer);
	}

	@Override
	public void calcularTotalTickets() {
		try {
			int ticketInicio = Integer.valueOf(view.getTxtTicketInicial().getValue());
			int ticketFin = Integer.valueOf(view.getTxtTicketFinal().getValue());
			int cantidad = ticketFin - ticketInicio;
			view.getTxtTotalDia().setValue(String.valueOf(cantidad));
		} catch(Exception e) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Formato incorrecto");
		}
	}

	@Override
	public void calcularTotalIngresoSalidas() {
		int totalEntradas = 0, totalSalidas = 0, totalPersonas = 0;
		
		try {
			for (Object propId : view.getTblOperacionesPorHorario().getContainerPropertyIds()) {
				ComboBox comboValorIngreso = (ComboBox) view.getTblOperacionesPorHorario().getItem(propId).getItemProperty(INGRESO_DETALLE);
				ComboBox comboValorSalida = (ComboBox) view.getTblOperacionesPorHorario().getItem(propId).getItemProperty(SALIDA_DETALLE);
				ComboBox comboValorPersonas = (ComboBox) view.getTblOperacionesPorHorario().getItem(propId).getItemProperty(PERSONAS_DETALLE);
				
				if (comboValorIngreso != null && comboValorSalida != null && comboValorPersonas != null) {
					totalEntradas += (Integer) comboValorPersonas.getValue();
					totalSalidas += (Integer) comboValorSalida.getValue();
					totalPersonas += (Integer) comboValorPersonas.getValue();
				}
			} 
			
			view.gdtTxtTotalIngresos().setValue(String.valueOf(totalEntradas));
			view.getTxtTotalSalidas().setValue(String.valueOf(totalSalidas));
			view.getTxtTotalPersonas().setValue(String.valueOf(totalPersonas));	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
