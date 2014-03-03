package pe.cp.web.ui;

import com.vaadin.ui.UI;

public class NavegacionUtil {
   
	//Usuarios
	public static void irBuscarUsuarios() {
		
	}
	
	public static void irAgregarUsuarioSistema() { 
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVOUSUARIO);	
	}
	
	public static void irAgregarUsuarioCliente(int idCliente) { 
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVOUSUARIO + "/" + idCliente);	
		
	}
	
	public static void irEditarUsuario(int idCliente, int idUsuario) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARUSUARIO + "/" + idCliente + "/" + idUsuario);
	}
	
	//Clientes
	public static void irBuscarCliente() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.BUSCARCLIENTES);
	}
	
	public static void irAgregarCliente() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVOCLIENTE);
	}
	
	public static void irEditarCliente(int idCliente) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITARCLIENTE + "/" + idCliente);
	}
	
	//Login
	public static void irLogin() {
		UI.getCurrent().getNavigator().navigateTo("");
	}
	
	//Main
	public static void irMain() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.MAIN);
	}
	
	//Unidades Operativas
	public static void irAgregarUnidadOperativa(int idCliente) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA + "/" + idCliente + "/");	
	}
	
	public static void irEditarUnidadOperativa(int idCliente, int idUnidadOperativa) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.UNIDADOPERATIVA + "/" + idCliente + "/" + idUnidadOperativa);	
	}
	
	//Operaciones
	public static void irOperaciones() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);	
	}
	
	public static void irNuevaOperacion() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.NUEVA_OPERACION);	
	}
	
	public static void irEditarOperacion(int idOperacion) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.EDITAR_OPERACION + "/" + idOperacion);
	}
	
	//Incidencias
	public static void irMantIncidencia(int idOperacion, int idIncidencia) {
		String id = (idIncidencia > 0) ? String.valueOf(idIncidencia) : "";
		
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.INCIDENCIA + "/" + idOperacion + "/" + id);
	}
	
	//Contrasena
	public static void irRecuperarContrasena() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.RECUPERAR_CONTRASENA);
	}
	
	public static void irConfiguracionUsuario() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.CONFIGURAR_USUARIO);
	}
	
	//Reportes
	public static void irReportes() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTES);
	}
	
	public static void irReporteIngresosSalidas(int idCliente, int idUnidadOp) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTE_INGRESOS_SALIDAS + "/" + idCliente + "/" + idUnidadOp);
	}
	
	public static void irReporteRecaudacion(int idCliente, int idUnidadOp) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTE_RECAUDACION + "/" + idCliente + "/" + idUnidadOp);
	}
	
	public static void irReporteVisitas(int idCliente, int idUnidadOp) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTE_VISITAS + "/" + idCliente + "/" + idUnidadOp);
	}
	
	public static void irReporteIncidencias(int idCliente, int idUnidadOp) {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTES_INCIDENCIAS + "/" + idCliente + "/" + idUnidadOp);
	}
	
	public static void irReporteConsolidado() {
		UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.REPORTE_CONSOLIDADO);
	}
}
