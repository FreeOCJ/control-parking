package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Tarifa;
import pe.cp.core.mapper.TarifaMapper;

@Repository
public class TarifaDaoImpl implements TarifaDao {
	private SimpleJdbcInsert insertarTarifa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	
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
		SqlParameterSource namedParameters = new MapSqlParameterSource("idTarifa", idTarifa);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new TarifaMapper());
	}

}