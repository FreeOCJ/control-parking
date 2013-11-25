package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Usuario;

public interface UsuarioDao {
	void actualizar(Usuario usuario);
	boolean existeUsuario(String login);
	int agregar(Usuario usuario);
	List<Usuario> buscar(String nombre);
	void eliminar(int id);
}
