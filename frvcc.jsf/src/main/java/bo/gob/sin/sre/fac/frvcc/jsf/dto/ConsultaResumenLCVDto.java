package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;

public class ConsultaResumenLCVDto extends ListaMensajesAplicacion{

	
    private  Integer mesPeriodo;
    private  String  descripcionMes;
    private  String  mesDescripcion;
    private  Integer anioPeriodo;
    private  BigDecimal totalCompras;
    private  BigDecimal totalVentas;
    private  Integer cantidadCompras;
    private  Integer cantidadVentas;
	private  BigDecimal totalBCCompras;
	private  BigDecimal totalBCVentas;	
    private  LocalDateTime fechaRegistroSistema;

    
    public Integer getMesPeriodo() {
		return mesPeriodo;
	}
	public Integer getAnioPeriodo() {
		return anioPeriodo;
	}
	public String getMesDescripcion() {
		return mesDescripcion;
	}
	public void setMesDescripcion(String mesDescripcion) {
		this.mesDescripcion = mesDescripcion;
	}
	public BigDecimal getTotalCompras() {
		return totalCompras;
	}
	public BigDecimal getTotalVentas() {
		return totalVentas;
	}
	public Integer getCantidadCompras() {
		return cantidadCompras;
	}
	public Integer getCantidadVentas() {
		return cantidadVentas;
	}
	public BigDecimal getTotalBCCompras() {
		return totalBCCompras;
	}
	public BigDecimal getTotalBCVentas() {
		return totalBCVentas;
	}
	public LocalDateTime getFechaRegistroSistema() {
		return fechaRegistroSistema;
	}
	public String getDescripcionMes() {
		return descripcionMes;
	}
	public void setDescripcionMes(String descripcionMes) {
		this.descripcionMes = descripcionMes;
	}
    
    
}
