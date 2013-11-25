package pe.cp.core.service;

import java.util.List;

import pe.cp.core.domain.Tarifa;

public class TarifaServiceImpl implements TarifaService {

	@Override
	public int agregar(Tarifa tarifa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void actualizar(Tarifa tarifa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idTarifa) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tarifa> buscar(String categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//Validar que no existan tarifas repetidas por categoria en una unidad operativa	
	public boolean validar() {
		// TODO Auto-generated method stub
		return false;
	}

}
