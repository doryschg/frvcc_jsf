package bo.gob.sin.sre.fac.frvcc.jsf.parametros;

public class ParametrosPattern {

	public static final String NIT_PATTERN= "^([1-9]{1}([0-9]{1}){5,11})$";
	
	public static final String NUMERO_FACTURA_PATTERN= "^([1-9]{1}([0-9]{1}){0,14})$";
	
	public static final String NUMERO_DUI_PATTERN= "^(2)\\d{3}((07)[1-3]{1}|[1-8]{1}(01)|[1-7]{1}(02)|[^0458]{1}(11)|(712)|[^0138]{1}(21)|[4-7]{1}(22)|[6-7]{1}(23)|[2-9]{1}(31)|[2|3|4|7]{1}(32)|[2|7]{1}(33|34|35)|(73)[6-8]{1}|[2|6|7|8]{1}(41)|[2|5|6|8]{1}(42)|[2|5|6|7]{1}(43)|244|751|752|[2|3|7]{1}(61)|(862))[C]{1}\\d{10}$";
	
	public static final String COD_AUTORIZACION_PATTERN= "^[1-9]{1}[0-9]{1}[1-9]{1}[1|3|4|6|7|8]{1}(01|05|09|15|16)[0-9]{9}$";
	
	public static final String CUF_PATTERN= "\\b([a-zA-Z0-9]{57}|[a-zA-Z0-9]{58})\\b";
	
	public static final String CODIGO_AUTORIZACION_ESPECIAl_PATTERN= "\\b[1|3]{1}\\b";
	
	public static final String CODIGO_AUTORIZACION_AVION_PATTERN= "\\b[1]{1}\\b";
	
	public static final String FECHA_FACTURA_PATTERN= "^(0[1-9]|1[0-9]|2[0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])(\\/)(2|3)\\d{3}$";
	
	public static final String CODIGO_CONTROL_PATTERN= "^([A-F0-9]{2}(-[A-F0-9]{2}){3,4})$";
	
	
	public static final String IMPORTE_TOTAL_MIL_COMA_DEC_PUNTO_PATTERN= "^[1-9]{1}([0-9]{1}){0,2}(\\,[0-9]{3}){0,4}\\.\\d{1,2}$";
	
	public static final String IMPORTE_TOTAL_MIL_PUNTO_DEC_COMA_PATTERN= "^[1-9]{1}([0-9]{1}){0,2}(\\.[0-9]{3}){0,4}\\,\\d{1,2}$";
	
	public static final String IMPORTE_TOTAL_MIL_COMA_PATTERN= "^[1-9]{1}([0-9]{1}){0,2}(\\,[0-9]{3}){0,4}$";
	
	public static final String IMPORTE_TOTAL_DEC_PUNTO_PATTERN= "^([1-9]{1}[0-9]{0,14})(\\.[0-9]{1,2})$";

	public static final String IMPORTE_TOTAL_DEC_COMA_PATTERN= "^([1-9]{1}[0-9]{0,14})(\\,[0-9]{1,2})$";
	
	public static final String IMPORTE_TOTAL_ENTERO_PATTERN= "^([1-9]{1}[0-9]{0,14})$";
	
	
	
}
