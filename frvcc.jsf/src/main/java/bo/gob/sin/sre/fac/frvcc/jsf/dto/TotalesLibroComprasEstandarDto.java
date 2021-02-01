package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class TotalesLibroComprasEstandarDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoCompraId;
	private String descripcionTipoCompra;
	private BigDecimal totalCompras;
	private BigDecimal importeNoSujetoCf;
	private BigDecimal subtotal;
	private BigDecimal descuentos;
	private BigDecimal importeBaseCf;
	private BigDecimal creditoFiscal;
	
	
	
	public TotalesLibroComprasEstandarDto(String tipoCompraId, String descripcionTipoCompra, BigDecimal totalCompras,
			BigDecimal importeNoSujetoCf, BigDecimal subtotal, BigDecimal descuentos, BigDecimal importeBaseCf,
			BigDecimal creditoFiscal) {
		super();
		this.tipoCompraId = tipoCompraId;
		this.descripcionTipoCompra = descripcionTipoCompra;
		this.totalCompras = totalCompras;
		this.importeNoSujetoCf = importeNoSujetoCf;
		this.subtotal = subtotal;
		this.descuentos = descuentos;
		this.importeBaseCf = importeBaseCf;
		this.creditoFiscal = creditoFiscal;
	}



	public String getTipoCompraId() {
		return tipoCompraId;
	}



	public void setTipoCompraId(String tipoCompraId) {
		this.tipoCompraId = tipoCompraId;
	}



	public String getDescripcionTipoCompra() {
		return descripcionTipoCompra;
	}



	public void setDescripcionTipoCompra(String descripcionTipoCompra) {
		this.descripcionTipoCompra = descripcionTipoCompra;
	}



	public BigDecimal getTotalCompras() {
		return totalCompras;
	}



	public void setTotalCompras(BigDecimal totalCompras) {
		this.totalCompras = totalCompras;
	}



	public BigDecimal getImporteNoSujetoCf() {
		return importeNoSujetoCf;
	}



	public void setImporteNoSujetoCf(BigDecimal importeNoSujetoCf) {
		this.importeNoSujetoCf = importeNoSujetoCf;
	}



	public BigDecimal getSubtotal() {
		return subtotal;
	}



	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}



	public BigDecimal getDescuentos() {
		return descuentos;
	}



	public void setDescuentos(BigDecimal descuentos) {
		this.descuentos = descuentos;
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



	public TotalesLibroComprasEstandarDto()
	{
		
	}

	
	

	
	
}
