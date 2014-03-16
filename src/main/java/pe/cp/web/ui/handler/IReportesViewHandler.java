package pe.cp.web.ui.handler;

public interface IReportesViewHandler {
	void irReporteIncidencias();
	void irReporteRecaudacion();
	void irReporteIngresosSalidas();
	void irReporteVisitas();
	void irReporteConsolidado();
	void cargar();
	void cargarUnidadesOperativaPorCliente();
	void validarUsuario();
}
