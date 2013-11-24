package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Incidencia;

@Repository
public class IncidenciaDaoImpl implements IncidenciaDao {
	private SimpleJdbcInsert insertarIncidencia;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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

}
