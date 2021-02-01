package bo.gob.sin.sre.fac.frvcc.jsf.parametros;

public class ParametrosFRVCC {
	
	// Valores Tipo Usuario
	public static final int TIPO_USUARIO_FUNCIONARIO = 861;
	public static final int TIPO_USUARIO_CONTRIBUYENTE = 862;
	public static final int TIPO_USUARIO_DEPENDIENTE = 1823;
	public static final int TIPO_USUARIO_BENEFICIARIO=4689;

	public static final String ESTADO_USO_REGISTRADO = "REG";
	public static final String ESTADO_USO_DECLARADO = "DEC";
	public static final String ESTADO_USO_APROPIADO = "APR";
	
	public static final String ESTADO_ID="AC";
	public static final String ESTADO_ASIGNACION_ID="AC";
	public static final String ESTADO_CONSOLIDACION_ID="AC";
	
	
	/* ESTADOS COMPRAS*/
	public static final String TIPO_DOCUMENTO_COMPRA_FACTURA = "FAC";
	public static final String TIPO_DOCUMENTO_COMPRA_NOTA_FISCAL = "NFIS";
	
	public static final String ESTADO_COMPRA_VIGENTE = "VIG";
	public static final String ESTADO_COMPRA_ANULADO = "ANL";
	public static final String ESTADO_COMPRA_NOTA_CREDITO_DEBITO = "NCD";
	
	public static final String ESTADO_USO_COMPRA_REGISTRADO = "REG";
	public static final String ESTADO_USO_COMPRA_DECLARADO = "DEC";
	public static final String ESTADO_USO_COMPRA_APROPIADO = "APR";
	public static final String ESTADO_USO_COMPRA_ASOCIADO = "ASC";
	public static final String ESTADO_USO_COMPRA_CONSOLIDADO = "CON";
	
	public static final String IMPUESTO_USO_COMPRA_RCIVA = "RC-IVA";
	public static final String IMPUESTO_USO_COMPRA_IVA = "IVA";
	public static final String IMPUESTO_USO_COMPRA_IUE = "IUE";
	
	public static final String ORIGEN_COMPRA_SERVICIO = "SER";
	public static final String ORIGEN_COMPRA_APLICACION = "APL";
	public static final String ORIGEN_COMPRA_MOBILE = "MOB";
	
	public static final String TIPO_DOC_CLIENTE_COMPRA_NIT = "NIT";
	public static final String TIPO_DOC_CLIENTE_COMPRA_CI = "CI";
	public static final String TIPO_DOC_CLIENTE_COMPRA_PASAPORTE = "PAS";
	public static final String CON_DERECHO_CF="SI";
	public static final String TIPO_DOCUMENTO = "CI";
	public static final String TIPO_COMPRA_ACTIVIDADES_GRAVADAS="IAG";
	public static final String TIPO_COMPRA_ACTIVIDADES_NO_GRAVADAS="IANG";
	
	/* ESTADOS FORMULARIOS*/
	public static final String ESTADO_FORMULARIO_REGISTRADO = "REG";
	public static final String ESTADO_FORMULARIO_DECLARADO = "DEC";
	public static final String ESTADO_FORMULARIO_ACEPTADO = "ACP";
	public static final String ESTADO_FORMULARIO_RECHAZADO = "RECH";
	public static final String ESTADO_FORMULARIO_RECTIFICADO = "REC";
	
	public static final String TIPO_PRESENTACION_FORMULARIO_ORIGINAL = "ORI";
	public static final String TIPO_PRESENTACION_FORMULARIO_RECTIFICATORIO = "REC";
	
	public static final String TIPO_FORMULARIO_110="F110";
	public static final String TIPO_FORMULARIO_111="F111";

	public static final String TIPO_FORMULARIO_110_ANEXO_F610="AF610";
	public static final String TIPO_FORMULARIO_110_ANEXO_F702="AF702";
	public static final String TIPO_FORMULARIO_110_ANEXO_F510="AF510";
	public static final String TIPO_FORMULARIO_LCV="LCV";
	
	public static final String PERIODICIDAD_DIARIA="D";
	public static final String PERIODICIDAD_MENSUAL="M";
	public static final String PERIODICIDAD_TRIMESTRAL="T";
	public static final String PERIODICIDAD_ANUAL="A";
	public static final String CANTIDAD_PERIODICIDAD_FORMULARIO_DIARIA="120";
	public static final String CANTIDAD_PERIODICIDAD_FORMULARIO_ANUAL="1";
	public static final String CANTIDAD_PERIODICIDAD_FORMULARIO_TRIMESTRAL="3";
	public static final String CANTIDAD_PERIODICIDAD_LIBRO_ANUAL="1";
	public static final String CANTIDAD_PERIODICIDAD_LIBRO_MENSUAL="1";
	
	public static final Integer CANTIDAD_COLUMNAS_EXCEL_COMPRAS=7;
	public static final Integer CANTIDAD_COLUMNAS_EXCEL_COMPRAS_ESTANDAR=10;
	
	public static final Integer ID_AGRUPADOR_TIPO_DOCUMENTO=4378;
	
	public static final String MARCA_ESPECIAL_IPN="IPN";
	public static final String SIN_MARCA_ESPECIAL="SM";
	public static final String MARCA_ESPECIAL_7RG="7RG";
	
	public static final String MODALIDAD_EN_LINEA="LIN";
	public static final String TIPO_MODALIDAD_FPW="FPW";
	public static final String TIPO_MODALIDAD_FCL="FCL";
	public static final String TIPO_MODALIDAD_FEL="FEL";
	
	/* estados libros*/
	public static final String ESTADO_LIBRO_RECTIFICADO = "REC";

	/*ROLES USUARIOS */

	public static final Integer ROL_AG_SUCURSAL_USUARIO_ID=1377;
	public static final Integer ROL_AG_CONSOLIDADOR_ID=1378;
	public static final Integer ROL_AG_RECEPTOR_ID=1388;

	public static final String TIPO_CONSOLIDACION_PRINCIPAL="P";
	public static final String TIPO_CONSOLIDACION_SECUENDARIA="S";

	public static final String TIPO_USO_FORMULARIO_DEPENDIENTE="D";
	public static final String TIPO_USO_FORMULARIO_INDEPENDIENTE="I";
	public static final String TIPO_USO_FORMULARIO_EXDEPENDIENTE="E";
	public static final String TIPO_USO_FORMULARIO_JUBILADO="J";
	public static final String TIPO_USO_FORMULARIO_REFRIGERIO="R";
	public static final String TIPO_USO_FORMULARIO_ANEXO="A";
	public static final Integer CODIGO_AGRUPADOR_DEPENDIENTE_TIPO_USO=4653;
	public static final Integer CODIGO_AGRUPADOR_CONTRIBUYENTE_TIPO_USO=4659;
	public static final Integer CODIGO_AGRUPADOR_AGENTE_TIPO_USO=4660;
	public static final String PAGO_MANUAL = "M";

}
