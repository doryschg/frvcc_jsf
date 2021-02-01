package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ConsultaLcvResponse implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  BigDecimal mesPeriodo;
    private  BigDecimal anioPeriodo;
    private  BigDecimal totalCompras;
    private  BigDecimal totalVentas;
    private  BigDecimal cantidadCompras;
    private  BigDecimal cantidadVentas;
	private  BigDecimal totalBCCompras;
	private  BigDecimal totalBCVentas;	
    private  Timestamp fechaRegistroSistema;
    private  String  descripcionMes;

    public ConsultaLcvResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
	public ConsultaLcvResponse(BigDecimal mesPeriodo, BigDecimal anioPeriodo, BigDecimal totalCompras,
			BigDecimal totalVentas, BigDecimal cantidadCompras, BigDecimal cantidadVentas, BigDecimal totalBCCompras,
			BigDecimal totalBCVentas, Timestamp fechaRegistroSistema) {
		super();
		this.mesPeriodo = mesPeriodo;
		this.anioPeriodo = anioPeriodo;
		this.totalCompras = totalCompras;
		this.totalVentas = totalVentas;
		this.cantidadCompras = cantidadCompras;
		this.cantidadVentas = cantidadVentas;
		this.totalBCCompras = totalBCCompras;
		this.totalBCVentas = totalBCVentas;
		this.fechaRegistroSistema = fechaRegistroSistema;
	}



	public BigDecimal getMesPeriodo() {
		return mesPeriodo;
	}
	public void setMesPeriodo(BigDecimal mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}
	public BigDecimal getAnioPeriodo() {
		return anioPeriodo;
	}
	public void setAnioPeriodo(BigDecimal anioPeriodo) {
		this.anioPeriodo = anioPeriodo;
	}
	public BigDecimal getTotalCompras() {
		return totalCompras;
	}
	public void setTotalCompras(BigDecimal totalCompras) {
		this.totalCompras = totalCompras;
	}
	public BigDecimal getTotalVentas() {
		return totalVentas;
	}
	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}
	public BigDecimal getCantidadCompras() {
		return cantidadCompras;
	}
	public void setCantidadCompras(BigDecimal cantidadCompras) {
		this.cantidadCompras = cantidadCompras;
	}
	public BigDecimal getCantidadVentas() {
		return cantidadVentas;
	}
	public void setCantidadVentas(BigDecimal cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}
	public BigDecimal getTotalBCCompras() {
		return totalBCCompras;
	}
	public void setTotalBCCompras(BigDecimal totalBCCompras) {
		this.totalBCCompras = totalBCCompras;
	}
	public BigDecimal getTotalBCVentas() {
		return totalBCVentas;
	}
	public void setTotalBCVentas(BigDecimal totalBCVentas) {
		this.totalBCVentas = totalBCVentas;
	}
	public Timestamp getFechaRegistroSistema() {
		return fechaRegistroSistema;
	}
	public void setFechaRegistroSistema(Timestamp fechaRegistroSistema) {
		this.fechaRegistroSistema = fechaRegistroSistema;
	}



	public String getDescripcionMes() {
		return descripcionMes;
	}



	public void setDescripcionMes(String descripcionMes) {
		this.descripcionMes = descripcionMes;
	}
	

}
