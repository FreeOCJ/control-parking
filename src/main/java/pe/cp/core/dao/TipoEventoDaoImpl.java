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

import pe.cp.core.domain.TipoEvento;

@Repository
public class TipoEventoDaoImpl implements TipoEventoDao {
	private SimpleJdbcInsert insertarTipoEvento;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarTipoEvento = new SimpleJdbcInsert(dataSource)
				.withTableName("tipoevento")
				.usingGeneratedKeyColumns("IDTIPOEVENTO");
	}
	
	@Override
	public int agregar(final TipoEvento tipoevento) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("DESTIPOEVENTO", tipoevento.getDescripcion());		
		Number key = insertarTipoEvento.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void eliminar(int idTipoEvento) {		
		jdbcTemplate.update("DELETE from tipoevento where IDTIPOEVENTO= ?",idTipoEvento);
	}	

	@Override
	public void actualizar(TipoEvento tipoevento) {
		jdbcTemplate.update("update tipoevento set DESTIPOEVENTO = ? where IDTIPOEVENTO = ?",
				tipoevento.getDescripcion(), tipoevento.getId());		
	}

	@Override
	public TipoEvento buscar(int idTipoEvento) {
		final String sql = "select * from tipoevento where IDTIPOEVENTO = :idTipoEvento";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idTipoEvento", idTipoEvento);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<TipoEvento>() {
					public TipoEvento mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TipoEvento tipoEvento = new TipoEvento();
						tipoEvento.setId(rs.getInt("IDTIPOEVENTO"));
						tipoEvento.setDescripcion(rs.getString("DESTIPOEVENTO"));
						tipoEvento.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
												
						return tipoEvento;
					}
				});
	}
}
