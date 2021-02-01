package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaCompraSingleDto extends ListaMensajesAplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private ComprasCvDto compraResponse;
	
	public RespuestaCompraSingleDto() {
		
	}

	public ComprasCvDto getCompraResponse() {
		return compraResponse;
	}

	public void setCompraResponse(ComprasCvDto compraResponse) {
		this.compraResponse = compraResponse;
	}
}
