package pe.cp.core.service;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Auditoria;

public interface AuditoriaService {
	int agregar(Auditoria auditoria);
	List<Auditoria> buscar(String usuario, String tipoEvento, Date fechaInicio, Date fechaFin);	
}
