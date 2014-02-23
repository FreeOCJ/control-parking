package pe.cp.core.dao;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.OperacionDetalle;

public interface OperacionDao {
	int agregar(Operacion op);
	void modificar(Operacion op);
	void eliminar(int idOperacion);
	Operacion buscar(int idOperacion);
	List<Operacion> buscar(int idUnidadOp, Date fechaOp, String estado);
	int agregarOperacionDetalle(OperacionDetalle opDetalle);
	void actualizarOperacionDetalle(OperacionDetalle opDetalle);
	List<OperacionDetalle> obtenerDetalles(int idOperacion);
}
