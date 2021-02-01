package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaLibrosDto extends ListaMensajesAplicacion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<LibroCvDto> librosReponse;

    private Integer totalPaginas;
    private Long totalElementos;
    
    public RespuestaLibrosDto()
    {
    	
    }

	public List<LibroCvDto> getLibrosReponse() {
		return librosReponse;
	}

	public void setLibrosReponse(List<LibroCvDto> librosReponse) {
		this.librosReponse = librosReponse;
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
