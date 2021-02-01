package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class ComprasNotasCvDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long ifcProveedor;
	private Long nitProveedor;
	private String razonSocialProveedor;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fechaNota;
	private Long numeroNota;
	private String codigoAutorizacion;
	private BigDecimal importeTotalNota;
	private BigDecimal creditoFiscalNota;
	private String codigoControlNota;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fechaFacturaOriginal;
	private Long numeroFacturaOriginal;
	private String codigoAutorizacionOriginal;
	private BigDecimal importeTotalOriginal;
	private String codigoControlOriginal;
	
	private String compraId;
	private String estadoNotaId;
	private String tipoDocumentoCliente;
	private String numeroDocumentoCliente;
	private String complementoDocumentoCliente;
	private String nombreCliente;
	private String origenId;
	private String estadoUsoId;
	private String impuestoUsoId;
	private Integer gestionUso;
	private Integer periodoUso;
	private String nombreFormularioUso;
	private String formularioId;
	private String ventaId;
	private String recepcionId;
	private Integer secuencia;
	private String tipoObservacionId;
	private Long usuarioRegistro;
	private LocalDateTime fechaRegistro;
	private Long usuarioUltimaModificacion;
	private LocalDateTime fechaUltimaModificacion;
	private String estadoId;
	
	private boolean ok;
	private Long totalPaginas;

	public ComprasNotasCvDto() {
		super();
	}

	public ComprasNotasCvDto(String id, Long ifcProveedor, Long nitProveedor, String razonSocialProveedor,
			Long numeroNota, String codigoAutorizacion, LocalDate fechaNota, BigDecimal importeTotalNota,
			BigDecimal creditoFiscalNota, String codigoControlNota, LocalDate fechaFacturaOriginal,
			Long numeroFacturaOriginal, String codigoAutorizacionOriginal, BigDecimal importeTotalOriginal,
			String codigoControlOriginal, String compraId, String estadoNotaId, String tipoDocumentoCliente,
			String numeroDocumentoCliente, String complementoDocumentoCliente, String nombreCliente, String origenId,
			String estadoUsoId, String impuestoUsoId, Integer gestionUso, Integer periodoUso,
			String nombreFormularioUso, String formularioId, String ventaId, String recepcionId, Integer secuencia,
			String tipoObservacionId, Long usuarioRegistro, LocalDateTime fechaRegistro, Long usuarioUltimaModificacion,
			LocalDateTime fechaUltimaModificacion, String estadoId) {
		super();
		this.id = id;
		this.ifcProveedor = ifcProveedor;
		this.nitProveedor = nitProveedor;
		this.razonSocialProveedor = razonSocialProveedor;
		this.numeroNota = numeroNota;
		this.codigoAutorizacion = codigoAutorizacion;
		this.fechaNota = fechaNota;
		this.importeTotalNota = importeTotalNota;
		this.creditoFiscalNota = creditoFiscalNota;
		this.codigoControlNota = codigoControlNota;
		
		this.fechaFacturaOriginal = fechaFacturaOriginal;
		this.numeroFacturaOriginal = numeroFacturaOriginal;
		this.codigoAutorizacionOriginal = codigoAutorizacionOriginal;
		this.importeTotalOriginal = importeTotalOriginal;
		this.codigoControlOriginal = codigoControlOriginal;
		
		this.compraId = compraId;
		this.estadoNotaId = estadoNotaId;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.numeroDocumentoCliente = numeroDocumentoCliente;
		this.complementoDocumentoCliente = complementoDocumentoCliente;
		this.nombreCliente = nombreCliente;
		this.origenId = origenId;
		this.estadoUsoId = estadoUsoId;
		this.impuestoUsoId = impuestoUsoId;
		this.gestionUso = gestionUso;
		this.periodoUso = periodoUso;
		this.nombreFormularioUso = nombreFormularioUso;
		
		this.formularioId = formularioId;
		this.ventaId = ventaId;
		this.recepcionId = recepcionId;
		this.secuencia = secuencia;
		this.tipoObservacionId = tipoObservacionId;
		this.usuarioRegistro = usuarioRegistro;
		this.fechaRegistro = fechaRegistro;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoId = estadoId;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Long getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(Long numeroNota) {
		this.numeroNota = numeroNota;
	}

	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}

	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	public LocalDate getFechaNota() {
		return fechaNota;
	}

	public void setFechaNota(LocalDate fechaNota) {
		this.fechaNota = fechaNota;
	}


	public BigDecimal getImporteTotalNota() {
		return importeTotalNota;
	}

	public void setImporteTotalNota(BigDecimal importeTotalNota) {
		this.importeTotalNota = importeTotalNota;
	}

	public BigDecimal getCreditoFiscalNota() {
		return creditoFiscalNota;
	}

	public void setCreditoFiscalNota(BigDecimal creditoFiscalNota) {
		this.creditoFiscalNota = creditoFiscalNota;
	}

	public String getCodigoControlNota() {
		return codigoControlNota;
	}

	public void setCodigoControlNota(String codigoControlNota) {
		this.codigoControlNota = codigoControlNota;
	}

	public LocalDate getFechaFacturaOriginal() {
		return fechaFacturaOriginal;
	}

	public void setFechaFacturaOriginal(LocalDate fechaFacturaOriginal) {
		this.fechaFacturaOriginal = fechaFacturaOriginal;
	}

	public Long getNumeroFacturaOriginal() {
		return numeroFacturaOriginal;
	}

	public void setNumeroFacturaOriginal(Long numeroFacturaOriginal) {
		this.numeroFacturaOriginal = numeroFacturaOriginal;
	}

	public String getCodigoAutorizacionOriginal() {
		return codigoAutorizacionOriginal;
	}

	public void setCodigoAutorizacionOriginal(String codigoAutorizacionOriginal) {
		this.codigoAutorizacionOriginal = codigoAutorizacionOriginal;
	}

	public BigDecimal getImporteTotalOriginal() {
		return importeTotalOriginal;
	}

	public void setImporteTotalOriginal(BigDecimal importeTotalOriginal) {
		this.importeTotalOriginal = importeTotalOriginal;
	}

	public String getEstadoNotaId() {
		return estadoNotaId;
	}

	public void setEstadoNotaId(String estadoNotaId) {
		this.estadoNotaId = estadoNotaId;
	}

	public String getTipoDocumentoCliente() {
		return tipoDocumentoCliente;
	}

	public void setTipoDocumentoCliente(String tipoDocumentoCliente) {
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getComplementoDocumentoCliente() {
		return complementoDocumentoCliente;
	}

	public void setComplementoDocumentoCliente(String complementoDocumentoCliente) {
		this.complementoDocumentoCliente = complementoDocumentoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getOrigenId() {
		return origenId;
	}

	public void setOrigenId(String origenId) {
		this.origenId = origenId;
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

	public Integer getGestionUso() {
		return gestionUso;
	}

	public void setGestionUso(Integer gestionUso) {
		this.gestionUso = gestionUso;
	}

	public Integer getPeriodoUso() {
		return periodoUso;
	}

	public void setPeriodoUso(Integer periodoUso) {
		this.periodoUso = periodoUso;
	}

	public String getNombreFormularioUso() {
		return nombreFormularioUso;
	}

	public void setNombreFormularioUso(String nombreFormularioUso) {
		this.nombreFormularioUso = nombreFormularioUso;
	}

	public String getFormularioId() {
		return formularioId;
	}

	public void setFormularioId(String formularioId) {
		this.formularioId = formularioId;
	}

	public String getVentaId() {
		return ventaId;
	}

	public void setVentaId(String ventaId) {
		this.ventaId = ventaId;
	}

	public String getRecepcionId() {
		return recepcionId;
	}

	public void setRecepcionId(String recepcionId) {
		this.recepcionId = recepcionId;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public String getTipoObservacionId() {
		return tipoObservacionId;
	}

	public void setTipoObservacionId(String tipoObservacionId) {
		this.tipoObservacionId = tipoObservacionId;
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

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public String getCodigoControlOriginal() {
		return codigoControlOriginal;
	}

	public void setCodigoControlOriginal(String codigoControlOriginal) {
		this.codigoControlOriginal = codigoControlOriginal;
	}

	public String getCompraId() {
		return compraId;
	}

	public void setCompraId(String compraId) {
		this.compraId = compraId;
	}
	
}






