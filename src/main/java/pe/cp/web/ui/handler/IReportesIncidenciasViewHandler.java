package pe.cp.web.ui.handler;

import java.sql.Date;

public interface IReportesIncidenciasViewHandler {
	String obtenerNombreCliente(int idCliente);
	String obtenerNombreUnidadOperativa(int idUnidadOp);
	void generarReportePDFMensual(int idUnidadOp,Date fecha,String ruta);
	void generarReporteXLSMensual(int idUnidadOp,Date fecha,String ruta);
	String generarRuta();
}
