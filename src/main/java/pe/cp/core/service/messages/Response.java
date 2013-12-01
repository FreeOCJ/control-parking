package pe.cp.core.service.messages;

public class Response {
	private String mensaje;
	private boolean resultadoEjecucion;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isResultadoEjecucion() {
		return resultadoEjecucion;
	}
	public void setResultadoEjecucion(boolean resultadoEjecucion) {
		this.resultadoEjecucion = resultadoEjecucion;
	}
}
