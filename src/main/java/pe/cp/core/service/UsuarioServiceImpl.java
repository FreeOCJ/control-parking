package pe.cp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.UsuarioDao;
import pe.cp.core.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioDao usuariodao;
	
	
	@Override
	public void actualizar(Usuario usuario) {
		usuariodao.actualizar(usuario);		
	}

	@Override
	public int agregar(Usuario usuario) {
		return usuariodao.agregar(usuario);
	}

	@Override
	public void eliminar(int id) {
		usuariodao.eliminar(id);		
	}

	@Override
	public List<Usuario> buscar(String nombre) {
		return usuariodao.buscar(nombre);
	}

	@Override
	public Usuario buscar(int idUsuario) {
		return usuariodao.buscar(idUsuario);
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		return usuariodao.buscarPorLogin(login);
	}

	

}
