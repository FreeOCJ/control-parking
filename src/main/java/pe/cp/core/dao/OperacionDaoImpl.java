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

import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.OperacionDetalle;
import pe.cp.core.domain.OperacionPorTarifa;
import pe.cp.core.mapper.OperacionMapper;

@Repository
public class OperacionDaoImpl implements OperacionDao {
	private SimpleJdbcInsert insertarOperacion;
	private SimpleJdbcInsert insertarOperacionDetalle;
	private SimpleJdbcInsert insertarOperacionPorTarifa;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarOperacion = new SimpleJdbcInsert(dataSource)
				.withTableName("operacion")
				.usingGeneratedKeyColumns("IDOPERACION");
		this.insertarOperacionDetalle = new SimpleJdbcInsert(dataSource)
		        .withTableName("opdetalle")
		        .usingGeneratedKeyColumns("IDDETALLE");
		this.insertarOperacionPorTarifa = new SimpleJdbcInsert(dataSource)
                .withTableName("optarifa")
                .usingGeneratedKeyColumns("IDOPTARIFA");
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
		jdbcTemplate.update("UPDATE operacion SET FECOPERACION = ?,DESOFERTA = ?," +
				"VEHICULOSENTRADA = ?,VEHICULOSSALIDA = ?,TICKETINICIO = ?, " +
				"TICKETFIN = ?,CONDUCTORESRAUDOS = ?,CANTIDADPERSONAS = ?," +
				"PERNOCTADOSFIN = ?, ACTUALIZADOPOR = ?," +
				"FECHAACTUALIZA = ?, ESTADO = ?, AJUSTE = ?, PERNOCTADOSINICIO = ? WHERE IDOPERACION = ?",
				op.getFechaTransaccion(),op.getOferta(),op.getCantidadVehiculosEntrada(),
				op.getCantidadVehiculosSalida(),op.getNumeroTicketInicio(),
				op.getNumeroTicketFin(),op.getCantidadRaudos(),op.getCantidadPersonas(),
				op.getCantidadPernoctadosFin(),
				op.getUltimoModificador(),
				op.getFechaActualizacion(),op.getEstado(),
				op.getCantidadPernoctadosInicio(),
				op.getAjuste(),op.getId());
	}

	@Override
	public void eliminar(int idOperacion) {
		jdbcTemplate.update("UPDATE operacion SET ELIMINADO = 'T' WHERE IDOPERACION = ?", idOperacion);

	}
	
	@Override
	public Operacion buscar(int idOperacion) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT o.IDOPERACION, o.FECOPERACION, o.DESOFERTA, o.VEHICULOSENTRADA, o.VEHICULOSSALIDA, ");
		sbSql.append(" o.TICKETINICIO, o.TICKETFIN, o.CONDUCTORESRAUDOS, o.CANTIDADPERSONAS, o.PERNOCTADOSINICIO,");
		sbSql.append(" o.PERNOCTADOSFIN, o.IDUNIDAD, o.CREADOPOR, o.ACTUALIZADOPOR, o.FECHACREACION, ");
		sbSql.append(" o.FECHAACTUALIZA, o.ESTADO, o.AJUSTE, o.ELIMINADO, u.NOMBRE, c.NOMBRECOMERCIAL");
		sbSql.append(" FROM operacion o, unidadoperativa u, cliente c where ");
		sbSql.append(" o.IDUNIDAD = u.IDUNIDAD and u.IDCLIENTE = c.IDCLIENTE and o.IDOPERACION=? and o.ELIMINADO='F' ");
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
		sbSql.append(" o.IDUNIDAD = u.IDUNIDAD and u.IDCLIENTE = c.IDCLIENTE and o.ELIMINADO='F' ");
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
	@Override
	public int agregarOperacionDetalle(OperacionDetalle opDetalle) {
		Map<String, Object> parameters = new HashMap<String, Object>(17);
		parameters.put("HORAINICIO", opDetalle.getHoraInicio());
		parameters.put("HORAFIN", opDetalle.getHoraFin());
		parameters.put("PERSONAS", opDetalle.getCantidadPersonas());
		parameters.put("INGRESOS", opDetalle.getCantidadIngresos());
		parameters.put("SALIDAS", opDetalle.getCantidadSalidas());
		parameters.put("IDOPERACION", opDetalle.getIdOperacion());
					
		Number key = insertarOperacionDetalle.executeAndReturnKey(parameters);
		return key.intValue();
	}
	@Override
	public void actualizarOperacionDetalle(OperacionDetalle opDetalle) {
		jdbcTemplate.update("UPDATE opdetalle SET PERSONAS = ?, INGRESOS = ?, SALIDAS = ? WHERE IDDETALLE = ?", 
				opDetalle.getCantidadPersonas(), opDetalle.getCantidadIngresos(), opDetalle.getCantidadSalidas(),
				opDetalle.getIdOpDetalle());
	}
	
	@Override
	public List<OperacionDetalle> obtenerDetalles(int idOperacion) {
		final String sql = "SELECT * from opdetalle where idoperacion = :idOperacion";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("idOperacion", idOperacion);
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(args);
		
		List<OperacionDetalle> detalles = new ArrayList<OperacionDetalle>();
		detalles = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<OperacionDetalle>() {
			@Override
			public OperacionDetalle mapRow(ResultSet rs, int rowNumber) throws SQLException {
				OperacionDetalle detalle = new OperacionDetalle();
				detalle.setCantidadIngresos(rs.getInt("INGRESOS"));
				detalle.setCantidadPersonas(rs.getInt("PERSONAS"));
				detalle.setCantidadSalidas(rs.getInt("SALIDAS"));
				detalle.setHoraFin(rs.getTimestamp("HORAFIN"));
				detalle.setHoraInicio(rs.getTimestamp("HORAINICIO"));
				detalle.setIdOpDetalle(rs.getInt("IDDETALLE"));
				detalle.setIdOperacion(rs.getInt("IDOPERACION"));
				return detalle;
			}
		});
		
		return detalles;
	}
	
	@Override
	public int agregarOperacionPorTarifa(OperacionPorTarifa operacionPorTarifa) {
		Map<String, Object> parameters = new HashMap<String, Object>(4);
		parameters.put("CANTIDADTICKETS", operacionPorTarifa.getCantidadTickets());
		parameters.put("MONTOTOTAL", operacionPorTarifa.getMonto());
		parameters.put("IDTARIFA", operacionPorTarifa.getIdTarifa());
		parameters.put("IDOPERACION", operacionPorTarifa.getIdOperacion());
				
		Number key = insertarOperacionPorTarifa.executeAndReturnKey(parameters);
		return key.intValue();
	}
	
	@Override
	public void actualizarOperacionPorTarifa(OperacionPorTarifa operacionPorTarifa) {
		jdbcTemplate.update("UPDATE optarifa SET CANTIDADTICKETS = ?, MONTOTOTAL = ? WHERE IDOPTARIFA = ?", 
				operacionPorTarifa.getCantidadTickets(), operacionPorTarifa.getMonto(), operacionPorTarifa.getIdOperacionPorTarifa());
	}
	@Override
	public List<OperacionPorTarifa> obtenerOpsPorTarifa(int idOperacion) {
		final String sql = "SELECT op.IDOPTARIFA, op.CANTIDADTICKETS, op.IDTARIFA, op.MONTOTOTAL, op.IDOPERACION, t.CATEGORIA, t.MONTOTARIFA from optarifa op, tarifa t where op.IDTARIFA = t.IDTARIFA and op.idoperacion = :idOperacion";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("idOperacion", idOperacion);
		SqlParameterSource namedParameters = new MapSqlParameterSource(args);
		
		List<OperacionPorTarifa> opsPorTarifa = new ArrayList<OperacionPorTarifa>();
		opsPorTarifa = namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<OperacionPorTarifa>() {
			@Override
			public OperacionPorTarifa mapRow(ResultSet rs, int rowNumber) throws SQLException {
				OperacionPorTarifa opTarifa = new OperacionPorTarifa();
				opTarifa.setCantidadTickets(rs.getInt("CANTIDADTICKETS"));
				opTarifa.setIdTarifa(rs.getInt("IDTARIFA"));
				opTarifa.setIdOperacionPorTarifa(rs.getInt("IDOPTARIFA"));
				opTarifa.setMonto(rs.getFloat("MONTOTOTAL"));
				opTarifa.setIdOperacion(rs.getInt("IDOPERACION"));
				opTarifa.setCategoria(rs.getString("CATEGORIA"));
				opTarifa.setPrecioTarifa(rs.getFloat("MONTOTARIFA"));

				return opTarifa;
			}
		});
		
		return opsPorTarifa;
	}
}