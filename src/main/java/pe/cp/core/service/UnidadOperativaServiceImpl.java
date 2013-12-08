package pe.cp.core.service;

import org.springframework.stereotype.Service;

import pe.cp.core.domain.UnidadOperativa;

@Service
public class UnidadOperativaServiceImpl implements UnidadOperativaService {

	@Override
	public int agregar(UnidadOperativa unidadOp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void actualizar(UnidadOperativa unidadOp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(int idUnidadOperativa) {
		// TODO Auto-generated method stub

	}

	@Override
	public int agregarTarifa(int idUnidadOp, int idTarifa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerTarifa(int idUnidadOperativa, int idTarifa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarOperador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerOperador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarAprobador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerAprobador(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarUsuarioCliente(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerUsuarioCliente(int idUnidadOp, int idUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

}
