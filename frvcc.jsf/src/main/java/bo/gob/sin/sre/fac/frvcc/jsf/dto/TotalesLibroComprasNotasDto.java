package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalesLibroComprasNotasDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String estadoCompraNotaId;
	private BigDecimal importeTotalCompraNota;
	private BigDecimal creditoFiscalCompraNota;
	private BigDecimal importeTotalCompraOriginal;
	
	
	public TotalesLibroComprasNotasDto(String estadoCompraNotaId, BigDecimal importeTotalCompraNota,
			BigDecimal creditoFiscalCompraNota, BigDecimal importeTotalCompraOriginal) {
		super();
		this.estadoCompraNotaId = estadoCompraNotaId;
		this.importeTotalCompraNota = importeTotalCompraNota;
		this.creditoFiscalCompraNota = creditoFiscalCompraNota;
		this.importeTotalCompraOriginal = importeTotalCompraOriginal;
	}
	public String getEstadoCompraNotaId() {
		return estadoCompraNotaId;
	}
	public void setEstadoCompraNotaId(String estadoCompraNotaId) {
		this.estadoCompraNotaId = estadoCompraNotaId;
	}
	public BigDecimal getImporteTotalCompraNota() {
		return importeTotalCompraNota;
	}
	public void setImporteTotalCompraNota(BigDecimal importeTotalCompraNota) {
		this.importeTotalCompraNota = importeTotalCompraNota;
	}
	public BigDecimal getCreditoFiscalCompraNota() {
		return creditoFiscalCompraNota;
	}
	public void setCreditoFiscalCompraNota(BigDecimal creditoFiscalCompraNota) {
		this.creditoFiscalCompraNota = creditoFiscalCompraNota;
	}
	public BigDecimal getImporteTotalCompraOriginal() {
		return importeTotalCompraOriginal;
	}
	public void setImporteTotalCompraOriginal(BigDecimal importeTotalCompraOriginal) {
		this.importeTotalCompraOriginal = importeTotalCompraOriginal;
	}
	
	
	
}
