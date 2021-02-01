package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.io.Serializable;
import java.util.List;

public class FormularioPeticionDto implements Serializable{
	
	private static final long serialVersionUID = 6008599553809777361L;
	private String formularioId;
	private List<String> compraIds;

	public String getFormularioId() {
		return formularioId;
	}

	public void setFormularioId(String formularioId) {
		this.formularioId = formularioId;
	}

	public List<String> getCompraIds() {
		return compraIds;
	}

	public void setCompraIds(List<String> compraIds) {
		this.compraIds = compraIds;
	}
	
}
