package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Rol;

@Repository
public class RolDaoImpl implements RolDao {
	private SimpleJdbcInsert insertarRol;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarRol = new SimpleJdbcInsert(dataSource)
				.withTableName("rol")
				.usingGeneratedKeyColumns("IDROL");
	}
	
	@Override
	public int agregar(Rol rol) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("DESROL", rol.getDescripcion());
		parameters.put("ELIMINADO", 'F');	
		Number key = insertarRol.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Rol rol) {
		jdbcTemplate.update("update rol set DESROL = ? where IDROL = ?",
				rol.getDescripcion(), rol.getId());		
	}

	@Override
	public void eliminar(int idRol) {
		jdbcTemplate.update("UPDATE rol SET ELIMINADO='T' WHERE IDROL = ?",idRol);
	}

	@Override
	public Rol buscar(int idRol) {
		final String sql = "select * from rol where IDROL = :idRol";
		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"idRol", idRol);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<Rol>() {
					public Rol mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Rol rol = new Rol();
						rol.setDescripcion(rs.getString("DESROL"));
						rol.setId(rs.getInt("IDROL"));	
						rol.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
						return rol;
					}
				});
	}

	@Override
	public List<Rol> obtenerTodos() {
		final String sql = "SELECT * FROM rol WHERE ELIMINADO = 'F'";			
		List<Rol> roles = null;
		
		Map<String, Object> args = new HashMap<String, Object>();		
		roles = namedParameterJdbcTemplate.query(sql, args, new RowMapper<Rol>(){
			@Override
			public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rolMapRow(rs, rowNum);
			}
			
		} );
		return roles;
	}
	
	public Rol rolMapRow(ResultSet rs, int n) throws SQLException {
		Rol rol = new Rol();
		rol.setId(rs.getInt("IDROL"));
		rol.setDescripcion(rs.getString("DESROL"));
		rol.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);				
		
		return rol;
	}

}
