package pe.cp.web.ui.view.operaciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.Rol;
import pe.cp.core.service.OperacionService;
import pe.cp.core.service.OperacionServiceImpl;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.IncidenciaView;
import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionPorTarifaView;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.domain.TarifaConsolidadoView;
import pe.cp.core.service.messages.ActualizarOperacionRequest;
import pe.cp.core.service.messages.AprobarOperacionRequest;
import pe.cp.core.service.messages.EliminarIncidenciaRequest;
import pe.cp.core.service.messages.EnviarAprobarOperacionRequest;
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
import pe.cp.core.service.messages.Response;
import pe.cp.web.ui.ControlParkingUI;
import pe.cp.web.ui.NavegacionUtil;

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
	private static final String TOTAL_RECAUDACION = "Recaudación";
	private static final String PRECIO_TARIFA = "Precio Tarifa";
	private static final String ID_INCIDENCIA = "Id";
	private static final String TIPO_INCIDENCIA = "Tipo";
	private static final String HORA_INCIDENCIA = "Hora";
	private static final String DETALLE_INCIDENCIA = "Detalle";
	private static final String BOTONES_INCIDENCIA = "";
	private final String WARNING_EDICION = "La operación está aprobada y no puede ser modificada";
	
	private boolean esModoEdicion = true;
	
	public EditarOperacionController(IEditarOperacionView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		opService = ac.getBean(OperacionService.class);
		this.view = view;
	}
	
	private void configurarAccionesDisponibles(OperacionView operacionView) {
		Subject currentUser = SecurityUtils.getSubject();
		
		//Acciones relativas al rol del usuario
		if (!currentUser.hasRole(Rol.APROBADOR)) view.getBtnAprobar().setVisible(false);
		
		//Acciones relativas al estado de la operacion
		if (operacionView.getEstado().equals(opService.getConstanteEstadoEnProceso())) {
			view.getBtnAprobar().setVisible(false);
		}
		else if (operacionView.getEstado().equals(opService.getConstanteEstadoPorAprobar())) {
			view.getBtnEnviarAprobar().setVisible(false);
		}
		else if (operacionView.getEstado().equals(opService.getConstanteEstadoAprobada())) {
			esModoEdicion = false;
			view.getBtnGuardar().setVisible(false);
			view.getBtnAprobar().setVisible(false);
			view.getBtnEnviarAprobar().setVisible(false);
			view.getBtnAgregarIncidencia().setEnabled(false);
			view.getTblControlTarifas().setEnabled(false);
			view.getTblOperacionesPorHorario().setEnabled(false);
			view.getTxtTicketFinal().setEnabled(false);
			view.gdtTxtTotalIngresos().setEnabled(false);
			view.getTxtAjuste().setEnabled(false);
			view.getTxtPernoctadosAnteriores().setEnabled(false);
			view.getBtnAgregarIncidencia().setDescription(WARNING_EDICION);
		}
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
			configurarAccionesDisponibles(response.getOperacionView());
		} else {
			Logger.getAnonymousLogger().log(Level.WARNING, "nada");
		}
	}

	private void cargarCabecera(OperacionView opView) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		view.getLblCreador().setValue(opView.getCreador());
		view.getLblEstado().setValue(opView.getEstado());
		view.getFechaRegistro().setValue(sdf.format(opView.getFechaCreacion()));
		
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
				txtCantidadIngresos.setWidth("80px");
				TextField txtCantidadSalidas = new TextField();
				txtCantidadSalidas.setWidth("80px");
				TextField txtCantidadPersonas = new TextField();
				txtCantidadPersonas.setWidth("80px");
				
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
				txtCantidad.setWidth("80px");
				txtCantidad.setValue(String.valueOf(opTarifaView.getCantidad()));
				TextField txtTotalRecaudacion = new TextField();
				txtTotalRecaudacion.setWidth("80px");
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
			
			Object[] visibleHeaders = {TIPO_INCIDENCIA, HORA_INCIDENCIA, DETALLE_INCIDENCIA, BOTONES_INCIDENCIA};
			view.getTblIncidencias().setVisibleColumns(visibleHeaders);
			view.getTblIncidencias().setColumnWidth(TIPO_INCIDENCIA, 200);
			view.getTblIncidencias().setColumnWidth(HORA_INCIDENCIA, 85);
			view.getTblIncidencias().setColumnWidth(BOTONES_INCIDENCIA, 125);
		} else {
			//TODO
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void guardar() {
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null) {
			int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		
			ActualizarOperacionRequest request = new ActualizarOperacionRequest(idUsuario);
			request.setIdOperacion(view.getIdOperacion());
			request.setOferta(view.getTxtOferta().getValue());
			request.setPernoctadosHoy(Integer.valueOf(view.getTxtPernoctadosHoy().getValue()));
			request.setRecaudacion(Float.valueOf(view.getTxtTotalRecaudacion().getValue()));
			request.setTicketFinal(Integer.valueOf(view.getTxtTicketFinal().getValue()));
			request.setTicketInicial(Integer.valueOf(view.getTxtTicketInicial().getValue()));
			request.setTotalCarros(Integer.valueOf(view.getTxtTotalCarrosTarifa().getValue()));
			request.setTotalDia(Integer.valueOf(view.getTxtTotalDia().getValue()));
			request.setTotalIngresos(Integer.valueOf(view.gdtTxtTotalIngresos().getValue()));
			request.setTotalPersonas(Integer.valueOf(view.getTxtTotalPersonas().getValue()));
			request.setTotalSalidas(Integer.valueOf(view.getTxtTotalSalidas().getValue()));
			request.setAjuste(Integer.valueOf(view.getTxtAjuste().getValue()));
			request.setPernoctadosAyer(Integer.valueOf(view.getTxtPernoctadosAnteriores().getValue()));
	        
			request.setDetalles(new ArrayList<OperacionDetalleView>());
			for (Iterator i = view.getTblOperacionesPorHorario().getItemIds().iterator(); i.hasNext();) {
				int iid = (Integer) i.next();
				Item item = view.getTblOperacionesPorHorario().getItem(iid);
				
				int idDetalle = (Integer) (item.getItemProperty(ID_DETALLE).getValue());
				TextField txtIngreso = (TextField) (item.getItemProperty(INGRESO_DETALLE).getValue());
				TextField txtSalida = (TextField) (item.getItemProperty(SALIDA_DETALLE).getValue());
				TextField txtPersonas = (TextField) (item.getItemProperty(PERSONAS_DETALLE).getValue());
				
				OperacionDetalleView opDetalle = new OperacionDetalleView();
				opDetalle.setCantidadIngreso(Integer.valueOf(txtIngreso.getValue()));
				opDetalle.setCantidadPersonas(Integer.valueOf(txtPersonas.getValue()));
				opDetalle.setCantidadSalida(Integer.valueOf(txtSalida.getValue()));
				opDetalle.setIdOperacionDetalle(idDetalle);
				
				request.getDetalles().add(opDetalle);
			}
			
			request.setTarifas(new ArrayList<OperacionPorTarifaView>());
			for (Iterator i = view.getTblControlTarifas().getItemIds().iterator(); i.hasNext();) {
				int iid = (Integer) i.next();
				Item item = view.getTblControlTarifas().getItem(iid);
				
				int id = (Integer)(item.getItemProperty(ID_OP_TARIFA).getValue());
				TextField txtCantidad = (TextField) (item.getItemProperty(CANTIDAD_TARIFAS).getValue());
				TextField txtRecaudacion = (TextField) (item.getItemProperty(TOTAL_RECAUDACION).getValue());
				String descripcion = (String) (item.getItemProperty(CAT_TARIFA_TARIFAS).getValue());
				
				OperacionPorTarifaView tarifa = new OperacionPorTarifaView();
				tarifa.setCantidad(Integer.valueOf(txtCantidad.getValue()));
				tarifa.setMonto(Float.valueOf(txtRecaudacion.getValue()));
				tarifa.setIdOpTarifa(id);
				tarifa.setDescripcion(descripcion);
				
				request.getTarifas().add(tarifa);
			}
			
			request.setLoginModificador(currentUser.getSession().getAttribute("login").toString());
			
			Response response = opService.actualizarOperacion(request);
			Notification notificacion = new Notification(response.getMensaje());
			notificacion.setPosition(Position.TOP_CENTER);
			notificacion.show(Page.getCurrent());
		}
	}

	@Override
	public void enviarAprobar() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		
		Response response = opService.enviarAprobarOperacion(new EnviarAprobarOperacionRequest(view.getIdOperacion(), idUsuario));
        if (response.isResultadoEjecucion()) {
        	currentUser.getSession().setAttribute("mensaje", response.getMensaje());
        	NavegacionUtil.irOperaciones();
        } else {
        	Notification notificacion = new Notification(response.getMensaje());
    		notificacion.setPosition(Position.TOP_CENTER);
    		notificacion.show(Page.getCurrent());
        }
	}

	@Override
	public void agregarIncidencia() {
		NavegacionUtil.irMantIncidencia(view.getIdOperacion(), 0);
	}

	@Override
	public void editarIncidencia(int idIncidencia) {
		NavegacionUtil.irMantIncidencia(view.getIdOperacion(), idIncidencia);
	}

	@SuppressWarnings("serial")
	@Override
	public void eliminarIncidencia(final int idIncidencia) {
		ConfirmDialog.show(UI.getCurrent(), "Confirmar Acción", "¿Estás seguro que deseas eliminar la incidencia?", "Si", "No", 
		        new ConfirmDialog.Listener() {
		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                	Subject currentUser = SecurityUtils.getSubject();
		                	int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		                    Response response = opService.eliminarIncidencia(new EliminarIncidenciaRequest(idIncidencia, idUsuario));
		                	if (response.isResultadoEjecucion()) {
		                		cargarIncidencias();
		                	} else {
		                		//TODO
		                	}
		                }
		            }
		        });	
	}

	@Override
	public void aprobar() {
		Subject currentUser = SecurityUtils.getSubject();
		int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		Response response = opService.aprobarOperacion(new AprobarOperacionRequest(view.getIdOperacion(), idUsuario));
        if (response.isResultadoEjecucion()) {
        	currentUser.getSession().setAttribute("mensaje", response.getMensaje());
        	NavegacionUtil.irOperaciones();
        } else {
        	Notification notificacion = new Notification(response.getMensaje());
    		notificacion.setPosition(Position.TOP_CENTER);
    		notificacion.show(Page.getCurrent());
        }
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			UI.getCurrent().getNavigator().navigateTo("");
		}else{
			if (!currentUser.hasRole(Rol.APROBADOR) || !currentUser.hasRole(Rol.OPERADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);
			}
		}
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
		Object[] visibleHeaders = {CAT_TARIFA_TARIFAS, CANTIDAD_TARIFAS, TOTAL_RECAUDACION};
		view.getTblControlTarifas().setVisibleColumns(visibleHeaders);
	}
	
	public void cargarContainerIncidencias() {
		incidenciasContainer = new IndexedContainer(); 
		
		incidenciasContainer.addContainerProperty(ID_INCIDENCIA,Integer.class, 0);
		incidenciasContainer.addContainerProperty(TIPO_INCIDENCIA,String.class, "");
		incidenciasContainer.addContainerProperty(HORA_INCIDENCIA,String.class, "");
		incidenciasContainer.addContainerProperty(DETALLE_INCIDENCIA, String.class, "");
		
		view.getTblIncidencias().setContainerDataSource(incidenciasContainer);
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
		int cantidad, totalCarros = 0;
		float totalRecaudacion = 0;
		
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
