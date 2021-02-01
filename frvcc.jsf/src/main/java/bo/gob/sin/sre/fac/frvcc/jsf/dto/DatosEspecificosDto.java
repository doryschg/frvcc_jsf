package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosEspecificosDto implements Serializable {

	/**
	 * 
	 */
	private String tipoDocumento;
	private String descripcionTipoDocumento;
	private String complementoDocumento;
	private String lugarExpedicion;
	private String descripcionLugarExpedicion;
	private String departamentoDescripcion;
	private String numeroDocumento;
	private String idFormularioRectificado;
	private BigDecimal montoOtrosFormRech;
	private BigDecimal montoIpnFormRech;   
	private BigDecimal montoSieteRgFormRech;
	private Integer cantidadFacOtrasFormRech;
	private Integer cantidadFacIpnFormRech;
	private Integer cantidadFacSrgFormRech;

	private BigDecimal promedioIngreso;
	private String bancoId;
	private String descripcionBanco;
	private String numeroCuenta;
	private String tipoCuentaBancariaId;
	private String tipoCuentaBancariaDescripcion;


	public void setPromedioIngreso(BigDecimal promedioIngreso) {
		this.promedioIngreso = promedioIngreso;
	}

	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}

	public void setDescripcionBanco(String descripcionBanco) {
		this.descripcionBanco = descripcionBanco;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public void setTipoCuentaBancariaId(String tipoCuentaBancariaId) {
		this.tipoCuentaBancariaId = tipoCuentaBancariaId;
	}

	public void setTipoCuentaBancariaDescripcion(String tipoCuentaBancariaDescripcion) {
		this.tipoCuentaBancariaDescripcion = tipoCuentaBancariaDescripcion;
	}

	public BigDecimal getPromedioIngreso() {
		return promedioIngreso;
	}

	public java.lang.String getBancoId() {
		return bancoId;
	}

	public java.lang.String getDescripcionBanco() {
		return descripcionBanco;
	}

	public java.lang.String getNumeroCuenta() {
		return numeroCuenta;
	}

	public java.lang.String getTipoCuentaBancariaId() {
		return tipoCuentaBancariaId;
	}

	public java.lang.String getTipoCuentaBancariaDescripcion() {
		return tipoCuentaBancariaDescripcion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}
	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}
	public String getComplementoDocumento() {
		return complementoDocumento;
	}
	public void setComplementoDocumento(String complementoDocumento) {
		this.complementoDocumento = complementoDocumento;
	}
	public String getLugarExpedicion() {
		return lugarExpedicion;
	}
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	public String getDescripcionLugarExpedicion() {
		return descripcionLugarExpedicion;
	}
	public void setDescripcionLugarExpedicion(String descripcionLugarExpedicion) {
		this.descripcionLugarExpedicion = descripcionLugarExpedicion;
	}
	public String getDepartamentoDescripcion() {
		return departamentoDescripcion;
	}
	public void setDepartamentoDescripcion(String departamentoDescripcion) {
		this.departamentoDescripcion = departamentoDescripcion;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getIdFormularioRectificado() {
		return idFormularioRectificado;
	}
	public void setIdFormularioRectificado(String idFormularioRectificado) {
		this.idFormularioRectificado = idFormularioRectificado;
	}
	public BigDecimal getMontoOtrosFormRech() {
		return montoOtrosFormRech;
	}
	public void setMontoOtrosFormRech(BigDecimal montoOtrosFormRech) {
		this.montoOtrosFormRech = montoOtrosFormRech;
	}
	public BigDecimal getMontoIpnFormRech() {
		return montoIpnFormRech;
	}
	public void setMontoIpnFormRech(BigDecimal montoIpnFormRech) {
		this.montoIpnFormRech = montoIpnFormRech;
	}
	public BigDecimal getMontoSieteRgFormRech() {
		return montoSieteRgFormRech;
	}
	public void setMontoSieteRgFormRech(BigDecimal montoSieteRgFormRech) {
		this.montoSieteRgFormRech = montoSieteRgFormRech;
	}

	public Integer getCantidadFacOtrasFormRech() {
		return cantidadFacOtrasFormRech;
	}

	public void setCantidadFacOtrasFormRech(Integer cantidadFacOtrasFormRech) {
		this.cantidadFacOtrasFormRech = cantidadFacOtrasFormRech;
	}

	public Integer getCantidadFacIpnFormRech() {
		return cantidadFacIpnFormRech;
	}

	public void setCantidadFacIpnFormRech(Integer cantidadFacIpnFormRech) {
		this.cantidadFacIpnFormRech = cantidadFacIpnFormRech;
	}

	public Integer getCantidadFacSrgFormRech() {
		return cantidadFacSrgFormRech;
	}

	public void setCantidadFacSrgFormRech(Integer cantidadFacSrgFormRech) {
		this.cantidadFacSrgFormRech = cantidadFacSrgFormRech;
	}
}
