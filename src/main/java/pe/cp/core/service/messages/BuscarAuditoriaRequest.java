package pe.cp.core.service.messages;

import java.util.Date;

public class BuscarAuditoriaRequest {
    private String tipoEvento;
    private Date fechaInicio;
    private Date fechaFin;
    private String login;
    
    public BuscarAuditoriaRequest(String tipoEvento, String login, Date fechaInicio, Date fechaFin) {
    	this.tipoEvento = tipoEvento;
    	this.login = login;
    	this.fechaInicio = fechaInicio;
    	this.fechaFin = fechaFin;
    }
    
    public String getLogin() {
    	return login;
    }
    
    public String getTipoEvento() {
    	return tipoEvento;
    }
    
    public Date getFechaInicio() {
    	return fechaInicio;
    }
    
    public Date getFechaFin() {
    	return fechaFin;
    }
}
