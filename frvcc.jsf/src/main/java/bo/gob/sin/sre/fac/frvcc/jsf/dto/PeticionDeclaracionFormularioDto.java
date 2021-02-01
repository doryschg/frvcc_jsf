package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class PeticionDeclaracionFormularioDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer codAdmin;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCodAdmin() {
		return codAdmin;
	}
	public void setCodAdmin(Integer codAdmin) {
		this.codAdmin = codAdmin;
	}
	
	

}
