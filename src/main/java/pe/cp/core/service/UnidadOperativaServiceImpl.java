package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.domain.Usuario;
import pe.cp.core.service.domain.UnidadOperativaView;
import pe.cp.core.service.domain.UsuarioView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.AgregarUnidadOperativaRequest;
import pe.cp.core.service.messages.AgregarUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadOpPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.ObtenerUnidadpOpPorClienteResponse;

@Service
public class UnidadOperativaServiceImpl implements UnidadOperativaService {

	@Autowired
	private UnidadOperativaDao unidadOpDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
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
			response.setIdUnidadOperativa(idUnidadOperativa);
			response.setResultadoEjecucion(true);
		}else{
			response.setResultadoEjecucion(false);
			response.setMensaje("No se pudo agregar la unidad operativa");
		}
		
		return response;
	}

	@Override
	public void actualizar(UnidadOperativa unidadOp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idUnidadOperativa) {
		// TODO Auto-generated method stub

	}

	@Override
	public int agregarTarifa(int idUnidadOp, int idTarifa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerTarifa(int idUnidadOperativa, int idTarifa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarOperador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerOperador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarAprobador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerAprobador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarUsuarioCliente(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerUsuarioCliente(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ObtenerUnidadOperativaResponse obtenerPorId(ObtenerUnidadOperativaRequest request) {
		ObtenerUnidadOperativaResponse response = new ObtenerUnidadOperativaResponse();		
		UnidadOperativa unidadOp = unidadOpDao.buscar(request.getIdUnidadOperativa());
		
		if (unidadOp != null){
			response.setUnidadOpView(WrapperDomain.ViewMapper(unidadOp));
			List<Usuario> usuarios = usuarioDao.obtenerUsuariosCliente(request.getIdUnidadOperativa());
			
			response.setUsuariosUnidadOpView(new ArrayList<UsuarioView>());
			for (Usuario usuario : usuarios) {
				response.getUsuariosUnidadOpView().add(WrapperDomain.ViewMapper(usuario));
			}
			
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

}
