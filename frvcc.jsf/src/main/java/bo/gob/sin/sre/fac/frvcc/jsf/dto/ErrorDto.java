package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descripcion;
	private String fila;
	private String columna;
	private String tipo;

	public ErrorDto(String descripcion, String fila, String columna, String tipo) {
		super();
		this.descripcion = descripcion;
		this.fila = fila;
		this.columna = columna;
		this.tipo = tipo;
	}

	public ErrorDto() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColumna() {
		return columna;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "ErrorDto [descripcion=" + descripcion + "]";
	}

}
