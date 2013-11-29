package pe.cp.core.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Cliente;
import pe.cp.core.mapper.ClienteMapper;

@Repository
public class ClienteDaoImpl implements ClienteDao {
		
	private SimpleJdbcInsert insertarCliente;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarCliente = new SimpleJdbcInsert(dataSource)
				.withTableName("cliente")
				.usingGeneratedKeyColumns("IDCLIENTE");
	}
	
	@Override
	public int agregar(Cliente cliente) {
		Map<String, Object> parameters = new HashMap<String, Object>(4);
		parameters.put("RAZONSOCIAL", cliente.getRazonSocial());	
		parameters.put("RUC", cliente.getRuc());
		parameters.put("NOMBRECOMERCIAL", cliente.getNombreComercial());
		parameters.put("ELIMINADO", 'F');
		Number key = insertarCliente.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(Cliente cliente) {
		jdbcTemplate.update("update cliente set RAZONSOCIAL = ?, RUC = ?, NOMBRECOMERCIAL = ? where IDCLIENTE = ?",
				cliente.getRazonSocial(), cliente.getRuc(), cliente.getNombreComercial(), cliente.getId());	
	}

	@Override
	public void eliminar(int idCliente) {
		jdbcTemplate.update("UPDATE cliente SET ELIMINADO = 'Y' WHERE IDCLIENTE = ?",idCliente);
	}

	@Override
	public List<Cliente> buscar(String nombreComercial) {
		final String sql = "SELECT * FROM CLIENTE WHERE NOMBRECOMERCIAL LIKE ? ";
		List<Cliente> clientes = null;
		Object[] args = { "%" + nombreComercial + "%"};
		clientes = jdbcTemplate.query(sql, args, new ClienteMapper());
		return clientes;
	}

	@Override
	public Cliente buscar(int idCliente) {
		final String sql = "SELECT * FROM CLIENTE WHERE IDCLIENTE = ? ";
		Cliente cliente= null;
		Object[] args = {idCliente};
		cliente = jdbcTemplate.queryForObject(sql, args, new ClienteMapper());
		return cliente;
	}

}