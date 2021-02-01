package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaComprasDto  extends ListaMensajesAplicacion implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<ComprasCvDto> comprasResponse;

	private Integer TotalPaginas;
	private Long TotalElementos;
	
	public RespuestaComprasDto()
	{
		
	}

	public List<ComprasCvDto> getComprasResponse() {
		return comprasResponse;
	}

	public void setComprasResponse(List<ComprasCvDto> comprasResponse) {
		this.comprasResponse = comprasResponse;
	}

	public Integer getTotalPaginas() {
		return TotalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		TotalPaginas = totalPaginas;
	}

	public Long getTotalElementos() {
		return TotalElementos;
	}

	public void setTotalElementos(Long totalElementos) {
		TotalElementos = totalElementos;
	}
	
}
