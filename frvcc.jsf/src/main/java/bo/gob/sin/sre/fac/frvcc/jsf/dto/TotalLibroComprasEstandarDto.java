package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

import com.ibm.icu.math.BigDecimal;

public class TotalLibroComprasEstandarDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BigDecimal totalComprasIAG;
	public BigDecimal importeNoSujetoCfIAG;
	public BigDecimal subtotalIAG;
	public BigDecimal descuentosIAG;
	public BigDecimal importeBaseCfIAG;
	public BigDecimal creditoFiscalIAG;
	
	public BigDecimal totalComprasIANG;
	public BigDecimal importeNoSujetoCfIANG;
	public BigDecimal subtotalIANG;
	public BigDecimal descuentosIANG;
	public BigDecimal importeBaseCfIANG;
	public BigDecimal creditoFiscalIANG;
	
	public BigDecimal totalComprasSP;
	public BigDecimal importeNoSujetoCfSP;
	public BigDecimal subtotalSP;
	public BigDecimal descuentosSP;
	public BigDecimal importeBaseCfSP;
	public BigDecimal creditoFiscalSP;
	
	public BigDecimal totalComprasE;
	public BigDecimal importeNoSujetoCfE;
	public BigDecimal subtotalE;
	public BigDecimal descuentosE;
	public BigDecimal importeBaseCfE;
	public BigDecimal creditoFiscalE;
	
	public BigDecimal totalComprasIE;
	public BigDecimal importeNoSujetoCfIE;
	public BigDecimal subtotalIE;
	public BigDecimal descuentosIE;
	public BigDecimal importeBaseCfIE;
	public BigDecimal creditoFiscalIE;
	
	public TotalLibroComprasEstandarDto() {
		super();
	}
	
	public void inicializar()
	{
		totalComprasIAG = new BigDecimal("0");
		importeNoSujetoCfIAG = new BigDecimal("0");
		subtotalIAG = new BigDecimal("0");
		descuentosIAG = new BigDecimal("0");
		importeBaseCfIAG = new BigDecimal("0");
		creditoFiscalIAG = new BigDecimal("0");
		totalComprasIANG = new BigDecimal("0");
		importeNoSujetoCfIANG = new BigDecimal("0");
		subtotalIANG = new BigDecimal("0");
		descuentosIANG = new BigDecimal("0");
		importeBaseCfIANG = new BigDecimal("0");
		creditoFiscalIANG = new BigDecimal("0");
		totalComprasSP = new BigDecimal("0");
		importeNoSujetoCfSP = new BigDecimal("0");
		subtotalSP = new BigDecimal("0");
		descuentosSP = new BigDecimal("0");
		importeBaseCfSP = new BigDecimal("0");
		creditoFiscalSP = new BigDecimal("0");
		totalComprasE = new BigDecimal("0");
		importeNoSujetoCfE = new BigDecimal("0");
		subtotalE = new BigDecimal("0");
		descuentosE = new BigDecimal("0");
		importeBaseCfE = new BigDecimal("0");
		creditoFiscalE = new BigDecimal("0");
		totalComprasIE = new BigDecimal("0");
		importeNoSujetoCfIE = new BigDecimal("0");
		subtotalIE = new BigDecimal("0");
		descuentosIE = new BigDecimal("0");
		importeBaseCfIE = new BigDecimal("0");
		creditoFiscalIE = new BigDecimal("0");
	}
	public BigDecimal getTotalComprasIAG() {
		return totalComprasIAG;
	}
	public void setTotalComprasIAG(BigDecimal totalComprasIAG) {
		this.totalComprasIAG = totalComprasIAG;
	}
	public BigDecimal getImporteNoSujetoCfIAG() {
		return importeNoSujetoCfIAG;
	}
	public void setImporteNoSujetoCfIAG(BigDecimal importeNoSujetoCfIAG) {
		this.importeNoSujetoCfIAG = importeNoSujetoCfIAG;
	}
	public BigDecimal getSubtotalIAG() {
		return subtotalIAG;
	}
	public void setSubtotalIAG(BigDecimal subtotalIAG) {
		this.subtotalIAG = subtotalIAG;
	}
	public BigDecimal getDescuentosIAG() {
		return descuentosIAG;
	}
	public void setDescuentosIAG(BigDecimal descuentosIAG) {
		this.descuentosIAG = descuentosIAG;
	}
	public BigDecimal getImporteBaseCfIAG() {
		return importeBaseCfIAG;
	}
	public void setImporteBaseCfIAG(BigDecimal importeBaseCfIAG) {
		this.importeBaseCfIAG = importeBaseCfIAG;
	}
	public BigDecimal getCreditoFiscalIAG() {
		return creditoFiscalIAG;
	}
	public void setCreditoFiscalIAG(BigDecimal creditoFiscalIAG) {
		this.creditoFiscalIAG = creditoFiscalIAG;
	}
	public BigDecimal getTotalComprasIANG() {
		return totalComprasIANG;
	}
	public void setTotalComprasIANG(BigDecimal totalComprasIANG) {
		this.totalComprasIANG = totalComprasIANG;
	}
	public BigDecimal getImporteNoSujetoCfIANG() {
		return importeNoSujetoCfIANG;
	}
	public void setImporteNoSujetoCfIANG(BigDecimal importeNoSujetoCfIANG) {
		this.importeNoSujetoCfIANG = importeNoSujetoCfIANG;
	}
	public BigDecimal getSubtotalIANG() {
		return subtotalIANG;
	}
	public void setSubtotalIANG(BigDecimal subtotalIANG) {
		this.subtotalIANG = subtotalIANG;
	}
	public BigDecimal getDescuentosIANG() {
		return descuentosIANG;
	}
	public void setDescuentosIANG(BigDecimal descuentosIANG) {
		this.descuentosIANG = descuentosIANG;
	}
	public BigDecimal getImporteBaseCfIANG() {
		return importeBaseCfIANG;
	}
	public void setImporteBaseCfIANG(BigDecimal importeBaseCfIANG) {
		this.importeBaseCfIANG = importeBaseCfIANG;
	}
	public BigDecimal getCreditoFiscalIANG() {
		return creditoFiscalIANG;
	}
	public void setCreditoFiscalIANG(BigDecimal creditoFiscalIANG) {
		this.creditoFiscalIANG = creditoFiscalIANG;
	}
	public BigDecimal getTotalComprasSP() {
		return totalComprasSP;
	}
	public void setTotalComprasSP(BigDecimal totalComprasSP) {
		this.totalComprasSP = totalComprasSP;
	}
	public BigDecimal getImporteNoSujetoCfSP() {
		return importeNoSujetoCfSP;
	}
	public void setImporteNoSujetoCfSP(BigDecimal importeNoSujetoCfSP) {
		this.importeNoSujetoCfSP = importeNoSujetoCfSP;
	}
	public BigDecimal getSubtotalSP() {
		return subtotalSP;
	}
	public void setSubtotalSP(BigDecimal subtotalSP) {
		this.subtotalSP = subtotalSP;
	}
	public BigDecimal getDescuentosSP() {
		return descuentosSP;
	}
	public void setDescuentosSP(BigDecimal descuentosSP) {
		this.descuentosSP = descuentosSP;
	}
	public BigDecimal getImporteBaseCfSP() {
		return importeBaseCfSP;
	}
	public void setImporteBaseCfSP(BigDecimal importeBaseCfSP) {
		this.importeBaseCfSP = importeBaseCfSP;
	}
	public BigDecimal getCreditoFiscalSP() {
		return creditoFiscalSP;
	}
	public void setCreditoFiscalSP(BigDecimal creditoFiscalSP) {
		this.creditoFiscalSP = creditoFiscalSP;
	}
	public BigDecimal getTotalComprasE() {
		return totalComprasE;
	}
	public void setTotalComprasE(BigDecimal totalComprasE) {
		this.totalComprasE = totalComprasE;
	}
	public BigDecimal getImporteNoSujetoCfE() {
		return importeNoSujetoCfE;
	}
	public void setImporteNoSujetoCfE(BigDecimal importeNoSujetoCfE) {
		this.importeNoSujetoCfE = importeNoSujetoCfE;
	}
	public BigDecimal getSubtotalE() {
		return subtotalE;
	}
	public void setSubtotalE(BigDecimal subtotalE) {
		this.subtotalE = subtotalE;
	}
	public BigDecimal getDescuentosE() {
		return descuentosE;
	}
	public void setDescuentosE(BigDecimal descuentosE) {
		this.descuentosE = descuentosE;
	}
	public BigDecimal getImporteBaseCfE() {
		return importeBaseCfE;
	}
	public void setImporteBaseCfE(BigDecimal importeBaseCfE) {
		this.importeBaseCfE = importeBaseCfE;
	}
	public BigDecimal getCreditoFiscalE() {
		return creditoFiscalE;
	}
	public void setCreditoFiscalE(BigDecimal creditoFiscalE) {
		this.creditoFiscalE = creditoFiscalE;
	}
	public BigDecimal getTotalComprasIE() {
		return totalComprasIE;
	}
	public void setTotalComprasIE(BigDecimal totalComprasIE) {
		this.totalComprasIE = totalComprasIE;
	}
	public BigDecimal getImporteNoSujetoCfIE() {
		return importeNoSujetoCfIE;
	}
	public void setImporteNoSujetoCfIE(BigDecimal importeNoSujetoCfIE) {
		this.importeNoSujetoCfIE = importeNoSujetoCfIE;
	}
	public BigDecimal getSubtotalIE() {
		return subtotalIE;
	}
	public void setSubtotalIE(BigDecimal subtotalIE) {
		this.subtotalIE = subtotalIE;
	}
	public BigDecimal getDescuentosIE() {
		return descuentosIE;
	}
	public void setDescuentosIE(BigDecimal descuentosIE) {
		this.descuentosIE = descuentosIE;
	}
	public BigDecimal getImporteBaseCfIE() {
		return importeBaseCfIE;
	}
	public void setImporteBaseCfIE(BigDecimal importeBaseCfIE) {
		this.importeBaseCfIE = importeBaseCfIE;
	}
	public BigDecimal getCreditoFiscalIE() {
		return creditoFiscalIE;
	}
	public void setCreditoFiscalIE(BigDecimal creditoFiscalIE) {
		this.creditoFiscalIE = creditoFiscalIE;
	}

	
	
}
