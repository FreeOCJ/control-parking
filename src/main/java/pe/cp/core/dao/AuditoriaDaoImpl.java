package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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

import pe.cp.core.domain.Auditoria;

@Repository
public class AuditoriaDaoImpl implements AuditoriaDao {
	private SimpleJdbcInsert insertarAuditoria;
	
	@Autowired
	private TipoEventoDao tEventoDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarAuditoria = new SimpleJdbcInsert(dataSource)
				.withTableName("auditoria")
				.usingGeneratedKeyColumns("IDAUDITORIA");
	}
	
	@Override
	public int agregar(Auditoria auditoria) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("FECHA", new Date());
		parameters.put("USUARIO", auditoria.getNombreUsuario());
		parameters.put("IDTIPOEVENTO", auditoria.getTipoEvento());
		Number key = insertarAuditoria.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public Auditoria buscar(int idAuditoria) {
		final String sql = "select * from auditoria where IDAUDITORIA = :idAuditoria";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idAuditoria", idAuditoria);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<Auditoria>() {
					public Auditoria mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Auditoria auditoria = new Auditoria();
						auditoria.setId(rs.getInt("IDAUDITORIA"));
						auditoria.setFechaCreacion(rs.getDate("FECHA"));
						auditoria.setNombreUsuario(rs.getString("USUARIO"));
						auditoria.setTipoEvento(tEventoDao.buscar(rs.getInt("IDTIPOEVENTO")));						
												
						return auditoria;
					}
				});
	}

}
