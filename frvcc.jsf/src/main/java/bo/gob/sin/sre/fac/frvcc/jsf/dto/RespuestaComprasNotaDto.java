package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaComprasNotaDto extends ListaMensajesAplicacion implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<ComprasNotasCvDto> comprasNotasResponse;

	private Integer totalPaginas;
	private Long totalElementos;
	
	public RespuestaComprasNotaDto()
	{
		
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

	public List<ComprasNotasCvDto> getComprasNotasResponse() {
		return comprasNotasResponse;
	}

	public void setComprasNotasResponse(List<ComprasNotasCvDto> comprasNotasResponse) {
		this.comprasNotasResponse = comprasNotasResponse;
	}
	
}
