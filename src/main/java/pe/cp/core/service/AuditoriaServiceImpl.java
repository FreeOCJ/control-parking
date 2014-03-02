package pe.cp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.AuditoriaDao;
import pe.cp.core.domain.Auditoria;
import pe.cp.core.service.domain.AuditoriaView;
import pe.cp.core.service.domain.WrapperDomain;
import pe.cp.core.service.messages.BuscarAuditoriaRequest;
import pe.cp.core.service.messages.BuscarAuditoriaResponse;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

	public static final String INSERTAR_USUARIO = "INSERTAR USUARIO";
	public static final String MODIFICAR_USUARIO = "MODIFICAR USUARIO";
	public static final String ELIMINAR_USUARIO = "ELIMINAR USUARIO";
	public static final String INSERTAR_CLIENTE = "INSERTAR CLIENTE";
	public static final String MODIFICAR_CLIENTE = "MODIFICAR CLIENTE";
	public static final String ELIMINAR_CLIENTE = "ELIMINAR CLIENTE";
	public static final String INSERTAR_OPERACION = "INSERTAR OPERACION";
	public static final String MODIFICAR_OPERACION = "MODIFICAR OPERACION";
	public static final String ELIMINAR_OPERACION = "ELIMINAR OPERACION";
	public static final String INSERTAR_UNIDAD_OP = "INSERTAR UNIDAD OPERATIVA";
	public static final String MODIFICAR_UNIDAD_OP = "MODIFICAR UNIDAD OPERATIVA";
	public static final String ELIMINAR_UNIDAD_OP = "ELIMINAR UNIDAD OPERATIVA";
	
	private final String ERR_BUSCAR_AUDIT = "Error al buscar los archivos auditables";
	
	@Autowired
	private AuditoriaDao adao;
	
	@Override
	public int agregar(Auditoria auditoria) {
		return adao.agregar(auditoria);
	}

	@Override
	public BuscarAuditoriaResponse buscar(BuscarAuditoriaRequest request) {
		BuscarAuditoriaResponse response = new BuscarAuditoriaResponse();
		
		try {
			List<Auditoria> registros = adao.buscar(request.getTipoEvento(), request.getFechaInicio(), 
					request.getFechaFin(), request.getLogin());
			response.setAuditViews(new ArrayList<AuditoriaView>());
			
			for (Auditoria auditoria : registros)
				response.getAuditViews().add(WrapperDomain.ViewMapper(auditoria));
			response.setResultadoEjecucion(true);
		} catch (Exception e) {
			response.setResultadoEjecucion(false);
			response.setMensaje(ERR_BUSCAR_AUDIT);
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

}
