package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

import pe.cp.core.domain.TipoIncidencia;

@Repository
public class TipoIncidenciaDaoImpl implements TipoIncidenciaDao {
	private SimpleJdbcInsert insertarTipoIncidencia;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarTipoIncidencia = new SimpleJdbcInsert(dataSource)
				.withTableName("tipoincidencia")
				.usingGeneratedKeyColumns("IDTIPOINC");
	}
	
	@Override
	public int agregar(TipoIncidencia tipoIncidencia) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("DESCTIPOINC", tipoIncidencia.getDescripcion());
		parameters.put("ELIMINADO", 'F');
		Number key = insertarTipoIncidencia.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void modificar(TipoIncidencia tipoIncidencia) {
		jdbcTemplate.update("update tipoincidencia set DESTIPOINC = ? where IDTIPOINC = ?",
				tipoIncidencia.getDescripcion(), tipoIncidencia.getId());
	}

	@Override
	public void eliminar(int idTipoIncidencia) {
		jdbcTemplate.update("DELETE from tipoincidencia where IDTIPOINC= ?",idTipoIncidencia);
	}

	@Override
	public TipoIncidencia buscar(int idTipoIncidencia) {
		final String sql = "select * from tipoincidencia where IDTIPOINC = :idTipoIncidencia";
		SqlParameterSource namedParameters = new MapSqlParameterSource(
														"idTipoIncidencia", idTipoIncidencia);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<TipoIncidencia>() {
					public TipoIncidencia mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TipoIncidencia tipoIncidencia = new TipoIncidencia();
						tipoIncidencia.setId(rs.getInt("IDTIPOINC"));
						tipoIncidencia.setDescripcion(rs.getString("DESCTIPOINC"));												
						tipoIncidencia.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
						return tipoIncidencia;
					}
				});
	}

}
