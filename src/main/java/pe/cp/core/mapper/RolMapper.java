package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.cp.core.domain.Rol;

public class RolMapper implements RowMapper<Rol> {

	@Override
	public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
		Rol rol = new Rol();
		rol.setDescripcion(rs.getString("DESROL"));
		rol.setId(rs.getInt("IDROL"));	
		rol.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
		return rol;
	}
}
