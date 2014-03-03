package pe.cp.core.dao;

import java.util.List;

import pe.cp.core.domain.Tarifa;

public interface TarifaDao {
	int agregar(Tarifa tarifa);
	void actualizar(Tarifa tarifa);
	Tarifa buscar(int idTarifa);
	List<Tarifa> obtenerTarifas(int idUnidadOperativa, String categoria);
	void eliminarPorCategoria(int idUnidadOperativa, String categoria);
	List<String> obtenerCategorias(int unidadOperativa);
	int existePorMonto(Tarifa tarifa);
}
