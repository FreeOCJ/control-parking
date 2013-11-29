package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.cp.core.domain.Usuario;

public class UsuarioMapper implements RowMapper<Usuario> {

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
		usuario.setId_cliente(rs.getInt("IDCLIENTE"));
		return usuario;
	}

}
