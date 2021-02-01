package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class LibroCvDto implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
    private Long nit;
    private Long ifc;
    private String razonSocial;
    private Long codAdministracion;
	private String administracion;
    private Integer mesPeriodo;
    private Integer anioPeriodo;
    private String nombreLibro;
    private Integer cantidadCompras;
    private BigDecimal totalCompras;
    
    private Integer cantidadComprasCfElec;
	private Integer cantidadComprasCfIpn;
	private Integer cantidadComprasCfOtras;
	private Integer cantidadComprasSdCf;
	private BigDecimal totalComprasCfElec;
	private BigDecimal totalComprasCfIpn;
	private BigDecimal totalComprasCfOtras;
	private BigDecimal totalComprasSdCf;
	private BigDecimal importeBaseCf;
	private BigDecimal importeBaseCfElec;
	private BigDecimal importeBaseCfIpn;
	private BigDecimal importeBaseCfOtras;
	private BigDecimal determinacionPagoCf;
	private BigDecimal determinacionPagoCfIpn;
	private BigDecimal determinacionPagoCfOtras;
	private BigDecimal determinacionPagoSdCf;
	private BigDecimal determinacionPago;
    
    private Integer cantidadComprasNotas;
    private BigDecimal totalComprasNotas;
    private Integer cantidadVentas;
    private BigDecimal totalVentas;
    
    private Integer  cantidadVentasCfElec;
	private Integer  cantidadVentasCfOtras;
	private BigDecimal totalVentasCfElec;
	private BigDecimal totalVentasCfOtras;
	private Integer cantidadVentasSdCf;
	private Integer cantidadVentasSdCfOtras;
	private Integer cantidadVentasSdCfSrg;
	private BigDecimal totalVentasSdCf;
	private BigDecimal totalVentasSdCfOtras;
	private BigDecimal totalVentasSdCfSrg;
    
    private Integer cantidadVentasNotas;
    private BigDecimal totalVentasNotas;
    private String  tipoPresentacionId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fechaPresentacion;
    private String periodicidadId;
    private String cantidadPeriodicidad;
    private Long numeroOrden;
    private String estadoLibroId;
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaEstado;
    private Integer numeroEnvio;
    private String libroRectificadoId;
    private String conMovimiento;
    private Long usuarioRegistro;
    private LocalDateTime fechaRegistro;
    private Long usuarioUltimaModificacion;
    private LocalDateTime fechaUltimaModificacion;
    private String estadoId;
    private List<String> comprasId;
    private List<String> comprasNotasId;
    
    public LibroCvDto() {
    	
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getNit() {
		return nit;
	}

	public void setNit(Long nit) {
		this.nit = nit;
	}

	public Long getIfc() {
		return ifc;
	}

	public void setIfc(Long ifc) {
		this.ifc = ifc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getCodAdministracion() {
		return codAdministracion;
	}

	public void setCodAdministracion(Long codAdministracion) {
		this.codAdministracion = codAdministracion;
	}

	public String getAdministracion() {
		return administracion;
	}

	public void setAdministracion(String administracion) {
		this.administracion = administracion;
	}

	public Integer getMesPeriodo() {
		return mesPeriodo;
	}

	public void setMesPeriodo(Integer mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}

	public Integer getAnioPeriodo() {
		return anioPeriodo;
	}

	public void setAnioPeriodo(Integer anioPeriodo) {
		this.anioPeriodo = anioPeriodo;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public Integer getCantidadCompras() {
		return cantidadCompras;
	}

	public void setCantidadCompras(Integer cantidadCompras) {
		this.cantidadCompras = cantidadCompras;
	}

	public BigDecimal getTotalCompras() {
		return totalCompras;
	}

	public void setTotalCompras(BigDecimal totalCompras) {
		this.totalCompras = totalCompras;
	}

	public Integer getCantidadComprasCfElec() {
		return cantidadComprasCfElec;
	}

	public void setCantidadComprasCfElec(Integer cantidadComprasCfElec) {
		this.cantidadComprasCfElec = cantidadComprasCfElec;
	}

	public Integer getCantidadComprasCfIpn() {
		return cantidadComprasCfIpn;
	}

	public void setCantidadComprasCfIpn(Integer cantidadComprasCfIpn) {
		this.cantidadComprasCfIpn = cantidadComprasCfIpn;
	}

	public Integer getCantidadComprasCfOtras() {
		return cantidadComprasCfOtras;
	}

	public void setCantidadComprasCfOtras(Integer cantidadComprasCfOtras) {
		this.cantidadComprasCfOtras = cantidadComprasCfOtras;
	}

	public Integer getCantidadComprasSdCf() {
		return cantidadComprasSdCf;
	}

	public void setCantidadComprasSdCf(Integer cantidadComprasSdCf) {
		this.cantidadComprasSdCf = cantidadComprasSdCf;
	}

	public BigDecimal getTotalComprasCfElec() {
		return totalComprasCfElec;
	}

	public void setTotalComprasCfElec(BigDecimal totalComprasCfElec) {
		this.totalComprasCfElec = totalComprasCfElec;
	}

	public BigDecimal getTotalComprasCfIpn() {
		return totalComprasCfIpn;
	}

	public void setTotalComprasCfIpn(BigDecimal totalComprasCfIpn) {
		this.totalComprasCfIpn = totalComprasCfIpn;
	}

	public BigDecimal getTotalComprasCfOtras() {
		return totalComprasCfOtras;
	}

	public void setTotalComprasCfOtras(BigDecimal totalComprasCfOtras) {
		this.totalComprasCfOtras = totalComprasCfOtras;
	}

	public BigDecimal getTotalComprasSdCf() {
		return totalComprasSdCf;
	}

	public void setTotalComprasSdCf(BigDecimal totalComprasSdCf) {
		this.totalComprasSdCf = totalComprasSdCf;
	}

	public BigDecimal getImporteBaseCf() {
		return importeBaseCf;
	}

	public void setImporteBaseCf(BigDecimal importeBaseCf) {
		this.importeBaseCf = importeBaseCf;
	}

	public BigDecimal getImporteBaseCfElec() {
		return importeBaseCfElec;
	}

	public void setImporteBaseCfElec(BigDecimal importeBaseCfElec) {
		this.importeBaseCfElec = importeBaseCfElec;
	}

	public BigDecimal getImporteBaseCfIpn() {
		return importeBaseCfIpn;
	}

	public void setImporteBaseCfIpn(BigDecimal importeBaseCfIpn) {
		this.importeBaseCfIpn = importeBaseCfIpn;
	}

	public BigDecimal getImporteBaseCfOtras() {
		return importeBaseCfOtras;
	}

	public void setImporteBaseCfOtras(BigDecimal importeBaseCfOtras) {
		this.importeBaseCfOtras = importeBaseCfOtras;
	}

	public BigDecimal getDeterminacionPagoCf() {
		return determinacionPagoCf;
	}

	public void setDeterminacionPagoCf(BigDecimal determinacionPagoCf) {
		this.determinacionPagoCf = determinacionPagoCf;
	}

	public BigDecimal getDeterminacionPagoCfIpn() {
		return determinacionPagoCfIpn;
	}

	public void setDeterminacionPagoCfIpn(BigDecimal determinacionPagoCfIpn) {
		this.determinacionPagoCfIpn = determinacionPagoCfIpn;
	}

	public BigDecimal getDeterminacionPagoCfOtras() {
		return determinacionPagoCfOtras;
	}

	public void setDeterminacionPagoCfOtras(BigDecimal determinacionPagoCfOtras) {
		this.determinacionPagoCfOtras = determinacionPagoCfOtras;
	}

	public BigDecimal getDeterminacionPagoSdCf() {
		return determinacionPagoSdCf;
	}

	public void setDeterminacionPagoSdCf(BigDecimal determinacionPagoSdCf) {
		this.determinacionPagoSdCf = determinacionPagoSdCf;
	}

	public BigDecimal getDeterminacionPago() {
		return determinacionPago;
	}

	public void setDeterminacionPago(BigDecimal determinacionPago) {
		this.determinacionPago = determinacionPago;
	}

	public Integer getCantidadComprasNotas() {
		return cantidadComprasNotas;
	}

	public void setCantidadComprasNotas(Integer cantidadComprasNotas) {
		this.cantidadComprasNotas = cantidadComprasNotas;
	}

	public BigDecimal getTotalComprasNotas() {
		return totalComprasNotas;
	}

	public void setTotalComprasNotas(BigDecimal totalComprasNotas) {
		this.totalComprasNotas = totalComprasNotas;
	}

	public Integer getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(Integer cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public BigDecimal getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}

	public Integer getCantidadVentasCfElec() {
		return cantidadVentasCfElec;
	}

	public void setCantidadVentasCfElec(Integer cantidadVentasCfElec) {
		this.cantidadVentasCfElec = cantidadVentasCfElec;
	}

	public Integer getCantidadVentasCfOtras() {
		return cantidadVentasCfOtras;
	}

	public void setCantidadVentasCfOtras(Integer cantidadVentasCfOtras) {
		this.cantidadVentasCfOtras = cantidadVentasCfOtras;
	}

	public BigDecimal getTotalVentasCfElec() {
		return totalVentasCfElec;
	}

	public void setTotalVentasCfElec(BigDecimal totalVentasCfElec) {
		this.totalVentasCfElec = totalVentasCfElec;
	}

	public BigDecimal getTotalVentasCfOtras() {
		return totalVentasCfOtras;
	}

	public void setTotalVentasCfOtras(BigDecimal totalVentasCfOtras) {
		this.totalVentasCfOtras = totalVentasCfOtras;
	}

	public Integer getCantidadVentasSdCf() {
		return cantidadVentasSdCf;
	}

	public void setCantidadVentasSdCf(Integer cantidadVentasSdCf) {
		this.cantidadVentasSdCf = cantidadVentasSdCf;
	}

	public Integer getCantidadVentasSdCfOtras() {
		return cantidadVentasSdCfOtras;
	}

	public void setCantidadVentasSdCfOtras(Integer cantidadVentasSdCfOtras) {
		this.cantidadVentasSdCfOtras = cantidadVentasSdCfOtras;
	}

	public Integer getCantidadVentasSdCfSrg() {
		return cantidadVentasSdCfSrg;
	}

	public void setCantidadVentasSdCfSrg(Integer cantidadVentasSdCfSrg) {
		this.cantidadVentasSdCfSrg = cantidadVentasSdCfSrg;
	}

	public BigDecimal getTotalVentasSdCf() {
		return totalVentasSdCf;
	}

	public void setTotalVentasSdCf(BigDecimal totalVentasSdCf) {
		this.totalVentasSdCf = totalVentasSdCf;
	}

	public BigDecimal getTotalVentasSdCfOtras() {
		return totalVentasSdCfOtras;
	}

	public void setTotalVentasSdCfOtras(BigDecimal totalVentasSdCfOtras) {
		this.totalVentasSdCfOtras = totalVentasSdCfOtras;
	}

	public BigDecimal getTotalVentasSdCfSrg() {
		return totalVentasSdCfSrg;
	}

	public void setTotalVentasSdCfSrg(BigDecimal totalVentasSdCfSrg) {
		this.totalVentasSdCfSrg = totalVentasSdCfSrg;
	}

	public Integer getCantidadVentasNotas() {
		return cantidadVentasNotas;
	}

	public void setCantidadVentasNotas(Integer cantidadVentasNotas) {
		this.cantidadVentasNotas = cantidadVentasNotas;
	}

	public BigDecimal getTotalVentasNotas() {
		return totalVentasNotas;
	}

	public void setTotalVentasNotas(BigDecimal totalVentasNotas) {
		this.totalVentasNotas = totalVentasNotas;
	}

	public String getTipoPresentacionId() {
		return tipoPresentacionId;
	}

	public void setTipoPresentacionId(String tipoPresentacionId) {
		this.tipoPresentacionId = tipoPresentacionId;
	}

	public LocalDateTime getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(LocalDateTime fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getPeriodicidadId() {
		return periodicidadId;
	}

	public void setPeriodicidadId(String periodicidadId) {
		this.periodicidadId = periodicidadId;
	}

	public String getCantidadPeriodicidad() {
		return cantidadPeriodicidad;
	}

	public void setCantidadPeriodicidad(String cantidadPeriodicidad) {
		this.cantidadPeriodicidad = cantidadPeriodicidad;
	}

	public Long getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getEstadoLibroId() {
		return estadoLibroId;
	}

	public void setEstadoLibroId(String estadoLibroId) {
		this.estadoLibroId = estadoLibroId;
	}

	public LocalDate getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(LocalDate fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public Integer getNumeroEnvio() {
		return numeroEnvio;
	}

	public void setNumeroEnvio(Integer numeroEnvio) {
		this.numeroEnvio = numeroEnvio;
	}

	public String getLibroRectificadoId() {
		return libroRectificadoId;
	}

	public void setLibroRectificadoId(String libroRectificadoId) {
		this.libroRectificadoId = libroRectificadoId;
	}

	public String getConMovimiento() {
		return conMovimiento;
	}

	public void setConMovimiento(String conMovimiento) {
		this.conMovimiento = conMovimiento;
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

	public List<String> getComprasId() {
		return comprasId;
	}

	public void setComprasId(List<String> comprasId) {
		this.comprasId = comprasId;
	}

	public List<String> getComprasNotasId() {
		return comprasNotasId;
	}

	public void setComprasNotasId(List<String> comprasNotasId) {
		this.comprasNotasId = comprasNotasId;
	}

	public LibroCvDto(String id, Long nit, Long ifc, String razonSocial, Long codAdministracion, String administracion,
			Integer mesPeriodo, Integer anioPeriodo, String nombreLibro, Integer cantidadCompras,
			BigDecimal totalCompras, Integer cantidadComprasCfElec, Integer cantidadComprasCfIpn,
			Integer cantidadComprasCfOtras, Integer cantidadComprasSdCf, BigDecimal totalComprasCfElec,
			BigDecimal totalComprasCfIpn, BigDecimal totalComprasCfOtras, BigDecimal totalComprasSdCf,
			BigDecimal importeBaseCf, BigDecimal importeBaseCfElec, BigDecimal importeBaseCfIpn,
			BigDecimal importeBaseCfOtras, BigDecimal determinacionPagoCf, BigDecimal determinacionPagoCfIpn,
			BigDecimal determinacionPagoCfOtras, BigDecimal determinacionPagoSdCf, BigDecimal determinacionPago,
			Integer cantidadComprasNotas, BigDecimal totalComprasNotas, Integer cantidadVentas, BigDecimal totalVentas,
			Integer cantidadVentasCfElec, Integer cantidadVentasCfOtras, BigDecimal totalVentasCfElec,
			BigDecimal totalVentasCfOtras, Integer cantidadVentasSdCf, Integer cantidadVentasSdCfOtras,
			Integer cantidadVentasSdCfSrg, BigDecimal totalVentasSdCf, BigDecimal totalVentasSdCfOtras,
			BigDecimal totalVentasSdCfSrg, Integer cantidadVentasNotas, BigDecimal totalVentasNotas,
			String tipoPresentacionId, LocalDateTime fechaPresentacion, String periodicidadId,
			String cantidadPeriodicidad, Long numeroOrden, String estadoLibroId, LocalDate fechaEstado,
			Integer numeroEnvio, String libroRectificadoId, String conMovimiento, Long usuarioRegistro,
			LocalDateTime fechaRegistro, Long usuarioUltimaModificacion, LocalDateTime fechaUltimaModificacion,
			String estadoId, List<String> comprasId, List<String> comprasNotasId) {
		super();
		this.id = id;
		this.nit = nit;
		this.ifc = ifc;
		this.razonSocial = razonSocial;
		this.codAdministracion = codAdministracion;
		this.administracion = administracion;
		this.mesPeriodo = mesPeriodo;
		this.anioPeriodo = anioPeriodo;
		this.nombreLibro = nombreLibro;
		this.cantidadCompras = cantidadCompras;
		this.totalCompras = totalCompras;
		this.cantidadComprasCfElec = cantidadComprasCfElec;
		this.cantidadComprasCfIpn = cantidadComprasCfIpn;
		this.cantidadComprasCfOtras = cantidadComprasCfOtras;
		this.cantidadComprasSdCf = cantidadComprasSdCf;
		this.totalComprasCfElec = totalComprasCfElec;
		this.totalComprasCfIpn = totalComprasCfIpn;
		this.totalComprasCfOtras = totalComprasCfOtras;
		this.totalComprasSdCf = totalComprasSdCf;
		this.importeBaseCf = importeBaseCf;
		this.importeBaseCfElec = importeBaseCfElec;
		this.importeBaseCfIpn = importeBaseCfIpn;
		this.importeBaseCfOtras = importeBaseCfOtras;
		this.determinacionPagoCf = determinacionPagoCf;
		this.determinacionPagoCfIpn = determinacionPagoCfIpn;
		this.determinacionPagoCfOtras = determinacionPagoCfOtras;
		this.determinacionPagoSdCf = determinacionPagoSdCf;
		this.determinacionPago = determinacionPago;
		this.cantidadComprasNotas = cantidadComprasNotas;
		this.totalComprasNotas = totalComprasNotas;
		this.cantidadVentas = cantidadVentas;
		this.totalVentas = totalVentas;
		this.cantidadVentasCfElec = cantidadVentasCfElec;
		this.cantidadVentasCfOtras = cantidadVentasCfOtras;
		this.totalVentasCfElec = totalVentasCfElec;
		this.totalVentasCfOtras = totalVentasCfOtras;
		this.cantidadVentasSdCf = cantidadVentasSdCf;
		this.cantidadVentasSdCfOtras = cantidadVentasSdCfOtras;
		this.cantidadVentasSdCfSrg = cantidadVentasSdCfSrg;
		this.totalVentasSdCf = totalVentasSdCf;
		this.totalVentasSdCfOtras = totalVentasSdCfOtras;
		this.totalVentasSdCfSrg = totalVentasSdCfSrg;
		this.cantidadVentasNotas = cantidadVentasNotas;
		this.totalVentasNotas = totalVentasNotas;
		this.tipoPresentacionId = tipoPresentacionId;
		this.fechaPresentacion = fechaPresentacion;
		this.periodicidadId = periodicidadId;
		this.cantidadPeriodicidad = cantidadPeriodicidad;
		this.numeroOrden = numeroOrden;
		this.estadoLibroId = estadoLibroId;
		this.fechaEstado = fechaEstado;
		this.numeroEnvio = numeroEnvio;
		this.libroRectificadoId = libroRectificadoId;
		this.conMovimiento = conMovimiento;
		this.usuarioRegistro = usuarioRegistro;
		this.fechaRegistro = fechaRegistro;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoId = estadoId;
		this.comprasId = comprasId;
		this.comprasNotasId = comprasNotasId;
	}

	
    

	
    
	    
}
