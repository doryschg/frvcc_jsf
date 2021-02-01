const CLASS_INPUT_CSS = 'ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-fluid ui-input-invalid';
const CLASS_INPUT_ERROR_CSS = 'bg-danger ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-state-error';
const CLASS_ICON_ERROR_CSS = 'btn btn-danger btn-sm fa fa-times-circle';
const CLASS_ICON_SUCCESS = 'btn btn-outline-success fa fa-check';
const FECHA_MINIMA_REGISTRO = new Date(2016, 0, 1);

const soloNumeros = (e) => {
    const key = window.event ? e.which : e.keyCode;
    if (key < 48 || key > 57) {
        e.preventDefault();
    }
}
const sinCodigoControl = (codigoAutorizacion) => {
    if (codigoAutorizacion.value) {
        if (expresiones.numeroAutorizacionCasosEspeciales.test(codigoAutorizacion.value)) {
            return true;
        }
        if (validarNumeroAutorizacion(codigoAutorizacion)) {
            const dato = codigoAutorizacion.value.toString().substring(3, 4);
            if (dato === '1' || dato === '3'|| dato === '6') {
                return true;
            }
        }
        return false;
    }
    return true;
}

const sinCodigoControlNota = (codigoAutorizacionNota) => {
    if (codigoAutorizacionNota.value) {
        if (validarNumeroAutorizacionNota(codigoAutorizacionNota)) {
            const dato = codigoAutorizacionNota.value.toString().substring(3, 4);
            if (dato === '1' || dato === '3'|| dato === '6') {
                return true;
            }
        }
        return false;
    }
    return true;
}

const validadorNit = (nit) => {
    return nitValidador.isValid(nit.value);
}

const validadorNumeroFactura = (numeroFactura) => {
    return numeroFacturaValidador.isValid(numeroFactura.value);
}

const validadorNumeroFacturaEstandar = (numeroFactura,numeroDui) => {
	 const valorNumeroFactura = numeroFactura.value? numeroFactura.value:'';
	 const valorNumeroDui = numeroDui.value? numeroDui.value:'';
    return numeroFacturaEstandarValidador.isValid(valorNumeroFactura+'|'+valorNumeroDui);
}

const validadorNumeroDui = (numeroFactura,numeroDui) => {
	 const valorNumeroFactura = numeroFactura.value? numeroFactura.value:'';
	 const valorNumeroDui = numeroDui.value? numeroDui.value:'';
    return numeroDuiValidador.isValid(valorNumeroFactura+'|'+valorNumeroDui);
}

const validatorFechaEmision = (fechaEmision) => {
    return fechaValidador.isValid(fechaEmision.value);
}

//refactorizar
const validadorCodigoControl = (numeroAutorizacion,codigoControl) => {
    const valorNumeroAutorizacion = numeroAutorizacion.value? numeroAutorizacion.value:'';
    const valorcodigoControl = codigoControl.value? codigoControl.value:'';
   return codigoControlValidador.isValid(valorNumeroAutorizacion+'|'+valorcodigoControl);
}

const validadorCodigoControlNota = (numeroAutorizacionNota,codigoControlNota) => {
    const valorNumeroAutorizacionNota = codigoControlNota.value? codigoControlNota.value:'';
    const valorcodigoControlNota = codigoControlNota.value? codigoControlNota.value:'';
   return codigoControlNotaValidador.isValid(valorNumeroAutorizacionNota+'|'+valorcodigoControlNota);
}

const validarNumeroAutorizacion = (numeroAutorizacion) => {
    if (numeroAutorizacion.value) {
        return numeroAutorizacionValidador.isValid(numeroAutorizacion.value);
    }
    return false;
}

const validarNumeroAutorizacionEstandar = (numeroAutorizacion,numeroDui,numeroFactura) => {
	
	 const valorAutorizacion = numeroAutorizacion.value? numeroAutorizacion.value:'';
	 const valorNumeroDui = numeroDui.value? numeroDui.value:'';
	 const valorNumeroFactura = numeroFactura.value? numeroFactura.value:'';
    return numeroAutorizacionEstandarValidador.isValid(valorAutorizacion+'|'+valorNumeroDui+'|'+valorNumeroFactura);
}

const validarNumeroAutorizacionNota = (numeroAutorizacionNota) => {
    if (numeroAutorizacionNota.value) {
        return numeroAutorizacionNotaValidador.isValid(numeroAutorizacionNota.value);
    }
    return false;
}

const validadorImporte = (importe) => {
    return importeValidador.isValid(importe.value);
}

const validadorImporteNoSujetoCf = (importe) => {
    return importeNoSujetoCfValidador.isValid(importe.value);
}

const validadorDsctoBonRebajasObt = (importe) => {
    return dsctoBonRebajasObtValidador.isValid(importe.value);
}

const validadorImporteBaseCf = (importe) => {
    return importeBaseCfValidador.isValid(importe.value);
}

//const validadorImporteNota = (importe) => {
//    return importeTotalNotaValidador.isValid(importe.value);
//}
//
//const validadorImporteOriginal = (importe) => {
//    return importeTotalOriginalValidador.isValid(importe.value);
//}

const validarDigitoVerificador = (nit) => {
    let vCheck = 0;
    const lnit = parseInt(nit);
    const snumero = Array.from(String(lnit), Number);
    const vLargo = snumero.length;
    let i = vLargo - 1;
    for (let j = 0; i >= 0; ++j) {
        let x = j % 8;
        let s = snumero[i].toString();
        let y = parseInt(s);
        let z = matriz_permutaciones(x, y);
        vCheck = matriz_verhoeff(vCheck, z);
        --i;
    }
    const ret = vCheck === 0 ? true : false;
    return ret;
}

const matriz_verhoeff = (x, y) => {
    const v = ['0123456789', '1234067895', '2340178956', '3401289567', '4012395678', '5987604321', '6598710432', '7659821043', '8765932104', '9876543210'];
    let vSalida = '0';
    if (x === 0) {
        vSalida = v[0].substring(y, y + 1);
    } else if (x === 1) {
        vSalida = v[1].substring(y, y + 1);
    } else if (x === 2) {
        vSalida = v[2].substring(y, y + 1);
    } else if (x === 3) {
        vSalida = v[3].substring(y, y + 1);
    } else if (x === 4) {
        vSalida = v[4].substring(y, y + 1);
    } else if (x === 5) {
        vSalida = v[5].substring(y, y + 1);
    } else if (x === 6) {
        vSalida = v[6].substring(y, y + 1);
    } else if (x === 7) {
        vSalida = v[7].substring(y, y + 1);
    } else if (x === 8) {
        vSalida = v[8].substring(y, y + 1);
    } else if (x === 9) {
        vSalida = v[9].substring(y, y + 1);
    } else {
        vSalida = '-1';
    }

    return parseInt(vSalida);
}

const matriz_permutaciones = (x, y) => {
    const v = ['0123456789', '1576283094', '5803796142', '8916043527', '9453126870', '4286573901', '2793806415', '7046913258'];
    let vSalida = '0';
    if (x === 0) {
        vSalida = v[0].substring(y, y + 1);
    } else if (x === 1) {
        vSalida = v[1].substring(y, y + 1);
    } else if (x === 2) {
        vSalida = v[2].substring(y, y + 1);
    } else if (x === 3) {
        vSalida = v[3].substring(y, y + 1);
    } else if (x === 4) {
        vSalida = v[4].substring(y, y + 1);
    } else if (x === 5) {
        vSalida = v[5].substring(y, y + 1);
    } else if (x === 6) {
        vSalida = v[6].substring(y, y + 1);
    } else if (x === 7) {
        vSalida = v[7].substring(y, y + 1);
    } else {
        vSalida = '-1';
    }
    return parseInt(vSalida);
}

const expresiones = {
    nit: /^([1-9]{1}([0-9]{1}){5,11})$/,
    numeroFactura: /^([1-9]{1}([0-9]{1}){0,9})$/,
    numeroDui: /^(2)\d{3}((07)[1-3]{1}|[1-8]{1}(01)|[1-7]{1}(02)|[^0458]{1}(11)|(712)|[^0138]{1}(21)|[4-7]{1}(22)|[6-7]{1}(23)|[2-9]{1}(31)|[2|3|4|7]{1}(32)|[2|7]{1}(33|34|35)|(73)[6-8]{1}|[2|6|7|8]{1}(41)|[2|5|6|8]{1}(42)|[2|5|6|7]{1}(43)|244|751|752|[2|3|7]{1}(61)|(862))[C]{1}\d{10}$/,
    codigoControl: /^([A-F0-9]{2}(-[A-F0-9]{2}){3,4})$/,
    fecha: /^(0[1-9]|1[0-9]|2[0-9]|3[0-1])(\/)(0[1-9]|1[0-2])(\/)(2|3)\d{3}$/,
    numeroAutorizacion: /\b[1-9]{1}[0-9]{1}[1-9]{1}[1|3|4|6|7|8]{1}[0]{1}[159]{1}[0-9]{9}\b/,
    numeroAutorizacionNota: /\b[1-9]{1}[1-9]{1}[1-9]{1}[1-9]{1}[0]{1}[3]{1}[0-9]{9}\b/,
    numeroAutorizacionCasosEspeciales: /\b[1|3]{1}\b/,
    numeroAutorizacionBoletoAvion: /\b[1]{1}\b/,
    cuf: /\b([a-zA-Z0-9]{57}|[a-zA-Z0-9]{58})\b/,
    carateresEspeciales: /\W/,
    numeroFila: /\b[0-9]{1,5}\b/,
    importeMilComaDecPunto: /^[1-9]{1}([0-9]{1}){0,2}(\,[0-9]{3}){0,4}\.\d{1,2}$/,
    importeMilPuntoDecComa: /^[1-9]{1}([0-9]{1}){0,2}(\.[0-9]{3}){0,4}\,\d{1,2}$/,
    importeDecPunto: /^([1-9]{1}[0-9]{0,14})(\.[0-9]{1,2})$/,
    importeDecComa: /^([1-9]{1}[0-9]{0,14})(\,[0-9]{1,2})$/,
    importeEntero: /^([1-9]{1}[0-9]{0,14})$/
};


const convertirStringToInporte= (valor)=>{
    if (expresiones.importeEntero.test(valor)) {
        return parseFloat(valor.toString().trim());
    }
    if (expresiones.importeDecPunto.test(valor)) {
        return parseFloat(valor.toString().trim());
    }
    if (expresiones.importeMilComaDecPunto.test(valor)) {
        valor= valor.toString().trim().replace(/,/g, '')
        return parseFloat(valor);
    }
    if (expresiones.importeDecComa.test(valor)) {
        return parseFloat(valor.toString().trim().replace(/,/g, '.'));
    }
    if (expresiones.importeMilPuntoDecComa.test(valor)) {
        valor = valor.toString().trim().replace('.', '');
        return parseFloat(valor.replace(/,/g, '.'));
    }
    return parseFloat('0');
}

const convertirStringToInporteYcero= (valor)=>{
	
	if (valor.toString()==='0,00' || valor.toString()==='0,0' || valor.toString()==='0.00' || valor.toString()==='0.0'|| valor.toString()==='0') {
		return parseFloat(valor.toString().trim());
    }
    if (expresiones.importeEntero.test(valor)) {
        return parseFloat(valor.toString().trim());
    }
    if (expresiones.importeDecPunto.test(valor)) {
        return parseFloat(valor.toString().trim());
    }
    if (expresiones.importeMilComaDecPunto.test(valor)) {
        valor= valor.toString().trim().replace(/,/g, '')
        return parseFloat(valor);
    }
    if (expresiones.importeDecComa.test(valor)) {
        return parseFloat(valor.toString().trim().replace(/,/g, '.'));
    }
    if (expresiones.importeMilPuntoDecComa.test(valor)) {
        valor = valor.toString().trim().replace('.', '');
        return parseFloat(valor.replace(/,/g, '.'));
    }
    return parseFloat('-1');
}


const eliminarCaracteresEspeciales = (e) => {
    const key = window.event ? e.which : e.keyCode;
    console.log(key);
    if (expresiones.carateresEspeciales.test(String.fromCharCode(key))) {
        e.preventDefault();
    }
}

const numeroFacturaValidador = {
    isValid: (valor) => {
        if (valor) {
            const valorTrim = valor.toString().trim();
            return expresiones.numeroFactura.test(valorTrim);
        }
        return false;
    },
    mensajeError: () => {
        return 'Número de Factura inválido.' + ',';
    }
}

const numeroFacturaEstandarValidador = {
	    isValid: (valor) => {
	        const numeroFactura = valor.toString().trim().toString().split('|')[0];
	        const numeroDui = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';

	        if (numeroFactura=='0') {
	        	 if (numeroDui!='0' && numeroDui!='') {
	        		 return true;
	 	        }else {
	 	        	 return false;
	 	        }
	        }else{
		       const valorNroFacturaTrim = numeroFactura.toString().trim();
		       return expresiones.numeroFactura.test(valorNroFacturaTrim);
		       
	        }
	    },
	    mensajeError: () => {
	        return 'Número Factura inválido.' + ',';
	    }
	}


const numeroDuiValidador = {
	    isValid: (valor) => {
	        const numeroFactura = valor.toString().trim().toString().split('|')[0];
	        const numeroDui = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';

	        if (numeroDui=='0') {
	        	 if (numeroFactura!='0' && numeroFactura!='') {
	        		 return true;
	 	        }else {
	 	        	 return false;
	 	        }
	        }else {
	        	const valorNroDuiTrim = numeroDui.toString().trim();
	            if (expresiones.numeroDui.test(valorNroDuiTrim)) {
	            	 const fechaActual = new Date();
		        	 const year =fechaActual.getFullYear(); 
		        	 
		             const dato = valorNroDuiTrim.toString().substring(0, 4);
		                 if (dato === year.toString() ) {
		                     return true;
		                 }else {
		                	 return false;
		                 }
                }else {
                	return false;
                }
	     	 
	        }
	    },
	    mensajeError: () => {
	        return 'Número Dui inválido.' + ',';
	    }
	}


const numeroFilaExcel = {
    isValid: (valor) => {
        if (valor) {
            return expresiones.numeroFila.test(valor.toString().trim());
        }
        return false;
    },
    mensajeError: () => {
        return 'Debe pegar con el número de fila.';
    }
}


const nitValidador = {
    isValid: (valor) => {
        if (valor) {
            if (!expresiones.nit.test(valor.toString().trim())) {
                return false;
            }
            return validarDigitoVerificador(valor);
        }
        return false;
    },
    mensajeError: () => {
        return 'NIT inválido.'+',';
    }
}

const numeroAutorizacionValidador = {
    isValid: (valor) => {
        if (valor) {
            if (expresiones.numeroAutorizacion.test(valor.toString().trim())) {
                return true;
            }
            if (expresiones.cuf.test(valor.toString().trim())) {
                return true;
            }
            return expresiones.numeroAutorizacionCasosEspeciales.test(valor.toString().trim());
        }
        return false;
    },
    mensajeError: () => {
        return 'Número de Autorización / CUF es inválido.'+',';
    }
}

const numeroAutorizacionEstandarValidador = {
	    isValid: (valor) => {  
	        if (valor) {
	        	 const valorCodigoAutorizacion = valor.toString().trim().toString().split('|')[0];
		         const valorNumeroDui= valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';
		         const valorNumeroFactura= valor.toString().trim().toString().split('|')[2] ? valor.toString().trim().toString().split('|')[2] : '';
		        
	            if (expresiones.numeroAutorizacion.test(valorCodigoAutorizacion)  && expresiones.numeroFactura.test(valorNumeroFactura) && valorNumeroDui==='0' ) {
	            	return true;        
	            }
	            if (expresiones.cuf.test(valorCodigoAutorizacion)  && expresiones.numeroFactura.test(valorNumeroFactura) && valorNumeroDui==='0' ) {
	                return true;
	            }
	            if (expresiones.numeroAutorizacionCasosEspeciales.test(valorCodigoAutorizacion)) {
	            	if (valorCodigoAutorizacion=="1"  && expresiones.numeroFactura.test(valorNumeroFactura) && valorNumeroDui==='0' ) {
	            		 return true;
	            		 }
	            	 if (valorCodigoAutorizacion=="3" && expresiones.numeroDui.test(valorNumeroDui) && valorNumeroFactura==='0' ) {
	 	                return true;
	 	            }          
	            }
	            return false;     
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Número de Autorización / CUF es inválido.'+',';
	    }
	}

const numeroAutorizacionNotaValidador = {
	    isValid: (valor) => {
	        if (valor) {
	            if (expresiones.numeroAutorizacionNota.test(valor.toString().trim())) {
	                return true;
	            }  
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Número de Autorización Nota es inválido.'+',';
	    }
	}

const importeValidador = {
    isValid: (valor) => {
        if (valor) {
            const importe = convertirStringToInporte(valor.toString());
            if (!importe) {
                return false;
            }
            if (importe < 1) {
                return false;
            }
            return true;
        }
        return false;
    },
    mensajeError: () => {
        return 'Importe Total Compra inválido.'+',';
    }
}

const importeNoSujetoCfValidador = {
	    isValid: (valor) => {
	        if (valor) {
	            const importe = convertirStringToInporte(valor.toString());
	            if (valor.toString()==='0,00' || valor.toString()==='0,0' || valor.toString()==='0.00' || valor.toString()==='0.0'|| valor.toString()==='0') {
	                return true;
	            }
	            if (!importe) {
	                return false;
	            }
	            if (importe < 1) {
	                return false;
	            }
	            return true;
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Importe no Sujeto a Crédito Fiscal inválido.'+',';
	    }
	}

const montoNoSujetoCfValidador = {
	    isValid: (valor) => {
	    	 const valorImporteTotal = valor.toString().trim().toString().split('|')[0];
	         const valorImporteNoSujeto = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';
	         const valorImporteDescuento = valor.toString().trim().toString().split('|')[2] ? valor.toString().trim().toString().split('|')[2] : '';
	        if (valor) {
	            const importeNoSujeto = convertirStringToInporteYcero(valorImporteNoSujeto);
	            const importeTotal = convertirStringToInporteYcero(valorImporteTotal);
	            const importeTotalDescuento = convertirStringToInporteYcero(valorImporteDescuento);
	            
	            if (importeNoSujeto < 0 || importeTotal < 0 || importeTotalDescuento < 0) {
	                return false;
	            }
	            if (importeNoSujeto > importeTotal) {
	                return false;
	            }else{
	            	const vImporteBase=parseFloat(importeTotal) - parseFloat(importeNoSujeto) - parseFloat(importeTotalDescuento);
	            	 if(parseFloat(vImporteBase.toFixed(2))< 0) { 
	            		 return false;
	            	 }else{
	            		 const vImporteCalculado=parseFloat(importeTotal) - parseFloat(vImporteBase.toFixed(2)) - parseFloat(importeTotalDescuento);
		            	 if(parseFloat(importeNoSujeto)==parseFloat(vImporteCalculado.toFixed(2))) { 
		            		 return true;
		            	 }else{
		            		 return false;
		            	 }
	            	 }	
	            }  
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Importe no Sujeto a Crédito Fiscal inválido.'+',';
	    }
	}

const montoDsctoBonRebajasObtValidador = {
	    isValid: (valor) => {
	        const valorImporteTotal = valor.toString().trim().toString().split('|')[0];
	        const valorImporteNoSujeto = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';
	        const valorImporteDescuento = valor.toString().trim().toString().split('|')[2] ? valor.toString().trim().toString().split('|')[2] : '';

	        if (valor) {
	            const importeDescuento = convertirStringToInporteYcero(valorImporteDescuento);
	            const importeTotal = convertirStringToInporteYcero(valorImporteTotal);
	            const importeNoSujeto = convertirStringToInporteYcero(valorImporteNoSujeto);
	          
	            if (importeDescuento < 0 || importeTotal < 0 || importeNoSujeto < 0) {
	                return false;
	            }
	            
	            if (importeDescuento > importeTotal) {
	                return false;
	            }else{
	            	const vImporteBase=parseFloat(importeTotal) - parseFloat(importeNoSujeto) - parseFloat(importeDescuento);
	            	 if(parseFloat(vImporteBase.toFixed(2))< 0) { 
	            		 return false;
	            	 }else{
	            		 const vImporteCalculado=parseFloat(importeTotal) - parseFloat(vImporteBase.toFixed(2)) - parseFloat(importeNoSujeto);
		            	 if(parseFloat(importeDescuento)==parseFloat(vImporteCalculado.toFixed(2))) { 
		            		 return true;
		            	 }else{
		            		 return false;
		            	 } 
	            	 }
	            }
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Descuento, Bonificaciones y Rebajas Obtenidas inválido.'+',';
	    }
	}

const dsctoBonRebajasObtValidador = {
	    isValid: (valor) => {
	        if (valor) {
	            const importe = convertirStringToInporte(valor.toString());
	            if (valor.toString()==='0,00' || valor.toString()==='0,0' || valor.toString()==='0.00' || valor.toString()==='0.0'|| valor.toString()==='0') {
	                return true;
	            }
	            if (!importe) {
	                return false;
	            }
	            if (importe < 1) {
	                return false;
	            }
	            return true;
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Descuento, Bonificaciones y Rebajas Obtenidas inválido.'+',';
	    }
	}

const importeBaseCfValidador = {
	    isValid: (valor) => {
	        if (valor) {
	            const importe = convertirStringToInporte(valor.toString());
	            if (!importe) {
	                return false;
	            }
	            if (importe < 1) {
	                return false;
	            }
	            return true;
	        }
	        return false;
	    },
	    mensajeError: () => {
	        return 'Importe Base para Crédito Fiscal inválido.'+',';
	    }
	}

//const importeTotalNotaValidador = {
//	    isValid: (valor) => {
//	        if (valor) {
//	            const importe = convertirStringToInporte(valor.toString());
//	            if (!importe) {
//	                return false;
//	            }
//	            if (importe < 1) {
//	                return false;
//	            }
//	            return true;
//	        }
//	        return false;
//	    },
//	    mensajeError: () => {
//	        return 'Importe Total Compra Nota inválido.'+',';
//	    }
//	}
//
//const importeTotalOriginalValidador = {
//	    isValid: (valor) => {
//	        if (valor) {
//	            const importe = convertirStringToInporte(valor.toString());
//	            if (!importe) {
//	                return false;
//	            }
//	            if (importe < 1) {
//	                return false;
//	            }
//	            return true;
//	        }
//	        return false;
//	    },
//	    mensajeError: () => {
//	        return 'Importe Total Compra Original inválido.'+',';
//	    }
//	}

const fechaValidador = {
    isValid: (valor) => {
        if (!valor) {
            return {
				success:false,
				mensaje:"Dato Invalido"
			}//false;
        }
        const valorTrim = valor.toString().trim();
        if (!expresiones.fecha.test(valorTrim)) {
            return {
				success:false,
				mensaje:"Fecha Invalida"
			}//false;
        }
        const dia = parseInt(valorTrim.toString().split('/')[0]);
        const mes = parseInt(valorTrim.toString().split('/')[1]) - 1;
        const anio = parseInt(valorTrim.toString().split('/')[2]);
        const fechaRegistro = new Date(anio, mes, dia);
        if (fechaRegistro > new Date()) {
            return {
				success:false,
				mensaje:"Fecha mayor a la de Hoy"
			}//false;
        }
        if (fechaRegistro < FECHA_MINIMA_REGISTRO) {
            return {
				success:false,
				mensaje:"Fecha menor al año 2016"
			}//false;
        }
        if(!(dia === fechaRegistro.getDate() && mes === fechaRegistro.getMonth()  && anio === fechaRegistro.getFullYear())){
            return {
				success:false,
				mensaje:"Fecha invalida"
			}//false;
        }
        return {
				success:true
			}
    },
    mensajeError: () => {
        return 'Fecha Emisión es inválido'+',';
    }
}


//TODO VALORES NULOS
const codigoControlValidador = {
    isValid: (valor) => {
        const numeroAutorizacion = valor.toString().trim().toString().split('|')[0];
        const codigoControl = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';


        if (sinCodigoControl2(numeroAutorizacion)) {
            if(codigoControl){
                return valor.length <= 0;
            }
            return true;
        }

        if (valor) {
            return expresiones.codigoControl.test(codigoControl.toString().toUpperCase());
        }

        return false;
    },
    mensajeError: () => {
        return 'Código de Control inválido';
    }
}
const codigoControlNotaValidador = {
	    isValid: (valor) => {
	        const numeroAutorizacionNota = valor.toString().trim().toString().split('|')[0];
	        const codigoControlNota = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';

	        if (sinCodigoControlNota2(numeroAutorizacionNota)) {
	            if(codigoControlNota){
	                return valor.length <= 0;
	            }
	            return true;
	        }
	        if (valor) {
	            return expresiones.codigoControl.test(codigoControl.toString().toUpperCase());
	        }

	        return false;
	    },
	    mensajeError: () => {
	        return 'Código de Control Nota inválido';
	    }
	}

//TODO
const sinCodigoControl2 = (codigoAutorizacion) => {
    if (codigoAutorizacion) {
        if (expresiones.numeroAutorizacionCasosEspeciales.test(codigoAutorizacion)) {
            return true;
        }
        if (expresiones.cuf.test(codigoAutorizacion)) {
            return true;
        }

        if (numeroAutorizacionValidador.isValid(codigoAutorizacion)) {
            const dato = codigoAutorizacion.toString().substring(3, 4);
            if (dato === '1' || dato === '3' || dato === '6') {
                return true;
            }
        }
        return false;
    }
    return true;
}

const sinCodigoControlNota2 = (codigoAutorizacionNota) => {
    if (codigoAutorizacionNota) {
        if (numeroAutorizacionNotaValidador.isValid(codigoAutorizacionNota)) {
            const dato = codigoAutorizacionNota.toString().substring(3, 4);
            if (dato === '1' || dato === '3' || dato === '6') {
                return true;
            }
        }
        return false;
    }
    return true;
}

const campoCambioCss = (campo, css, iconCss) => {
    campo.className = css;
    if (campo.parentElement.tagName === 'SPAN') {
        campo.parentElement.parentElement.nextElementSibling.firstElementChild.className = iconCss;
        campo.parentElement.parentElement.nextElementSibling.firstElementChild.style.visibility = "visible";   
    } else {
        campo.parentElement.nextElementSibling.firstElementChild.className = iconCss;
        campo.parentElement.nextElementSibling.firstElementChild.style.visibility = "visible";
    }
}


const desactivarEnter = (e) => {
    if (e.keyCode === 13) {
        return e.preventDefault();
    }
}



