
/* *********************************** 
*	TODO CUSTOME MESSAGE
**************************************/

// TODO set value of tooltip MESSAGE
function setTooltipMsg(id,msg){
	var idTooltip= id.toString().replace(/:([^:]*)$/, ':_' + '$1');
	document.getElementById(idTooltip).children[1].innerHTML=msg;
}
 
// TODO set item whit errors
function setErrorItem(data, msg){
	
	var idTooltip= data.id.toString().replace(/:([^:]*)$/, ':_' + '$1');
	if(msg) {
		setTooltipMsg(data.id,msg);
		data.className +=" ErrorImput ui-state-hover ui-state-focus"; //
		/// return false;
	}else{
		data.classList.remove("ErrorImput");
		setTooltipMsg(data.id,"");		
	}

	//return true;
}


/* *********************************** 
*	TODO VALIDACIONES INDIVIDUALES - inputs
**************************************/

// TODO validar fecha - Fecha Factura'
function validarFecha(date) {
	console.log(date.id);
	var msg = dateValid(date.value);
	setErrorItem(date,msg)
	//var re=!msg;
	return msg;
}

// TODO validar FACTURA - N° de Factura
function validarFac(nroFac){
	console.log(nroFac.id);
	var msg = nroFacturaValid(nroFac.value);
	setErrorItem(nroFac,msg);
	return msg;
}

// TODO  - N° Autorizacion/CUF
function validarNroAuto(na){
	var msg= nroAutorValid(na.value);
	setErrorItem(na,msg);
	return msg;
}

// TODO validar NIT - Nit/CI Cliente 
function validarNit(nit){
	console.log(nit.id);
	var msg;
	msg = nitValid(nit.value,"v");
		setErrorItem(nit,msg);
	return msg;
}

// TODO - Nombre/Razon Social
function validarNombre(nom){
	var msg= stringValid(nom.value);
	setErrorItem(nom,msg);
	return msg;
}	

// TODO - Importe Total Factura
function validarItf(itf){
	//itf.select();
	var msg=importeValid(itf.value,1)
	setErrorItem(itf,msg);
	return msg;
}	
	
// TODO - Descuentos, Bonificaciones y Rebajas
function validarDbr(dbr){
	var msg= importeValid(dbr.value,0);
	setErrorItem(dbr,msg);
	return msg;
}	

// TODO - Importe Base Debito Fiscal
function validarIbdf(ibdf){
	var msg= importeValid(ibdf.value,0);
	setErrorItem(ibdf,msg);
	return msg;
}


// TODO - Codigo de Control
function validarCodCont(cod){	
    IMask(cod, {
        mask: '**-**-**-**-**'
    });
	var msg=codControlValid(cod.value);
	setErrorItem(cod,msg);
	return msg;

}


/* *********************************** 
*	TODO VALIDACIONES - MASIVAS COPY PASTE
**************************************/
PrimeFaces.widgets.wVPaste.jq.on("paste", function(e){
		console.log(e);
	var result;
	var data = e.originalEvent.clipboardData;
	if(data){
		result = data.getData('Text');
	}else{
		result = data.getData('text/plain');
	}

	getObjetct(result);
	//console.log(result);
});

function getObjetct(text){
	const excel = text.toString().split('\n');
    let ventas = new Array();
    excel.forEach(x => {
	if (x){
			const textLine = x.toString().split('\t');
	
			const fchFactura = textLine[0] ? textLine[0].trim() :'';			// FECHA FACTURA 16
			const nroFactura= textLine[1] ? textLine[1].trim() :'';				// Nro DE FACTURA
			const codAutorCuf= textLine[2] ? textLine[2].trim() :'';			// Nro AUTORIZACION/CUF
			const estado= textLine[3] ? textLine[3].trim() :'';					// ESTADO 
			const nitCliente= textLine[4] ? textLine[4].trim() :'';				// NIT/CI CLIENTE
			const razonSocial= textLine[5] ? textLine[5] :'';					// NOMBRE O RAZON SOCIAL
			const imprtTotal= convertirStringToInporte(textLine[6].trim());		// IMPORTE TOTAL FACTURA o LA VENTA
			const descuento= convertirStringToInporte(textLine[11].trim());		// DESCUENTOS
			const imprtBaseDf= convertirStringToInporte(textLine[12].trim());	// DEBITO FISCAL
			const codControl= textLine[14] ? textLine[14].trim() :'';			// CODIGO DE CONTROL
			
			const venta = {
				fchFactura : {
	                valor:  fchFactura,
	                mensaje: dateValid(fchFactura)
	            },
				nroFactura: {
	                valor:  nroFactura,
	                mensaje: nroFacturaValid(nroFactura)
	            },
				nitCliente: {
	                valor:  nitCliente,
	                mensaje: nitValid(nitCliente)
	            }, 
				razonSocial: {
	                valor:  razonSocial,
	                mensaje: stringValid(razonSocial)
	            },
				imprtTotal: {
	                valor:  imprtTotal,
	                mensaje: importeValid(imprtTotal,1)
	            },
				descuento: {
	                valor:  descuento,
	                mensaje: importeValid(descuento,0)
	            },
				imprtBaseDf: {
	                valor:  imprtBaseDf,
	                mensaje: importeValid(imprtBaseDf,0)
	            },
				codAutorCuf: {
	                valor:  codAutorCuf,
	                mensaje: nroAutorValid(codAutorCuf)
	            }, 
				codControl: {
	                valor:  codControl,
	                mensaje: codControlValid(codControl),
	            },
				estado: {
	                valor:  estado,
	                mensaje: stringValid(estado)
	            },
			};
			
			ventas.push(venta);
			
		}


	});

	const errors =ventas.filter(el => checkContents(el));
	if(errors.length > 0) {
		var err='\n';
		errors.forEach(function(error) {
			err+=formatError(error)+"\n";
		});
		download("errores.txt",err);
	}else{
		// enviar a la consulta
		enviarRows([{name: 'ventas', value: JSON.stringify(ventas)}]);

	}
	
}

function checkContents(data){
			
	return ( data.fchFactura.mensaje ||
			data.nroFactura.mensaje ||
			data.codAutorCuf.mensaje ||
			data.estado.mensaje ||
			data.nitCliente.mensaje ||
			data.razonSocial.mensaje ||
			data.imprtTotal.mensaje ||
			data.imprtTasas.mensaje ||
			data.imprtExntas.mensaje ||
			data.vntasTsaCero.mensaje ||
			data.subTotal.mensaje ||
			data.descuento.mensaje ||
			data.imprtBaseDf.mensaje ||
			data.dbitoFiscal.mensaje ||
			data.codControl.mensaje);
	
}

function formatError(data){
	var arr= Object.entries(data);
	return	("- Nro Factura : "+data.nroFactura.valor)+ " : "+
	  		(data.fchFactura.mensaje? data.fchFactura.mensaje :" ") +","+ 
			(data.nroFactura.mensaje? data.nroFactura.mensaje :" " ) +","+
			(data.codAutorCuf.mensaje? data.codAutorCuf.mensaje :" " ) +","+
			(data.estado.mensaje? data.estado.mensaje :" ") +","+
			(data.nitCliente.mensaje ? data.nitCliente.mensaje :" ") +","+
			(data.razonSocial.mensaje ? data.razonSocial.mensaje :" ") +","+
			(data.imprtTotal.mensaje ? data.imprtTotal.mensaje :" ") +","+
			(data.imprtTasas.mensaje ? data.imprtTasas.mensaje :" ") +","+
			(data.imprtExntas.mensaje ? data.imprtExntas.mensaje :" ") +","+
			(data.vntasTsaCero.mensaje ? data.vntasTsaCero.mensaje :" ") +","+
			(data.subTotal.mensaje ? data.subTotal.mensaje :" ") +","+
			(data.descuento.mensaje ? data.descuento.mensaje :" ") +","+
			(data.imprtBaseDf.mensaje ? data.imprtBaseDf.mensaje :" ") +","+
			(data.dbitoFiscal.mensaje ? data.dbitoFiscal.mensaje :" ") +","+
			(data.codControl.mensaje? data.codControl.mensaje :" ") +",";
	
}



/* *********************************** 
*	TODO VALIDACIONES - ROW
**************************************/
function firstRow(){
	var row=PrimeFaces.widgets.dtWgtVenta.findRow(0);
	var colums=row[0].cells[1].childNodes[0].focus();
}

function dataValid(data){
	
	if(!data.disabled){
		var attrFn=data.getAttribute('onkeyup');
		if(attrFn){
			const fnstring= attrFn.slice(0, -6);
			var fn = window[fnstring];
			if (typeof fn === "function"){
				return fn(data);
			}
		}		
	}
		
}



/* *********************************** 
*	TODO EVENTOS 
**************************************/
// TODO VERIFICA LA OPCION ENTER
function saveRow(event,row){
	console.log(event.key);
	const rowIndex = parseInt(row);
	console.log(rowIndex);
	if (event.key === 'Enter') {
		if(checkRow(event,rowIndex,PrimeFaces.widgets.dtWgtVenta)){
			var guardar = document.getElementById("frmVentas:dtVentas:"+row+":btnGuardar");
	      	guardar.click();
			event.preventDefault();
		}
	}
}



// TODO		-----------------  CARGA DE ARCHIVOS EXCEL -------------
function importFile(evt) {
  	var file = evt.files[0];
	var m=fileValid(file);
	if(m){
		toastr.info(m.msg, 'Info');
		return 
	}
	do_file(file);
	
	// if(contents) enviarFile();
	
}

// TODO VERIFICA SI UN ARCHIVO ES VALIDO
function fileValid(file){
	if (!file) return {msg:"Error en la carga del archivo"};;
			
	regex = new RegExp("(.*?)\.(ods|xlsx|xls|csv)$");
	if (!(regex.test(file.name))) {
			return {msg:"Extension de archivo invalido"};
	}
			
	if (file.size > 5 * 1024 * 1024) {
			return{msg:"El archivo excede el tamaño permitido"};
	}
}




var X = XLSX; 
var XW = {
	msg : 'xlsx',
	worker : '../javax.faces.resource/xlsxworker.js.xhtml?ln=js'
};


var webworkers;
var ejecutar_webworkers = (function() {
	var crear_csv = function to_csv(workbook) {
		var result = [];
		workbook.SheetNames.forEach(function(sheetName) {
			var csv = X.utils.sheet_to_csv(workbook.Sheets[sheetName], {
				  blankrows : false,
				  defval: '',
		    	  dateNF: 'DD"/"MM"/"YYYY',
		    	  skipHidden:false,
		    	  FS:"|"
			    });
			
			if (csv.length) {
				var lines = csv.split('\n');	
				var vCabecera = lines[0].split('|');
				var vCabeceraFinal = "";
				var vIteracion = 0;
				var vTamanioElementosCabecera = vCabecera.length;
				vCabecera.forEach(function(elemento) {
					var vKey = getKeyByValue(dict, elemento);
					if(vKey != 'undefined')
					 if((vIteracion + 1) == (vTamanioElementosCabecera)){
						vCabeceraFinal = vCabeceraFinal + vKey;
					}else{
						vCabeceraFinal = vCabeceraFinal + vKey + "|";
					}					 
					 vIteracion++;
				});
					
				lines[0] = vCabeceraFinal;
				csv = lines.join('\n');
			
				result.push(csv);
			}
		});
		return result;
	};

	var crear_json = (function() {
		var fmt = document.getElementsByName("JSON");
		return function() {
			for (var i = 0; i < fmt.length; ++i)
				if (fmt[i].checked || fmt.length === 1)
					return fmt[i].value;
		};
	})();

	var to_json = function to_json(workbook) {
		var result = {};
		workbook.SheetNames.forEach(function(sheetName) {
			var roa = X.utils.sheet_to_json(workbook.Sheets[sheetName], {
		    	  dateNF: 'DD"/"MM"/"YYYY'
			});
			if (roa.length)
				result = roa;
		});
		return result;//JSON.stringify(result);
	};

	return function ejecutar_webworkers(wb) {
		webworkers = wb;
		var output = "";
		
		switch (crear_json()) {
		default:
			output = to_json(wb);
		}

		console.log("dada>>>> ",output);
		
		validateAjv(output);
		
		if (typeof console !== 'undefined')
			console.log("output", new Date());

	};
})();


var do_file = (function() {
	var rABS = typeof FileReader !== "undefined" && (FileReader.prototype || {}).readAsBinaryString;
	var domrabs = document.getElementsByName("userabs")[0];

	var use_worker = typeof Worker !== 'undefined';

	var domwork = document.getElementsByName("useworker")[0];

	var xw = function xw(data, cb) {
		var worker = new Worker(XW.worker);
		console.log("test: " + X.version);
		console.log(worker);
		worker.onmessage = function(e) {
			switch (e.data.t) {
			case 'ready':
				break;
			case 'e':
				console.error(e.data.d);
				break;
			case XW.msg:
				cb(JSON.parse(e.data.d));
				break;
			}
		};

		worker.postMessage({
			d : data,
			b : rABS ? 'binary' : 'array'
		});
	};

	return function do_file(f) {
		localStorage.clear();
		
		var reader = new FileReader();
		reader.onload = function(e) {
			if (typeof console !== 'undefined')
				console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if (!rABS)
				data = new Uint8Array(data);
//			if (false){
//				xw(data, ejecutar_webworkers);
//			}
			else
				ejecutar_webworkers(X.read(data, {
					type : rABS ? 'binary' : 'array',
							cellDates : true,
						    blankrows : false,
						    defval: '',
						    cellNF : false,
						    dateNF: 'DD"/"MM"/"YYYY',
						    cellText:false,
						    raw:true
				}));
		};
		if (rABS)
			reader.readAsBinaryString(f);
		else
			reader.readAsArrayBuffer(f);
	};
})();

const ventaJsc = {"$schema":"http://json-schema.org/draft-07/schema#","additionalProperties":false,"errorMessage":{"type":"El archivo no tiene el formato correcto","required":"Debe tener la propiedad venta","additionalProperties":"El archivo tiene columnas no validas"},"title":"venta","type":"array","items":{"type":"object","errorMessage":{"type":"El archivo no tiene el formato correcto","additionalProperties":"El archivo tiene columnas no validas","required":{"Fecha Factura":"El campo Fecha Emisión es requerido","Nro de Factura":"El campo Número de Factura es requerido","Nro Autorizacion/CUF":"El campo Número Autorización / CUF es requerido","Estado":"Campo requerido","Nit/CI Cliente":"El campo NIT cliente es requerido","Nombre/Razon Social":"Campo requerido","Importe Total Factura":"Campo requerido","Importe ICE/IEHD/TASAS":"Campo requerido","Exportaciones y Operaciones Exentas":"Campo requerido","Ventas Grabadas Tasa Cero":"Campo requerido","Sub Total":"Campo requerido","Descuentos, Bonificaciones y Rebajas":"Campo requerido","Importe Base Debito Fiscal":"Campo requerido","Debito Fiscal":"Campo requerido","Codigo de Control":"Campo requerido"}},"properties":{"additionalProperties":false,"Fecha Factura":{"type":"string","pattern":"^([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})$","errorMessage":{"type":"El campo fechaEmision debe tener un valor"}},"Nro de Factura":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,99999999}?$"},{"maximum":99999999,"minimum":1,"type":"integer"}],"errorMessage":{"type":"El campo numeroFactura debe ser entero"}},"Nro Autorización / CUF":{"anyOf":[{"maxLength":100,"minLength":1,"type":"string","errorMessage":{"type":"El campo Número Autorización / CUF debe tener un valor"}},{"maximum":999999999999999,"minimum":1,"type":"integer"}]},"Estado":{"type":"string","errorMessage":{"type":"El estado debe contener letras"},"minLength":1},"Nit/CI Cliente":{"anyOf":[{"type":"string","pattern":"^[0-9]{5,12}?$","maxLength":12},{"maximum":999999999999,"minimum":100000,"type":"integer"}],"errorMessage":{"type":"El campo NIT Proveedor debe ser númerico"}},"Nombre/Razon Social":{"type":"string","errorMessage":{"type":"El campo Nro debe ser númerico"}},"Importe Total Factura":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Importe ICE/IEHD/TASAS":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Exportaciones y Operaciones Exentas":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Ventas Grabadas Tasa Cero":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Sub Total":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Descuentos, Bonificaciones y Rebajas":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Importe Base Debito Fiscal":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Debito Fiscal":{"anyOf":[{"type":"string","pattern":"^[0-9]{1,20}([.,][0-9]{1,5})?$"},{"exclusiveMinimum":0,"type":"number"}],"errorMessage":{"type":"El campo Importe Total Compra debe ser númerico"}},"Código de Control":{"type":"string","pattern":"^0|^([A-Fa-f0-9]{2,2}-)*[A-Fa-f0-9]{2,2}$","errorMessage":{"type":"El campo Código de Control debe tener un valor"},"minLength":1,"maxLength":15}},"required":["Fecha Factura","Nro de Factura","Nro Autorizacion/CUF","Estado","Nit/CI Cliente","Nombre/Razon Social","Importe Total Factura","Importe ICE/IEHD/TASAS","Exportaciones y Operaciones Exentas","Ventas Grabadas Tasa Cero","Sub Total","Descuentos, Bonificaciones y Rebajas","Importe Base Debito Fiscal","Codigo de Control"]}};

function validateAjv(data){
	
	var ajv = Ajv({allErrors: true});
	
	var valid = ajv.validate(ventaJsc, data);
	if (valid) {
	  	console.log('data is valid');
		enviarFile();
	} else {
	  	console.log('data NO INVALID!');
		var errors = localize_es(ajv.errors);
		console.log(errors);
		var err='\n';
		errors.forEach(function(error) {
			err += error.message +'\n';
		});
		download("errores.txt",err);
	}
	
}


 function download(file, text) { 
 
      var element = document.createElement('a'); 
      element.setAttribute('href','data:text/plain;charset=utf-8, ' + encodeURIComponent(text)); 
      element.setAttribute('download', file);              
      document.body.appendChild(element); 
      element.click(); 
              
      document.body.removeChild(element); 
 } 

