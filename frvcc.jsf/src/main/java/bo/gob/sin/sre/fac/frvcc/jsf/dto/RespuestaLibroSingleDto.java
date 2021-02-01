package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaLibroSingleDto extends ListaMensajesAplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private LibroCvDto libroResponse;
	
	public RespuestaLibroSingleDto() {
		
	}

	public LibroCvDto getLibroResponse() {
		return libroResponse;
	}

	public void setLibroResponse(LibroCvDto libroResponse) {
		this.libroResponse = libroResponse;
	}
	
	
	
}
