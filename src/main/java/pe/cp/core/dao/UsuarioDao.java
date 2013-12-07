package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Rol;
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;

public interface UsuarioDao {
	void actualizar(Usuario usuario);
	int agregar(Usuario usuario);
	void eliminar(int id);
	List<Usuario> buscar(String nombre);
	List<Usuario> buscarOr(UsuarioFilter filtro);
	Usuario buscar(int idUsuario);
	Usuario buscarPorLogin(String login);
	void agregarRol(int idUsuario, int idRol);
	void removerTodosRoles(int idUsuario);
	List<Rol> obtenerRoles(int idUsuario);
}
