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

import pe.cp.core.domain.Tarifa;

@Repository
public class TarifaDaoImpl implements TarifaDao {
	private SimpleJdbcInsert insertarTarifa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private UnidadOperativaDao uoDao;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarTarifa = new SimpleJdbcInsert(dataSource)
				.withTableName("tarifa")
				.usingGeneratedKeyColumns("IDTARIFA");
	}
	
	@Override
	public int agregar(Tarifa tarifa) {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("MONTOTARIFA", tarifa.getMonto());
		parameters.put("CATEGORIA", tarifa.getCategoria());
		parameters.put("ELIMINADO", 'F');
		Number key = insertarTarifa.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Tarifa tarifa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idTarifa) {
		// TODO Auto-generated method stub

	}

	@Override
	public Tarifa buscar(int idTarifa) {
		final String sql = "select * from tarifa where IDTARIFA = :idTarifa";
		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"idTarifa", idTarifa);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<Tarifa>() {
					public Tarifa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Tarifa tarifa = new Tarifa();
						tarifa.setId(rs.getInt("IDTARIFA"));
						tarifa.setMonto(rs.getDouble("MONTOTARIFA"));	
						tarifa.setCategoria(rs.getString("CATEGORIA"));
						tarifa.setUnidadOperativa(uoDao.buscar(rs.getInt("IDUNIDADOP")));
						tarifa.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
						return tarifa;
					}
				});
	}

}