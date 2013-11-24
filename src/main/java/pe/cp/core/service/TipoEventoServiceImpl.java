package pe.cp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.TipoEventoDAO;
import pe.cp.core.domain.TipoEvento;

@Service
public class TipoEventoServiceImpl implements TipoEventoService {

	@Autowired
	private TipoEventoDAO tipoEventoDAO;
	
	@Override
	public int agregar(TipoEvento tipoEvento) {
		return tipoEventoDAO.agregar(tipoEvento);
	}
}
