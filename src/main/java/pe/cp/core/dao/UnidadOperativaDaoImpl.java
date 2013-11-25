package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.UnidadOperativa;

@Repository
public class UnidadOperativaDaoImpl implements UnidadOperativaDao {
	private SimpleJdbcInsert insertarUnidadOperativa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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

}
