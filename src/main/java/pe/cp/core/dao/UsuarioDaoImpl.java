package pe.cp.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

@Repository
public class UsuarioDaoImpl implements IUsuarioDao{

	
	
	@Autowired
	public UsuarioDaoImpl(){
		
	}
	
	@Override
	public Respuesta actualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta existeUsuario(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta nuevo(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta buscar(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta eliminar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
