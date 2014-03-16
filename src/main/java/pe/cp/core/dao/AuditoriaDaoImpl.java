package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import pe.cp.core.domain.Auditoria;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.IAuditInfo;
import pe.cp.core.domain.Usuario;

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
		if (auditoria.getEvento().length() > 500) auditoria.setEvento(auditoria.getEvento().substring(0, 499));
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("FECHA", new Date());
		parameters.put("USUARIO", auditoria.getLoginUsuario());
		parameters.put("EVENTO", auditoria.getEvento());
		parameters.put("TIPOEVENTO", auditoria.getTipoEvento());
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
						//auditoria.setTipoEvento(tEventoDao.buscar(rs.getInt("IDTIPOEVENTO")));						
												
						return auditoria;
					}
				});
	}
	
	@Override
	public int agregarAuditoria(Usuario modificador, IAuditInfo auditInfo, String tipoEvento) {
		StringBuilder sb = new StringBuilder();
		sb.append("El usuario ");
		sb.append(modificador.getLogin());
		sb.append(" ");
		sb.append(tipoEvento);
		sb.append(" al ");
		sb.append(auditInfo.getAuditInfo());
		
		Auditoria audit = new Auditoria();
		audit.setFechaCreacion(new Date());
		audit.setLoginUsuario(modificador.getLogin());
		audit.setTipoEvento(tipoEvento);
		audit.setEvento(sb.toString());
		
		return agregar(audit);
	}

	@Override
	public List<Auditoria> buscar(String tipoEvento, Date fechaInicio, Date fechaFin, String login) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM auditoria WHERE 1=1 ");
        if (tipoEvento != null && !tipoEvento.isEmpty()) sb.append(" AND TIPOEVENTO=:tipoEvento");
        if (login != null && !login.isEmpty()) sb.append(" AND USUARIO=:login");
        if (fechaInicio != null) sb.append(" AND FECHA  >= :fechaInicio");
        if (fechaFin != null) sb.append(" AND FECHA <= :fechaFin");
        
		List<Auditoria> registros = new ArrayList<Auditoria>();		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("tipoEvento", tipoEvento);
		args.put("login", login);
		args.put("fechaInicio", fechaInicio);
		args.put("fechaFin", fechaFin);
		
		SqlParameterSource sqlArgs = new MapSqlParameterSource(args);		
		registros = namedParameterJdbcTemplate.query(sb.toString(), sqlArgs, new RowMapper<Auditoria>(){
			@Override
			public Auditoria mapRow(ResultSet rs, int rowNum) throws SQLException {
				return auditMapRow(rs, rowNum);
			}
			
		} );
		
		return registros;
	}
	
	public Auditoria auditMapRow(ResultSet rs, int n) throws SQLException {
		Auditoria auditoria = new Auditoria();
		auditoria.setEvento(rs.getString("EVENTO"));
		auditoria.setFechaCreacion(rs.getTimestamp("FECHA"));
		auditoria.setId(rs.getInt("IDAUDITORIA"));
		auditoria.setLoginUsuario(rs.getString("USUARIO"));
		auditoria.setNombreUsuario(rs.getString("USUARIO"));
		auditoria.setTipoEvento(rs.getString("TIPOEVENTO"));
		
		return auditoria;
	}

}
