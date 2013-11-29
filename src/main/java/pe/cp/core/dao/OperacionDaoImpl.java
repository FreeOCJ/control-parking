package pe.cp.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Operacion;
import pe.cp.core.mapper.OperacionMapper;

@Repository
public class OperacionDaoImpl implements OperacionDao {
	private SimpleJdbcInsert insertarOperacion;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarOperacion = new SimpleJdbcInsert(dataSource)
				.withTableName("operacion")
				.usingGeneratedKeyColumns("IDOPERACION");
	}
	@Override
	public int agregar(Operacion op) {
		Map<String, Object> parameters = new HashMap<String, Object>(17);
		parameters.put("FECOPERACION", op.getFechaTransaccion());
		parameters.put("DESOFERTA", op.getOferta());
		parameters.put("VEHICULOSENTRADA", op.getCantidadVehiculosEntrada());
		parameters.put("VEHICULOSALIDA", op.getCantidadVehiculosSalida());
		parameters.put("TICKETINICIO", op.getNumeroTicketInicio());
		parameters.put("TICKETFIN", op.getNumeroTicketFin());
		parameters.put("CONDUCTORESRAUDOS", op.getCantidadRaudos());
		parameters.put("CANTIDADPERSONAS", op.getCantidadPersonas());
		parameters.put("PERNOCTADOSINICIO", op.getCantidadPernoctadosInicio());
		parameters.put("PERNOCTADOSFIN", op.getCantidadPernoctadosFin());
		parameters.put("IDUNIDAD", op.getUnidadoperativa().getId()); 
		parameters.put("CREADOPOR", op.getCreador());
		parameters.put("ACTUALIZADOPOR", op.getUltimoModificador());
		parameters.put("FECHACREACION", op.getFechaCreacion());
		parameters.put("FECHAACTUALIZA", op.getFechaActualizacion());
		parameters.put("ESTADO", op.getEstado());
		parameters.put("AJUSTE", op.getAjuste());
		parameters.put("ELIMINADO", 'F');
					
		Number key = insertarOperacion.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void modificar(Operacion op) {
		jdbcTemplate.update("UPDATE OPERACION SET FECOPERACION = ?,DESOFERTA = ?," +
				"VEHICULOSENTRADA = ?,VEHICULOSALIDA = ?,TICKETINICIO = ?, " +
				"TICKETFIN = ?,CONDUCTORESRAUDOS = ?,CANTIDADPERSONAS = ?," +
				"PERNOCTADOSINICIO = ?, PERNOCTADOSFIN = ?,IDUNIDAD = ?," +
				"CREADOPOR = ?, ACTUALIZADOPOR = ?,FECHACREACION = ?," +
				"FECHAACTUALIZA = ?, ESTADO = ?, AJUSTE = ? WHERE IDOPERACION = ?",
				op.getFechaTransaccion(),op.getOferta(),op.getCantidadVehiculosEntrada(),
				op.getCantidadVehiculosSalida(),op.getNumeroTicketInicio(),
				op.getNumeroTicketFin(),op.getCantidadRaudos(),op.getCantidadPersonas(),
				op.getCantidadPernoctadosInicio(),op.getCantidadPernoctadosFin(),
				op.getUnidadoperativa().getId(),op.getCreador(),op.getUltimoModificador(),
				op.getFechaCreacion(),op.getFechaActualizacion(),op.getEstado(),
				op.getAjuste(),op.getId());

	}

	@Override
	public void eliminar(int idOperacion) {
		jdbcTemplate.update("UPDATE OPERACION SET ELIMINADO = 'T' WHERE IDOPERACION = ?", idOperacion);

	}
	
	
	@Override
	public Operacion buscar(int idOperacion) {
		final String sql = "SELECT * FROM OPERACION WHERE IDOPERACION = ? ";
		Operacion operacion = null;
		Object[] args = {idOperacion};
		operacion = jdbcTemplate.queryForObject(sql, args,new OperacionMapper());
		return operacion;
	}

}