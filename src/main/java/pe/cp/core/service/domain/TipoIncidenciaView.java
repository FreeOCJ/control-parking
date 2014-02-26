package pe.cp.core.service.domain;

public class TipoIncidenciaView {
    private int id;
    private String tipo;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncidenciaView other = (IncidenciaView) obj;
		if (id != other.getId())
			return false;
		if (tipo == null) {
			if (other.getTipo() != null)
				return false;
		} else if (!tipo.equals(other.getTipo()))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return tipo;		
	}
}
