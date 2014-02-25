package pe.cp.web.ui.view.operaciones;

import java.util.Iterator;
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
import pe.cp.core.service.domain.IncidenciaView;
import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionPorTarifaView;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerIncidenciasRequest;
import pe.cp.core.service.messages.ObtenerIncidenciasResponse;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaRequest;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;

public class EditarOperacionController implements IEditarOperacionHandler {

	private ApplicationContext ac;
	private IEditarOperacionView view;
	private Container controlHorasContainer;
	private Container tarifasContainer;
	private Container incidenciasContainer;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
	private static final String ID_DETALLE = "ID";
	private static final String INGRESO_DETALLE = "Ingreso";
	private static final String SALIDA_DETALLE = "Salida";
	private static final String PERSONAS_DETALLE = "Personas";
	private static final String HORARIO_DETALLE = "Horario";
	private static final String ID_OP_TARIFA = "ID";
	private static final String CAT_TARIFA_TARIFAS = "Categoría - Tarifa";
	private static final String CANTIDAD_TARIFAS = "Cantidad";
	private static final String TOTAL_RECAUDACION = "Total Recaudación";
	private static final String PRECIO_TARIFA = "Precio Tarifa";
	private static final String ID_INCIDENCIA = "Id";
	private static final String TIPO_INCIDENCIA = "Tipo";
	private static final String HORA_INCIDENCIA = "Hora";
	private static final String DETALLE_INCIDENCIA = "Detalle";
	private static final String BOTONES_INCIDENCIA = "";
	
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
			cargarDatosOperacion();
			cargarDatosTarifas();
			cargarIncidencias();
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
	
	private void cargarDatosOperacion() {
		ObtenerOperacionResponse response = opService.obtener(new ObtenerOperacionRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			OperacionView opView = response.getOperacionView();
			
			view.getTxtOferta().setValue(opView.getOferta());
			view.getTxtAjuste().setValue(String.valueOf(opView.getAjuste()));
			view.getTxtPernoctadosAnteriores().setValue(String.valueOf(opView.getCantidadPernoctadosInicio()));
			view.getTxtTicketFinal().setValue(String.valueOf(opView.getNumeroTicketFin()));
			view.getTxtTicketInicial().setValue(String.valueOf(opView.getNumeroTicketInicio()));
			view.getTxtTotalDia().setValue(String.valueOf(opView.getNumeroTicketFin() - opView.getNumeroTicketInicio()));
			view.getTxtTotalPersonas().setValue(String.valueOf(opView.getCantidadPersonas()));
			view.getTxtTotalSalidas().setValue(String.valueOf(opView.getCantidadVehiculosSalida()));
			view.gdtTxtTotalIngresos().setValue(String.valueOf(opView.getCantidadVehiculosEntrada()));
			view.getTxtPernoctadosHoy().setValue(String.valueOf(opView.getCantidadPernoctadosFin()));
		} else {
			//TODO
		}
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	private void cargarDatosTarifas() {
		cargarContainerTarifas();
		ObtenerOperacionPorTarifaResponse response = 
				opService.obtenerOperacionesPorTarifa(new ObtenerOperacionPorTarifaRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			view.getTxtTotalCarrosTarifa().setValue(String.valueOf(response.getTotalCarros()));
			view.getTxtTotalRecaudacion().setValue(String.valueOf(response.getTotalRecaudacion()));
			
			for (OperacionPorTarifaView opTarifaView : response.getOpsPorTarifaView()) {
				Item detalle = tarifasContainer.getItem(tarifasContainer.addItem());
				detalle.getItemProperty(ID_OP_TARIFA).setValue(opTarifaView.getIdOpTarifa());  
				detalle.getItemProperty(CAT_TARIFA_TARIFAS).setValue(opTarifaView.getDescripcion());
				detalle.getItemProperty(PRECIO_TARIFA).setValue(opTarifaView.getPrecioTarifa());
				
				TextField txtCantidad = new TextField();
				txtCantidad.setValue(String.valueOf(opTarifaView.getCantidad()));
				TextField txtTotalRecaudacion = new TextField();
				txtTotalRecaudacion.setValue(String.valueOf(opTarifaView.getMonto()));
				txtTotalRecaudacion.setEnabled(false);
				detalle.getItemProperty(CANTIDAD_TARIFAS).setValue(txtCantidad);
				detalle.getItemProperty(TOTAL_RECAUDACION).setValue(txtTotalRecaudacion);
				
				txtCantidad.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						calcularTotalTarifas();
					}
				});
				txtCantidad.setImmediate(true);
			}
		} else {
			//TODO
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarIncidencias() {
		cargarContainerIncidencias();
		
		ObtenerIncidenciasResponse response = opService.obtenerIncidencias(new ObtenerIncidenciasRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			for (IncidenciaView incidenciaView : response.getIncidenciasView()) {
				Item incidencia = incidenciasContainer.getItem(incidenciasContainer.addItem());
				incidencia.getItemProperty(ID_INCIDENCIA).setValue(incidenciaView.getId());  
				incidencia.getItemProperty(HORA_INCIDENCIA).setValue(incidenciaView.getHora());
				incidencia.getItemProperty(TIPO_INCIDENCIA).setValue(incidenciaView.getTipo());
				incidencia.getItemProperty(DETALLE_INCIDENCIA).setValue(incidenciaView.getDetalle());
			}
			
			Object[] visibleHeaders = {ID_INCIDENCIA, TIPO_INCIDENCIA, HORA_INCIDENCIA, DETALLE_INCIDENCIA, BOTONES_INCIDENCIA};
			view.getTblIncidencias().setVisibleColumns(visibleHeaders);
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
		Object[] visibleHeaders = {HORARIO_DETALLE, INGRESO_DETALLE, SALIDA_DETALLE, PERSONAS_DETALLE};
		view.getTblOperacionesPorHorario().setVisibleColumns(visibleHeaders);
	}
	
	public void cargarContainerTarifas() {
		tarifasContainer = new IndexedContainer(); 
		
		tarifasContainer.addContainerProperty(ID_OP_TARIFA,Integer.class, 0);
		tarifasContainer.addContainerProperty(CAT_TARIFA_TARIFAS,String.class, "");
		tarifasContainer.addContainerProperty(CANTIDAD_TARIFAS,TextField.class, new TextField());
		tarifasContainer.addContainerProperty(PRECIO_TARIFA, Float.class, 0f);
		tarifasContainer.addContainerProperty(TOTAL_RECAUDACION,TextField.class, new TextField());
		
		view.getTblControlTarifas().setContainerDataSource(tarifasContainer);
		Object[] visibleHeaders = {CAT_TARIFA_TARIFAS, CANTIDAD_TARIFAS, PRECIO_TARIFA, TOTAL_RECAUDACION};
		view.getTblControlTarifas().setVisibleColumns(visibleHeaders);
	}
	
	public void cargarContainerIncidencias() {
		tarifasContainer = new IndexedContainer(); 
		
		tarifasContainer.addContainerProperty(ID_INCIDENCIA,Integer.class, 0);
		tarifasContainer.addContainerProperty(TIPO_INCIDENCIA,String.class, "");
		tarifasContainer.addContainerProperty(HORA_INCIDENCIA,String.class, "");
		tarifasContainer.addContainerProperty(DETALLE_INCIDENCIA, String.class, "");
		
		view.getTblIncidencias().setContainerDataSource(tarifasContainer);
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

	@SuppressWarnings("rawtypes")
	@Override
	public void calcularTotalIngresoSalidas() {
		int totalEntradas = 0, totalSalidas = 0, totalPersonas = 0;
		
		try {
			for (Iterator i = view.getTblOperacionesPorHorario().getItemIds().iterator(); i.hasNext();) {
				int iid = (Integer) i.next();
				Item item = view.getTblOperacionesPorHorario().getItem(iid);
				
				TextField txtIngreso = (TextField) (item.getItemProperty(INGRESO_DETALLE).getValue());
				TextField txtSalida = (TextField) (item.getItemProperty(SALIDA_DETALLE).getValue());
				TextField txtPersonas = (TextField) (item.getItemProperty(PERSONAS_DETALLE).getValue());
				
				totalEntradas += Integer.valueOf(txtIngreso.getValue());
				totalSalidas += Integer.valueOf(txtSalida.getValue());
				totalPersonas += Integer.valueOf(txtPersonas.getValue());
			}
			
			view.gdtTxtTotalIngresos().setValue(String.valueOf(totalEntradas));
			view.getTxtTotalSalidas().setValue(String.valueOf(totalSalidas));
			view.getTxtTotalPersonas().setValue(String.valueOf(totalPersonas));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void calcularTotalTarifas() {
		float recaudacion;
		int cantidad;
		float totalRecaudacion = 0, totalCarros = 0;
		
		try {
			for (@SuppressWarnings("rawtypes")
			Iterator i = view.getTblControlTarifas().getItemIds().iterator(); i.hasNext();) {
				int iid = (Integer) i.next();
				Item item = view.getTblControlTarifas().getItem(iid);
				
				TextField txtCantidad = (TextField) (item.getItemProperty(CANTIDAD_TARIFAS).getValue());
				TextField txtRecaudacion = (TextField) (item.getItemProperty(TOTAL_RECAUDACION).getValue());
				float precio =  Float.parseFloat(item.getItemProperty(PRECIO_TARIFA).getValue().toString());
				
				cantidad = Integer.valueOf(txtCantidad.getValue());
				recaudacion = (float) (precio * (cantidad));
				txtRecaudacion.setValue(String.valueOf(recaudacion));
				totalCarros += cantidad;
				totalRecaudacion += recaudacion;
			}
			view.getTxtTotalCarrosTarifa().setValue(String.valueOf(totalCarros));
			view.getTxtTotalRecaudacion().setValue(String.valueOf(totalRecaudacion));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
