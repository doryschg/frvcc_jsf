package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class PeticionRepresentacionGraficaDto implements Serializable{
	private String cuf;
	private Long nitEmisor;
    private Long numeroFactura;
    private Integer tamanioPapel;
    
	public PeticionRepresentacionGraficaDto(String cuf, Long nitEmisor, Long numeroFactura, Integer tamanioPapel) {
		super();
		this.cuf = cuf;
		this.nitEmisor = nitEmisor;
		this.numeroFactura = numeroFactura;
		this.tamanioPapel = tamanioPapel;
	}
	public String getCuf() {
		return cuf;
	}
	public void setCuf(String cuf) {
		this.cuf = cuf;
	}
	public Long getNitEmisor() {
		return nitEmisor;
	}
	public void setNitEmisor(Long nitEmisor) {
		this.nitEmisor = nitEmisor;
	}
	public Long getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public Integer getTamanioPapel() {
		return tamanioPapel;
	}
	public void setTamanioPapel(Integer tamanioPapel) {
		this.tamanioPapel = tamanioPapel;
	}
    
	
}
