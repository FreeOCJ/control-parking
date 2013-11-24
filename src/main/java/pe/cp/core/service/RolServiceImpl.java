package pe.cp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.RolDao;
import pe.cp.core.domain.Rol;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolDao rdao;
	
	@Override
	public int agregar(Rol rol) {
		return rdao.agregar(rol);
	}

	@Override
	public void actualizar(Rol rol) {
		rdao.actualizar(rol);		
	}

	@Override
	public void eliminar(int idRol) {
		rdao.eliminar(idRol);
	}

}
