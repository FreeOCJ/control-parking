package pe.cp.web.ui.handler;

public interface IReporteIngresosSalidasHandler {
    void cargar();
    void generarReporteDiario(String formato);
    void generarReporteSemanal(String formato);
    void generarReporteMensual(String formato);
    void generarReporteAnual(String formato);
}
