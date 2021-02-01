package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class RespuestaRepresentacionGraficaDto implements Serializable{

	private boolean transaccion;
	private String respuestaMensajes;
	private String archivoBase64;
	public boolean isTransaccion() {
		return transaccion;
	}
	public void setTransaccion(boolean transaccion) {
		this.transaccion = transaccion;
	}
	public String getRespuestaMensajes() {
		return respuestaMensajes;
	}
	public void setRespuestaMensajes(String respuestaMensajes) {
		this.respuestaMensajes = respuestaMensajes;
	}
	public String getArchivoBase64() {
		return archivoBase64;
	}
	public void setArchivoBase64(String archivoBase64) {
		this.archivoBase64 = archivoBase64;
	}
	
	
}
