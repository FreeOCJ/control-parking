package pe.cp.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.RolDao;
import pe.cp.core.domain.Rol;
import pe.cp.core.service.domain.RolView;
import pe.cp.core.service.domain.WrapperDomain;

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

	@Override
	public List<RolView> obtenerTodos() {
		List<Rol> roles = rdao.obtenerTodos();
		List<RolView> rolesView = new ArrayList<RolView>(roles.size());
		
		for (Rol rol : roles) {
			rolesView.add(WrapperDomain.ViewMapper(rol));
		}
		
		return rolesView;
	}

}
