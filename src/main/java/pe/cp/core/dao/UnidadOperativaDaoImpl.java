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

import pe.cp.core.domain.Rol;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.domain.Usuario;

@Repository
public class UnidadOperativaDaoImpl implements UnidadOperativaDao {
	private SimpleJdbcInsert insertarUnidadOperativa;
	private SimpleJdbcInsert insertarUsuarioPorUnidad;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.insertarUnidadOperativa = new SimpleJdbcInsert(dataSource)
		   .withTableName("unidadoperativa")
		   .usingGeneratedKeyColumns("IDUNIDAD");
		this.insertarUsuarioPorUnidad = new SimpleJdbcInsert(dataSource)
		   .withTableName("uoxuser");
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
		parameters.put("NOMBRE", unidadOp.getNombre());
		parameters.put("NROCAJONES", unidadOp.getNumeroCajones());
		parameters.put("ELIMINADO", "F");
		Number key = insertarUnidadOperativa.executeAndReturnKey(parameters);
		return key.intValue();
	}

	@Override
	public void actualizar(UnidadOperativa unidadOp) {
		jdbcTemplate.update("UPDATE unidadoperativa SET NROCAJONES = ?,DIRECCION = ?, " +
				"DEPARTAMENTO= ?,PROVINCIA= ?, DISTRITO = ?,  HORAINICIO = ?, " +
				"HORAFIN = ?, NOMBRE = ? WHERE IDUNIDAD = ?",unidadOp.getNumeroCajones(),
				unidadOp.getDireccion(),unidadOp.getDepartamento(),unidadOp.getProvincia(),
				unidadOp.getDistrito(),unidadOp.getHoraInicio(),unidadOp.getHoraFin(),
				unidadOp.getNombre(), unidadOp.getId());
	}

	@Override
	public void eliminar(int idUnidadOp) {
		jdbcTemplate.update("UPDATE unidadoperativa SET ELIMINADO = 'T' WHERE IDUNIDAD = ?",idUnidadOp);
	}

	@Override
	public UnidadOperativa buscar(int idUnidadOp) {
		final String sql = "select * from unidadoperativa where IDUNIDAD = :idUnidad";
		SqlParameterSource namedParameters = new MapSqlParameterSource("idUnidad", idUnidadOp);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
				new RowMapper<UnidadOperativa>() {
					public UnidadOperativa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnidadOperativa unidadOp = new UnidadOperativa();
						unidadOp.setId(rs.getInt("IDUNIDAD"));
						unidadOp.setNombre(rs.getString("NOMBRE"));
						unidadOp.setDireccion(rs.getString("DIRECCION"));
						unidadOp.setDepartamento(rs.getString("DEPARTAMENTO"));
						unidadOp.setProvincia(rs.getString("PROVINCIA"));
						unidadOp.setDistrito(rs.getString("DISTRITO"));
						unidadOp.setHoraInicio(rs.getTimestamp("HORAINICIO"));
						unidadOp.setHoraFin(rs.getTimestamp("HORAFIN"));
						unidadOp.setNumeroCajones(rs.getInt("NROCAJONES"));
						unidadOp.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
												
						return unidadOp;
					}
				});
	}

	@Override
	public List<UnidadOperativa> obtenerPorCliente(int idCliente) {
		final String sql = "select * from unidadoperativa where IDCLIENTE = :idCliente";		
		List<UnidadOperativa> unidades = null;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("idCliente", idCliente);		
		unidades = namedParameterJdbcTemplate.query(sql, args, new RowMapper<UnidadOperativa>(){
			@Override
			public UnidadOperativa mapRow(ResultSet rs, int rowNum) throws SQLException {
				UnidadOperativa unidadOp = new UnidadOperativa();
				unidadOp.setId(rs.getInt("IDUNIDAD"));
				unidadOp.setNombre(rs.getString("NOMBRE"));
				unidadOp.setDireccion(rs.getString("DIRECCION"));
				unidadOp.setDepartamento(rs.getString("DEPARTAMENTO"));
				unidadOp.setProvincia(rs.getString("PROVINCIA"));
				unidadOp.setDistrito(rs.getString("DISTRITO"));
				unidadOp.setHoraInicio(rs.getDate("HORAINICIO"));
				unidadOp.setHoraFin(rs.getDate("HORAFIN"));
				unidadOp.setNumeroCajones(rs.getInt("NROCAJONES"));
				unidadOp.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
										
				return unidadOp;
			}
			
		} );
		return unidades;
	}

	@Override
	public void retirarUsuariosUnidadOp(int idUnidadOp, String rol) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("DELETE FROM uoxuser where IDUNIDAD=");
		sbSql.append(idUnidadOp);
		if (rol.equals(Rol.APROBADOR)) sbSql.append(" and APROBAR='T'");
		else if (rol.equals(Rol.OPERADOR)) sbSql.append(" and UNIDAD='T'");
		else if (rol.equals(Rol.CLIENTE)) sbSql.append(" and REPORTES='T'");
		
		jdbcTemplate.update(sbSql.toString());
	}

	@Override
	public void agregarUsuariosUnidadOp(int idUnidadOp, int[] idsUsuarios,
			String rol) {
		char aprobar = (rol.equals(Rol.APROBADOR)) ? 'T' : 'F';
		char cliente = (rol.equals(Rol.CLIENTE)) ? 'T' : 'F';
		char operador = (rol.equals(Rol.OPERADOR)) ? 'T' : 'F';
		
		retirarUsuariosUnidadOp(idUnidadOp, rol);
		
		if (idsUsuarios != null && idsUsuarios.length > 0)
			for (int idUsuario : idsUsuarios) {
				Map<String, Object> parameters = new HashMap<String, Object>(5);
				parameters.put("IDUSUARIO", idUsuario);
				parameters.put("IDUNIDAD", idUnidadOp);
				parameters.put("APROBAR", aprobar);
				parameters.put("REPORTES", cliente);
				parameters.put("UNIDAD", operador);
				insertarUsuarioPorUnidad.execute(parameters);
			}
	}

	@Override
	public List<UnidadOperativa> obtenerParaProcesar(String login, boolean esOperador, boolean esRevisor) {		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  distinct u.IDUNIDAD, u.NOMBRE, u.DEPARTAMENTO, u.DIRECCION, u.DISTRITO, ");
        sb.append("u.ELIMINADO, u.HORAINICIO, u.HORAFIN, u.IDCLIENTE, u.NROCAJONES, u.PROVINCIA ");
        sb.append("FROM unidadoperativa u, uoxuser uu, usuario us ");
        sb.append("WHERE u.IDUNIDAD = uu.IDUNIDAD and us.IDUSUARIO = uu.IDUSUARIO ");
        sb.append("and u.ELIMINADO = 'F' and us.login= :login ");
        if (esOperador && esRevisor) sb.append("and (uu.APROBAR = 'T' OR uu.UNIDAD = 'T')");
        else if (esOperador) sb.append(" and (uu.UNIDAD = 'T')");
        else if (esRevisor) sb.append(" and (uu.APROBAR = 'T')");
        
		List<UnidadOperativa> unidades = null;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("login", login);
		unidades = namedParameterJdbcTemplate.query(sb.toString(), args, new RowMapper<UnidadOperativa>(){
			@Override
			public UnidadOperativa mapRow(ResultSet rs, int rowNum) throws SQLException {
				UnidadOperativa unidadOp = new UnidadOperativa();
				unidadOp.setId(rs.getInt("IDUNIDAD"));
				unidadOp.setNombre(rs.getString("NOMBRE"));
				unidadOp.setDireccion(rs.getString("DIRECCION"));
				unidadOp.setDepartamento(rs.getString("DEPARTAMENTO"));
				unidadOp.setProvincia(rs.getString("PROVINCIA"));
				unidadOp.setDistrito(rs.getString("DISTRITO"));
				unidadOp.setHoraInicio(rs.getDate("HORAINICIO"));
				unidadOp.setHoraFin(rs.getDate("HORAFIN"));
				unidadOp.setNumeroCajones(rs.getInt("NROCAJONES"));
				unidadOp.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
										
				return unidadOp;
			}
			
		} );
		return unidades;
	}

	
}
