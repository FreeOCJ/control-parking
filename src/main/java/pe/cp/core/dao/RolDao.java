package pe.cp.core.dao;

import pe.cp.core.domain.Rol;

public interface RolDao {
	int agregar(Rol rol);
	void actualizar(Rol rol);
	void eliminar(int idRol);
	Rol buscar(int idRol);
}
