package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaFormulariosDto extends ListaMensajesAplicacion implements Serializable {
	private List<FormularioCvDto> formulariosReponse;

    private Integer totalPaginas;
    private Long totalElementos;
    
    public RespuestaFormulariosDto()
    {
    	
    }

	public List<FormularioCvDto> getFormulariosReponse() {
		return formulariosReponse;
	}

	public void setFormulariosReponse(List<FormularioCvDto> formulariosReponse) {
		this.formulariosReponse = formulariosReponse;
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
