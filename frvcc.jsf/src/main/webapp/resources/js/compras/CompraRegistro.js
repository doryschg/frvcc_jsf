const nitProveedor0 = document.getElementById('frmRegistroCompras:dtMisFacturas:0:txtNitProveedor');
const tablaRegistro='frmRegistroCompras:dtMisFacturas';
const botonGuardar = 'btnGuardar';
const txtNitProveedor='txtNitProveedor';
const txtNumeroFactura='txtNumeroFactura';
const txtFechaFactura='txtFechaFactura';
const txtCodigoAutorizacion='txtCodigoAutorizacion';
const txtImporteTotal_input='txtImporteTotal_input';
const txtCodigoControl='txtCodigoControl';

const CABECER_EXCEL_NRO= 'N°';
const CABECER_EXCEL_NIT_PROVEEDOR= 'NIT Proveedor';
const CABECER_EXCEL_NRO_FACTURA= 'N° Factura';
const CABECER_EXCEL_NUMERO_AUTORIZACION_CUF= 'Número Autorización / CUF';
const CABECER_EXCEL_FECHA_EMISION= 'Fecha Emisión';
const CABECER_EXCEL_IMPORTE_TOTAL= 'Importe Total';
const CABECER_EXCEL_CCDIGO_DE_C_ONTROL= 'Código de Control';


var mensajes={
	txtNitProveedor:"NIT inválido!", 
	txtNumeroFactura: "Número invalido",
	txtCodigoAutorizacion:"Código Autorización inválido", 
	txtImporteTotal_input:"Importe inválido", 
	txtCodigoControl:"Código de control inválido"
}


const guardarCompra = (event) => {
    let index;
    if (event.target) {
        index = event.target.id.split(':')[2];
    } else {
        index = event.id.split(':')[2];
    }
	
	
	if(!event.keyCode.toString().match(/9|16|17|18|20|33|34|35|36|37|38|40|39/g)){
		var pos=parseInt(index)+1;
		var x = document.getElementById(`${tablaRegistro}`).getElementsByTagName("tr")[pos];
		var d=x.getElementsByTagName("td");
	    d[0].childNodes[0].innerHTML = "";
	}
	
    
    if (event.key === 'Enter') {
        if (validarCompra(event)) {
            const guardar = document.getElementById(`${tablaRegistro}:${index}:${botonGuardar}`);
            guardar.click();
            event.preventDefault();
        }
        return event.preventDefault();
    }
}


const validarCompra = (event) => {
    let e;
    if (event.target) {
        e = event.target
    }
    if (event.id) {
        e = event
    }
    const index = e.id.split(':')[2];
    const nitProveedor = document.getElementById(`${tablaRegistro}:${index}:${txtNitProveedor}`);
    const numeroFactura = document.getElementById(`${tablaRegistro}:${index}:${txtNumeroFactura}`);
    const fechaFactura = document.getElementById(`${tablaRegistro}:${index}:${txtFechaFactura}`);
    const numeroAutrizacion = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoAutorizacion}`);
    const importeTotal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotal_input}`);
    const codigoControl = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoControl}`);

    const valNitProveedor = validadorNit(nitProveedor);
    const valNumeroFactura = validadorNumeroFactura(numeroFactura);
    const valNumeroAutrizacion = validadorNumeroAutorizacion(numeroAutrizacion);
    const valFechaFactura = validatorFechaEmision(fechaFactura).success;
    const valImporteTotal = validadorImporte(importeTotal);
    const valcodigoControl = validadorCodigoControl(numeroAutrizacion,codigoControl);
    if (!valImporteTotal) {
        importeTotal.focus()
    }
    if (!valFechaFactura) {
        fechaFactura.focus()
    }
    if (!valNumeroAutrizacion) {
        numeroAutrizacion.focus()
    }
    if (!valNumeroFactura) {
        numeroFactura.focus()
    }
    if (!valNitProveedor) {
        nitProveedor.focus()
    }
    if (!(valNitProveedor
        && valNumeroFactura
        && valNumeroAutrizacion
        && valFechaFactura
        && valImporteTotal)) {
        return false;
    }
    if (!sinCodigoControl(numeroAutrizacion)) {
        if (!valcodigoControl) {
            codigoControl.focus();
            return false;
        }
    } else {
        codigoControl.value = '';
    }
    return true;
}


function ponerFoco(data) {
    if (data.status === "success") {
        nitProveedor0.focus();
    }
}

const autoCompletado = (id, campo, datalistCampo) => {
    const index = data.source.id.toString().split(':')[2];
    const nitProveedor = document.getElementById(`${tablaRegistro}:${index}:${campo}`);
    const datalist = document.getElementById(datalistCampo);
    const optionList = Array.from(datalist.children);
    const resultado = optionList.map(x => x.value).find(x => x === nitProveedor.value);
    if (!resultado) {
        const opcion = document.createElement('option');
        opcion.value = nitProveedor.value;
        datalist.appendChild(opcion);
    }
}

const validarFormulario = e => {
    const codigoControl = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoControl}`);
    IMask(codigoControl, {
        mask: '**-**-**-**-**'
    });
var ruta= e.id.toString().replace(/:([^:]*)$/, ':_' + '$1');
    switch (e.id.toString().split(':')[3]) {
        case txtNitProveedor:
			
            if (validadorNit(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtNitProveedor; 
            }
            break;
        case txtNumeroFactura:
			
            if (validadorNumeroFactura(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtNumeroFactura;
            }
            break;
        case txtCodigoAutorizacion:
            if (validadorNumeroAutorizacion(e)) {
                if (!sinCodigoControl(e)) {
                    codigoControl.disabled = false;
                } else {
                    codigoControl.value = '';
                    codigoControl.className = CLASS_INPUT_CSS;
                    codigoControl.disabled = true;
                }
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                codigoControl.className = CLASS_INPUT_CSS;
                codigoControl.disabled = true;
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoAutorizacion;
            }
            break;
        case txtFechaFactura:
			var fecha=validatorFechaEmision(e);
            if (fecha.success) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=fecha.mensaje;
            }
            break;
        case txtImporteTotal_input:
            if (validadorImporte(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
				console.log(ruta);
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteTotal_input;
            }
            break;
        case txtCodigoControl:
            const numeroAutorizacion = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoAutorizacion}`);
            if (!numeroAutorizacion.value) {
                numeroAutorizacion.focus();
            } else {
                if (!sinCodigoControl(numeroAutorizacion)) {
                    if (validadorCodigoControl(numeroAutorizacion,e)) {
                        campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
						document.getElementById(ruta).children[1].innerHTML="";
                    } else {
                        campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
						document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoControl;
                    }
                }
            }
            break;
    }
}





const cargarEventos = () => {
    const fileUpload_input =  document.getElementById('frmRegistroCompras:fileUpload_input');
    const copiarExcel =  document.getElementById('txtCopiarExcel');
    fileUpload_input.addEventListener('change', function (e) {
        leerArchivo(e);
    });

    copiarExcel.addEventListener('paste', e => {
        let pastedText;
        if (window.clipboardData && window.clipboardData.getData) {
            pastedText = window.clipboardData.getData('Text');
        } else if (e.clipboardData && e.clipboardData.getData) {
            pastedText = e.clipboardData.getData('text/plain');
        }
        enviarCompras(pastedText, '\t');
    });
}

window.addEventListener("load", function(){
    cargarEventos();
});

const leerArchivo = (e)=>{
    var files = e.target.files;
    if (files.length == 0) return;
    var file = files[0];
    var reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = function (e) {
        let data = e.target.result;
        let workbook = XLSX.read(data, {
            type: 'binary'
        }); //XLSX: / xlsx.core.min.js read excel through XLSX.read(data, {type: type}) method


        let sheetNames = workbook.SheetNames; // Sheet name collection

        let worksheet = workbook.Sheets[sheetNames[0]]; // Here we only read the first sheet

        let json = XLSX.utils.sheet_to_row_object_array(worksheet);
        let compras = '';
        json.forEach(x => {
            let compra = Object.values(x);
            let linea = '';
            compra.forEach(function (item, index) {
                if (index + 1 == compra.length) {
                    linea = linea + item + '\n';

                } else {
                    linea = linea + item + '|';
                }

            });
            compras = compras + linea;
        });
        enviarCompras(compras, '|');
    };

}

const enviarCompras = (text, separador) => {
    const excel = text.toString().split('\n');
    let compras = new Array();
    excel.forEach(x => {
        const textLine = x.toString().split(separador);
        const nro= textLine[0] ? textLine[0].trim() :''
        const nitProveedor=  textLine[1] ? textLine[1].trim() :'';
        const nroFactura=  textLine[2] ? textLine[2].trim() :'';
        const nroAutorizacion=  textLine[3] ? textLine[3].trim() :'';
        const fechaEmision=  textLine[4] ? textLine[4].trim() :'';
        const importeTotal=  textLine[5] ? textLine[5].trim() :'';
        const codigoControl=  textLine[6] ? textLine[6].trim() :'';
		
        const compra = {
            nro: {
                campo: CABECER_EXCEL_NRO,
                valor: nro,
                valido: numeroFilaExcel.isValid(nro),
                mensaje: numeroFilaExcel.mensajeError()
            },
            nitProveedor: {
                campo: CABECER_EXCEL_NIT_PROVEEDOR,
                valor:  nitProveedor,
                valido: nitValidador.isValid(nitProveedor),
                mensaje: nitValidador.mensajeError()
            },
            nroFactura: {
                campo: CABECER_EXCEL_NRO_FACTURA,
                valor:  nroFactura,
                valido: numeroFacturaValidador.isValid(nroFactura),
                mensaje: numeroFacturaValidador.mensajeError()
            },
            nroAutorizacion: {
                campo: CABECER_EXCEL_NUMERO_AUTORIZACION_CUF,
                valor: nroAutorizacion,
                valido: numeroAutorizacionValidador.isValid(nroAutorizacion),
                mensaje: numeroAutorizacionValidador.mensajeError()
            },
            fechaEmision: {
                campo: CABECER_EXCEL_FECHA_EMISION,
                valor: fechaEmision,
                valido: fechaValidador.isValid(fechaEmision).success,
                mensaje: fechaValidador.mensajeError()
            },
            importeTotal: {
                campo: CABECER_EXCEL_IMPORTE_TOTAL,
                valor: convertirStringToInporte(importeTotal),
                valido: importeValidador.isValid(importeTotal),
                mensaje: importeValidador.mensajeError()
            },
            codigoControl: {
                campo: CABECER_EXCEL_CCDIGO_DE_C_ONTROL,
                valor: codigoControl,
                valido: codigoControlValidador.isValid(nroAutorizacion + '|' + codigoControl),
                mensaje: codigoControlValidador.mensajeError()
            },
        };
        compras.push(compra);
    });

    const errores = compras.filter(compra => validarCompraExelError(compra)).filter(compra => validarCompraExel(compra)).map(compra => error(compra));
    if(errores.length > 0) {
        mostrarErrorJsBean([ { name : 'Errores', value :
                JSON.stringify(errores) } ]);
        return;
    }

    const comprasDto = compras.map(compra => convertir(compra)).filter(compra => limpiarCompra(compra));
    const vfacturas = JSON.stringify(comprasDto);
    validacionArchivoBean([{name: 'Factura', value: vfacturas}]);
}

const error = (compra) => {
    let descripcion = '';
    if (!compra.nitProveedor.valido) {
        descripcion = descripcion + '\n' + compra.nitProveedor.mensaje;
    }
    if (!compra.nroFactura.valido) {
        descripcion = descripcion + '\n' + compra.nroFactura.mensaje;
    }
    if (!compra.nroAutorizacion.valido) {
        descripcion = descripcion + '\n' + compra.nroAutorizacion.mensaje;
    }
    if (!compra.fechaEmision.valido) {
        descripcion = descripcion + '\n' + compra.fechaEmision.mensaje;
    }
    if (!compra.importeTotal.valido) {
        descripcion = descripcion + '\n' + compra.importeTotal.mensaje;
    }
    if (!compra.codigoControl.valido) {
        descripcion = descripcion + '\n' + compra.codigoControl.mensaje;
    }

    return {
        descripcion: descripcion,
        fila: compra.nro.valor,
        columna: '',
        tipo: ''
    }
}

const limpiarCompra = (compra) => {
    if (compra.nro === '') {
        return false;
    }
    if (compra.nro.trim().toUpperCase() === CABECER_EXCEL_NRO.trim().toUpperCase()) {
        return false;
    }
    return true;
}

const convertir = (compra) => {
    return {
        nro: compra.nro.valor,
        nitProveedor: compra.nitProveedor.valor,
        nroFactura: compra.nroFactura.valor,
        nroAutorizacion: compra.nroAutorizacion.valor,
        fechaEmision: compra.fechaEmision.valor,
        importeTotal: compra.importeTotal.valor,
        codigoControl: compra.codigoControl.valor
    }
}

const validarCompraExel = (compra) => {
    return !(
        compra.nro.valido &&
        compra.nitProveedor.valido &&
        compra.nroFactura.valido &&
        compra.nroAutorizacion.valido &&
        compra.fechaEmision.valido &&
        compra.importeTotal.valido &&
        compra.codigoControl.valido);
}

const validarCompraExelError = (compra) => {

    return (
        compra.nro.valido ||
        compra.nitProveedor.valido ||
        compra.nroFactura.valido ||
        compra.nroAutorizacion.valido ||
        compra.fechaEmision.valido ||
        compra.importeTotal.valido);
}
