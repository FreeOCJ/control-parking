package pe.cp.core.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Auditoria;

@Repository
public class AuditoriaDaoImpl implements AuditoriaDao {
	private SimpleJdbcInsert insertarAuditoria;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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

}
