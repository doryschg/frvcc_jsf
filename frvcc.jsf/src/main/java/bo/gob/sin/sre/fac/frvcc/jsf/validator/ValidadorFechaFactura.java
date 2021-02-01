package bo.gob.sin.sre.fac.frvcc.jsf.validator;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosPattern;

public class ValidadorFechaFactura implements Serializable {

	private static final long serialVersionUID = 7876953343556165451L;

	private static Pattern patternFechaFactura;
	private final static String FECHA_FACTURA_PATTERN = ParametrosPattern.FECHA_FACTURA_PATTERN;
	
	
	private static String FORMATO_FECHA = "dd/MM/yyyy";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);

	public static boolean validarFechaFactura(String pFechaFactura) {
		if (pFechaFactura.isEmpty()) {
			return false;
		} 
		if (!isFechaFactura(pFechaFactura)) {
			return false;
		} 
		if (!isFechaValida(pFechaFactura)) {
			return false;
		} 
		if (!validarRangoFecha(pFechaFactura)) {
			return false;
		}
			
		return true;
	}

	public static String errorInvalido() {
		return ParametrosMensajes.MSJ_FECHA_FACTURA;
	}

	public static boolean isFechaFactura(String pFechaFactura) {
		patternFechaFactura = Pattern.compile(FECHA_FACTURA_PATTERN);
		if (!patternFechaFactura.matcher(pFechaFactura).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validarRangoFecha(String pFechaFactura) {
		boolean retorno = false;
		LocalDate fechaActual = LocalDate.now();
		LocalDate vFechaEmision = LocalDate.parse(pFechaFactura, formatter);
		
		int anioActual = fechaActual.getYear();
		int anioInicial = 2016;
		
		if (vFechaEmision.getYear()>=anioInicial  && vFechaEmision.getYear()<=anioActual) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}
	
	public static boolean isFechaValida(String pFechaFactura) {
		if (pFechaFactura == null)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");
		if (pFechaFactura.trim().length() != dateFormat.toPattern().length())
			return false;
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(pFechaFactura.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
}
