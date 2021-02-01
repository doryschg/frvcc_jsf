package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorCodigoAutorizacion implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternCodAutorizacion;
	private final static String COD_AUTORIZACION_PATTERN = ParametrosPattern.COD_AUTORIZACION_PATTERN;
	
	private static Pattern patternCuf;
	private final static String CUF_PATTERN = ParametrosPattern.CUF_PATTERN;
	
	private static Pattern patternCasosEspeciales;
	private final static String AVION_PATTERN = ParametrosPattern.CODIGO_AUTORIZACION_AVION_PATTERN;

	public static boolean validarCodigoAutorizacion(String pCodigoAutorizacion) {
		if (pCodigoAutorizacion.isEmpty()) {
			return false;
		} 
		
		if (!isCodigoAutorizacion(pCodigoAutorizacion) && !isCUF(pCodigoAutorizacion) && !isCasosEspecial(pCodigoAutorizacion)) {
			return false;
		}
		return true;
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_CODIGO_AUTORIZACION;	
	}
	
	public static boolean isCodigoAutorizacion(String pCodigoAutorizacion) {
		patternCodAutorizacion = Pattern.compile(COD_AUTORIZACION_PATTERN);
		if (!patternCodAutorizacion.matcher(pCodigoAutorizacion).matches()) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean isCUF(String pCodigoAutorizacion) {
		patternCuf = Pattern.compile(CUF_PATTERN);
		if (!patternCuf.matcher(pCodigoAutorizacion).matches()) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean isCasosEspecial(String pCodigoAutorizacion) {
		patternCasosEspeciales = Pattern.compile(AVION_PATTERN);
		if (!patternCasosEspeciales.matcher(pCodigoAutorizacion).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validarModalidadSinCodigoControl(String pCodigoAutorizacion) {
		String dato = "";
		boolean retorno = false;
		dato = pCodigoAutorizacion.substring(3, 4);
		if (dato.equals("1") || dato.equals("3")) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}
	
}
