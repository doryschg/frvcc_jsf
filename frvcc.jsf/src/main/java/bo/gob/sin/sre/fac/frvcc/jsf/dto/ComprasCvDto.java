package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class ComprasCvDto implements Serializable {

	private String id;
	private String recepcionId;
	private Integer secuencia;
	private String tipoDocumentoId;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fechaFactura;
	private String fechaFacturaCadena;
	private Long ifcProveedor;
	private Long nitProveedor;
	private String razonSocialProveedor;
	private Long numeroFactura;
	private String numeroDui;
	private String codigoAutorizacion;
	private BigDecimal importeTotalCompra;
	private BigDecimal importeNoSujetoCf;
	private BigDecimal subtotal;
	private BigDecimal descuento;
	private BigDecimal importeBaseCf;
	private BigDecimal creditoFiscal;
	private String codigoControl;
	private Integer tipoSectorId;
	private String estadoCompraId;
	private String estadoUsoId;
	private String impuestoUsoId;
	private Integer periodoUso;
	private String nombreFormularioUso;
	private String formularioId;
	private String ventaId;
	private Long usuarioRegistro;
	private LocalDateTime fechaRegistro;
	private Long usuarioUltimaModificacion;
	private LocalDateTime fechaUltimaModificacion;
	private String estadoId;
	private String nombreCliente;
	private String origenId;
	private String tipoDocumentoCliente;
	private String numeroDocumentoCliente;
	private boolean ok;
	private Long totalPaginas;
	private String complementoDocumentoCliente;
	private Integer gestionUso;
	private String modalidadId;
	private String tipoCompraId;
	private String tipoObservacionId;
	private String marcaEspecialId;
	private String conDerechoCf;
	private Integer nroFila;
	
	public ComprasCvDto() {
		super();
	}

    public ComprasCvDto(Integer nroFila,LocalDate fechaFactura, Long nitProveedor, Long numeroFactura, String codigoAutorizacion, BigDecimal importeTotalCompra,BigDecimal importeBaseCf, String codigoControl, String tipoDocumentoCliente, String numeroDocumentoCliente, String complementoDocumentoCliente,String nombreCliente, String origenId) {
		this.id = UUID.randomUUID().toString();
	    this.nroFila = nroFila;
        this.fechaFactura = fechaFactura;
        this.nitProveedor = nitProveedor;
        this.numeroFactura = numeroFactura;
        this.codigoAutorizacion = codigoAutorizacion;
        this.importeTotalCompra = importeTotalCompra;
        this.importeBaseCf = importeBaseCf;
        this.codigoControl = codigoControl;
        this.tipoDocumentoCliente = tipoDocumentoCliente;
        this.numeroDocumentoCliente = numeroDocumentoCliente;
        this.complementoDocumentoCliente = complementoDocumentoCliente;
        this.nombreCliente = nombreCliente;
        this.origenId = origenId;
    }
    
    public ComprasCvDto(
    		Integer nroFila,
    		LocalDate fechaFactura, 
    		Long nitProveedor, 
    		Long numeroFactura,
    		String numeroDui,
    		String codigoAutorizacion, 
    		BigDecimal importeTotalCompra,
    		BigDecimal importeNoSujetoFc, 
    		BigDecimal descuento,
    		BigDecimal importeBaseCf,
    		String codigoControl, 
    		String tipoDocumentoCliente, 
    		String numeroDocumentoCliente, 
    		String complementoDocumentoCliente,
    		String nombreCliente, 
    		String origenId) {
  		this.id = UUID.randomUUID().toString();
  	    this.nroFila = nroFila;
          this.fechaFactura = fechaFactura;
          this.nitProveedor = nitProveedor;
          this.numeroFactura = numeroFactura;
          this.numeroDui = numeroDui;
          this.codigoAutorizacion = codigoAutorizacion;
          this.importeTotalCompra = importeTotalCompra;
          this.importeNoSujetoCf = importeNoSujetoFc;
          this.descuento = descuento;
          this.importeBaseCf = importeBaseCf;
          this.codigoControl = codigoControl;
          this.tipoDocumentoCliente = tipoDocumentoCliente;
          this.numeroDocumentoCliente = numeroDocumentoCliente;
          this.complementoDocumentoCliente = complementoDocumentoCliente;
          this.nombreCliente = nombreCliente;
          this.origenId = origenId;
      }

    public ComprasCvDto(String id, String recepcionId, Integer secuenciaNb,
                        String tipoDocumentoId, LocalDate fechaFactura, Long ifcProveedorNb, Long nitProveedor, String razonSocial,
                        Long numeroFactura, String numeroDui, String codigoAutorizacion, BigDecimal importeTotalCompra,
                        BigDecimal importeNoSujejtoFc, BigDecimal subtotal, BigDecimal descuento, BigDecimal importeBaseCf,
                        BigDecimal creditoFiscal, String codigoControl, Integer tipoSectorId, String estadoCompraId,
                        String estadoUsoId, String impuestoUsoId, Integer periodoUso, String nombreFormularioUso, String formularioId,
                        String ventaId, Long usuarioRegistro, LocalDateTime fechaRegistro, Long usuarioUltimaModificacion,
                        LocalDateTime fechaUltimaModificacion, String estadoId, String nombreCliente, String origenId,
                        String tipoDocumentoCliente, String numeroDocumentoCliente, String compraComplementoDocumentoCliente,
                        Integer gestionUso, String modalidadId) {
		super();
		this.id = id;
		this.recepcionId = recepcionId;
		this.secuencia = secuenciaNb;
		this.tipoDocumentoId = tipoDocumentoId;
		this.fechaFactura = fechaFactura;
		this.ifcProveedor = ifcProveedorNb;
		this.nitProveedor = nitProveedor;
		this.razonSocialProveedor = razonSocial;
		this.numeroFactura = numeroFactura;
		this.numeroDui = numeroDui;
		this.codigoAutorizacion = codigoAutorizacion;
		this.importeTotalCompra = importeTotalCompra;
		this.importeNoSujetoCf = importeNoSujejtoFc;
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.importeBaseCf = importeBaseCf;
		this.creditoFiscal = creditoFiscal;
		this.codigoControl = codigoControl;
		this.tipoSectorId = tipoSectorId;
		this.estadoCompraId = estadoCompraId;
		this.estadoUsoId = estadoUsoId;
		this.impuestoUsoId = impuestoUsoId;
		this.periodoUso = periodoUso;
		this.nombreFormularioUso = nombreFormularioUso;
		this.formularioId = formularioId;
		this.ventaId = ventaId;
		this.usuarioRegistro = usuarioRegistro;
		this.fechaRegistro = fechaRegistro;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoId = estadoId;
		this.nombreCliente = nombreCliente;
		this.origenId = origenId;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.numeroDocumentoCliente = numeroDocumentoCliente;
		//this.complementoDocumentoCliente = complementoDocumentoCliente;
		this.complementoDocumentoCliente=compraComplementoDocumentoCliente;
		this.gestionUso=gestionUso;
		this.modalidadId=modalidadId;
	}

    public Integer getNroFila() {
        return nroFila;
    }

    public void setNro(Integer nroFila) {
        this.nroFila = nroFila;
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

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuenciaNb) {
		this.secuencia = secuenciaNb;
	}

	public String getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(String tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Long getIfcProveedor() {
		return ifcProveedor;
	}

	public void setIfcProveedor(Long ifcProveedorNb) {
		this.ifcProveedor = ifcProveedorNb;
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
	
	public void setRazonSocialProveedor(String razonSocial) {
		this.razonSocialProveedor = razonSocial;
	}

	public Long getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroDui() {
		return numeroDui;
	}

	public void setNumeroDui(String numeroDui) {
		this.numeroDui = numeroDui;
	}

	public void setNroFila(Integer nroFila) {
		this.nroFila = nroFila;
	}

	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}

	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	public BigDecimal getImporteTotalCompra() {
		return importeTotalCompra;
	}

	public void setImporteTotalCompra(BigDecimal importeTotalCompra) {
		this.importeTotalCompra = importeTotalCompra;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getImporteBaseCf() {
		return importeBaseCf;
	}

	public void setImporteBaseCf(BigDecimal importeBaseCf) {
		this.importeBaseCf = importeBaseCf;
	}

	public BigDecimal getCreditoFiscal() {
		return creditoFiscal;
	}

	public void setCreditoFiscal(BigDecimal creditoFiscal) {
		this.creditoFiscal = creditoFiscal;
	}

	public String getCodigoControl() {
		return codigoControl;
	}

	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}

	public Integer getTipoSectorId() {
		return tipoSectorId;
	}

	public void setTipoSectorId(Integer tipoSectorId) {
		this.tipoSectorId = tipoSectorId;
	}

	public String getEstadoCompraId() {
		return estadoCompraId;
	}

	public void setEstadoCompraId(String estadoCompraId) {
		this.estadoCompraId = estadoCompraId;
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

	public void setComplementoDocumentoCliente(String compraComplementoDocumentoCliente) {
		this.complementoDocumentoCliente = compraComplementoDocumentoCliente;
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

	public Integer getGestionUso() {
		return gestionUso;
	}

	public void setGestionUso(Integer gestionUso) {
		this.gestionUso = gestionUso;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}


	public void setImporteNoSujetoCf(BigDecimal importeNoSujetoFc) {
		this.importeNoSujetoCf = importeNoSujetoFc;
	}

	public String getTipoCompraId() {
		return tipoCompraId;
	}

	public void setTipoCompraId(String tipoCompraId) {
		this.tipoCompraId = tipoCompraId;
	}

	public String getTipoObservacionId() {
		return tipoObservacionId;
	}

	public void setTipoObservacionId(String tipoObservacionId) {
		this.tipoObservacionId = tipoObservacionId;
	}

	public String getMarcaEspecialId() {
		return marcaEspecialId;
	}

	public void setMarcaEspecialId(String marcaEspecialId) {
		this.marcaEspecialId = marcaEspecialId;
	}

	public String getConDerechoCf() {
		return conDerechoCf;
	}

	public void setConDerechoCf(String conDerechoCf) {
		this.conDerechoCf = conDerechoCf;
	}

	public BigDecimal getImporteNoSujetoCf() {
		return importeNoSujetoCf;
	}

	public String getFechaFacturaCadena() {
		return fechaFacturaCadena;
	}

	public void setFechaFacturaCadena(String fechaFacturaCadena) {
		this.fechaFacturaCadena = fechaFacturaCadena;
	}

	@Override
	public String toString() {
		return "ComprasCvDto [id=" + id + ", recepcionId=" + recepcionId + ", secuenciaNb=" + secuencia
				+ ", tipoDocumentoId=" + tipoDocumentoId + ", fechaFactura=" + fechaFactura + ", ifcProveedorNb="
				+ ifcProveedor + ", nitProveedor=" + nitProveedor + ", razonSocialProveedor=" + razonSocialProveedor
				+ ", numeroFactura=" + numeroFactura + ", numeroDui=" + numeroDui + ", codigoAutorizacion="
				+ codigoAutorizacion + ", importeTotalCompra=" + importeTotalCompra + ", importeNoSujetoFc="
				+ importeNoSujetoCf + ", subtotal=" + subtotal + ", descuento=" + descuento + ", importeBaseCf="
				+ importeBaseCf + ", creditoFiscal=" + creditoFiscal + ", codigoControl=" + codigoControl
				+ ", tipoSectorId=" + tipoSectorId + ", estadoCompraId=" + estadoCompraId + ", estadoUsoId="
				+ estadoUsoId + ", impuestoUsoId=" + impuestoUsoId + ", periodoUso=" + periodoUso
				+ ", nombreFormularioUso=" + nombreFormularioUso + ", formularioId=" + formularioId + ", ventaId="
				+ ventaId + ", usuarioRegistro=" + usuarioRegistro + ", fechaRegistro=" + fechaRegistro
				+ ", usuarioUltimaModificacion=" + usuarioUltimaModificacion + ", fechaUltimaModificacion="
				+ fechaUltimaModificacion + ", estadoId=" + estadoId + ", nombreCliente=" + nombreCliente
				+ ", origenId=" + origenId + ", tipoDocumentoCliente=" + tipoDocumentoCliente
				+ ", numeroDocumentoCliente=" + numeroDocumentoCliente + ", ok=" + ok + ", totalPaginas=" + totalPaginas
				+ ", complementoDocumentoCliente=" + complementoDocumentoCliente + ", gestionUso=" + gestionUso
				+ ", modalidadId=" + modalidadId + ", tipoCompraId=" + tipoCompraId + ", tipoObservacionId="
				+ tipoObservacionId + ", marcaEspecialId=" + marcaEspecialId + ", conDerechoCf=" + conDerechoCf + "]";
	}

	
}
