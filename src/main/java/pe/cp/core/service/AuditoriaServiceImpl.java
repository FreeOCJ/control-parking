package pe.cp.core.service;

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

}
