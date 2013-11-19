package pe.cp.core.service;

import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

public interface IUsuarioService {
	public Respuesta nuevo(Usuario usuario);
}
