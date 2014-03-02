package pe.cp.core.domain;

public class Tarifa implements IAuditInfo {
	private int id;
	private double monto;
	private String categoria;
	private boolean eliminado;
	private int idUnidadOperativa;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	public int getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(int idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}
	@Override
	public String getAuditInfo() {
		String audit = String.format("Tarifa [ID=%s, MONTO=%s, CATEGORIA=%s, ID UNIDAD OP=%s]", 
                String.valueOf(id), String.valueOf(monto), categoria, String.valueOf(idUnidadOperativa));
        return audit;
	}	
}
