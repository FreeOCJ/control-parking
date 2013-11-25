package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.AuditoriaDao;
import pe.cp.core.domain.Auditoria;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

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
