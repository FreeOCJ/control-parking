package pe.cp.core.service.domain;

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Rol;
import pe.cp.core.domain.UnidadOperativa;
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
		usuarioview.setNombreCompleto(usuario.getNombres() + " " + usuario.getApellidos());
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
	
	public static ClienteView ViewMapper(Cliente cliente){
		ClienteView clienteView = new ClienteView();
		clienteView.setId(cliente.getId());
		clienteView.setNombreComercial(cliente.getNombreComercial());
		clienteView.setRazonSocial(cliente.getRazonSocial());
		clienteView.setRuc(cliente.getRuc());
		
		return clienteView;
	}
	
	public static UnidadOperativaView ViewMapper(UnidadOperativa unidadOp){
		UnidadOperativaView unidadOpView = new UnidadOperativaView();
		unidadOpView.setDepartamento(unidadOp.getDepartamento());
		unidadOpView.setDireccion(unidadOp.getDireccion());
		unidadOpView.setDistrito(unidadOp.getDistrito());
		unidadOpView.setHoraApertura(unidadOp.getHoraInicio());
		unidadOpView.setHoraCierre(unidadOp.getHoraFin());
		unidadOpView.setNombre(unidadOp.getNombre());
		unidadOpView.setNroCajones(unidadOp.getNumeroCajones());
		unidadOpView.setProvincia(unidadOp.getProvincia());
		unidadOpView.setId(unidadOp.getId());
		
		return unidadOpView;
	}
}
