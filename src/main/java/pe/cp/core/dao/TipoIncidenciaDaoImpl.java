package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.TipoIncidencia;

@Repository
public class TipoIncidenciaDaoImpl implements TipoIncidenciaDao {
	private SimpleJdbcInsert insertarTipoIncidencia;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarTipoIncidencia = new SimpleJdbcInsert(dataSource)
				.withTableName("tipoincidencia")
				.usingGeneratedKeyColumns("IDTIPOINC");
	}
	
	@Override
	public int agregar(TipoIncidencia tipoIncidencia) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("DESCTIPOINC", tipoIncidencia.getDescripcion());		
		Number key = insertarTipoIncidencia.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void modificar(TipoIncidencia tipoIncidencia) {
		jdbcTemplate.update("update tipoincidencia set DESTIPOINC = ? where IDTIPOINC = ?",
				tipoIncidencia.getDescripcion(), tipoIncidencia.getId());
	}

	@Override
	public void eliminar(int idTipoIncidencia) {
		jdbcTemplate.update("DELETE from tipoincidencia where IDTIPOINC= ?",idTipoIncidencia);
	}

}
