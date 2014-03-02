package pe.cp.core.service;

import java.util.List;

public interface UtilService {
	List<String> obtenerDepartamentos();
	List<String> obtenerProvincias(String departamento);
	List<String> obtenerDistritos(String departamento, String provincia);
	void enviarEmail(String from, String pwd);
}
