package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaUsuariosDto extends ListaMensajesAplicacion implements Serializable{
	
	private List<UsuarioConsolidadorDto> usuariosReponse;
	private  Integer totalPaginas;
    private  Long totalElementos;
    
    public  RespuestaUsuariosDto()
    {
    	
    }
	public RespuestaUsuariosDto(List<UsuarioConsolidadorDto> usuariosReponse, Integer totalPaginas,
			Long totalElementos) {
		super();
		this.usuariosReponse = usuariosReponse;
		this.totalPaginas = totalPaginas;
		this.totalElementos = totalElementos;
	}
	public List<UsuarioConsolidadorDto> getUsuariosReponse() {
		return usuariosReponse;
	}
	public void setUsuariosReponse(List<UsuarioConsolidadorDto> usuariosReponse) {
		this.usuariosReponse = usuariosReponse;
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
