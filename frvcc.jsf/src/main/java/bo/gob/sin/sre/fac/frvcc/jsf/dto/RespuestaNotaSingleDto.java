package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaNotaSingleDto extends ListaMensajesAplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private ComprasNotasCvDto compraNotaResponse;
	
	public RespuestaNotaSingleDto() {
		
	}

	public ComprasNotasCvDto getCompraNotaResponse() {
		return compraNotaResponse;
	}

	public void setCompraNotaResponse(ComprasNotasCvDto compraNotaResponse) {
		this.compraNotaResponse = compraNotaResponse;
	}

}
