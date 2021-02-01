package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorNumeroDui implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternNroDUI;
	private final static String NUMERO_DUI_PATTERN = ParametrosPattern.NUMERO_DUI_PATTERN;

	public static boolean validarNumeroDui(String pNumeroDui) {
		if (pNumeroDui.isEmpty()) {
			return false;
		} 
		if (!isNumeroDui(pNumeroDui)) {
			return false;
		}
		return true;
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_NUMERO_DUI;	
	}
	
	public static boolean isNumeroDui(String pNumeroFactura) {
		patternNroDUI = Pattern.compile(NUMERO_DUI_PATTERN);
		if (!patternNroDUI.matcher(pNumeroFactura).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
