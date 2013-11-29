package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Usuario;

public interface UsuarioDao {
	void actualizar(Usuario usuario);
	int agregar(Usuario usuario);
	void eliminar(int id);
	List<Usuario> buscar(String nombre);
	Usuario buscar(int idUsuario);
	Usuario buscarPorLogin(String login);
	
}
