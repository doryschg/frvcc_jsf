package bo.gob.sin.sre.fac.frvcc.jsf.ventas.model;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaVentasDto extends ListaMensajesAplicacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<VentasCvDto> ventasResponse;

	private Integer TotalPaginas;
	private Long TotalElementos;

	public RespuestaVentasDto() {

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

	public List<VentasCvDto> getVentasResponse() {
		return ventasResponse;
	}

	public void setVentasResponse(List<VentasCvDto> ventasResponse) {
		this.ventasResponse = ventasResponse;
	}

}
