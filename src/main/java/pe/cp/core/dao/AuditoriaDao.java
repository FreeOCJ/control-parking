package pe.cp.core.dao;

import java.util.Date;
import java.util.List;

import pe.cp.core.domain.Auditoria;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.IAuditInfo;
import pe.cp.core.domain.Usuario;

public interface AuditoriaDao {
	int agregar(Auditoria auditoria);
	Auditoria buscar(int idAuditoria);
	int agregarAuditoria(Usuario modificaor, IAuditInfo auditInfo, String tipoEvento);
	List<Auditoria> buscar(String tipoEvento, Date fechaInicio, Date fechaFin, String login);
}
