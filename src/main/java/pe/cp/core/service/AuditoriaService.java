package pe.cp.core.service;

import pe.cp.core.domain.Auditoria;
import pe.cp.core.service.messages.BuscarAuditoriaRequest;
import pe.cp.core.service.messages.BuscarAuditoriaResponse;
import pe.cp.core.service.messages.ObtenerTipoEventosResponse;

public interface AuditoriaService {
	int agregar(Auditoria auditoria);
	BuscarAuditoriaResponse buscar(BuscarAuditoriaRequest request);
	ObtenerTipoEventosResponse obtenerTipoEventos();
}
