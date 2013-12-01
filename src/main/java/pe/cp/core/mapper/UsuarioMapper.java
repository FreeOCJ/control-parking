package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Usuario;

@Component
public class UsuarioMapper implements RowMapper<Usuario> {

	@Autowired
	private ClienteDao clienteDao;
		
	@Override
	public Usuario mapRow(ResultSet rs, int n) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("IDUSUARIO"));
		usuario.setEmail(rs.getString("EMAIL"));
		usuario.setNombres(rs.getString("NOMBRES"));
		usuario.setApellidos(rs.getString("APELLIDOS"));
		usuario.setCargo(rs.getString("CARGO"));
		usuario.setLogin(rs.getString("LOGIN"));
		usuario.setPassword(rs.getString("PASSWORD"));
		
		Cliente cliente = clienteDao.buscar(rs.getInt("IDCLIENTE"));
		usuario.setCliente(cliente);
		return usuario;
	}

}
