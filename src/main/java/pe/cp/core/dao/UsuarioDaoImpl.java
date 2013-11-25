package pe.cp.core.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.cp.core.domain.Respuesta;
import pe.cp.core.domain.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UsuarioDaoImpl(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);		
	}
	
	@Override
	public void actualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existeUsuario(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int agregar(Usuario usuario) {
		String sql = "insert into usuario(EMAIL,NOMBRES,APELLIDOS,CARGO,LOGIN,PASSWORD,IDCLIENTE) VALUES " + 
                "(?,?,?,?,?,?,?)";
		Object[] args = new Object[]{usuario.getEmail(),usuario.getNombres(),usuario.getApellidos(),usuario.getCargo(),usuario.getLogin(),usuario.getPassword(),1};
		jdbcTemplate.update(sql,args);
		usuario.setId(2);
		Respuesta rpta = new Respuesta();
		rpta.setResultado(true);
		rpta.setMensaje("Operación Exitosa");
		rpta.setData(usuario);
		return 0;
	}

	@Override
	public List<Usuario> buscar(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub		
	}

	
}
