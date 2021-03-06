package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class RespuestaSucursalUsuarioDto extends ListaMensajesAplicacion implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SucursalUsuarioDto> sucursalesUsuariosReponse;
    private Integer totalPaginas;
    private Long totalElementos;
    
    public RespuestaSucursalUsuarioDto()
    {
    	
    }

	public List<SucursalUsuarioDto> getSucursalesUsuariosReponse() {
		return sucursalesUsuariosReponse;
	}

	public void setSucursalesUsuariosReponse(List<SucursalUsuarioDto> sucursalesUsuariosReponse) {
		this.sucursalesUsuariosReponse = sucursalesUsuariosReponse;
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
