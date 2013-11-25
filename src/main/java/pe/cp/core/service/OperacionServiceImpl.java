package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Operacion;

public class OperacionServiceImpl implements OperacionService {

	@Override
	public int agregar(Operacion op) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void editar(Operacion op) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idOperacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public int agregarIncidencia(int idOperacion, int idIncidencia) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerIncidencia(int idOperacion, int idIncidencia) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Operacion> buscar(String nombreUnidadOp, Date fechaOp,
			String estadoOp) {
		// TODO Auto-generated method stub
		return null;
	}

}
