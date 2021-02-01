package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class PeticionAsociarCompraLibroDto implements Serializable{

	private String libroId;
	private String tipoCompraId;
	public PeticionAsociarCompraLibroDto()
	{
		
	}
	public PeticionAsociarCompraLibroDto(String libroId, String tipoCompraId) {
		super();
		this.libroId = libroId;
		this.tipoCompraId = tipoCompraId;
	}
	public String getLibroId() {
		return libroId;
	}
	public void setLibroId(String libroId) {
		this.libroId = libroId;
	}
	public String getTipoCompraId() {
		return tipoCompraId;
	}
	public void setTipoCompraId(String tipoCompraId) {
		this.tipoCompraId = tipoCompraId;
	}
	
	
}
