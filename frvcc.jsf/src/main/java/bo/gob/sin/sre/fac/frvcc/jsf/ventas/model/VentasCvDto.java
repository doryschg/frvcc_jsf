package bo.gob.sin.sre.fac.frvcc.jsf.ventas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VentasCvDto implements Serializable {

	private String id;
	private String recepcionId;
	private Long secuencia;
	private Long ifcProveedor;
	private Long nitProveedor;
	private String razonSocialProveedor;
	private String numeroDocumentoCliente;
	private String nombreCliente;
	private Long numeroFactura;
	private String codigoAutorizacion;
	private String modalidadId;
	private LocalDate fechaFactura;
	private BigDecimal importeTotalVenta;
	private BigDecimal importeIceIehdTasas;
	private BigDecimal importeExtento;
	private BigDecimal importeTasaCero;
	private BigDecimal subTotal;
	private BigDecimal descuento;
	private BigDecimal importeBaseDf;
	private BigDecimal debitoFiscal;
	private String codigoControl;
	private String origenId;
	private String estadoVentaId;
	private String estadoUsoId;
	private String impuestoUsoId;
	private Integer periodoUso;
	private String nombreArchivo;
	private String formularioId;
	private Long usuarioRegistro;
	private LocalDateTime fechaRegistro;
	private Long usuarioUltimaModificacion;
	private LocalDateTime fechaUltimaModificacion;
	private String estadoId;

	public VentasCvDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecepcionId() {
		return recepcionId;
	}

	public void setRecepcionId(String recepcionId) {
		this.recepcionId = recepcionId;
	}

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}

	public Long getIfcProveedor() {
		return ifcProveedor;
	}

	public void setIfcProveedor(Long ifcProveedor) {
		this.ifcProveedor = ifcProveedor;
	}

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}

	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Long getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}

	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public BigDecimal getImporteTotalVenta() {
		return importeTotalVenta;
	}

	public void setImporteTotalVenta(BigDecimal importeTotalVenta) {
		this.importeTotalVenta = importeTotalVenta;
	}

	public BigDecimal getImporteIceIehdTasas() {
		return importeIceIehdTasas;
	}

	public void setImporteIceIehdTasas(BigDecimal importeIceIehdTasas) {
		this.importeIceIehdTasas = importeIceIehdTasas;
	}

	public BigDecimal getImporteExtento() {
		return importeExtento;
	}

	public void setImporteExtento(BigDecimal importeExtento) {
		this.importeExtento = importeExtento;
	}

	public BigDecimal getImporteTasaCero() {
		return importeTasaCero;
	}

	public void setImporteTasaCero(BigDecimal importeTasaCero) {
		this.importeTasaCero = importeTasaCero;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getImporteBaseDf() {
		return importeBaseDf;
	}

	public void setImporteBaseDf(BigDecimal importeBaseDf) {
		this.importeBaseDf = importeBaseDf;
	}

	public BigDecimal getDebitoFiscal() {
		return debitoFiscal;
	}

	public void setDebitoFiscal(BigDecimal debitoFiscal) {
		this.debitoFiscal = debitoFiscal;
	}

	public String getCodigoControl() {
		return codigoControl;
	}

	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}

	public String getOrigenId() {
		return origenId;
	}

	public void setOrigenId(String origenId) {
		this.origenId = origenId;
	}

	public String getEstadoVentaId() {
		return estadoVentaId;
	}

	public void setEstadoVentaId(String estadoVentaId) {
		this.estadoVentaId = estadoVentaId;
	}

	public String getEstadoUsoId() {
		return estadoUsoId;
	}

	public void setEstadoUsoId(String estadoUsoId) {
		this.estadoUsoId = estadoUsoId;
	}

	public String getImpuestoUsoId() {
		return impuestoUsoId;
	}

	public void setImpuestoUsoId(String impuestoUsoId) {
		this.impuestoUsoId = impuestoUsoId;
	}

	public Integer getPeriodoUso() {
		return periodoUso;
	}

	public void setPeriodoUso(Integer periodoUso) {
		this.periodoUso = periodoUso;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getFormularioId() {
		return formularioId;
	}

	public void setFormularioId(String formularioId) {
		this.formularioId = formularioId;
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
