package pe.cp.core.service;

import pe.cp.core.domain.Auditoria;
import pe.cp.core.service.messages.BuscarAuditoriaRequest;
import pe.cp.core.service.messages.BuscarAuditoriaResponse;

public interface AuditoriaService {
	int agregar(Auditoria auditoria);
	BuscarAuditoriaResponse buscar(BuscarAuditoriaRequest request);
}
