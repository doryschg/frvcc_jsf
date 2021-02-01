package bo.gob.sin.sre.fac.frvcc.jsf.util;

public enum OperadorCriteria {
	IGUAL("="), MAYOR(">"), MENOR("<"), DISTINTO("!="), 
	MENOR_IGUAL("<="), MAYOR_IGUAL(">="),IN("IN"),NOT_IN("NOT_IN"),IS_NULL("IS NULL"),
	IS_NOT_NULL("IS NOT NULL")
	,CONTAINS("CONTAINS"),NOT_CONTAINS("NOT_CONTAINS");

	private final String value;

	private OperadorCriteria(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
