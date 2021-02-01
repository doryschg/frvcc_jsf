package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

public class LibroPeticionDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String libroId;
	private List<String> listaIdsCompras;

	public String getLibroId() {
		return libroId;
	}

	public void setLibroId(String libroId) {
		this.libroId = libroId;
	}

	public List<String> getListaIdsCompras() {
		return listaIdsCompras;
	}

	public void setListaIdsCompras(List<String> listaIdsCompras) {
		this.listaIdsCompras = listaIdsCompras;
	}
	
}
