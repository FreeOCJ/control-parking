package pe.cp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.IUsuarioDao;
import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioDao usuariodao;
	
	@Override
	public Respuesta nuevo(Usuario usuario) {		
		return usuariodao.nuevo(usuario);
	}

	

}
