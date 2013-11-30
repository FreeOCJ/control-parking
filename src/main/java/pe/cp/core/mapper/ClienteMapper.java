package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.cp.core.domain.Cliente;

public class ClienteMapper implements RowMapper<Cliente>{

	@Override
	public Cliente mapRow(ResultSet rs, int n) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("IDCLIENTE"));
		cliente.setNombreComercial(rs.getString("NOMBRECOMERCIAL"));
		cliente.setRazonSocial(rs.getString("RAZONSOCIAL"));
		cliente.setRuc(rs.getString("RUC"));
		cliente.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
		return cliente;
	}

}
