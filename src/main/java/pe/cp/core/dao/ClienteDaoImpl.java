package pe.cp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Rol;
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
		jdbcTemplate.update("DELETE from cliente where IDCLIENTE= ?",idCliente);
	}

	@Override
	public List<Cliente> buscar(String nombreComercial) {
		/*final String sql = "select * from cliente where NOMBRECOMERCIAL like '%?'";
		SqlParameterSource namedParameters = 
				new MapSqlParameterSource("nombreComercial", nombreComercial);
	 
		List<Cliente> clientes = new ArrayList<Cliente>();
		 
		List<Map> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Customer customer = new Customer();
			customer.setCustId((Long)(row.get("CUST_ID")));
			customer.setName((String)row.get("NAME"));
			customer.setAge((Integer)row.get("AGE"));
			customers.add(customer);
		}
	 
		return clientes;*/
		final String sql = "select * from cliente where NOMBRECOMERCIAL like ?";
		List<Cliente> clientes = null;
		Object[] args = { "%" + nombreComercial + "%"};
		clientes = jdbcTemplate.query(sql, args, new ClienteMapper());
		return clientes;
	}

	@Override
	public Cliente buscar(int idCliente) {
		final String sql = "select * from cliente where IDCLIENTE = :idCliente";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idCliente", idCliente);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<Cliente>() {
					public Cliente mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Cliente cliente = new Cliente();
						cliente.setId(rs.getInt("IDCLIENTE"));
						cliente.setNombreComercial(rs.getString("NOMBRECOMERCIAL"));
						cliente.setRazonSocial(rs.getString("RAZONSOCIAL"));
						cliente.setRuc(rs.getString("RUC"));
						cliente.setEliminado(rs.getString("ELIMINADO").equals("F") ? true : false);
						return cliente;
					}
				});
	}

}