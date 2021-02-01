package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SucursalUsuarioDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long ifcAgente;
	private Long nitAgente;
	private Integer nroSucursal;
	private Long usuarioReceptor;
	private String loginUsuarioReceptor;
	private String nombreUsuarioReceptor;
	private String estadoAsignacionId;
	private LocalDate fechaDesde;
	private LocalDate fechaHasta;
	private Long usuarioRegistro;
	private LocalDateTime fechaRegistro;
	private Long usuarioUltimaModificacion;
	private LocalDateTime fechaUltimaModificacion;
	private String estadoId;
	
	public SucursalUsuarioDto(String id, Long ifcAgente, Long nitAgente, Integer nroSucursal, Long usuarioReceptor,
			String loginUsuarioReceptor, String nombreUsuarioReceptor, String estadoAsignacionId, LocalDate fechaDesde,
			LocalDate fechaHasta, Long usuarioRegistro, LocalDateTime fechaRegistro, Long usuarioUltimaModificacion,
			LocalDateTime fechaUltimaModificacion, String estadoId) {
		super();
		this.id = id;
		this.ifcAgente = ifcAgente;
		this.nitAgente = nitAgente;
		this.nroSucursal = nroSucursal;
		this.usuarioReceptor = usuarioReceptor;
		this.loginUsuarioReceptor = loginUsuarioReceptor;
		this.nombreUsuarioReceptor = nombreUsuarioReceptor;
		this.estadoAsignacionId = estadoAsignacionId;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.usuarioRegistro = usuarioRegistro;
		this.fechaRegistro = fechaRegistro;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoId = estadoId;
	}
	public SucursalUsuarioDto()
	{
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getIfcAgente() {
		return ifcAgente;
	}
	public void setIfcAgente(Long ifcAgente) {
		this.ifcAgente = ifcAgente;
	}
	public Long getNitAgente() {
		return nitAgente;
	}
	public void setNitAgente(Long nitAgente) {
		this.nitAgente = nitAgente;
	}
	public Integer getNroSucursal() {
		return nroSucursal;
	}
	public void setNroSucursal(Integer nroSucursal) {
		this.nroSucursal = nroSucursal;
	}
	public Long getUsuarioReceptor() {
		return usuarioReceptor;
	}
	public void setUsuarioReceptor(Long usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}
	public String getLoginUsuarioReceptor() {
		return loginUsuarioReceptor;
	}
	public void setLoginUsuarioReceptor(String loginUsuarioReceptor) {
		this.loginUsuarioReceptor = loginUsuarioReceptor;
	}
	public String getNombreUsuarioReceptor() {
		return nombreUsuarioReceptor;
	}
	public void setNombreUsuarioReceptor(String nombreUsuarioReceptor) {
		this.nombreUsuarioReceptor = nombreUsuarioReceptor;
	}
	public String getEstadoAsignacionId() {
		return estadoAsignacionId;
	}
	public void setEstadoAsignacionId(String estadoAsignacionId) {
		this.estadoAsignacionId = estadoAsignacionId;
	}
	public LocalDate getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public LocalDate getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Long getUsuarioUltimaModificacion() {
		return usuarioUltimaModificacion;
	}
	public void setUsuarioUltimaModificacion(Long usuarioUltimaModificacion) {
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
	}
	public LocalDateTime getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public String getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}
	
	
}
