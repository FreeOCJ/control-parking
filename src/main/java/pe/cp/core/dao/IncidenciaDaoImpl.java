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
import pe.cp.core.domain.Usuario;

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
		Map<String, Object> parameters = new HashMap<String, Object>(6);
		parameters.put("DESINCIDENCIA", incidencia.getDescripcion());
		parameters.put("FECHAINCIDENCIA", incidencia.getFechaIncidencia());
		parameters.put("IDOPERACION", incidencia.getIdOperacion());
		parameters.put("ELIMINADO", "F");
		parameters.put("IDTIPOINC", incidencia.getTipoIncidencia().getId());
		parameters.put("ACCION", incidencia.getAccionTomada());
		Number key = insertarIncidencia.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Incidencia incidencia) {
		jdbcTemplate.update("UPDATE incidencia SET DESINCIDENCIA = ?, IDTIPOINC = ?, FECHAINCIDENCIA = ?, ACCION = ? WHERE IDINCIDENCIA = ?", 
				incidencia.getDescripcion(), incidencia.getTipoIncidencia().getId(), incidencia.getFechaIncidencia(), 
				incidencia.getAccionTomada(), incidencia.getId());
	}

	@Override
	public void eliminar(int idIncidencia) {
		jdbcTemplate.update("UPDATE incidencia SET ELIMINADO = 'T' WHERE IDINCIDENCIA = ?", idIncidencia);
	}

	@Override
	public List<Incidencia> obtenerIncidencias(int idOperacion) {
		final String sql = "SELECT i.IDINCIDENCIA, i.DESINCIDENCIA, i.FECHAINCIDENCIA, i.IDOPERACION, i.ELIMINADO, i.IDTIPOINC, t.DESCTIPOINC  from incidencia i, tipoincidencia t where t.IDTIPOINC = i.IDTIPOINC and idoperacion = :idOperacion and i.ELIMINADO = 'F'";
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

	@Override
	public List<TipoIncidencia> obtenerTipos() {
		final String sql = "SELECT * from tipoincidencia where ELIMINADO='F'";
		SqlParameterSource namedParameters = new MapSqlParameterSource(new HashMap<String, Object>());
		
		List<TipoIncidencia> tipos = new ArrayList<TipoIncidencia>();
		tipos = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<TipoIncidencia>() {
			@Override
			public TipoIncidencia mapRow(ResultSet rs, int rowNumber) throws SQLException {
				TipoIncidencia tipo = new TipoIncidencia();
				tipo.setId(rs.getInt("IDTIPOINC"));
				tipo.setDescripcion(rs.getString("DESCTIPOINC"));
				tipo.setEliminado(false);
				
				return tipo;
			}
		});
		
		return tipos;
	}

	@Override
	public Incidencia buscar(int idIncidencia) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i.IDINCIDENCIA, i.DESINCIDENCIA, i.FECHAINCIDENCIA, i.IDOPERACION, i.ELIMINADO, i.IDTIPOINC, t.DESCTIPOINC ");
		sb.append(" from incidencia i, tipoincidencia t ");
		sb.append("where t.IDTIPOINC = i.IDTIPOINC and idincidencia = ? and i.ELIMINADO = 'F'");
				
		Incidencia incidencia = null;
		Object[] args = {idIncidencia};
		
		incidencia = jdbcTemplate.queryForObject(sb.toString(), args, new RowMapper<Incidencia>(){
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
			
		} );
		return incidencia;
	}
}
