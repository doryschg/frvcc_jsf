package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConsolidadorDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private  String id;

	private Long ifcAgente;
	private Long nitAgente;
	private String razonSocialAgente;
	private Integer sucursal;
	private String direccionSucursal;
	private String departamentoSucursal;
	private Long administracion;
	private Integer gestion;
	private Integer periodo;
	private String periodoDescripcion;
	private String administracionDescripcion;
	private Long cantidadFormularios;
	private Integer totalCantidadFacturas;
	private Integer totalCantidadOtrasFacturas;
	private Integer totalCantidad7rgFacturas;
	private BigInteger totalImporteFacturas;
	private BigInteger totalImporteOtrasFacturas;
	private BigInteger totalImporte7rgFacturas;
	private BigInteger totalDeterminacionPagoCuenta;
	private BigInteger totalPagoCuenta;
	private String estadoConsolidadoId;
	private LocalDateTime fechaConsolidado;
	private Long usuarioConsolidador;
	private String loginUsuarioConsolidador;
	private String nombreConsolidador;
	private Long usuarioRegistro;
	private LocalDateTime  fechaRegistro;
	private Long usuarioUltimaModificacion;
	private LocalDateTime fechaUltimaModificacion;
	private String estadoId;
	private String tipoId;
	private String consolidacionPrincipalId;
	private List<String> formularios;
	private Integer totalCantidadIpnFacturas;
	private BigInteger totalImporteIpnFacturas;
	private LocalDate fechaImpresion;

	public ConsolidadorDto(){

	}

	public ConsolidadorDto(Integer sucursal, String direccionSucursal, String departamentoSucursal, Long cantidadFormularios, BigInteger totalDeterminacionPagoCuenta, BigInteger totalPagoCuenta) {
		this.sucursal = sucursal;
		this.direccionSucursal = direccionSucursal;
		this.departamentoSucursal = departamentoSucursal;
		this.cantidadFormularios = cantidadFormularios;
		this.totalDeterminacionPagoCuenta = totalDeterminacionPagoCuenta;
		this.totalPagoCuenta = totalPagoCuenta;
	}

	public ConsolidadorDto(LocalDate fechaImpresion, String id, Long ifcAgente, Long nitAgente, String razonSocialAgente,
						   Long administracion, Integer gestion, Integer periodo, Long usuarioConsolidador,
						   String loginUsuarioConsolidador, String nombreConsolidador, Long cantidadFormularios,
						   BigInteger totalDeterminacionPagoCuenta, BigInteger totalPagoCuenta,
						   String administracionDescripcion, String periodoDescripcion) {
		this.fechaImpresion = fechaImpresion;
		this.id = id;
		this.ifcAgente = ifcAgente;
		this.nitAgente = nitAgente;
		this.razonSocialAgente = razonSocialAgente;
		this.administracion = administracion;
		this.gestion = gestion;
		this.periodo = periodo;
		this.usuarioConsolidador = usuarioConsolidador;
		this.loginUsuarioConsolidador = loginUsuarioConsolidador;
		this.nombreConsolidador = nombreConsolidador;
		this.cantidadFormularios = cantidadFormularios;
		this.totalDeterminacionPagoCuenta = totalDeterminacionPagoCuenta;
		this.totalPagoCuenta = totalPagoCuenta;
		this.administracionDescripcion=administracionDescripcion;
		this.periodoDescripcion=periodoDescripcion;
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

	public String getRazonSocialAgente() {
		return razonSocialAgente;
	}

	public void setRazonSocialAgente(String razonSocialAgente) {
		this.razonSocialAgente = razonSocialAgente;
	}

	public Integer getSucursal() {
		return sucursal;
	}

	public void setSucursal(Integer sucursal) {
		this.sucursal = sucursal;
	}

	public String getDireccionSucursal() {
		return direccionSucursal;
	}

	public void setDireccionSucursal(String direccionSucursal) {
		this.direccionSucursal = direccionSucursal;
	}

	public String getDepartamentoSucursal() {
		return departamentoSucursal;
	}

	public void setDepartamentoSucursal(String departamentoSucursal) {
		this.departamentoSucursal = departamentoSucursal;
	}

	public Long getAdministracion() {
		return administracion;
	}

	public void setAdministracion(Long administracion) {
		this.administracion = administracion;
	}

	public Integer getGestion() {
		return gestion;
	}

	public void setGestion(Integer gestion) {
		this.gestion = gestion;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Long getCantidadFormularios() {
		return cantidadFormularios;
	}

	public void setCantidadFormularios(Long cantidadFormularios) {
		this.cantidadFormularios = cantidadFormularios;
	}

	public Integer getTotalCantidadFacturas() {
		return totalCantidadFacturas;
	}

	public void setTotalCantidadFacturas(Integer totalCantidadFacturas) {
		this.totalCantidadFacturas = totalCantidadFacturas;
	}

	public Integer getTotalCantidadOtrasFacturas() {
		return totalCantidadOtrasFacturas;
	}

	public void setTotalCantidadOtrasFacturas(Integer totalCantidadOtrasFacturas) {
		this.totalCantidadOtrasFacturas = totalCantidadOtrasFacturas;
	}

	public Integer getTotalCantidad7rgFacturas() {
		return totalCantidad7rgFacturas;
	}

	public void setTotalCantidad7rgFacturas(Integer totalCantidad7rgFacturas) {
		this.totalCantidad7rgFacturas = totalCantidad7rgFacturas;
	}

	public BigInteger getTotalImporteFacturas() {
		return totalImporteFacturas;
	}

	public void setTotalImporteFacturas(BigInteger totalImporteFacturas) {
		this.totalImporteFacturas = totalImporteFacturas;
	}

	public BigInteger getTotalImporteOtrasFacturas() {
		return totalImporteOtrasFacturas;
	}

	public void setTotalImporteOtrasFacturas(BigInteger totalImporteOtrasFacturas) {
		this.totalImporteOtrasFacturas = totalImporteOtrasFacturas;
	}

	public BigInteger getTotalImporte7rgFacturas() {
		return totalImporte7rgFacturas;
	}

	public void setTotalImporte7rgFacturas(BigInteger totalImporte7rgFacturas) {
		this.totalImporte7rgFacturas = totalImporte7rgFacturas;
	}

	public BigInteger getTotalDeterminacionPagoCuenta() {
		return totalDeterminacionPagoCuenta;
	}

	public void setTotalDeterminacionPagoCuenta(BigInteger totalDeterminacionPagoCuenta) {
		this.totalDeterminacionPagoCuenta = totalDeterminacionPagoCuenta;
	}

	public BigInteger getTotalPagoCuenta() {
		return totalPagoCuenta;
	}

	public void setTotalPagoCuenta(BigInteger totalPagoCuenta) {
		this.totalPagoCuenta = totalPagoCuenta;
	}

	public String getEstadoConsolidadoId() {
		return estadoConsolidadoId;
	}

	public void setEstadoConsolidadoId(String estadoConsolidadoId) {
		this.estadoConsolidadoId = estadoConsolidadoId;
	}

	public LocalDateTime getFechaConsolidado() {
		return fechaConsolidado;
	}

	public void setFechaConsolidado(LocalDateTime fechaConsolidado) {
		this.fechaConsolidado = fechaConsolidado;
	}

	public Long getUsuarioConsolidador() {
		return usuarioConsolidador;
	}

	public void setUsuarioConsolidador(Long usuarioConsolidador) {
		this.usuarioConsolidador = usuarioConsolidador;
	}

	public String getLoginUsuarioConsolidador() {
		return loginUsuarioConsolidador;
	}

	public void setLoginUsuarioConsolidador(String loginUsuarioConsolidador) {
		this.loginUsuarioConsolidador = loginUsuarioConsolidador;
	}

	public String getNombreConsolidador() {
		return nombreConsolidador;
	}

	public void setNombreConsolidador(String nombreConsolidador) {
		this.nombreConsolidador = nombreConsolidador;
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

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getConsolidacionPrincipalId() {
		return consolidacionPrincipalId;
	}

	public void setConsolidacionPrincipalId(String consolidacionPrincipalId) {
		this.consolidacionPrincipalId = consolidacionPrincipalId;
	}

	public List<String> getFormularios() {
		return formularios;
	}

	public void setFormularios(List<String> formularios) {
		this.formularios = formularios;
	}

	public Integer getTotalCantidadIpnFacturas() {
		return totalCantidadIpnFacturas;
	}

	public void setTotalCantidadIpnFacturas(Integer totalCantidadIpnFacturas) {
		this.totalCantidadIpnFacturas = totalCantidadIpnFacturas;
	}

	public BigInteger getTotalImporteIpnFacturas() {
		return totalImporteIpnFacturas;
	}

	public void setTotalImporteIpnFacturas(BigInteger totalImporteIpnFacturas) {
		this.totalImporteIpnFacturas = totalImporteIpnFacturas;
	}

	public LocalDate getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(LocalDate fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getPeriodoDescripcion() {
		return periodoDescripcion;
	}

	public void setPeriodoDescripcion(String periodoDescripcion) {
		this.periodoDescripcion = periodoDescripcion;
	}

	public String getAdministracionDescripcion() {
		return administracionDescripcion;
	}

	public void setAdministracionDescripcion(String administracionDescripcion) {
		this.administracionDescripcion = administracionDescripcion;
	}
}
