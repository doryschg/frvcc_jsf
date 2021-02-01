package bo.gob.sin.sre.fac.frvcc.jsf.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FiltroCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7781112219178875417L;
	private LinkedHashMap<String, Serializable> filtros;

	public FiltroCriteria() {
		super();
		this.filtros = new LinkedHashMap<String, Serializable>();
	}

	public LinkedHashMap<String, Serializable> adicionar(String campo, OperadorCriteria operador, Serializable valor) {
		int numeroCriterio = filtros.size() / 3;
		filtros.put(String.format("filters[%s][field]", numeroCriterio), campo);
		filtros.put(String.format("filters[%s][operator]", numeroCriterio), operador.value());
		filtros.put(String.format("filters[%s][value]", numeroCriterio), valor);
		return filtros;
	}

	public void limpiar() {
		this.filtros = new LinkedHashMap<String, Serializable>();
	}

	public LinkedHashMap<String, Serializable> getFiltros() {
		return filtros;
	}

	public void setFiltros(LinkedHashMap<String, Serializable> filtros) {
		this.filtros = filtros;
	}
}
