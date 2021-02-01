/**
 *
 */
jQuery.noConflict();

function validarCsvJsonAjv(datosFacturasCsv) {

    var tipoFactura = 1382;
    var esquema = JSON
        .parse("{ \"$schema\":\"http://json-schema.org/draft-07/schema#\", \"additionalProperties\":false, \"errorMessage\":{ \"type\":\"El archivo no tiene el formato correcto\", \"required\":\"Debe tener la propiedad compra\", \"additionalProperties\":\"El archivo tiene columnas no validas\" }, \"title\":\"compra\", \"type\":\"array\", \"items\":{ \"type\":\"object\", \"errorMessage\":{ \"type\":\"El archivo no tiene el formato correcto\", \"additionalProperties\":\"El archivo tiene columnas no validas\", \"required\":{ " +
        		"\"Nro.\":\"El campo Nro es requerido\", " +
        		"\"NIT Proveedor\":\"El campo NIT Proveedor es requerido\", " +
        		"\"Nro Factura\":\"El campo Número de Factura es requerido\", " +
        		"\"Fecha Emisión\":\"El campo Fecha Emisión es requerido\", " +
        		"\"Número Autorización / CUF\":\"El campo Número Autorización / CUF es requerido\", " +
        		"\"Importe Total Compra\":\"El campo Importe Total Compra es requerido\" } }, " +
        		"\"properties\":{ \"additionalProperties\":false," +
        		"\"Nro\":{ \"anyOf\":[ { \"type\":\"string\", \"pattern\":\"^[0-9]{1,4}?$\", \"maxLength\":4 }, { \"maximum\":9999, \"minimum\":0, \"type\":\"integer\" } ], \"errorMessage\":{ \"type\":\"El campo Nro debe ser númerico\" } }, " +
        		"\"NIT Proveedor\":{ \"anyOf\":[ { \"type\":\"string\", \"pattern\":\"^[0-9]{5,12}?$\", \"maxLength\":12 }, { \"maximum\":999999999999, \"minimum\":100000, \"type\":\"integer\" } ], \"errorMessage\":{ \"type\":" +
        		"\"El campo NIT Proveedor debe ser númerico\" } }, " +
        		"\"Nro Factura\":{ \"anyOf\":[ { \"type\":\"string\", \"pattern\":\"^[0-9]{1,99999999}?$\" }, { \"maximum\":99999999, \"minimum\":1, \"type\":\"integer\" } ], \"errorMessage\":{ \"type\":\"El campo numeroFactura debe ser entero\" } }, " +
        		"\"Fecha Emisión\":{ \"type\":\"string\", \"pattern\":\"^([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})$\", \"errorMessage\":{ \"type\":\"El campo fechaEmision debe tener un valor\" } }, " +
        		"\"Número Autorización / CUF\":{ \"anyOf\":[ { \"maxLength\":100, \"minLength\":1, \"type\":\"string\", \"errorMessage\":{ \"type\":" +
        		"\"El campo Número Autorización / CUF debe tener un valor\" } }, { \"maximum\":999999999999999, \"minimum\":1, \"type\":\"integer\" } ] }, " +
        		"\"Código de Control\":{ \"type\":\"string\", \"pattern\":\"^0|^([A-Fa-f0-9]{2,2}\-)*[A-Fa-f0-9]{2,2}$\", \"errorMessage\":{ \"type\":\"El campo Código de Control debe tener un valor\" }, \"minLength\":1, \"maxLength\":15 }, " +
        		"\"Importe Total Compra\":{ \"anyOf\":[ { \"type\":\"string\", \"pattern\":\"^[0-9]{1,20}([.,][0-9]{1,5})?$\" }, { \"exclusiveMinimum\":0, \"type\":\"number\" } ], \"errorMessage\":{ \"type\":\"El campo Importe Total Compra debe ser númerico\" } } }, " +
        		"\"required\":[ \"Nro.\", \"NIT Proveedor\", \"Nro Factura\", \"Fecha Emisión\", \"Número Autorización / CUF\", \"Importe Total Compra\" ] }}");

    var objectoJsonConstruidoParaEvaluacion = JSON.parse(datosFacturasCsv);
    console.log(objectoJsonConstruidoParaEvaluacion);

    if (objectoJsonConstruidoParaEvaluacion != undefined) {
        var ajv = new Ajv({
            $data : true,
            allErrors : true,
            multipleOfPrecision : 5,
            jsonPointers : true,
            removeAdditional : true
        });

        if (esquema != undefined) {
            let facturaEstandarJsonSchema = esquema;
            fila = 1;
            var errores = [];
            var validate = ajv.compile(facturaEstandarJsonSchema);
            var valid = validate(objectoJsonConstruidoParaEvaluacion);
            // if (!valid) {
            if (!true) {
                validate.errors = localize_es(validate.errors);
                var erroresValidacion = [];
                validate.errors.forEach(function(error) {
                    var errorValidacion = new Object();
                    errorValidacion['error'] = error.message;
                    erroresValidacion.push(errorValidacion);
                    fila++;
                });

                console.log("Errores:");
                console.log(erroresValidacion);
                toastr.error('Error en el archivo', 'Error');

                /*
                 * mostrarErrorJsBean([ { name : 'Errores', value :
                 * JSON.stringify(erroresValidacion) } ]);
                 */
            } else {
                let vfacturas = JSON.stringify(objectoJsonConstruidoParaEvaluacion);
                console.log("validacionArchivoBean");
                validacionArchivoBean([ { name : 'Factura',	value : vfacturas}]);
            }
        } else {
            toastr.error('Ocurrio un error al recuperar el esquema', 'Error');
        }
    }
}

String.prototype.toFloat = function() {
    var thousandSeparator = (1111).toLocaleString().replace(/1/g, '');
    var decimalSeparator = (1.1).toLocaleString().replace(/1/g, '');

    return parseFloat(this.replace(new RegExp('\\\\' + thousandSeparator, 'g'),
        '').replace(new RegExp('\\\\' + decimalSeparator), '.'));
}
