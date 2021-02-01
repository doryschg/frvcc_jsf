package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorNumeroFactura implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternNroFactura;
	private final static String NUMERO_FACTURA_PATTERN = ParametrosPattern.NUMERO_FACTURA_PATTERN;

	public static boolean validarNumeroFactura(String pNumeroFactura) {
		if (pNumeroFactura.isEmpty()) {
			return false;
		} 
		if (!isNumeroFactura(pNumeroFactura)) {
			return false;
		}
		return true;
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_NUMERO_FACTURA;	
	}
	
	public static boolean isNumeroFactura(String pNumeroFactura) {
		//negativos
		patternNroFactura = Pattern.compile(NUMERO_FACTURA_PATTERN);
		if (!patternNroFactura.matcher(pNumeroFactura).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
