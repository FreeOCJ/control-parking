package pe.cp.web.ui.view.operaciones;

public interface IEditarOperacionHandler {
   void cargar();
   void guardar();
   void enviarAprobar();
   void agregarIncidencia();
   void editarIncidencia(int idIncidencia);
   void eliminarIncidencia(int idIncidencia);
   void aprobar();
   void validarUsuario();
   boolean validarEdicion();
   void calcularTotalTickets();
   void calcularTotalIngresoSalidas();
}
