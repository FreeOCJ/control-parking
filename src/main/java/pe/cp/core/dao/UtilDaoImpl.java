package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UtilDaoImpl implements UtilDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<String> obtenerDepartamentos() {
		final String sql = "SELECT * FROM ubigeo WHERE IDPROV = 1 and IDDIST = 1";		
		List<String> departamentos = null;		
		SqlParameterSource args = new MapSqlParameterSource();
		departamentos = namedParameterJdbcTemplate.query(sql, args, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("DEPARTAMENTO");				
			}
			
		} );
	
		return departamentos;
	}

	@Override
	public List<String> obtenerProvincias(String departamento) {
		final String sql = "SELECT * FROM ubigeo WHERE DEPARTAMENTO = :dpto and IDDIST = 1";		
		List<String> provincias = null;		
		SqlParameterSource args = new MapSqlParameterSource("dpto",departamento);
		provincias = namedParameterJdbcTemplate.query(sql, args, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PROVINCIA");				
			}
			
		} );
	
		return provincias;
	}

	@Override
	public List<String> obtenerDistritos(String departamento, String provincia) {
		final String sql = "SELECT * FROM ubigeo WHERE DEPARTAMENTO = :dpto and PROVINCIA = :prov and IDDIST > 1";		
		List<String> distritos = null;	
		
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("dpto", departamento);
		namedParameters.put("prov", provincia);				
		
		distritos = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("DISTRITO");				
			}			
		} );
	
		return distritos;
	}

}
