package pe.cp.core.service.messages;

import java.util.List;

import pe.cp.core.service.domain.AuditoriaView;

public class BuscarAuditoriaResponse extends Response {
    private List<AuditoriaView> auditViews;

	public List<AuditoriaView> getAuditViews() {
		return auditViews;
	}

	public void setAuditViews(List<AuditoriaView> auditViews) {
		this.auditViews = auditViews;
	}
}
