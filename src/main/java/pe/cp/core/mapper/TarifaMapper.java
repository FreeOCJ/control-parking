package pe.cp.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pe.cp.core.dao.UnidadOperativaDao;
import pe.cp.core.domain.Tarifa;

@Component
public class TarifaMapper implements RowMapper<Tarifa> {
	
	@Autowired
	private UnidadOperativaDao uoDao;
	
	public Tarifa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tarifa tarifa = new Tarifa();
		tarifa.setId(rs.getInt("IDTARIFA"));
		tarifa.setMonto(rs.getDouble("MONTOTARIFA"));	
		tarifa.setCategoria(rs.getString("CATEGORIA"));
		tarifa.setIdUnidadOperativa(rs.getInt("IDUNIDADOP"));
		tarifa.setEliminado(rs.getString("ELIMINADO").equals("F") ? false : true);
		return tarifa;
	}

}
