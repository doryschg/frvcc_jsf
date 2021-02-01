package bo.gob.sin.sre.fac.frvcc.jsf.util;

public enum EnumEstadosFormulario {
	REG("REGISTRADO"), DEC("DECLARADO"), ACP("ACEPTADO"), RECH("RECHAZADO");

	private final String value;

	private EnumEstadosFormulario(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
