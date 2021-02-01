package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

import java.io.Serializable;
import java.util.List;




public class RespuestaConsultaResumenLCVDto extends ListaMensajesAplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private   List<ConsultaLcvResponse> listaConsultaLcvResponse;
	public List<ConsultaLcvResponse> getListaConsultaLcvResponse() {
		return listaConsultaLcvResponse;
	}
	public void setListaConsultaLcvResponse(List<ConsultaLcvResponse> listaConsultaLcvResponse) {
		this.listaConsultaLcvResponse = listaConsultaLcvResponse;
	}
	public RespuestaConsultaResumenLCVDto() {

		// TODO Auto-generated constructor stub
	}


	
}
