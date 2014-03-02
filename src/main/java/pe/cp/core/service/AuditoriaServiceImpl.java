package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.AuditoriaDao;
import pe.cp.core.domain.Auditoria;

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
	
	@Autowired
	private AuditoriaDao adao;
	
	@Override
	public int agregar(Auditoria auditoria) {
		return adao.agregar(auditoria);
	}

	@Override
	public List<Auditoria> buscar(String usuario, String tipoEvento,
			Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return null;
	}

}
