package pe.cp.core.dao;

import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

public interface IUsuarioDao {
	public Respuesta actualizar(Usuario usuario);
	public Respuesta existeUsuario(String login);
	public Respuesta nuevo(Usuario usuario);
	public Respuesta buscar(String nombre);
	public Respuesta eliminar(Integer id);
}
