"use strict";

var CLASS_INPUT_CSS = 'ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-fluid ui-input-invalid';
var CLASS_INPUT_ERROR_CSS = 'bg-danger ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-state-error';
var CLASS_ICON_ERROR_CSS = 'btn btn-danger btn-sm fa fa-times-circle';
var CLASS_ICON_SUCCESS = 'btn btn-outline-success fa fa-check';
var FECHA_MINIMA_REGISTRO = new Date(2016, 0, 1);


/* *********************************** 
*	TODO VALIDACIONES GLOBAL - individual
**************************************/

var regEx = {
	nombre:/^[a-zA-Z ]{2,254}$/,
	nit: /^([1-9]{1}([0-9]{1}){5,11})$/,
  	nroFactura: /^([1-9]{1}([0-9]{1}){0,14})$/,
  	codigoControl: /^([A-F0-9]{2}(-[A-F0-9]{2}){3,4})$/,
  	fecha: /^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/,
  	nroAutorizacion: /^[1-9]{1}[0-9]{1}[1-9]{1}[1|3|4|6|7|8]{1}(01|05|09|15|16)[0-9]{9}$/,
	numeroAutorizacionBoletoAvion: /\b[1]{1}\b/,
  	cuf: /\b([a-zA-Z0-9]{57}|[a-zA-Z0-9]{58})\b/,
	
};
/* *********************************** 
*	TODO VALIDACIONES GLOBAL - individual
**************************************/
// TODO fecha
function dateValid(date){
	//var msg;
	if(date=="__/__/____") return "Campo requerido";
	
	if(!regEx.fecha.test(date)) return "Fecha Invalida"; 
	
	var arrayDate= date.toString().split('/');
    var dia = parseInt(arrayDate[0]);
    var mes = parseInt(arrayDate[1]) - 1;
    var anio = parseInt(arrayDate[2]);
    var fecha = new Date(anio, mes, dia);

    if (fecha > new Date()) return "Fecha mayor a la de Hoy";

    if (fecha < new Date(2016, 0, 1)) return "Fecha menor al 2016";
      
}

// TODO nit // ADD VERHOEFF
function nitValid(nit, opt){
	if(opt=="v"){	// VALIDA SI CE TRATA DE VENTA
		if(nit.toString().trim()=="0") return;
		if (!regEx.nit.test(nit.toString().trim())) {
	        return "Nit Incorrecto";
	    }
		if(validarDigitoVerificador(nit.toString().trim())){  // !
	      	return 'NIT inválido.';
	    }
		
	}{	// EN OTRO CASO VALIDA COMO COMPRA
		if (!regEx.nit.test(nit.toString().trim())) {
	        return "Nit Incorrecto";
	    }
		if(validarDigitoVerificador(nit.toString().trim())){  // !
	      	return 'NIT inválido.';
	    }	
	}
	
}

// TODO VALIDAR nro FACTURA
function nroFacturaValid(factura){
	if (!regEx.nroFactura.test(factura)) {
        return "Factura no invalida";
    }
}

// TODO VALIDAR NRO DE AUTORIZACION
function nroAutorValid(nro){
	// [\d]{0,15}
	if(!nro) return "deben tener 15 ";
	
	if (!regEx.nroAutorizacion.test(nro.toString().trim())) {
        return 'Número de Autorización / CUF es inválido. 1';
    }

    if (regEx.cuf.test(nro.toString().trim())) {
        return 'Codigo CUF es inválido.';
    }
	if(regEx.numeroAutorizacionBoletoAvion.test(nro.toString().trim())){
		return 'Número de Autorización de aerolineas es inválido.';
	}

}


function stringValid(dstring){
	if(!dstring) return "El dato es necesario";
	if(!regEx.nombre.test(dstring)) return "Dato no valido"
}

function importeValid (monto,mMin) {
	var m=monto.toString();
  	if(!m)return "Debe Ingresar un Monto";
	var importe = convertirStringToInporte(m);
	var min = convertirStringToInporte(mMin.toString());
	
    if (importe < min) {
        return 'Importe inválido.';
    }
  
};

function codControlValid(cod) {
	// TODO verifica con numero d autorizacion correspondiente  
    if (!regEx.codigoControl.test(cod.toString().toUpperCase())) {
      return 'Código de Control inválido';
    }

};





/************************ VALIDATOR FOR EACH ROW ***************************** */

function checkRow(e,rowIndex, widgetPf){
	
	var rowIn = parseInt(rowIndex);
	
	var row=widgetPf.findRow(rowIn);
	var celdas=row[0].cells;
	var col=celdas.length-2;
	
	for(let i=1;i<col; i++){
		var dat= celdas[i].childNodes[0];
				
		if(dat.classList.contains("ui-inputnumber")){
			var inputNum= dat.childNodes[0];
			
			console.log("validacion normal ");
			var ms=dataValid(inputNum);
			if(ms) {
				inputNum.focus();
				return false;
			}
			console.log(inputNum.value);
		}
		if(dat.classList.contains("ui-inputfield")){
			
			var ms=dataValid(dat);
			if(ms) {
				dat.focus();
				return false;
			}
			console.log(dat.value);
		}
		
		console.log("no tiene funcion");
		
	}
	return true
}






/************************************************************************************* */

var sinCodigoControl2 = function sinCodigoControl2(codigoAutorizacion) {
  if (codigoAutorizacion) {
    if (expresiones.numeroAutorizacionBoletoAvion.test(codigoAutorizacion)) {
      return true;
    }

    if (expresiones.cuf.test(codigoAutorizacion)) {
      return true;
    }

    if (numeroAutorizacionValidador.isValid(codigoAutorizacion)) {
      var dato = codigoAutorizacion.toString().substring(3, 4);

      if (dato === '1' || dato === '3') {
        return true;
      }
    }

    return false;
  }

  return true;
};

var convertirStringToInporte = function convertirStringToInporte(valor) {
  if (expresiones.importeEntero.test(valor)) {
    return parseFloat(valor.toString().trim());
  }

  if (expresiones.importeDecPunto.test(valor)) {
    return parseFloat(valor.toString().trim());
  }

  if (expresiones.importeMilComa.test(valor)) {
    valor = valor.toString().trim().replace(/,/g, '');
    return parseFloat(valor);
  }

  if (expresiones.importeMilComaDecPunto.test(valor)) {
    valor = valor.toString().trim().replace(/,/g, '');
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
};

var soloNumeros = function soloNumeros(e) {
  var key = window.event ? e.which : e.keyCode;

  if (key < 48 || key > 57) {
    e.preventDefault();
  }
};

var sinCodigoControl = function sinCodigoControl(codigoAutorizacion) {
  if (codigoAutorizacion.value) {
    if (expresiones.numeroAutorizacionBoletoAvion.test(codigoAutorizacion.value)) {
      return true;
    }

    if (validadorNumeroAutorizacion(codigoAutorizacion)) {
      var dato = codigoAutorizacion.value.toString().substring(3, 4);

      if (dato === '1' || dato === '3') {
        return true;
      }
    }

    return false;
  }

  return true;
};

var validadorNit = function validadorNit(nit) {
  return nitValidador.isValid(nit.value);
};

var validadorNumeroFactura = function validadorNumeroFactura(numeroFactura) {
  return numeroFacturaValidador.isValid(numeroFactura.value);
};

var validadorNumeroAutorizacion = function validadorNumeroAutorizacion(numeroAutorizacion) {
  if (numeroAutorizacion.value) {
    return numeroAutorizacionValidador.isValid(numeroAutorizacion.value);
  }

  return false;
};

var validatorFechaEmision = function validatorFechaEmision(fechaEmision) {
  return fechaValidador.isValid(fechaEmision.value);
};

var validadorImporte = function validadorImporte(importe) {
  return importeValidador.isValid(importe.value);
};

var validadorCodigoControl = function validadorCodigoControl(numeroAutorizacion, codigoControl) {
  var valorNumeroAutorizacion = numeroAutorizacion.value ? numeroAutorizacion.value : '';
  var valorcodigoControl = codigoControl.value ? codigoControl.value : '';
  return codigoControlValidador.isValid(valorNumeroAutorizacion + '|' + valorcodigoControl);
};

var validarDigitoVerificador = function validarDigitoVerificador(nit) {
  var vCheck = 0;
  var lnit = parseInt(nit);
  var snumero = Array.from(String(lnit), Number);
  var vLargo = snumero.length;
  var i = vLargo - 1;

  for (var j = 0; i >= 0; ++j) {
    var x = j % 8;
    var s = snumero[i].toString();
    var y = parseInt(s);
    var z = matriz_permutaciones(x, y);
    vCheck = matriz_verhoeff(vCheck, z);
    --i;
  }

  var ret = vCheck === 0 ? true : false;
  return ret;
};

var matriz_verhoeff = function matriz_verhoeff(x, y) {
  var v = ['0123456789', '1234067895', '2340178956', '3401289567', '4012395678', '5987604321', '6598710432', '7659821043', '8765932104', '9876543210'];
  var vSalida = '0';

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
};

var matriz_permutaciones = function matriz_permutaciones(x, y) {
  var v = ['0123456789', '1576283094', '5803796142', '8916043527', '9453126870', '4286573901', '2793806415', '7046913258'];
  var vSalida = '0';

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
};

var expresiones = {
  nit: /^([1-9]{1}([0-9]{1}){5,11})$/,
  numeroFactura: /^([1-9]{1}([0-9]{1}){0,14})$/,
  numeroDui: /^(2)\d{3}((07)[1-3]{1}|[1-8]{1}(01)|[1-7]{1}(02)|[^0458]{1}(11)|(712)|[^0138]{1}(21)|[4-7]{1}(22)|[6-7]{1}(23)|[2-9]{1}(31)|[2|3|4|7]{1}(32)|[2|7]{1}(33|34|35)|(73)[6-8]{1}|[2|6|7|8]{1}(41)|[2|5|6|8]{1}(42)|[2|5|6|7]{1}(43)|244|751|752|[2|3|7]{1}(61)|(862))[C]{1}\d{10}$/,
  codigoControl: /^([A-F0-9]{2}(-[A-F0-9]{2}){3,4})$/,
  fecha: /^(0[1-9]|1[0-9]|2[0-9]|3[0-1])(\/)(0[1-9]|1[0-2])(\/)(2|3)\d{3}$/,
  numeroAutorizacion: /^[1-9]{1}[0-9]{1}[1-9]{1}[1|3|4|6|7|8]{1}(01|05|09|15|16)[0-9]{9}$/,
  numeroAutorizacionNota: /\b[1-9]{1}[1-9]{1}[1-9]{1}[1-9]{1}[0]{1}[3]{1}[0-9]{9}\b/,
  numeroAutorizacionCasosEspeciales: /\b[1|3]{1}\b/,
  numeroAutorizacionBoletoAvion: /\b[1]{1}\b/,
  cuf: /\b([a-zA-Z0-9]{57}|[a-zA-Z0-9]{58})\b/,
  carateresEspeciales: /\W/,
  numeroFila: /\b[0-9]{1,5}\b/,
  importeMilComaDecPunto: /^[1-9]{1}([0-9]{1}){0,2}(\,[0-9]{3}){0,4}\.\d{1,2}$/,
  importeMilPuntoDecComa: /^[1-9]{1}([0-9]{1}){0,2}(\.[0-9]{3}){0,4}\,\d{1,2}$/,
  importeMilComa: /^[1-9]{1}([0-9]{1}){0,2}(\,[0-9]{3}){0,4}$/,
  importeDecPunto: /^([1-9]{1}[0-9]{0,14})(\.[0-9]{1,2})$/,
  importeDecComa: /^([1-9]{1}[0-9]{0,14})(\,[0-9]{1,2})$/,
  importeEntero: /^([1-9]{1}[0-9]{0,14})$/
};



var eliminarCaracteresEspeciales = function eliminarCaracteresEspeciales(e) {
  var key = window.event ? e.which : e.keyCode;
  console.log(key);

  if (expresiones.carateresEspeciales.test(String.fromCharCode(key))) {
    e.preventDefault();
  }
};

var numeroFilaExcel = {
  isValid: function isValid(valor) {
    if (valor) {
      return expresiones.numeroFila.test(valor.toString().trim());
    }

    return false;
  },
  mensajeError: function mensajeError() {
    return 'Debe pegar con el número de fila.';
  }
};
var nitValidador = {
  isValid: function isValid(valor) {
    if (valor) {
      if (!expresiones.nit.test(valor.toString().trim())) {
        return false;
      }

      return validarDigitoVerificador(valor);
    }

    return false;
  },
  mensajeError: function mensajeError() {
    return 'NIT inválido.' + ',';
  }
};

var numeroAutorizacionValidador = {
  isValid: function isValid(valor) {
    if (valor) {
      if (expresiones.numeroAutorizacion.test(valor.toString().trim())) {
        return true;
      }

      if (expresiones.cuf.test(valor.toString().trim())) {
        return true;
      }

      return expresiones.numeroAutorizacionBoletoAvion.test(valor.toString().trim());
    }

    return false;
  },
  mensajeError: function mensajeError() {
    return 'Número de Autorización / CUF es inválido.' + ',';
  }
};
var fechaValidador = {
  isValid: function isValid(valor) {
    if (!valor) {
      return {
        success: false,
        mensaje: "Dato Invalido"
      }; //false;
    }

    var valorTrim = valor.toString().trim();

    if (!expresiones.fecha.test(valorTrim)) {
      return {
        success: false,
        mensaje: "Fecha Invalida"
      }; //false;
    }

    var dia = parseInt(valorTrim.toString().split('/')[0]);
    var mes = parseInt(valorTrim.toString().split('/')[1]) - 1;
    var anio = parseInt(valorTrim.toString().split('/')[2]);
    var fechaRegistro = new Date(anio, mes, dia);

    if (fechaRegistro > new Date()) {
      return {
        success: false,
        mensaje: "Fecha mayor a la de Hoy"
      }; //false;
    }

    if (fechaRegistro < FECHA_MINIMA_REGISTRO) {
      return {
        success: false,
        mensaje: "Fecha menor al año 2016"
      }; //false;
    }

    if (!(dia === fechaRegistro.getDate() && mes === fechaRegistro.getMonth() && anio === fechaRegistro.getFullYear())) {
      return {
        success: false,
        mensaje: "Fecha invalida"
      }; //false;
    }

    return {
      success: true
    };
  },
  mensajeError: function mensajeError() {
    return 'Fecha Emisión es inválido' + ',';
  }
};
var importeValidador = {
  isValid: function isValid(valor) {
    if (valor) {
      var importe = convertirStringToInporte(valor.toString());

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
  mensajeError: function mensajeError() {
    return 'Importe Total Compra inválido.' + ',';
  }
};
var codigoControlValidador = {
  isValid: function isValid(valor) {
    var numeroAutorizacion = valor.toString().trim().toString().split('|')[0];
    var codigoControl = valor.toString().trim().toString().split('|')[1] ? valor.toString().trim().toString().split('|')[1] : '';

    if (sinCodigoControl2(numeroAutorizacion)) {
      if (codigoControl) {
        return valor.length <= 0;
      }

      return true;
    }

    if (valor) {
      return expresiones.codigoControl.test(codigoControl.toString().toUpperCase());
    }

    return false;
  },
  mensajeError: function mensajeError() {
    return 'Código de Control inválido';
  }
};



var campoCambioCss = function campoCambioCss(campo, css, iconCss) {
  campo.className = css;

  if (campo.parentElement.tagName === 'SPAN') {
    campo.parentElement.parentElement.nextElementSibling.firstElementChild.className = iconCss;
    campo.parentElement.parentElement.nextElementSibling.firstElementChild.style.visibility = "visible";
  } else {
    campo.parentElement.nextElementSibling.firstElementChild.className = iconCss;
    campo.parentElement.nextElementSibling.firstElementChild.style.visibility = "visible";
  }
};

var desactivarEnter = function desactivarEnter(e) {
  if (e.keyCode === 13) {
    return e.preventDefault();
  }
};