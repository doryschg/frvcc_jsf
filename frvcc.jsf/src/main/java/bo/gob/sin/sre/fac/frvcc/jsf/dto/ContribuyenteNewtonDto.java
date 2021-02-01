package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class ContribuyenteNewtonDto extends ListaMensajesAplicacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String esNewton;
	public ContribuyenteNewtonDto()
	{
		
	}
	public String getEsNewton() {
		return esNewton;
	}
	public void setEsNewton(String esNewton) {
		this.esNewton = esNewton;
	}
	
}
