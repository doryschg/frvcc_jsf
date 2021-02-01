package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class ComprasExcelDto implements Serializable {

	private String numeroExcel;
	private String fechaFactura;
	private String nitProveedor;
	private String numeroFactura;
	private String numeroDui;
	private String codigoAutorizacion;
	private String importeTotalCompra;
	private String importeNoSujetoFc;
	private String descuento;
	private String importeBaseCf;
	private String codigoControl;

	public ComprasExcelDto() {
		super();
	}

	public ComprasExcelDto(String numeroExcel,
                           String fechaFactura,
                           String nitProveedor,
                           String numeroFactura,
			               String codigoAutorizacion,
                           String importeTotalCompra,
                           String codigoControl) {
		super();
		this.numeroExcel = numeroExcel;
		this.fechaFactura = fechaFactura;
		this.nitProveedor = nitProveedor;
		this.numeroFactura = numeroFactura;
		this.codigoAutorizacion = codigoAutorizacion;
		this.importeTotalCompra = importeTotalCompra;
		this.codigoControl = codigoControl;

	}
	
	public ComprasExcelDto(String numeroExcel, 
			String fechaFactura, 
			String nitProveedor, 
			String numeroFactura,
			String numeroDui,
			String codigoAutorizacion, 
			String importeTotalCompra, 
			String importeNoSujetoFc,
			String descuento, 
			String importeBaseCf, 
			String codigoControl) {
		super();
		this.numeroExcel = numeroExcel;
		this.fechaFactura = fechaFactura;
		this.nitProveedor = nitProveedor;
		this.numeroFactura = numeroFactura;
		this.numeroDui = numeroDui;
		this.codigoAutorizacion = codigoAutorizacion;
		this.importeTotalCompra = importeTotalCompra;
		this.importeNoSujetoFc = importeNoSujetoFc;
		this.descuento = descuento;
		this.importeBaseCf = importeBaseCf;
		this.codigoControl = codigoControl;
	}
	

	public String getNumeroExcel() {
		return numeroExcel;
	}

	public void setNumeroExcel(String numeroExcel) {
		this.numeroExcel = numeroExcel;
	}

	public String getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(String nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroDui() {
		return numeroDui;
	}

	public void setNumeroDui(String numeroDui) {
		this.numeroDui = numeroDui;
	}

	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}

	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	public String getImporteTotalCompra() {
		return importeTotalCompra;
	}

	public void setImporteTotalCompra(String importeTotalCompra) {
		this.importeTotalCompra = importeTotalCompra;
	}

	public String getCodigoControl() {
		return codigoControl;
	}

	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}

	public String getImporteBaseCf() {
		return importeBaseCf;
	}

	public void setImporteBaseCf(String importeBaseCf) {
		this.importeBaseCf = importeBaseCf;
	}

	public String getImporteNoSujetoFc() {
		return importeNoSujetoFc;
	}

	public void setImporteNoSujetoFc(String importeNoSujetoFc) {
		this.importeNoSujetoFc = importeNoSujetoFc;
	}

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	
	
}
