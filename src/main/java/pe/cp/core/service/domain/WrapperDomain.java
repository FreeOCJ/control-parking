package pe.cp.core.service.domain;

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
		usuarioview.setCliente(usuario.getCliente());
		return usuarioview;
	}
}
