package pe.cp.core.service;

import pe.cp.core.domain.Rol;

public interface RolService {
	int agregar(Rol rol);
	void actualizar(Rol rol);
	void eliminar(int idRol);
}
