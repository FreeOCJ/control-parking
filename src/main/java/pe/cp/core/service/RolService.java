package pe.cp.core.service;

import java.util.List;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.domain.RolView;

public interface RolService {
	int agregar(Rol rol);
	void actualizar(Rol rol);
	void eliminar(int idRol);
	List<RolView> obtenerTodos();
}
