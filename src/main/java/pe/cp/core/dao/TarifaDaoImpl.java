package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		parameters.put("IDUNIDADOP", tarifa.getIdUnidadOperativa());
		Number key = insertarTarifa.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Tarifa tarifa) {
		
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("UPDATE tarifa SET ELIMINADO = 'F' where IDUNIDADOP=");
		sbSql.append(tarifa.getIdUnidadOperativa());
		sbSql.append(" AND CATEGORIA='");
		sbSql.append(tarifa.getCategoria());
		sbSql.append("' And MONTOTARIFA = ");
		sbSql.append(tarifa.getMonto());
				
		jdbcTemplate.update(sbSql.toString());

	}

	@Override
	public Tarifa buscar(int idTarifa) {
		final String sql = "select * from tarifa where IDTARIFA = :idTarifa AND ELIMINADO = 'F'";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idTarifa", idTarifa);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new TarifaMapper());
	}

	@Override
	public List<Tarifa> obtenerTarifas(int idUnidadOperativa, String categoria) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from tarifa where IDUNIDADOP = :idUnidadOp AND ELIMINADO = 'F' ");
		if (categoria != null && !categoria.isEmpty()) sbSql.append(" and CATEGORIA = :categoria ");
		
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("idUnidadOp", idUnidadOperativa);
		parameters.put("categoria", categoria);
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);
		
		List<Tarifa> tarifas = namedParameterJdbcTemplate.query(sbSql.toString(), namedParameters, new TarifaMapper());
		return tarifas;
	}

	@Override
	public void eliminarPorCategoria(int idUnidadOperativa, String categoria) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("UPDATE tarifa SET ELIMINADO = 'T' where IDUNIDADOP=");
		sbSql.append(idUnidadOperativa);
		sbSql.append(" AND CATEGORIA='");
		sbSql.append(categoria);
		sbSql.append("'");
		
		jdbcTemplate.update(sbSql.toString());
	}

	
	@Override
	public List<String> obtenerCategorias(int unidadOperativa) {
		
		final String sql = "select distinct categoria from tarifa where IDUNIDADOP = :idUnidadOp AND ELIMINADO = 'F'";
		
		List<String> categorias = new ArrayList<String>();
		SqlParameterSource namedParameters = new MapSqlParameterSource("idUnidadOp", unidadOperativa);
		categorias = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CATEGORIA");
			}
		} );
		
		return categorias;
		
	}

	@Override
	public int existePorMonto(Tarifa tarifa) {
		final String sql = "select count(1) from tarifa where MONTOTARIFA = :monto AND ELIMINADO = 'T' And IDUNIDADOP= :idUnidadOp AND CATEGORIA = :idCategoria";
		
		MapSqlParameterSource msp = new MapSqlParameterSource();
		msp.addValue("monto", tarifa.getMonto());
		msp.addValue("idUnidadOp", tarifa.getIdUnidadOperativa());
		msp.addValue("idCategoria", tarifa.getCategoria());

		return (int)namedParameterJdbcTemplate.queryForInt(sql, msp);
	}

		
}