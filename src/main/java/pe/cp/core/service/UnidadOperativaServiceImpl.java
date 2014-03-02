package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.dao.TarifaDao;
import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Tarifa;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.service.domain.TarifaConsolidadoView;
import pe.cp.core.service.domain.TarifaView;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.ActualizarUnidadOpRequest;
import pe.cp.core.service.messages.ActualizarUnidadOpResponse;
import pe.cp.core.service.messages.AgregarTarifaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.AgregarUsuarioUnidadOperativaRequest;
import pe.cp.core.service.messages.EliminarUnidadOpRequest;
import pe.cp.core.service.messages.ObtenerConsTarifasRequest;
import pe.cp.core.service.messages.ObtenerConsTarifasResponse;
import pe.cp.core.service.messages.ObtenerTarifasRequest;
import pe.cp.core.service.messages.ObtenerTarifasResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarRequest;
import pe.cp.core.service.messages.ObtenerUnidadesOpProcesarResponse;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;
import pe.cp.core.service.messages.RemoverTarifaRequest;
import pe.cp.core.service.messages.Response;

@Service
public class UnidadOperativaServiceImpl implements UnidadOperativaService {

	private final String REGULAR = "REGULAR";
	private final String RAUDOS = "RAUDOS";
	private final String PERNOCTADOS = "PERNOCTADOS";
	private final String EXITO_TARIFA_ELIMINADA = "La tarifa fue retirada exitosamente";
	private final String ERR_ELIMINAR_TARIFA = "Error al eliminar la tarifa";
	private final String ERR_TARIFA_DEFAULT = "No se pueden eliminar las tarifas RAUDOS, REGULAR o PERNOCTADOS";
	private final String ERR_ELIMINAR_UNIDAD = "Error al eliminar la unidad operativa";
	private final String EXITO_ELIMINAR_UNIDAD = "Se eliminó la unidad operativa";
	
	@Autowired
	private UnidadOperativaDao unidadOpDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private TarifaDao tarifaDao;
	
	@Override
	public AgregarUnidadOperativaResponse agregar(AgregarUnidadOperativaRequest request) {
		AgregarUnidadOperativaResponse response = new AgregarUnidadOperativaResponse();
		
		UnidadOperativa unidadOp = new UnidadOperativa();
		unidadOp.setNombre(request.getNombre());
		unidadOp.setDepartamento(request.getDepartamento());
		unidadOp.setDireccion(request.getDireccion());
		unidadOp.setDistrito(request.getDistrito());
		unidadOp.setHoraInicio(request.getHoraInicio());
		unidadOp.setHoraFin(request.getHoraCierre());
		unidadOp.setNumeroCajones(request.getNroCajones());
		unidadOp.setProvincia(request.getProvincia());
		unidadOp.setCliente(clienteDao.buscar(request.getIdCliente()));		
		
		Integer idUnidadOperativa = unidadOpDao.agregar(unidadOp);
		if (idUnidadOperativa != null && idUnidadOperativa > 0){
			//Cada nueva unidad operativa debe contar con tarifas por defecto: REGULAR, RAUDOS, PERNOCTADOS
			agregarTarifasDefecto(idUnidadOperativa);
			
			response.setIdUnidadOperativa(idUnidadOperativa);
			response.setResultadoEjecucion(true);
			response.setMensaje("Se agregó la unidad operativa exitosamente");
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("No se pudo agregar la unidad operativa");
		}
		
		return response;
	}
	
	private void agregarTarifasDefecto(int idUnidadOp) {
		double montoDefecto = 0.0;
		String [] categoriasDefecto = { REGULAR, RAUDOS, PERNOCTADOS}; 
		
		for (String categoria : categoriasDefecto) {
			Tarifa tarifa = new Tarifa();
			tarifa.setCategoria(categoria);
			tarifa.setIdUnidadOperativa(idUnidadOp);
			tarifa.setMonto(montoDefecto);
			
			tarifaDao.agregar(tarifa);
		}
	}

	/***
	 * 
	 * Permite actualizar los datos principales de la unidad operativa. No se actualiza ninguna
	 * tabla relacionada.
	 */
	@Override
	public ActualizarUnidadOpResponse actualizar(ActualizarUnidadOpRequest request) {
		ActualizarUnidadOpResponse response = new ActualizarUnidadOpResponse();
		
		UnidadOperativa unidadOp = new UnidadOperativa();
		unidadOp.setId(request.getIdUnidadOperativa());
		unidadOp.setNombre(request.getNombre());
		unidadOp.setDepartamento(request.getDepartamento());
		unidadOp.setProvincia(request.getProvincia());
		unidadOp.setDistrito(request.getDistrito());
		unidadOp.setDireccion(request.getDireccion());
		unidadOp.setNumeroCajones(request.getNroCajones());
		unidadOp.setHoraInicio(request.getHoraInicio());
		unidadOp.setHoraFin(request.getHoraCierre());
		
		try{
			unidadOpDao.actualizar(unidadOp);
			response.setResultadoEjecucion(true);
			response.setMensaje("Se actualizó la unidad operativa satisfactoriamente");
		}catch (Exception e){
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			response.setResultadoEjecucion(false);
			response.setMensaje("Ocurrió un error al actualizar la unidad operativa");
		}
		
		return response;
	}

	@Override
	public ObtenerUnidadOperativaResponse obtenerPorId(ObtenerUnidadOperativaRequest request) {
		ObtenerUnidadOperativaResponse response = new ObtenerUnidadOperativaResponse();		
		UnidadOperativa unidadOp = unidadOpDao.buscar(request.getIdUnidadOperativa());
		
		if (unidadOp != null){
			response.setUnidadOpView(WrapperDomain.ViewMapper(unidadOp));
			//Creo que se debe cargar de manera lazy
			/*List<Usuario> usuarios = usuarioDao.obtenerUsuariosPorUnidadOperativa(request.getIdUnidadOperativa());
			
			response.setUsuariosUnidadOpView(new ArrayList<UsuarioView>());
			for (Usuario usuario : usuarios) {
				response.getUsuariosUnidadOpView().add(WrapperDomain.ViewMapper(usuario));
			}*/
			
			response.setResultadoEjecucion(true);
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("No se pudo insertar la unidad operativa");
		}
		
		return response;
	}

	@Override
	public ObtenerUnidadpOpPorClienteResponse obtenerUnidadesPorCliente(
			ObtenerUnidadOpPorClienteRequest request) {
		ObtenerUnidadpOpPorClienteResponse response = new ObtenerUnidadpOpPorClienteResponse();
		List<UnidadOperativa> unidades = unidadOpDao.obtenerPorCliente(request.getIdCliente());
		
		response.setUnidadesOpView(new ArrayList<UnidadOperativaView>());
		response.setResultadoEjecucion(true);
		for (UnidadOperativa unidadOperativa : unidades) {
			response.getUnidadesOpView().add(WrapperDomain.ViewMapper(unidadOperativa));
		}
		
		return response;
	}

	@Override
	public Response agregarUsuario(AgregarUsuarioUnidadOperativaRequest request) {
		Response response = new Response();
		
		try{
			unidadOpDao.agregarUsuariosUnidadOp(request.getIdUnidadOperativa(), request.getIdsUsuarios(), request.getRol());
			response.setResultadoEjecucion(true);
			response.setMensaje("Los usuarios fueron actualizados");
		}catch (Exception e){
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al actualizar los usuarios de la unidad operativa");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerTarifasResponse obtenerTarifas(ObtenerTarifasRequest request) {
		ObtenerTarifasResponse response = new ObtenerTarifasResponse();
		
		try {
			List<Tarifa> tarifas = tarifaDao.obtenerTarifas(request.getIdUnidadOperativa(), request.getCategoriaTarifa());
			response.setTarifasView(new ArrayList<TarifaView>());
			response.setResultadoEjecucion(true);
			
			for (Tarifa tarifa : tarifas) {
				response.getTarifasView().add(WrapperDomain.ViewMapper(tarifa));
			}
		} catch(Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las tarifas de la unidad operativa");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public Response agregarTarifa(AgregarTarifaRequest request) {
		Response response = new Response();
		
		try {
			tarifaDao.eliminarPorCategoria(request.getIdUnidadOperativa(), request.getCategoria());
			for (double monto : request.getMontos()) {
				UnidadOperativa unidadOp = new UnidadOperativa();
				unidadOp.setId(request.getIdUnidadOperativa());
				
				Tarifa tarifa = new Tarifa();
				tarifa.setCategoria(request.getCategoria());
				tarifa.setMonto(monto);
				tarifa.setIdUnidadOperativa(request.getIdUnidadOperativa());
				tarifaDao.agregar(tarifa);
			}
			response.setMensaje("Tarifas actualizadas en la unidad operativa");
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al actualizar las tarifas de la unidad operativa");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerConsTarifasResponse obtenerTarifas(ObtenerConsTarifasRequest request) {
		ObtenerConsTarifasResponse response = new ObtenerConsTarifasResponse();
		
		try {
			List<String> categorias = tarifaDao.obtenerCategorias(request.getIdUnidadOperativa());
			response.setTarifas(new ArrayList<TarifaConsolidadoView>());
			
			for (String categoria : categorias) {
				TarifaConsolidadoView tarifaCons = new TarifaConsolidadoView();
				List<Tarifa> tarifasPorCat = tarifaDao.obtenerTarifas(request.getIdUnidadOperativa(), categoria);	
				
				tarifaCons.setCategoria(categoria);
				StringBuilder sb = new StringBuilder();
				for (Tarifa tarifa : tarifasPorCat) {
					sb.append(String.valueOf(tarifa.getMonto()));
					sb.append(" ");
				}
				tarifaCons.setMontos(sb.toString());
				response.getTarifas().add(tarifaCons);
			}
			
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las tarifas");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ObtenerUnidadesOpProcesarResponse obtenerParaProcesar(
			ObtenerUnidadesOpProcesarRequest request) {
		ObtenerUnidadesOpProcesarResponse response = new ObtenerUnidadesOpProcesarResponse();
		
		try {
			List<UnidadOperativa> unidades = 
					unidadOpDao.obtenerParaProcesar(request.getLogin(), request.getIsParaOperador(), request.getIsParaAprobador());
			response.setUnidadesOpViews(new ArrayList<UnidadOperativaView>());
			
			for (UnidadOperativa unidadOperativa : unidades)
				response.getUnidadesOpViews().add(WrapperDomain.ViewMapper(unidadOperativa));
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje("Error al obtener las unidades operativas a procesar");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	@Override
	public Response removerTarifa(RemoverTarifaRequest request) {
		Response response = new Response();
		    
		try {
			if (request.getCategoria().equals(RAUDOS) || request.getCategoria().equals(PERNOCTADOS) ||
					request.getCategoria().equals(REGULAR)) {
				response.setResultadoEjecucion(false);
				response.setMensaje(ERR_TARIFA_DEFAULT);
			} else {
				tarifaDao.eliminarPorCategoria(request.getIdUnidadOp(), request.getCategoria());
				response.setResultadoEjecucion(true);
				response.setMensaje(EXITO_TARIFA_ELIMINADA);	
			}
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_ELIMINAR_TARIFA);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public String getConstanteRaudos() {
		return RAUDOS;
	}

	@Override
	public String getConstantePernoctados() {
		return PERNOCTADOS;
	}

	@Override
	public String getConstanteRegular() {
		return REGULAR;
	}
	
	@Override
	public Response eliminarUnidadOperativa(EliminarUnidadOpRequest request) {
		Response response = new Response();
		
		try {
			unidadOpDao.eliminar(request.getIdUnidadOperativa());
			response.setResultadoEjecucion(true);
			response.setMensaje(EXITO_ELIMINAR_UNIDAD);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_ELIMINAR_UNIDAD);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

}
