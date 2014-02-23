package pe.cp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.OperacionDao;
import pe.cp.core.dao.RolDao;
import pe.cp.core.domain.Operacion;
import pe.cp.core.service.messages.AgregarOperacionRequest;
import pe.cp.core.service.messages.AgregarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionResponse;
import pe.cp.core.service.messages.BuscarOperacionesRequest;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.domain.WrapperDomain;

@Service
public class OperacionServiceImpl implements OperacionService {
	
	@Autowired
	private OperacionDao opdao;
	
	private final String ERROR_VALIDAR_NUEVA_OPERACION = "Ya existe una operacion para la unidad operativa en la fecha indicada";
	private final String ERROR_PARAMS_INCOMPLETOS = "Los parametros enviados no estan completos";
	private final String ERROR_AGREGAR_OPERACION = "Error al agregar la operacion";
	private final String ERROR_INSERT_OPERACION = "Error al insertar la operacion";
	private final String EXITO_AGREGAR_OPERACION = "Se agrego la operacion";
	
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
			if (request.getIdUnidadOperativa() > 0 && request.getFechaOperacion() != null 
					&& request.getLogin() != null && !request.getLogin().isEmpty()) {
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

}
