package pe.cp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.UtilDao;

@Service
public class UtilServiceImpl implements UtilService {

	@Autowired
	private UtilDao utildao;
	
	@Override
	public List<String> obtenerDepartamentos() {
		return utildao.obtenerDepartamentos();
	}

	@Override
	public List<String> obtenerProvincias(String departamento) {
		return utildao.obtenerProvincias(departamento);
	}

	@Override
	public List<String> obtenerDistritos(String departamento, String provincia) {
		return utildao.obtenerDistritos(departamento, provincia);
	}

}
