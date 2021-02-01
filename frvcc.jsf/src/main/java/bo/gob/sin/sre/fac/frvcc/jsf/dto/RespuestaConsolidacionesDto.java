package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

import java.io.Serializable;
import java.util.List;

public class RespuestaConsolidacionesDto extends ListaMensajesAplicacion implements Serializable {
	private List<ConsolidadorDto> consolidacionesResponse;

    private Integer totalPaginas;
    private Long totalElementos;

    public RespuestaConsolidacionesDto()
    {
    	
    }

	public List<ConsolidadorDto> getConsolidacionesResponse() {
		return consolidacionesResponse;
	}
	public void setConsolidacionesResponse(List<ConsolidadorDto> consolidacionesResponse) {
		this.consolidacionesResponse = consolidacionesResponse;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public Long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(Long totalElementos) {
		this.totalElementos = totalElementos;
	}
    
}
