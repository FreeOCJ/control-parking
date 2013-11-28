package pe.cp.core.dao;

import pe.cp.core.domain.Tarifa;

public interface TarifaDao {
	int agregar(Tarifa tarifa);
	void actualizar(Tarifa tarifa);
	void eliminar(int idTarifa);
	Tarifa buscar(int idTarifa);
}
