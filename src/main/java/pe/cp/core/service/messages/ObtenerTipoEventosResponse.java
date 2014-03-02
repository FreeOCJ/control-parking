package pe.cp.core.service.messages;

import java.util.List;

public class ObtenerTipoEventosResponse extends Response{
    List<String> tipoEventos;

	public List<String> getTipoEventos() {
		return tipoEventos;
	}

	public void setTipoEventos(List<String> tipoEventos) {
		this.tipoEventos = tipoEventos;
	}
}
