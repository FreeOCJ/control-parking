package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.TipoEvento;

@Repository(value = "tipoEventoDao")
public class TipoEventoDAOImpl implements TipoEventoDAO {
	private SimpleJdbcInsert insertarTipoEvento;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		jdbcTemplate.update("DELETE from tipoevento where DESTIPOEVENTO= ?",idTipoEvento);
	}	

	@Override
	public void actualizar(TipoEvento tipoevento) {
		jdbcTemplate.update("update tipoevento set DESTIPOEVENTO = ? where IDTIPOEVENTO = ?",
				tipoevento.getDescripcion(), tipoevento.getId());		
	}
}
