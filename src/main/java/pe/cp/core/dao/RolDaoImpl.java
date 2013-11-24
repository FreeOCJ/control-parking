package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Rol;

@Repository
public class RolDaoImpl implements RolDao {

	private SimpleJdbcInsert insertarRol;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarRol = new SimpleJdbcInsert(dataSource)
				.withTableName("rol")
				.usingGeneratedKeyColumns("IDROL");
	}
	
	@Override
	public int agregar(Rol rol) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("DESROL", rol.getDescripcion());		
		Number key = insertarRol.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Rol rol) {
		jdbcTemplate.update("update rol set DESROL = ? where IDROL = ?",
				rol.getDescripcion(), rol.getId());		
	}

	@Override
	public void eliminar(int idRol) {
		jdbcTemplate.update("DELETE from rol where IDROL= ?",idRol);
	}

}
