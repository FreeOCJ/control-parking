package pe.cp.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.navigator.View;

import pe.cp.core.dao.IncidenciaDao;
import pe.cp.core.dao.OperacionDao;
import pe.cp.core.dao.RolDao;
import pe.cp.core.dao.TarifaDao;
import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.domain.DetallePorOperacion;
import pe.cp.core.domain.Incidencia;
import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.OperacionDetalle;
import pe.cp.core.domain.OperacionPorTarifa;
import pe.cp.core.domain.Tarifa;
import pe.cp.core.domain.TipoIncidencia;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.service.messages.ActualizarOperacionRequest;
import pe.cp.core.service.messages.AgregarIncidenciaRequest;
import pe.cp.core.service.messages.AgregarIncidenciaResponse;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.AprobarOperacionRequest;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ActualizarIncidenciaRequest;
import pe.cp.core.service.messages.EliminarIncidenciaRequest;
import pe.cp.core.service.messages.EnviarAprobarOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerIncidenciaRequest;
import pe.cp.core.service.messages.ObtenerIncidenciaResponse;
import pe.cp.core.service.messages.ObtenerIncidenciasRequest;
import pe.cp.core.service.messages.ObtenerIncidenciasResponse;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaRequest;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.messages.ObtenerTipoIncidenciasResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.core.service.domain.IncidenciaView;
import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionPorTarifaView;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.domain.TipoIncidenciaView;
import pe.cp.core.service.domain.WrapperDomain;

@Service
public class OperacionServiceImpl implements OperacionService {
	
	@Autowired
	private OperacionDao opdao;
	@Autowired
	private UnidadOperativaDao unidadOpdao;
	@Autowired
	private TarifaDao tarifadao;
	@Autowired
	private IncidenciaDao incidenciaDao;
	
	private final String ESTADO_EN_PROCESO = "EN PROCESO";
	private final String ESTADO_POR_APROBAR = "POR APROBAR";
	private final String ESTADO_APROBADA = "APROBADA";
	
	private final String ERROR_VALIDAR_NUEVA_OPERACION = "Ya existe una operacion para la unidad operativa en la fecha indicada";
	private final String ERROR_PARAMS_INCOMPLETOS = "Los parametros enviados no estan completos";
	private final String ERROR_AGREGAR_OPERACION = "Error al agregar la operacion";
	private final String ERROR_INSERT_OPERACION = "Error al insertar la operacion";
	private final String EXITO_AGREGAR_OPERACION = "Se agrego la operacion";
	private final String ERROR_UNIDAD_OPERATIVA_SIN_TARIFAS = "La unidad operativa no tiene tarifas asociadas";
	
	@Override
	public int agregar(Operacion op) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void editar(Operacion op) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idOperacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public int agregarIncidencia(int idOperacion, int idIncidencia) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerIncidencia(int idOperacion, int idIncidencia) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Operacion> buscar(String nombreUnidadOp, Date fechaOp,
			String estadoOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuscarOperacionResponse buscar(BuscarOperacionesRequest request) {
		BuscarOperacionResponse response = new BuscarOperacionResponse();
		
		try {
			List<Operacion> operaciones = 
					 opdao.buscar(request.getIdUnidadOperativa(), request.getFechaOperacion(), request.getEstado());
			response.setOperacionesView(new ArrayList<OperacionView>());
			for (Operacion operacion : operaciones)
				response.getOperacionesView().add(WrapperDomain.ViewMapper(operacion));
			
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las operaciones");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	private int obtenerCantidadPernoctadosAyer(int idUnidadOperativa) {
		int cantidad = 0;
		
		GregorianCalendar ayer = new GregorianCalendar();
		ayer.setTime(new Date());
		ayer.add(Calendar.DATE, -1);
		List<Operacion> operaciones = opdao.buscar(idUnidadOperativa, ayer.getTime(), ESTADO_APROBADA);
		
		if (operaciones != null && operaciones.size() > 0) {
		   cantidad = operaciones.get(0).getCantidadPernoctadosFin();	
		}
		
        return cantidad;
	}
	
	@Override
	public AgregarOperacionResponse agregar(AgregarOperacionRequest request) {
		AgregarOperacionResponse response = new AgregarOperacionResponse();
		
		try {
			UnidadOperativa unidadOp = unidadOpdao.buscar(request.getIdUnidadOperativa());
			Date horaInicio = unidadOp.getHoraInicio();
			Date horaFin = unidadOp.getHoraFin();
			
			List<Tarifa> tarifas = tarifadao.obtenerTarifas(request.getIdUnidadOperativa(), null);
			if (tarifas == null || tarifas.size() == 0) {
				response.setResultadoEjecucion(false);
				response.setMensaje(ERROR_UNIDAD_OPERATIVA_SIN_TARIFAS);
				return response;
			}
			
			if (request.getIdUnidadOperativa() > 0 && request.getFechaOperacion() != null 
					&& request.getLogin() != null && !request.getLogin().isEmpty()
					&& unidadOp != null) {
				if (validarNuevaOperacion(request.getIdUnidadOperativa(), request.getFechaOperacion())) {
					int pernoctadosAyer = obtenerCantidadPernoctadosAyer(request.getIdUnidadOperativa());
					
					//Crear la nueva operacion
					Operacion op = new Operacion();
					op.setCreador(request.getLogin());
					op.setEstado("EN PROCESO");
					op.setFechaCreacion(new Date());
					op.setFechaActualizacion(new Date());
					op.setFechaTransaccion(request.getFechaOperacion());
					op.setIdUnidadOperativa(request.getIdUnidadOperativa());
					op.setOferta("");
					op.setUltimoModificador(request.getLogin());
					op.setCantidadPernoctadosInicio(pernoctadosAyer);
					
					int idNuevaOperacion = opdao.agregar(op);
					if (idNuevaOperacion > 0) {
						//Crear las tablas de detalle
						while (horaInicio.compareTo(horaFin) < 0) {
							Date horaFinTemp = new Date(horaInicio.getTime() + 3600 * 1000);
							if (horaFinTemp.compareTo(horaFin) > 0) horaFinTemp = horaFin;
							
							OperacionDetalle opDetalle = new OperacionDetalle();
							opDetalle.setHoraInicio(horaInicio);
							opDetalle.setHoraFin(horaFinTemp);
							opDetalle.setIdOperacion(idNuevaOperacion);
							opdao.agregarOperacionDetalle(opDetalle);
							
							horaInicio = horaFinTemp;
						}
						
						//Cargar las tarifas por operacion
						for (Tarifa tarifa : tarifas) {
							OperacionPorTarifa opPorTarifa = new OperacionPorTarifa();
							opPorTarifa.setIdOperacion(idNuevaOperacion);
							opPorTarifa.setIdTarifa(tarifa.getId());
							opPorTarifa.setMonto(0.0f);
							opdao.agregarOperacionPorTarifa(opPorTarifa);
						}
						
						response.setIdNuevaOperacion(idNuevaOperacion);
						response.setResultadoEjecucion(true);
						response.setMensaje(EXITO_AGREGAR_OPERACION);
					} else {
						response.setResultadoEjecucion(false);
						response.setMensaje(ERROR_INSERT_OPERACION);
						Logger.getAnonymousLogger().log(Level.SEVERE, ERROR_INSERT_OPERACION);
					}	
				} else {
					response.setResultadoEjecucion(false);
					response.setMensaje(ERROR_VALIDAR_NUEVA_OPERACION);
					Logger.getAnonymousLogger().log(Level.SEVERE, ERROR_VALIDAR_NUEVA_OPERACION);
				}
			} else {
				response.setResultadoEjecucion(false);
				response.setMensaje(ERROR_PARAMS_INCOMPLETOS);
				Logger.getAnonymousLogger().log(Level.SEVERE, ERROR_PARAMS_INCOMPLETOS);
			}
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERROR_AGREGAR_OPERACION);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public boolean validarNuevaOperacion(int idUnidad, Date fechaOp) {
		List<Operacion> operacionesRepetidas = opdao.buscar(idUnidad, fechaOp, null);
		if (operacionesRepetidas.size() > 0) return false;
		else return true;
	}

	@Override
	public ObtenerOperacionResponse obtener(ObtenerOperacionRequest request) {
		ObtenerOperacionResponse response = new ObtenerOperacionResponse();
		
		try {
			Operacion operacion = opdao.buscar(request.getIdOperacion());
			response.setOperacionView(WrapperDomain.ViewMapper(operacion));
			response.setResultadoEjecucion(true);
		} catch(Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("No se pudo obtener la operacion");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerDetalleOperacionResponse obtenerDetalles(ObtenerDetalleOperacionRequest request) {
		ObtenerDetalleOperacionResponse response = new ObtenerDetalleOperacionResponse();
		
		try {
			List<OperacionDetalle> detalles = opdao.obtenerDetalles(request.getIdOperacion());
			response.setDetallesView(new ArrayList<OperacionDetalleView>(detalles.size()));
			for (OperacionDetalle detalle : detalles)
				response.getDetallesView().add(WrapperDomain.ViewMapper(detalle));
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener los detalles de la operacion");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerOperacionPorTarifaResponse obtenerOperacionesPorTarifa(
			ObtenerOperacionPorTarifaRequest request) {
		ObtenerOperacionPorTarifaResponse response = new ObtenerOperacionPorTarifaResponse();
		
		try {
			float totalRecaudacion = 0f;
			int cantidadCarros = 0;
			List<OperacionPorTarifa> operacionesPorTarifa = opdao.obtenerOpsPorTarifa(request.getIdOperacion());
			response.setOpsPorTarifaView(new ArrayList<OperacionPorTarifaView>());
			for (OperacionPorTarifa operacionPorTarifa : operacionesPorTarifa) {
				response.getOpsPorTarifaView().add(WrapperDomain.ViewMapper(operacionPorTarifa));
				totalRecaudacion += operacionPorTarifa.getMonto();
				cantidadCarros += operacionPorTarifa.getCantidadTickets();
			}
			response.setTotalCarros(cantidadCarros);
			response.setTotalRecaudacion(totalRecaudacion);
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las tarifas por operacion");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerIncidenciasResponse obtenerIncidencias(
			ObtenerIncidenciasRequest request) {
		ObtenerIncidenciasResponse response = new ObtenerIncidenciasResponse();
		
		try {
			List<Incidencia> incidencias = incidenciaDao.obtenerIncidencias(request.getIdOperacion());
			response.setIncidenciasView(new ArrayList<IncidenciaView>());
			for (Incidencia incidencia : incidencias) {
				response.getIncidenciasView().add(WrapperDomain.ViewMapper(incidencia));
			}
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las incidencias");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public AgregarIncidenciaResponse agregarIncidencia(AgregarIncidenciaRequest request) {
		AgregarIncidenciaResponse response = new AgregarIncidenciaResponse();
		
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(request.getFecha());
			calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
			
			Incidencia incidencia = new Incidencia();
			TipoIncidencia tipo = new TipoIncidencia();
			tipo.setDescripcion(request.getTipoIncidencia());
			tipo.setId(request.getIdTipo());
			
			incidencia.setDescripcion(request.getDescripcion());
			incidencia.setFechaIncidencia(calendar.getTime());
			incidencia.setIdOperacion(request.getIdOperacion());
			incidencia.setTipoIncidencia(tipo);
			
			int idNuevaIncidencia = incidenciaDao.agregar(incidencia);
			response.setIdIncidencia(idNuevaIncidencia);
			response.setMensaje("Se agregó la incidencia");
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al ingresar la incidencia");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerTipoIncidenciasResponse obtenerTipoIncidencias() {
		ObtenerTipoIncidenciasResponse response = new ObtenerTipoIncidenciasResponse();
		
		try {
			List<TipoIncidencia> tipos = incidenciaDao.obtenerTipos();
			response.setTiposView(new ArrayList<TipoIncidenciaView>());
			for (TipoIncidencia tipoIncidencia : tipos)
				response.getTiposView().add(WrapperDomain.ViewMapper(tipoIncidencia));
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al traer los tipos de incidencias");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public Response eliminarIncidencia(EliminarIncidenciaRequest request) {
		Response response = new Response();
		
		try {
			incidenciaDao.eliminar(request.getIdIncidencia());
			response.setResultadoEjecucion(true);
			response.setMensaje("Se eliminó la incidencia");
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al eliminar la incidencia");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public Response actualizarIncidencia(ActualizarIncidenciaRequest request) {
		Response response = new Response();
		
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(request.getHora());
			calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
			
			Incidencia incidencia = new Incidencia();
			TipoIncidencia tipo = new TipoIncidencia();
			tipo.setId(request.getIdTipo());
			
			incidencia.setDescripcion(request.getDescripcion());
			incidencia.setFechaIncidencia(calendar.getTime());
			incidencia.setId(request.getIdIncidencia());
			incidencia.setTipoIncidencia(tipo);
			
			incidenciaDao.actualizar(incidencia);
			response.setResultadoEjecucion(true);
			response.setMensaje("Se actualizó la incidencia");
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al actualizar la incidencia");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerIncidenciaResponse obtenerIncidencia(ObtenerIncidenciaRequest request) {
		ObtenerIncidenciaResponse response = new ObtenerIncidenciaResponse();
		
		try {
			Incidencia incidencia = incidenciaDao.buscar(request.getIdIncidencia());
			if (incidencia != null) {
				response.setIncidenciaView(WrapperDomain.ViewMapper(incidencia));
				response.setTipoView(WrapperDomain.ViewMapper(incidencia.getTipoIncidencia()));
				response.setResultadoEjecucion(true);
			} else {
				response.setResultadoEjecucion(false);
				response.setMensaje("No se encontró la incidencia. Probablemente fue eliminada.");
			}
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al actualizar la incidencia");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public String getConstanteEstadoEnProceso() {
		return ESTADO_EN_PROCESO;
	}

	@Override
	public String getConstanteEstadoPorAprobar() {
		return ESTADO_POR_APROBAR;
	}

	@Override
	public String getConstanteEstadoAprobada() {
		return ESTADO_APROBADA;
	}

	@Override
	public Response actualizarOperacion(ActualizarOperacionRequest request) {
		Response response = new Response();
		
		try {
			Operacion op = opdao.buscar(request.getIdOperacion());
		    op.setId(request.getIdOperacion());
		    op.setAjuste(request.getAjuste());
		    op.setCantidadPernoctadosFin(request.getPernoctadosHoy());
		    op.setCantidadPersonas(request.getTotalPersonas());
		    op.setCantidadVehiculosEntrada(request.getTotalIngresos());
		    op.setCantidadVehiculosSalida(request.getTotalSalidas());
		    op.setFechaActualizacion(new Date());
		    op.setNumeroTicketFin(request.getTicketFinal());
		    op.setNumeroTicketInicio(request.getTicketInicial());
		    op.setOferta(request.getOferta());
		    op.setUltimoModificador(request.getLoginModificador());
		    op.setCantidadPernoctadosInicio(request.getPernoctadosAyer());
		    
		    //Los raudos se sacan de las tarifas
		    for (OperacionPorTarifaView opTarifaView : request.getTarifas()) {
		    	OperacionPorTarifa opTarifa = new OperacionPorTarifa();
		    	opTarifa.setCantidadTickets(opTarifaView.getCantidad());
		    	opTarifa.setIdOperacionPorTarifa(opTarifaView.getIdOpTarifa());
		    	opTarifa.setMonto(opTarifaView.getMonto());
				if (opTarifaView.getDescripcion().toUpperCase().contains("RAUDO")) op.setCantidadRaudos(opTarifaView.getCantidad());
				
				opdao.actualizarOperacionPorTarifa(opTarifa);
			}
		    
		    for (OperacionDetalleView detalleView : request.getDetalles()) {;
		    	OperacionDetalle detalle = new OperacionDetalle();
		    	detalle.setIdOpDetalle(detalleView.getIdOperacionDetalle());
		    	detalle.setCantidadIngresos(detalleView.getCantidadIngreso());
		    	detalle.setCantidadPersonas(detalleView.getCantidadPersonas());
		    	detalle.setCantidadSalidas(detalleView.getCantidadSalida());
		    	
		    	opdao.actualizarOperacionDetalle(detalle);
		    }
		    
		    opdao.modificar(op);
		    response.setResultadoEjecucion(true);
		    response.setMensaje("Se actualizaron los datos de la operación");
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al actualizar la operación");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public Response enviarAprobarOperacion(EnviarAprobarOperacionRequest request) {
		Response response = new Response();
		String mensajeError = "";
		
		try {
			Operacion op = opdao.buscar(request.getIdOperacion());
			
			mensajeError = validarEnvioAprobacion(op);
			if (mensajeError != null) {
				response.setResultadoEjecucion(false);
			} else {
				op.setEstado(ESTADO_POR_APROBAR);
				opdao.modificar(op);
				response.setMensaje("Se envió la operación a aprobación");
				response.setResultadoEjecucion(true);
			}
			
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al enviar a aprobar la operación");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	private String validarEnvioAprobacion(Operacion op) {
		String error = null;
		
		if (!sonValidosVehiculosIngresantes(op))
			return "La diferencia entre la cantidad de tickets registrados y las entradas del día es mayor al 5%";
		if (!sonValidosRegistroTarifas(op))
			return "La cantidad de vehículos registrados en tarifas debe ser igual a la suma de los ingresantes y pernoctados iniciales menos los pernoctados del día";
		
		return error;
	} 
	
	private boolean sonValidosVehiculosIngresantes(Operacion op) {
		boolean valido = false;
		
		int totalVehiculosDia = op.getTotalIngresos();
		int diferenciaTickets = op.getNumeroTicketFin() - op.getNumeroTicketInicio();
		int diferencia = diferenciaTickets - totalVehiculosDia;
		float tolerancia = 0.05f;
		
		if (diferencia == 0) {
			valido = true;
		} else {
		    if (totalVehiculosDia > diferenciaTickets) {
		    	if ((diferenciaTickets/totalVehiculosDia) < tolerancia) valido = true;
		    } else
		    	if ((totalVehiculosDia/diferenciaTickets) < tolerancia) valido = true;
		}
		
		return valido;
	}
	
	private boolean sonValidosRegistroTarifas(Operacion op) {
		boolean valido = false;
		int registradosPorTarifas = 0;
		
		int comparativa = op.getCantidadVehiculosEntrada() + op.getCantidadPernoctadosInicio() - op.getCantidadPernoctadosFin();
		List<OperacionPorTarifa> opsTarifa = opdao.obtenerOpsPorTarifa(op.getId());
		for (OperacionPorTarifa opPorTarifa : opsTarifa) registradosPorTarifas += opPorTarifa.getCantidadTickets();
		
		if (comparativa == registradosPorTarifas) valido = true;
		
		return valido;
	}

	@Override
	public Response aprobarOperacion(AprobarOperacionRequest request) {
		Response response = new Response();
		String mensajeError = "";
		
		try {
			Operacion op = opdao.buscar(request.getIdOperacion());
			
			mensajeError = validarEnvioAprobacion(op);
			if (mensajeError != null) {
				response.setResultadoEjecucion(false);
			} else {
				op.setEstado(ESTADO_APROBADA);
				opdao.modificar(op);
				response.setMensaje("Se aprobó la operación");
				response.setResultadoEjecucion(true);
			}
			
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al aprobar la operación");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

}
