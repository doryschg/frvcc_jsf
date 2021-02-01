package bo.gob.sin.sre.fac.frvcc.jsf.ventas.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class VentaDto {

	// TODO VARIABLES						ESTANDAR									SIETE-RG 
	private String 		id;
	
	private String 		fchFactura;			// FECHA FACTURA							FECHA FACTURA							
	private Long 		nroFactura;			// Nro DE FACTURA							NRO DE FACTURA
	private Long 		nitCliente;			// NIT/CI CLIENTE 							NIT/CI CLIENTE
	private String 		razonSocial;		// NOMBRE O RAZON SOCIAL					NOMBRE O RAZON SOCIAL
	private BigDecimal 	imprtTotal;			// IMPORTE TOTAL FACTURA o LA VENTA			IMPORTE TOTAL FACTURA
	private BigDecimal 	imprtTasas;			// IMPORTE ICE/IEHD/TASAS					
	private BigDecimal 	imprtExntas;		// IMPORTACIONES Y OPERACIONES EXENTAS
	private BigDecimal 	vntasTsaCero;		// VENTAS GRABADAS A TASA CERO
	private BigDecimal 	subTotal;			// SUBTOTAL
	private BigDecimal 	descuento;			// DESCUENTOS y 							DESCUENTOS
	private BigDecimal 	imprtBaseDf;		// DEBITO FISCAL							DEBITO FISCAL
	private BigDecimal 	dbitoFiscal;		// IMPORTE BASE PARA DEBITO FISCAL	
	private String 		codAutorCuf;		// Nro AUTORIZACION/CUF 					NRO DE AUTORIZACION
	private String 		codControl;			// CODIGO DE CONTROL						CODIGO D CONTROL
	private String 		estado;				// ESTADO									ESTADO
	
	
	 // TODO CONSTRUCTOR
	public VentaDto(String id, String fchFactura, Long nroFactura, Long nitCliente, String razonSocial,
			BigDecimal imprtTotal, BigDecimal imprtTasas, BigDecimal imprtExntas, BigDecimal vntasTsaCero,
			BigDecimal subTotal, BigDecimal descuento, BigDecimal imprtBaseDf, BigDecimal dbitoFiscal,
			String codAutorCuf, String codControl, String estado) {
		
		this.id = id;
		this.fchFactura = fchFactura;
		this.nroFactura = nroFactura;
		this.nitCliente = nitCliente;
		this.razonSocial = razonSocial;
		this.imprtTotal = imprtTotal;
		this.imprtTasas = imprtTasas;
		this.imprtExntas = imprtExntas;
		this.vntasTsaCero = vntasTsaCero;
		this.subTotal = subTotal;
		this.descuento = descuento;
		this.imprtBaseDf = imprtBaseDf;
		this.dbitoFiscal = dbitoFiscal;
		this.codAutorCuf = codAutorCuf;
		this.codControl = codControl;
		this.estado = estado;
	}
	

	public VentaDto() {

	}



	// TODO GETTER AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFchFactura() {
		return fchFactura;
	}

	public void setFchFactura(String fchFactura) {
		this.fchFactura = fchFactura;
	}

	public Long getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Long getNitCliente() {
		return nitCliente;
	}

	public void setNitCliente(Long nitCliente) {
		this.nitCliente = nitCliente;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public BigDecimal getImprtTotal() {
		return imprtTotal;
	}

	public void setImprtTotal(BigDecimal imprtTotal) {
		this.imprtTotal = imprtTotal;
	}

	public BigDecimal getImprtTasas() {
		return imprtTasas;
	}

	public void setImprtTasas(BigDecimal imprtTasas) {
		this.imprtTasas = imprtTasas;
	}

	public BigDecimal getImprtExntas() {
		return imprtExntas;
	}

	public void setImprtExntas(BigDecimal imprtExntas) {
		this.imprtExntas = imprtExntas;
	}

	public BigDecimal getVntasTsaCero() {
		return vntasTsaCero;
	}

	public void setVntasTsaCero(BigDecimal vntasTsaCero) {
		this.vntasTsaCero = vntasTsaCero;
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


	public BigDecimal getImprtBaseDf() {
		return imprtBaseDf;
	}


	public void setImprtBaseDf(BigDecimal imprtBaseDf) {
		this.imprtBaseDf = imprtBaseDf;
	}

	public BigDecimal getDbitoFiscal() {
		return dbitoFiscal;
	}

	public void setDbitoFiscal(BigDecimal dbitoFiscal) {
		this.dbitoFiscal = dbitoFiscal;
	}

	public String getCodAutorCuf() {
		return codAutorCuf;
	}

	public void setCodAutorCuf(String codAutorCuf) {
		this.codAutorCuf = codAutorCuf;
	}

	public String getCodControl() {
		return codControl;
	}

	public void setCodControl(String codControl) {
		this.codControl = codControl;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
		

}
