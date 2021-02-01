package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaFormularioSingleDto extends ListaMensajesAplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private FormularioCvDto formularioResponse;
	
	public RespuestaFormularioSingleDto() {
		
	}

	public FormularioCvDto getFormularioResponse() {
		return formularioResponse;
	}

	public void setFormularioResponse(FormularioCvDto formularioResponse) {
		this.formularioResponse = formularioResponse;
	}
	
	
	
}
