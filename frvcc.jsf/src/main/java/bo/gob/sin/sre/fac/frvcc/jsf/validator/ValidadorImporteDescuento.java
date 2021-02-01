package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorImporteDescuento implements Serializable {

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

	public static boolean validarImporteDescuento(String pImporteTotal, String pImporteNoSujeto,
			String pImporteDescuento) {
		if (pImporteTotal.isEmpty() || pImporteNoSujeto.isEmpty() || pImporteDescuento.isEmpty()) {
			return false;
		}

		BigDecimal montoTotal = isImporte(pImporteTotal);
		BigDecimal montoNoSujeto = isImporte(pImporteNoSujeto);
		BigDecimal montoDescuento = isImporte(pImporteDescuento);
		if (montoTotal.compareTo(new BigDecimal("0")) == -1 || montoNoSujeto.compareTo(new BigDecimal("0")) == -1 || montoDescuento.compareTo(new BigDecimal("0")) == -1) {
			return false;
		}
		if (montoDescuento.compareTo(montoTotal) == 1) {
			return false;
		} else {
			BigDecimal vImporteBase = (montoTotal.subtract(montoNoSujeto)).subtract(montoDescuento);
			if (vImporteBase.compareTo(new BigDecimal("0")) == -1) {
				return false;
			} else {
				BigDecimal vImporteCalculado = (montoTotal.subtract(vImporteBase)).subtract(montoNoSujeto);
				if (montoDescuento.compareTo(vImporteCalculado) == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_IMPORTE_DESCUENTO;
	}
	

	public static BigDecimal isImporte(String pImporte) {
		patternImporteTotalMilComaDecPunto = Pattern.compile(IMPORTE_TOTAL_MIL_COMA_DEC_PUNTO_PATTERN);
		patternImporteTotalMilPuntoDecComa = Pattern.compile(IMPORTE_TOTAL_MIL_PUNTO_DEC_COMA_PATTERN);
		patternImporteTotalDecPunto = Pattern.compile(IMPORTE_TOTAL_DEC_PUNTO_PATTERN);
		patternImporteTotalDecComa = Pattern.compile(IMPORTE_TOTAL_DEC_COMA_PATTERN);
		patternImporteTotalEntero = Pattern.compile(IMPORTE_TOTAL_ENTERO_PATTERN);
		
		if (pImporte.equals("0,00") || pImporte.equals("0,0") || pImporte.equals("0.00")|| pImporte.equals("0.0")|| pImporte.equals("0")) {
			return new BigDecimal(pImporte.trim());
	    }
		if (patternImporteTotalEntero.matcher(pImporte).matches()) {
			return new BigDecimal(pImporte.trim());
			}
		if (patternImporteTotalDecPunto.matcher(pImporte).matches()) {
			return new BigDecimal(pImporte.trim());
			}
		if (patternImporteTotalMilComaDecPunto.matcher(pImporte).matches()) {
			 String valor= pImporte.trim().replace(",", "");
			 return new BigDecimal(valor.trim());
			}
		if (patternImporteTotalDecComa.matcher(pImporte).matches()) {
			 return new BigDecimal(pImporte.trim().replace(",","."));
			}
		if (patternImporteTotalMilPuntoDecComa.matcher(pImporte).matches()) {
			  String valor = pImporte.trim().replace(".", "");
		        return new BigDecimal(valor.trim().replace(",", "."));
			}
		return new BigDecimal("-1");
	}
		
}
