package pe.cp.core.service;

import java.util.List;

import pe.cp.core.domain.Tarifa;

public interface TarifaService {
	int agregar(Tarifa tarifa);
	void actualizar(Tarifa tarifa);
	void eliminar(int idTarifa);
	List<Tarifa> buscar(String categoria);
	boolean validar();
}
