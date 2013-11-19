package pe.cp.core.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

@Repository
public class UsuarioDaoImpl implements IUsuarioDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UsuarioDaoImpl(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);		
	}
	
	@Override
	public Respuesta actualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta existeUsuario(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta nuevo(Usuario usuario) {
		String sql = "insert into usuario(EMAIL,NOMBRES,APELLIDOS,CARGO,LOGIN,PASSWORD,IDCLIENTE) VALUES " + 
                "(?,?,?,?,?,?,?)";
		Object[] args = new Object[]{usuario.getEmail(),usuario.getNombres(),usuario.getApellidos(),usuario.getCargo(),usuario.getLogin(),usuario.getPassword(),1};
		jdbcTemplate.update(sql,args);
		usuario.setId(2);
		Respuesta rpta = new Respuesta();
		rpta.setResultado(true);
		rpta.setMensaje("Operación Exitosa");
		rpta.setData(usuario);
		return rpta;
	}

	@Override
	public Respuesta buscar(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Respuesta eliminar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
