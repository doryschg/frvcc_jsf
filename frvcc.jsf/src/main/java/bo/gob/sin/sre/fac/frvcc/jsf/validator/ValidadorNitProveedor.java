package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;
import bo.gob.sin.str.util.nit.UtilsNit;

public class ValidadorNitProveedor implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternNIT;
	private final static String NIT_PATTERN = ParametrosPattern.NIT_PATTERN;
	

	public static boolean validarNIT(String pNitProveedor) {
		if (pNitProveedor.isEmpty()) {
			return false;
		}	
		if (!isNIT(String.valueOf(pNitProveedor))) {
			return false;
		}
		if (UtilsNit.validarDigitoVerificador(Long.valueOf(pNitProveedor).longValue()) != 1) {// ==1 true
			return false;
		}	
		return true;
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_NIT_PROVEEDOR;	
	}
	
	public static boolean isNIT(String pNitProveedor) {
		//negativos
		patternNIT = Pattern.compile(NIT_PATTERN);
		if (!patternNIT.matcher(pNitProveedor).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
