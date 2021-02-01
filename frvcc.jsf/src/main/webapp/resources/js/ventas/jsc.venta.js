//custom-date-time

//"Fecha Factura": {
//				"type": "string",
//				"pattern": "^([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})$",
//				"errorMessage": {
//					"type": "El campo fechaEmision debe tener un valor"
//				}
//			},

/* *********************************** 
*	TODO CUSTOME SCHEMA
**************************************/

// TODO set value of tooltip MESSAGE

const ventaJsc = {
	"$schema": "http://json-schema.org/draft-07/schema#",
	"additionalProperties": false,
	"errorMessage": {
		"type": "El archivo no tiene el formato correcto",
		"required": "Debe tener la propiedad venta",
		"additionalProperties": "El archivo tiene columnas no validas"
	},
	"title": "venta",
	"type": "array",
	"items": {
		"type": "object",
		"errorMessage": {
			"type": "El archivo no tiene el formato correcto",
			"additionalProperties": "El archivo tiene columnas no validas",
			"required": {
				"Fecha Factura": "El campo Fecha Emisión es requerido",
				"Nro de Factura": "El campo Número de Factura es requerido",
				"Nro Autorizacion/CUF": "El campo Número Autorización / CUF es requerido",
				"Estado": "Campo requerido",
				"Nit/CI Cliente": "El campo NIT cliente es requerido",
				"Nombre/Razon Social": "Campo requerido",
				"Importe Total Factura": "Campo requerido",
				"Importe ICE/IEHD/TASAS": "Campo requerido",
				"Exportaciones y Operaciones Exentas": "Campo requerido",
				"Ventas Grabadas Tasa Cero": "Campo requerido",
				"Sub Total": "Campo requerido",
				"Descuentos, Bonificaciones y Rebajas": "Campo requerido",
				"Importe Base Debito Fiscal": "Campo requerido",
				"Debito Fiscal": "Campo requerido",
				"Codigo de Control": "Campo requerido"
			}
		},
		"properties": {
			"additionalProperties": false,
			"Fecha Factura": {
				"checkFecha":"string",
				"errorMessage": {
					"type": "El campo fechaEmision debe tener un valor"
				}
			},
			"Nro de Factura": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,99999999}?$"
				}, {
					"maximum": 99999999,
					"minimum": 1,
					"type": "integer"
				}],
				"errorMessage": {
					"type": "El campo numeroFactura debe ser entero"
				}
			},
			"Nro Autorización / CUF": {
				"anyOf": [{
					"maxLength": 100,
					"minLength": 1,
					"type": "string",
					"errorMessage": {
						"type": "El campo Número Autorización / CUF debe tener un valor"
					}
				}, {
					"maximum": 999999999999999,
					"minimum": 1,
					"type": "integer"
				}]
			},
			"Estado": {
				"type": "string",
				"errorMessage": {
					"type": "El estado debe contener letras"
				},
				"minLength": 1
			},
			"Nit/CI Cliente": {
				"checkNit":"string",
				"errorMessage": {
					"type": "El campo NIT Proveedor debe ser númerico"
				}
			},
			"Nombre/Razon Social": {
				"type": "string",
				"errorMessage": {
					"type": "El campo Nro debe ser númerico"
				}
			},
			"Importe Total Factura": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Importe ICE/IEHD/TASAS": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Exportaciones y Operaciones Exentas": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Ventas Grabadas Tasa Cero": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Sub Total": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Descuentos, Bonificaciones y Rebajas": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Importe Base Debito Fiscal": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Debito Fiscal": {
				"anyOf": [{
					"type": "string",
					"pattern": "^[0-9]{1,20}([.,][0-9]{1,5})?$"
				}, {
					"exclusiveMinimum": 0,
					"type": "number"
				}],
				"errorMessage": {
					"type": "El campo Importe Total Compra debe ser númerico"
				}
			},
			"Código de Control": {
				"type": "string",
				"pattern": "^0|^([A-Fa-f0-9]{2,2}-)*[A-Fa-f0-9]{2,2}$",
				"errorMessage": {
					"type": "El campo Código de Control debe tener un valor"
				},
				"minLength": 1,
				"maxLength": 15
			}
		},
		"required": ["Fecha Factura", "Nro de Factura", "Nro Autorizacion/CUF", "Estado", "Nit/CI Cliente", "Nombre/Razon Social", "Importe Total Factura", "Importe ICE/IEHD/TASAS", "Exportaciones y Operaciones Exentas", "Ventas Grabadas Tasa Cero", "Sub Total", "Descuentos, Bonificaciones y Rebajas", "Importe Base Debito Fiscal", "Codigo de Control"]
	}
};