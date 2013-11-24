package pe.cp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.TipoEventoDao;
import pe.cp.core.domain.TipoEvento;

@Service
public class TipoEventoServiceImpl implements TipoEventoService {

	@Autowired
	private TipoEventoDao tipoEventoDAO;
	
	@Override
	public int agregar(TipoEvento tipoEvento) {
		return tipoEventoDAO.agregar(tipoEvento);
	}
}
