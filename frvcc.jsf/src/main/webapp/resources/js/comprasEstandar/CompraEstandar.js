const nitProveedor0 = document.getElementById('frmRegistroCompras:dtMisFacturas:0:txtNitProveedor');
const tablaRegistro='frmRegistroCompras:dtMisFacturas';
const botonGuardar = 'btnGuardar';
const txtNitProveedor='txtNitProveedor';
const txtNumeroFactura='txtNumeroFactura';
const txtNumeroDui='txtNumeroDui';
const txtFechaFactura='txtFechaFactura';
const txtCodigoAutorizacion='txtCodigoAutorizacion';
const txtImporteTotal_input='txtImporteTotal_input';
const txtImporteNoSujetoCF_input='txtImporteNoSujetoCF_input';
const txtDsctoBonRebajasObt_input='txtDsctoBonRebajasObt_input';
const txtImporteBaseCf_input='txtImporteBaseCf_input';
const txtCodigoControl='txtCodigoControl';

const CABECER_EXCEL_NRO= 'N°';
const CABECER_EXCEL_NIT_PROVEEDOR= 'NIT Proveedor';
const CABECER_EXCEL_NRO_FACTURA= 'N° Factura';
const CABECER_EXCEL_NRO_DUI= 'N° DUI';
const CABECER_EXCEL_NUMERO_AUTORIZACION_CUF= 'Número Autorización / CUF';
const CABECER_EXCEL_FECHA_EMISION= 'Fecha Emisión';
const CABECER_EXCEL_IMPORTE_TOTAL= 'Importe Total';
const CABECER_EXCEL_IMPORTE_NO_SUJETO_CF= 'Importe no Sujeto a Crédito Fiscal';
const CABECER_EXCEL_DESCUENTOS= 'Descuentos, Bonificaciones y Rebajas Obtenidas';
const CABECER_EXCEL_IMPORTE_BASE_CF= 'Importe Base Crédito Fiscal';
const CABECER_EXCEL_CCDIGO_DE_C_ONTROL= 'Código Control';

var mensajes={
	txtNitProveedor:"NIT inválido!",
	txtNumeroFactura:'Número Factura inválido!',
	txtNumeroDui:'Número DUI inválido!',
	txtCodigoAutorizacion:'Código Autorización inválido!',
	txtImporteTotal_input:'Importe Total inválido!',
	txtImporteNoSujetoCF_input:'Importe no Sujeto CF inválido!',
	txtDsctoBonRebajasObt_input:'Descuento inválido!',
	txtImporteBaseCf_input:'Importe Base CF inválido!',
	txtCodigoControl:'Código de control inválido!'
}

const guardarCompra = (event) => {
    let index;
    if (event.target) {
        index = event.target.id.split(':')[2];
    } else {
        index = event.id.split(':')[2];
    }
	var pos=parseInt(index)+1;
	var x = document.getElementById(`${tablaRegistro}`).getElementsByTagName("tr")[pos];
	var d=x.getElementsByTagName("td");
    d[11].innerHTML = "";


    if (event.key === 'Enter') {
        if (validarCompra(event)) {
            const guardar = document.getElementById(`${tablaRegistro}:${index}:${botonGuardar}`);
            guardar.click();
            event.preventDefault();
        }
        return event.preventDefault();
    }
}

//TODO VALIDA PARA EL REGISTRO INDIVIDUAL (ENTER)
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
    const numeroDui = document.getElementById(`${tablaRegistro}:${index}:${txtNumeroDui}`);
    const fechaFactura = document.getElementById(`${tablaRegistro}:${index}:${txtFechaFactura}`);
    const numeroAutrizacion = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoAutorizacion}`);
    const importeTotal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotal_input}`);
    const importeNoSujetoCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteNoSujetoCF_input}`);
    const dsctoBonRebajasObt = document.getElementById(`${tablaRegistro}:${index}:${txtDsctoBonRebajasObt_input}`);
    const importeBaseCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteBaseCf_input}`);
    const codigoControl = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoControl}`);

    const valNitProveedor = validadorNit(nitProveedor);
    const valNumeroFactura = validadorNumeroFacturaEstandar(numeroFactura,numeroDui);
    const valNumeroDui = validadorNumeroDui(numeroFactura,numeroDui);
    const valNumeroAutrizacion = validadorNumeroAutorizacionEstandar(numeroAutrizacion,numeroDui,numeroFactura);
    const valFechaFactura = validatorFechaEmision(fechaFactura).success;
    const valImporteTotal = validadorImporte(importeTotal);
    const valImporteNoSujetoCf = validadorImporteNoSujetoCf(importeNoSujetoCf);
    const valDsctoBonRebajasObt = validadorDsctoBonRebajasObt(dsctoBonRebajasObt);
    const valImporteBaseCf = validadorImporteBaseCf(importeBaseCf);
    const valcodigoControl = validadorCodigoControl(numeroAutrizacion,codigoControl);
    
    
    const vImporteBaseCalculado =calcularImporteBaseCf(e);
    
    if (!valImporteBaseCf) {
        importeBaseCf.focus()
    }
    if (!valDsctoBonRebajasObt) {
    	dsctoBonRebajasObt.focus()
    }
    if (!valImporteNoSujetoCf) {
    	importeNoSujetoCf.focus()
    }
    if (!valImporteTotal) {
        importeTotal.focus()
    }
    if (!valFechaFactura) {
        fechaFactura.focus()
    }
    if (!valNumeroAutrizacion) {
        numeroAutrizacion.focus()
    }
    if (!valNitProveedor) {
        nitProveedor.focus()
    }
    if (!valNumeroFactura) {
    	numeroFactura.focus()
    }
    if (!valNumeroDui) {
    	numeroDui.focus()
    }
    
    if (!(valNitProveedor
            && valNumeroFactura
            && valNumeroDui
            && valNumeroAutrizacion
            && valFechaFactura
            && valImporteTotal
            && valImporteNoSujetoCf
            && valDsctoBonRebajasObt
            && valImporteBaseCf
    )) {
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

//TODO VALIDA PARA EL REGISTRO INDIVIDUAL (XHTML)
const validarFormulario = e => {
    const codigoControl = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoControl}`);
    const numeroDui = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtNumeroDui}`);
    const numeroFactura = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtNumeroFactura}`);
    const numeroAutorizacion = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoAutorizacion}`);
    
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
        	if (e.value=='') {
        		numeroDui.disabled = false;	
        		campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
        		//numeroAutorizacion.value = '0';
        	}else{
        		if (validadorNumeroFacturaEstandar(e,numeroDui)) {
        			campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
        			document.getElementById(ruta).children[1].innerHTML="";
        			
        			numeroDui.value = '0';
    	            numeroDui.className = CLASS_INPUT_CSS;
    	            campoCambioCss(numeroDui, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
    	            numeroDui.disabled = true; 
    	            
        			} else {
        				campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
        				document.getElementById(ruta).children[1].innerHTML=mensajes.txtNumeroFactura;
        				}		 
            }	
            break;
        case txtNumeroDui: 
        	if (e.value=='') {
        		numeroFactura.disabled = false;
        		campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
        	}else{
        		if (validadorNumeroDui(numeroFactura,e)) {
        			campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
        			document.getElementById(ruta).children[1].innerHTML=""; 
        			
        			numeroFactura.value = '0';
            		numeroFactura.className = CLASS_INPUT_CSS;
            		campoCambioCss(numeroFactura, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
            		numeroFactura.disabled = true;  
        			} else {
        				campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
    					document.getElementById(ruta).children[1].innerHTML=mensajes.txtNumeroDui;
            			} 		
        }
            break;
        case txtCodigoAutorizacion:
            if (validadorNumeroAutorizacionEstandar(e,numeroDui,numeroFactura)) {
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
			var fchFac=validatorFechaEmision(e);
            if (fchFac.success) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=fchFac.mensaje;
            }
            break;
        case txtImporteTotal_input:
        	const vImporteBaseCalculado =calcularImporteBaseCf(e);
        	
            if (validadorImporte(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteTotal_input;
            }
            break;
        case txtImporteNoSujetoCF_input:
        	const vImporteBaseCalculado1 =calcularImporteBaseCf(e);
        	
        	if (parseFloat(vImporteBaseCalculado1.value) < parseFloat("0.0")) {
        		const vImpBaseCF  = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtImporteBaseCf_input}`);
        	  	vImpBaseCF.value="0.00";
        	  	campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteNoSujetoCF_input;
        	}else{
        		if (validadorImporteNoSujetoCf(e)) {
                	if (validarValorImporteNoSujetoCf(e)) {
                		campoCambioCss(e, CLASS_INPUT_CSS,CLASS_ICON_SUCCESS); 
						document.getElementById(ruta).children[1].innerHTML=""; 
                	}else {
                		campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
						document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteNoSujetoCF_input;
                	}
                } else {
                    campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
					document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteNoSujetoCF_input;
                }
        	}
            break;    
        case txtDsctoBonRebajasObt_input:  
        	const vImporteBaseCalculado2 =calcularImporteBaseCf(e);
        	if(parseFloat(vImporteBaseCalculado2.value)< parseFloat("0.0")) {
        		const vImpBaseCF  = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtImporteBaseCf_input}`);
        	  	vImpBaseCF.value="0.00";
        	  	campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtDsctoBonRebajasObt_input;
        	}else{
        		if (validadorDsctoBonRebajasObt(e)) {
                	if (validarValorDsctoBonRebajasObt(e)) {
                		campoCambioCss(e, CLASS_INPUT_CSS,CLASS_ICON_SUCCESS); 
						document.getElementById(ruta).children[1].innerHTML="";
                	}else {
                		campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
						document.getElementById(ruta).children[1].innerHTML=mensajes.txtDsctoBonRebajasObt_input;
                	}
                } else {
                    campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
					document.getElementById(ruta).children[1].innerHTML=mensajes.txtDsctoBonRebajasObt_input;
                }
        	}
            break;     
        case txtImporteBaseCf_input:
            if (validadorImporteBaseCf(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS,CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS,CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteBaseCf_input;
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

//TODO VALIDA PARA EL REGISTRO MASIVO (EXCEL)
const enviarCompras = (text, separador) => {
    const excel = text.toString().split('\n');
    let compras = new Array();
    excel.forEach(x => {
        const textLine = x.toString().split(separador);
        const nro= textLine[0] ? textLine[0].trim() :''
        const nitProveedor=  textLine[1] ? textLine[1].trim() :'';
        const nroFactura=  textLine[2] ? textLine[2].trim() :'';
        const nroDui=  textLine[3] ? textLine[3].trim() :'';
        const nroAutorizacion=  textLine[4] ? textLine[4].trim() :'';
        const fechaEmision=  textLine[5] ? textLine[5].trim() :'';
        const importeTotal=  textLine[6] ? textLine[6].trim() :'';
        const importeNoSujetoCF=  textLine[7] ? textLine[7].trim() :'';
        const dsctoBonRebajasObt=  textLine[8] ? textLine[8].trim() :'';
        const importeBaseCf=  textLine[9] ? textLine[9].trim() :'';
        const codigoControl=  textLine[10] ? textLine[10].trim() :'';
        
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
                valido: numeroFacturaEstandarValidador.isValid(nroFactura + '|' + nroDui),
                mensaje: numeroFacturaEstandarValidador.mensajeError()
            },
            nroDui: {
                campo: CABECER_EXCEL_NRO_DUI,
                valor:  nroDui,
                valido: numeroDuiValidador.isValid(nroFactura + '|' + nroDui),
                mensaje: numeroDuiValidador.mensajeError()
            },
            nroAutorizacion: {
                campo: CABECER_EXCEL_NUMERO_AUTORIZACION_CUF,
                valor: nroAutorizacion,
                valido: numeroAutorizacionEstandarValidador.isValid(nroAutorizacion+ '|' + nroDui+ '|' + nroFactura),
                mensaje: numeroAutorizacionEstandarValidador.mensajeError()
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
            importeNoSujetoCF: {
                campo: CABECER_EXCEL_IMPORTE_NO_SUJETO_CF,
                valor: convertirStringToInporteYcero(importeNoSujetoCF),
                valido: montoNoSujetoCfValidador.isValid(importeTotal + '|' + importeNoSujetoCF+ '|' + dsctoBonRebajasObt),
                mensaje: montoNoSujetoCfValidador.mensajeError()
            },
            dsctoBonRebajasObt: {
                campo: CABECER_EXCEL_DESCUENTOS,
                valor: convertirStringToInporteYcero(dsctoBonRebajasObt),
                valido: montoDsctoBonRebajasObtValidador.isValid(importeTotal + '|' + importeNoSujetoCF+ '|' + dsctoBonRebajasObt),
                mensaje: montoDsctoBonRebajasObtValidador.mensajeError()
            },
            importeBaseCf: {
            	campo: CABECER_EXCEL_IMPORTE_BASE_CF,
                valor: convertirStringToInporte(importeBaseCf),
                valido: true,
                mensaje: importeBaseCfValidador.mensajeError()
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
    if (!compra.nroDui.valido) {
        descripcion = descripcion + '\n' + compra.nroDui.mensaje;
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
    if (!compra.importeNoSujetoCF.valido) {
        descripcion = descripcion + '\n' + compra.importeNoSujetoCF.mensaje;
    }
    if (!compra.dsctoBonRebajasObt.valido) {
        descripcion = descripcion + '\n' + compra.dsctoBonRebajasObt.mensaje;
    }
    if (!compra.importeBaseCf.valido) {
        descripcion = descripcion + '\n' + compra.importeBaseCf.mensaje;
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
        nroDui: compra.nroDui.valor,
        nroAutorizacion: compra.nroAutorizacion.valor,
        fechaEmision: compra.fechaEmision.valor,
        importeTotal: compra.importeTotal.valor,
        importeNoSujetoCF: compra.importeNoSujetoCF.valor,
        dsctoBonRebajasObt: compra.dsctoBonRebajasObt.valor,
        importeBaseCf: compra.importeBaseCf.valor,
        codigoControl: compra.codigoControl.valor
    }
}

const validarCompraExel = (compra) => {
    return !(
        compra.nro.valido &&
        compra.nitProveedor.valido &&
        compra.nroFactura.valido &&
        compra.nroDui.valido &&
        compra.nroAutorizacion.valido &&
        compra.fechaEmision.valido &&
        compra.importeTotal.valido &&
        compra.importeNoSujetoCF.valido &&
        compra.dsctoBonRebajasObt.valido &&
        compra.importeBaseCf.valido &&
        compra.codigoControl.valido);
}

const validarCompraExelError = (compra) => {

    return (
        compra.nro.valido ||
        compra.nitProveedor.valido );
}

const calcularImporteBaseCf = (e) => {
	const index = e.id.split(':')[2];
	const importeTotal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotal_input}`);
    const importeNoSujetoCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteNoSujetoCF_input}`);
    const dsctoBonRebajasObt = document.getElementById(`${tablaRegistro}:${index}:${txtDsctoBonRebajasObt_input}`);
    const importeBaseCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteBaseCf_input}`);
    
    const vImporteTotal = (importeTotal.value == "") ? "0.0" :(importeTotal.value).replaceAll(",", "");
    const vImporteNoSujetoCf = (importeNoSujetoCf.value == "") ? "0.0" : (importeNoSujetoCf.value).replaceAll(",", "");
    const vDsctoBonRebajasObt = (dsctoBonRebajasObt.value == "") ? "0.0" : (dsctoBonRebajasObt.value).replaceAll(",", "");
    
    const vSubTotal= parseFloat(vImporteTotal) - parseFloat(vImporteNoSujetoCf);
    const vImporteBaseCf = parseFloat(vSubTotal)- parseFloat(vDsctoBonRebajasObt); 
  
    importeBaseCf.value=currencyFormat(vImporteBaseCf);
      
    return importeBaseCf;
}

const currencyFormat = (num) => {
	const valor=num.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
	return valor;
}

const validarValorImporteNoSujetoCf = (e) => {
	const index = e.id.split(':')[2];
	const importeTotal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotal_input}`);
    const importeNoSujetoCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteNoSujetoCF_input}`);
    const dsctoBonRebajasObt = document.getElementById(`${tablaRegistro}:${index}:${txtDsctoBonRebajasObt_input}`);
    const importeBaseCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteBaseCf_input}`);
    
    const vImporteTotal = (importeTotal.value == "") ? "0.0" : (importeTotal.value).replaceAll(",", "");
    const vImporteNoSujetoCf = (importeNoSujetoCf.value == "") ? "0.0" : (importeNoSujetoCf.value).replaceAll(",", "");
    const vDsctoBonRebajasObt = (dsctoBonRebajasObt.value == "") ? "0.0" : (dsctoBonRebajasObt.value).replaceAll(",", "");
    const vImporteBaseCf = (importeBaseCf.value == "") ? "0.0":  (importeBaseCf.value).replaceAll(",", "");
    
  	if (parseFloat(vImporteNoSujetoCf) > parseFloat(vImporteTotal)) {
  		 return false;
      } else {
    	  const vImporteCalculado=parseFloat(vImporteTotal) - parseFloat(vDsctoBonRebajasObt) - parseFloat(vImporteBaseCf);
    		
    	  if(parseFloat(vImporteNoSujetoCf)==parseFloat(vImporteCalculado.toFixed(2))) {  
        		 return true;
        	 }else{
        		 return false;
        	 }
      }    
}

const validarValorDsctoBonRebajasObt = (e) => {
	const index = e.id.split(':')[2];
	const importeTotal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotal_input}`);
    const importeNoSujetoCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteNoSujetoCF_input}`);
    const dsctoBonRebajasObt = document.getElementById(`${tablaRegistro}:${index}:${txtDsctoBonRebajasObt_input}`);
    const importeBaseCf = document.getElementById(`${tablaRegistro}:${index}:${txtImporteBaseCf_input}`);
     
    const vImporteTotal = (importeTotal.value == "") ? "0.0" : (importeTotal.value).replaceAll(",", "");
    const vImporteNoSujetoCf = (importeNoSujetoCf.value == "") ? "0.0" : (importeNoSujetoCf.value).replaceAll(",", "");
    const vDsctoBonRebajasObt = (dsctoBonRebajasObt.value == "") ? "0.0" : (dsctoBonRebajasObt.value).replaceAll(",", "");
    const vImporteBaseCf = (importeBaseCf.value == "") ? "0.0" : (importeBaseCf.value).replaceAll(",", "");
    
  	if (parseFloat(vDsctoBonRebajasObt) > parseFloat(vImporteTotal)) {
  		return false;	
      } else {
    	  const vImporteCalculado=parseFloat(vImporteTotal) - parseFloat(vImporteNoSujetoCf) - parseFloat(vImporteBaseCf);
    	    if(parseFloat(vDsctoBonRebajasObt)==parseFloat(vImporteCalculado.toFixed(2))) { 
        		 return true;
        	 }else{
        		 return false;
        	 }
      }    
}
