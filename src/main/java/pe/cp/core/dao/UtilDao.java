package pe.cp.core.dao;

import java.util.List;

public interface UtilDao {
	List<String> obtenerDepartamentos();
	List<String> obtenerProvincias(String departamento);
	List<String> obtenerDistritos(String departamento, String provincia);
}
