package pe.cp.core.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
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
		parameters.put("VEHICULOSSALIDA", op.getCantidadVehiculosSalida());
		parameters.put("TICKETINICIO", op.getNumeroTicketInicio());
		parameters.put("TICKETFIN", op.getNumeroTicketFin());
		parameters.put("CONDUCTORESRAUDOS", op.getCantidadRaudos());
		parameters.put("CANTIDADPERSONAS", op.getCantidadPersonas());
		parameters.put("PERNOCTADOSINICIO", op.getCantidadPernoctadosInicio());
		parameters.put("PERNOCTADOSFIN", op.getCantidadPernoctadosFin());
		parameters.put("IDUNIDAD", op.getIdUnidadOperativa()); 
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
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT o.IDOPERACION, o.FECOPERACION, o.DESOFERTA, o.VEHICULOSENTRADA, o.VEHICULOSSALIDA, ");
		sbSql.append(" o.TICKETINICIO, o.TICKETFIN, o.CONDUCTORESRAUDOS, o.CANTIDADPERSONAS, o.PERNOCTADOSINICIO,");
		sbSql.append(" o.PERNOCTADOSFIN, o.IDUNIDAD, o.CREADOPOR, o.ACTUALIZADOPOR, o.FECHACREACION, ");
		sbSql.append(" o.FECHAACTUALIZA, o.ESTADO, o.AJUSTE, o.ELIMINADO, u.NOMBRE, c.NOMBRECOMERCIAL");
		sbSql.append(" FROM operacion o, unidadoperativa u, cliente c where ");
		sbSql.append(" o.IDUNIDAD = u.IDUNIDAD and u.IDCLIENTE = c.IDCLIENTE and o.IDOPERACION=? ");
		Operacion operacion = null;
		Object[] args = {idOperacion};
		operacion = jdbcTemplate.queryForObject(sbSql.toString(), args,new OperacionMapper());
		return operacion;
	}
	
	@Override
	public List<Operacion> buscar(int idUnidadOp, Date fechaOp, String estado) {
		StringBuilder sbSql = new StringBuilder();
		Map<String, Object> args = new HashMap<String, Object>();
		
		sbSql.append("SELECT o.IDOPERACION, o.FECOPERACION, o.DESOFERTA, o.VEHICULOSENTRADA, o.VEHICULOSSALIDA, ");
		sbSql.append(" o.TICKETINICIO, o.TICKETFIN, o.CONDUCTORESRAUDOS, o.CANTIDADPERSONAS, o.PERNOCTADOSINICIO,");
		sbSql.append(" o.PERNOCTADOSFIN, o.IDUNIDAD, o.CREADOPOR, o.ACTUALIZADOPOR, o.FECHACREACION, ");
		sbSql.append(" o.FECHAACTUALIZA, o.ESTADO, o.AJUSTE, o.ELIMINADO, u.NOMBRE, c.NOMBRECOMERCIAL");
		sbSql.append(" FROM operacion o, unidadoperativa u, cliente c where ");
		sbSql.append(" o.IDUNIDAD = u.IDUNIDAD and u.IDCLIENTE = c.IDCLIENTE ");
		if (idUnidadOp > 0) { 
			sbSql.append(" and u.idunidad = :idUnidad "); 
			args.put("idUnidad", idUnidadOp);
		}
		if (fechaOp != null) {
			sbSql.append(" and o.fecoperacion = :fechaOp ");
			args.put("fechaOp", fechaOp);
		}
		if (estado != null && !estado.isEmpty()) {
			sbSql.append(" and estado = :estado");
			args.put("estado", estado);
		}
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(args);
		
		List<Operacion> operaciones = new ArrayList<Operacion>();
		operaciones = namedParameterJdbcTemplate.query(sbSql.toString(), namedParameters, new OperacionMapper());
		
		return operaciones;
	}

}