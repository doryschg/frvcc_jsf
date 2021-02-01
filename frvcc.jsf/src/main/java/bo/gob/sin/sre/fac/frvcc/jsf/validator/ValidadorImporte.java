package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorImporte implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternImporteTotalMilComaDecPunto;
	private final static String IMPORTE_TOTAL_MIL_COMA_DEC_PUNTO_PATTERN = ParametrosPattern.IMPORTE_TOTAL_MIL_COMA_DEC_PUNTO_PATTERN;
	
	private static Pattern patternImporteTotalMilPuntoDecComa;
	private final static String IMPORTE_TOTAL_MIL_PUNTO_DEC_COMA_PATTERN = ParametrosPattern.IMPORTE_TOTAL_MIL_PUNTO_DEC_COMA_PATTERN;
	
	private static Pattern patternImporteTotalDecPunto;
	private final static String IMPORTE_TOTAL_DEC_PUNTO_PATTERN = ParametrosPattern.IMPORTE_TOTAL_DEC_PUNTO_PATTERN;
	
	private static Pattern patternImporteTotalDecComa;
	private final static String IMPORTE_TOTAL_DEC_COMA_PATTERN = ParametrosPattern.IMPORTE_TOTAL_DEC_COMA_PATTERN;
	
	private static Pattern patternImporteTotalEntero;
	private final static String IMPORTE_TOTAL_ENTERO_PATTERN = ParametrosPattern.IMPORTE_TOTAL_ENTERO_PATTERN;


	public static boolean validarImporte(String pImporteTotal) {
		if (pImporteTotal.isEmpty()) {
			return false;
		} 
		if (!isImporteTotal(pImporteTotal)) {
			return false;
		}
		return true;
	}
	
	public static String errorInvalidoImporteTotal() {
		return ParametrosMensajes.MSJ_IMPORTE_TOTAL;	
	}
	public static String errorInvalidoImporteNoSujetoCF() {
		return ParametrosMensajes.MSJ_IMPORTE_NO_SUJETO_CF;	
	}
	public static String errorInvalidoDescuento() {
		return ParametrosMensajes.MSJ_IMPORTE_DESCUENTO;	
	}

	public static boolean isImporteTotal(String pImporteTotal) {
		patternImporteTotalMilComaDecPunto = Pattern.compile(IMPORTE_TOTAL_MIL_COMA_DEC_PUNTO_PATTERN);
		patternImporteTotalMilPuntoDecComa = Pattern.compile(IMPORTE_TOTAL_MIL_PUNTO_DEC_COMA_PATTERN);
		patternImporteTotalDecPunto = Pattern.compile(IMPORTE_TOTAL_DEC_PUNTO_PATTERN);
		patternImporteTotalDecComa = Pattern.compile(IMPORTE_TOTAL_DEC_COMA_PATTERN);
		patternImporteTotalEntero = Pattern.compile(IMPORTE_TOTAL_ENTERO_PATTERN);
		
		if ( !patternImporteTotalEntero.matcher(pImporteTotal).matches()
				&& !patternImporteTotalDecPunto.matcher(pImporteTotal).matches()
				
//				&& !patternImporteTotalDecComa.matcher(pImporteTotal).matches()
//				&& !patternImporteTotalMilComaDecPunto.matcher(pImporteTotal).matches()
//				&& !patternImporteTotalMilPuntoDecComa.matcher(pImporteTotal).matches()
				
				
				) {
			return false;
		}
	
		return true;	
	}	
}
