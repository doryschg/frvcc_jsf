package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import bo.gob.sin.scn.empa.caco.dto.SucursalDto;

public class SucursalUsuarioRelacionadoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private SucursalDto sucursal;
	private Long idUsuarioAsig;
	private String nroDocumentoUsuarioAsig;
	private String nombreUsuarioAsig;
	private String loginUsuarioAsig;
	
	public SucursalUsuarioRelacionadoDto(String id,SucursalDto sucursal, Long idUsuarioAsig, String nroDocumentoUsuarioAsig,
			String nombreUsuarioAsig) {
		super();
		this.id=id;
		this.sucursal = sucursal;
		this.idUsuarioAsig = idUsuarioAsig;
		this.nroDocumentoUsuarioAsig = nroDocumentoUsuarioAsig;
		this.nombreUsuarioAsig = nombreUsuarioAsig;
	}
	public SucursalUsuarioRelacionadoDto()
	{
		
	}
	public SucursalDto getSucursal() {
		return sucursal;
	}
	public void setSucursal(SucursalDto sucursal) {
		this.sucursal = sucursal;
	}
	public Long getIdUsuarioAsig() {
		return idUsuarioAsig;
	}
	public void setIdUsuarioAsig(Long idUsuarioAsig) {
		this.idUsuarioAsig = idUsuarioAsig;
	}
	public String getNroDocumentoUsuarioAsig() {
		return nroDocumentoUsuarioAsig;
	}
	public void setNroDocumentoUsuarioAsig(String nroDocumentoUsuarioAsig) {
		this.nroDocumentoUsuarioAsig = nroDocumentoUsuarioAsig;
	}
	public String getNombreUsuarioAsig() {
		return nombreUsuarioAsig;
	}
	public void setNombreUsuarioAsig(String nombreUsuarioAsig) {
		this.nombreUsuarioAsig = nombreUsuarioAsig;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginUsuarioAsig() {
		return loginUsuarioAsig;
	}
	public void setLoginUsuarioAsig(String loginUsuarioAsig) {
		this.loginUsuarioAsig = loginUsuarioAsig;
	}
	
}
