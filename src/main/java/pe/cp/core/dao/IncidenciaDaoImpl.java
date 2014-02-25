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

import pe.cp.core.domain.Incidencia;
import pe.cp.core.domain.OperacionDetalle;
import pe.cp.core.domain.TipoIncidencia;

@Repository
public class IncidenciaDaoImpl implements IncidenciaDao {
	private SimpleJdbcInsert insertarIncidencia;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarIncidencia = new SimpleJdbcInsert(dataSource)
				.withTableName("incidencia")
				.usingGeneratedKeyColumns("IDINCIDENCIA");
	}
	
	@Override
	public int agregar(Incidencia incidencia) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		//parameters.put("IDOPERACION", 0); TODO
		parameters.put("DESINCIDENCIA", incidencia.getDescripcion());
		//parameters.put("IDTIPOINC", tarifa.getCategoria());
		Number key = insertarIncidencia.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Incidencia incidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idIncidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Incidencia> obtenerIncidencias(int idOperacion) {
		final String sql = "SELECT i.IDINCIDENCIA, i.DESCINCIDENCIA, i.FECHAINCIDENCIA, i.IDOPERACION, i.ELIMINADO, i.IDTIPOINC, t.DESCTIPOINC  from incidencia i, tipoincidencia t where t.IDTIPOINC = i.IDTIPOINC and idoperacion = :idOperacion";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("idOperacion", idOperacion);
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(args);
		
		List<Incidencia> incidencias = new ArrayList<Incidencia>();
		incidencias = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<Incidencia>() {
			@Override
			public Incidencia mapRow(ResultSet rs, int rowNumber) throws SQLException {
				Incidencia incidencia = new Incidencia();
				incidencia.setDescripcion(rs.getString("DESINCIDENCIA"));
				incidencia.setFechaIncidencia(rs.getTimestamp("FECHAINCIDENCIA"));
				incidencia.setId(rs.getInt("IDINCIDENCIA"));
				incidencia.setIdOperacion(rs.getInt("IDOPERACION"));
				
				TipoIncidencia tipoIncidencia = new TipoIncidencia();
				tipoIncidencia.setId(rs.getInt("IDTIPOINC"));
				tipoIncidencia.setDescripcion(rs.getString("DESCTIPOINC"));
				
				incidencia.setTipoIncidencia(tipoIncidencia);
				return incidencia;
			}
		});
		
		return incidencias;
	}

}
