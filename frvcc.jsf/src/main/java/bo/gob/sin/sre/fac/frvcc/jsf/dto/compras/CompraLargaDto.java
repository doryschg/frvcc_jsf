package bo.gob.sin.sre.fac.frvcc.jsf.dto.compras;

import java.math.BigDecimal;
import java.time.LocalDate;

public final  class CompraLargaDto {
    private String id;
    private LocalDate fechaFactura;
    private Long nitProveedor;
    private Long numeroFactura;
    private String codigoAutorizacion;
    private BigDecimal importeBaseCf;
    private BigDecimal importeNoSujetoCf;
    private BigDecimal descuento;
    private String codigoControl;
    private Long usuarioRegistro;
    private String nombreCliente;
    private String tipoDocumentoCliente;
    private String numeroDocumentoCliente;
    private String complementoDocumentoCliente;
    private String tipoCompraId;
    private String origenId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Long getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Long nitProveedor) {
        this.nitProveedor = nitProveedor;
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

    public BigDecimal getImporteBaseCf() {
        return importeBaseCf;
    }

    public void setImporteBaseCf(BigDecimal importeBaseCf) {
        this.importeBaseCf = importeBaseCf;
    }

    public BigDecimal getImporteNoSujetoCf() {
        return importeNoSujetoCf;
    }

    public void setImporteNoSujetoCf(BigDecimal importeNoSujetoCf) {
        this.importeNoSujetoCf = importeNoSujetoCf;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public Long getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Long usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public String getTipoCompraId() {
        return tipoCompraId;
    }

    public void setTipoCompraId(String tipoCompraId) {
        this.tipoCompraId = tipoCompraId;
    }

    public String getOrigenId() {
        return origenId;
    }

    public void setOrigenId(String origenId) {
        this.origenId = origenId;
    }
}
