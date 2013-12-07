package pe.cp.core.service.domain;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Rol;
import pe.cp.core.domain.Usuario;

public class WrapperDomain {
	
	public static UsuarioView ViewMapper(Usuario usuario){
		UsuarioView usuarioview = new UsuarioView();
		usuarioview.setId(usuario.getId());
		usuarioview.setNombres(usuario.getNombres());
		usuarioview.setApellidos(usuario.getApellidos());
		usuarioview.setCargo(usuario.getCargo());
		usuarioview.setRoles(usuario.getRoles());
		usuarioview.setEmail(usuario.getEmail());
		usuarioview.setLogin(usuario.getLogin());
		usuarioview.setPassword(usuario.getPassword());
		if (usuario.getCliente() != null)
			usuarioview.setCliente(usuario.getCliente());
		else
			usuarioview.setCliente(new Cliente());
		return usuarioview;
	}
	
	public static RolView ViewMapper(Rol rol){
		RolView rolView = new RolView();
		rolView.setId(rol.getId());
		rolView.setNombre(rol.getDescripcion());
		
		return rolView;
	}
}
