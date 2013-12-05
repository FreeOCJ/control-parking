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
import pe.cp.core.domain.Usuario;
import pe.cp.core.domain.filters.UsuarioFilter;

@Repository
public class UsuarioDaoImpl implements UsuarioDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private ClienteDao clienteDao;
	
	private SimpleJdbcInsert insertarUsuario;	
	private SimpleJdbcInsert insertarRolPorUsuario;
	
	@Autowired
	public UsuarioDaoImpl(DataSource dataSource){
		this.insertarUsuario = new SimpleJdbcInsert(dataSource)
		.withTableName("usuario")
		.usingGeneratedKeyColumns("IDUSUARIO");
		
		this.insertarRolPorUsuario = new SimpleJdbcInsert(dataSource)
		.withTableName("rolxusuario");
	}
	
	@Override
	public void actualizar(Usuario usuario) {
		jdbcTemplate.update("UPDATE USUARIO SET EMAIL = ?,NOMBRES = ?," +
							"APELLIDOS = ?,CARGO = ?,LOGIN = ?,PASSWORD = ?," +
							"IDCLIENTE = ? WHERE IDUSUARIO = ?",
				            usuario.getEmail(),usuario.getNombres(),usuario.getApellidos(),
				            usuario.getCargo(),usuario.getLogin(),usuario.getPassword(),
				            usuario.getCliente().getId(),usuario.getId());		
	}	

	@Override
	public int agregar(Usuario usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>(8);
		parameters.put("EMAIL", usuario.getEmail());	
		parameters.put("NOMBRES", usuario.getNombres());
		parameters.put("APELLIDOS",usuario.getApellidos());
		parameters.put("CARGO", usuario.getCargo());
		parameters.put("LOGIN", usuario.getLogin());
		parameters.put("PASSWORD",usuario.getPassword());
		parameters.put("IDCLIENTE", usuario.getCliente().getId());
		parameters.put("ELIMINADO", 'F');
		Number key = insertarUsuario.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public List<Usuario> buscar(String nombre) {
		final String sql = "SELECT * FROM USUARIO WHERE NOMBRES LIKE :nombre OR APELLIDOS LIKE :nombre";		
		List<Usuario> usuarios = null;
		SqlParameterSource args = new MapSqlParameterSource("nombre","%" + nombre + "%");		
		usuarios = namedParameterJdbcTemplate.query(sql, args, new RowMapper<Usuario>(){
			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return userMapRow(rs, rowNum);
			}
			
		} );
		return usuarios;
	}

	@Override
	public void eliminar(int id) {
		final String sql = "UPDATE USUARIO SET ELIMINADO = 'T' WHERE IDUSUARIO = ?";		
		jdbcTemplate.update(sql,id);		
	}

	@Override
	public Usuario buscar(int idUsuario) {
		final String sql = "SELECT * FROM USUARIO WHERE IDUSUARIO = ? AND ELIMINADO = 'F'";
		Usuario usuario = null;
		Object[] args = {idUsuario};
		usuario = jdbcTemplate.queryForObject(sql, args, new RowMapper<Usuario>(){
			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return userMapRow(rs, rowNum);
			}
			
		} );
		return usuario;
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		final String sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND ELIMINADO = 'F'";
		Usuario usuario = null;
		Object[] args = {login};
		try{
			usuario = jdbcTemplate.queryForObject(sql, args, new RowMapper<Usuario>(){
				@Override
				public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
					return userMapRow(rs, rowNum);
				}
				
			} );
		}catch(Exception e){}		
		return usuario;
	}

	@Override
	public void agregarRol(int idUsuario, int idRol) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("IDUSUARIO", idUsuario);	
		parameters.put("IDROL", idRol);
		insertarRolPorUsuario.execute(parameters);		
	}

	@Override
	public void removerTodosRoles(int idUsuario) {
		final String sql = "DELETE FROM usuarioxrol where IDUSUARIO = ?";		
		jdbcTemplate.update(sql,idUsuario);	
	}

	@Override
	public List<Usuario> buscarOr(UsuarioFilter filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Usuario userMapRow(ResultSet rs, int n) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("IDUSUARIO"));
		usuario.setEmail(rs.getString("EMAIL"));
		usuario.setNombres(rs.getString("NOMBRES"));
		usuario.setApellidos(rs.getString("APELLIDOS"));
		usuario.setCargo(rs.getString("CARGO"));
		usuario.setLogin(rs.getString("LOGIN"));
		usuario.setPassword(rs.getString("PASSWORD"));
		
		Cliente cliente = clienteDao.buscar(rs.getInt("IDCLIENTE"));
		usuario.setCliente(cliente);
		return usuario;
	}
}
