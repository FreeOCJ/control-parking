package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.OperacionPorTarifa;

@Repository
public class OperacionPorTarifaDaoImpl implements OperacionPorTarifaDao {
	private SimpleJdbcInsert insertarOpXTarifa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarOpXTarifa = new SimpleJdbcInsert(dataSource)
				.withTableName("optarifa")
				.usingGeneratedKeyColumns("IDOPTARIFA");
	}
	
	@Override
	public int agregar(OperacionPorTarifa opTarifa) {
		Map<String, Object> parameters = new HashMap<String, Object>(5);
		parameters.put("CANTIDADTICKETS", opTarifa.getCantidadTickets());
		//parameters.put("IDTARIFA", );
		//parameters.put("IDTARIFA", tipoevento.getDescripcion());
		parameters.put("MONTOTOTAL", opTarifa.getMonto());
		//parameters.put("IDOPERACION", tipoevento.getDescripcion());
				
		Number key = insertarOpXTarifa.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(OperacionPorTarifa opTarifa) {
		// TODO Auto-generated method stub

	}

}