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

import pe.cp.core.domain.UnidadOperativa;

@Repository
public class UnidadOperativaDaoImpl implements UnidadOperativaDao {
	private SimpleJdbcInsert insertarUnidadOperativa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarUnidadOperativa = new SimpleJdbcInsert(dataSource)
				.withTableName("unidadoperativa")
				.usingGeneratedKeyColumns("IDUNIDAD");
	}
	
	@Override
	public int agregar(UnidadOperativa unidadOp) {
		Map<String, Object> parameters = new HashMap<String, Object>(7);
		parameters.put("NROCAJONES", unidadOp.getNumeroCajones());
		parameters.put("DIRECCION", unidadOp.getDireccion());
		parameters.put("DEPARTAMENTO", unidadOp.getDepartamento());
		parameters.put("PROVINCIA", unidadOp.getProvincia());
		parameters.put("DISTRITO", unidadOp.getDistrito());
		parameters.put("HORAINICIO", unidadOp.getHoraInicio());
		parameters.put("HORAFIN", unidadOp.getHoraFin());
		parameters.put("IDCLIENTE", unidadOp.getCliente().getId());
		parameters.put("ELIMINADO", "F");
		Number key = insertarUnidadOperativa.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(UnidadOperativa unidadOp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idUnidadOp) {
		// TODO Auto-generated method stub

	}

	@Override
	public UnidadOperativa buscar(int idUnidadOp) {
		final String sql = "select * from unidadoperativa where IDUNIDAD = :idUnidad";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idUnidad", idUnidadOp);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<UnidadOperativa>() {
					public UnidadOperativa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnidadOperativa unidadOp = new UnidadOperativa();
						unidadOp.setId(rs.getInt("IDUNIDAD"));
						unidadOp.setDireccion(rs.getString("DIRECCION"));
						unidadOp.setDepartamento(rs.getString("DEPARTAMENTO"));
						unidadOp.setProvincia(rs.getString("PROVINCIA"));
						unidadOp.setDistrito(rs.getString("DISTRITO"));
						unidadOp.setHoraInicio(rs.getDate("HORAINICIO"));
						unidadOp.setHoraFin(rs.getDate("HORAFIN"));
						unidadOp.setNumeroCajones(rs.getInt("NROCAJONES"));
						unidadOp.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
												
						return unidadOp;
					}
				});
	}

}
