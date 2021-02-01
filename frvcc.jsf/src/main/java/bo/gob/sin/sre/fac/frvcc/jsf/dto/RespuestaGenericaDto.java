package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaGenericaDto extends ListaMensajesAplicacion{
    private String vResp;
    private Boolean respuesta;
    
	public String getvResp() {
		return vResp;
	}

	public void setvResp(String vResp) {
		this.vResp = vResp;
	}

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}
    
    
    
}
