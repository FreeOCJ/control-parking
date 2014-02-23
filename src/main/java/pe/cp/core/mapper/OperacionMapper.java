package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Operacion;

@Repository
public class OperacionMapper implements RowMapper<Operacion> {
	
	@Override
	public Operacion mapRow(ResultSet rs, int n) throws SQLException {
		Operacion operacion = new Operacion();
		operacion.setFechaTransaccion(rs.getDate("FECOPERACION"));
		operacion.setOferta(rs.getString("DESOFERTA"));
		operacion.setCantidadVehiculosEntrada(rs.getInt("VEHICULOSENTRADA"));
		operacion.setCantidadVehiculosSalida(rs.getInt("VEHICULOSSALIDA"));
		operacion.setNumeroTicketInicio(rs.getInt("TICKETINICIO"));
		operacion.setNumeroTicketFin(rs.getInt("TICKETFIN"));
		operacion.setCantidadRaudos(rs.getInt("CONDUCTORESRAUDOS"));
		operacion.setCantidadPersonas(rs.getInt("CANTIDADPERSONAS"));
		operacion.setCantidadPernoctadosInicio(rs.getInt("PERNOCTADOSINICIO"));
		operacion.setCantidadPernoctadosFin(rs.getInt("PERNOCTADOSFIN"));
		operacion.setIdUnidadOperativa(rs.getInt("IDUNIDAD"));
		operacion.setCreador(rs.getString("CREADOPOR"));
		operacion.setUltimoModificador(rs.getString("ACTUALIZADOPOR"));		
		operacion.setFechaCreacion(rs.getDate("FECHACREACION"));
		operacion.setFechaActualizacion(rs.getDate("FECHAACTUALIZA"));
		operacion.setEstado(rs.getString("ESTADO"));
		operacion.setAjuste(rs.getInt("AJUSTE"));
		operacion.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);	
		operacion.setAuxNombreCliente(rs.getString("NOMBRECOMERCIAL"));
		operacion.setAuxNombreUnidadOperativa(rs.getString("NOMBRE"));
		operacion.setId(rs.getInt("IDOPERACION"));
		
		return operacion;
	}

}
