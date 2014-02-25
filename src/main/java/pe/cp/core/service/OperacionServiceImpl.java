package pe.cp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.IncidenciaDao;
import pe.cp.core.dao.OperacionDao;
import pe.cp.core.dao.RolDao;
import pe.cp.core.dao.TarifaDao;
import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.domain.Incidencia;
import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.OperacionDetalle;
import pe.cp.core.domain.OperacionPorTarifa;
import pe.cp.core.domain.Tarifa;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionRequest;
import pe.cp.core.service.messages.ObtenerDetalleOperacionResponse;
import pe.cp.core.service.messages.ObtenerIncidenciasRequest;
import pe.cp.core.service.messages.ObtenerIncidenciasResponse;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaRequest;
import pe.cp.core.service.messages.ObtenerOperacionPorTarifaResponse;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.domain.IncidenciaView;
import pe.cp.core.service.domain.OperacionDetalleView;
import pe.cp.core.service.domain.OperacionPorTarifaView;
import pe.cp.core.service.domain.OperacionView;
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
					Operacion op = new Operacion();
					op.setCreador(request.getLogin());
					op.setEstado("EN PROCESO");
					op.setFechaCreacion(new Date());
					op.setFechaActualizacion(new Date());
					op.setFechaTransaccion(request.getFechaOperacion());
					op.setIdUnidadOperativa(request.getIdUnidadOperativa());
					op.setOferta("");
					op.setUltimoModificador(request.getLogin());
					
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

}
