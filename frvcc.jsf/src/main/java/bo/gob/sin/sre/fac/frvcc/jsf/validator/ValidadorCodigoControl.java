package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorCodigoControl implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternCodigoControl;
	private final static String CODIGO_CONTROL_PATTERN = ParametrosPattern.CODIGO_CONTROL_PATTERN;

	public static boolean validarCodigoControl(String pCodigoControl, String pCodigoAutorizacion) {

		if (pCodigoAutorizacion.isEmpty()) {
			return false;
		}
		if (!ValidadorCodigoAutorizacion.isCasosEspecial(pCodigoAutorizacion)
				&& !ValidadorCodigoAutorizacion.validarModalidadSinCodigoControl(pCodigoAutorizacion)
				&& !ValidadorCodigoAutorizacion.isCUF(pCodigoAutorizacion)) {
			if (!isCodigoControl(pCodigoControl)) {
				return false;
			}
		} else {
			if (!pCodigoControl.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_CODIGO_CONTROL;	
	}
	
	public static boolean isCodigoControl(String pCodigoControl) {
		patternCodigoControl = Pattern.compile(CODIGO_CONTROL_PATTERN);
		if (!patternCodigoControl.matcher(pCodigoControl).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
}
