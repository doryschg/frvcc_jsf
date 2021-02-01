
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
	calculate(itf);
	var msg=importeValid(itf.value,1)
	setErrorItem(itf,msg);
	return msg;
}	

// TODO - Importe ICE/IEHD/TASAS
function validarItii(itii){
	calculate(itii);
	var msg= importeValid(itii.value,0);
	setErrorItem(itii,msg);
	return msg;
}

// TODO - Exportaciones y Operaciones Exentas
function validarEoe(eoe){
	calculate(eoe);
	var msg= importeValid(eoe.value,0);
	setErrorItem(eoe,msg);
	return msg;
}	

// TODO - Ventas Grabadas Tasa Cero
function validarVgtc(vgtc){
	calculate(vgtc);
	var msg= importeValid(vgtc.value,0);
	setErrorItem(vgtc,msg);
	return msg;
}

// TODO - Sub Total
function validarSt(st){
	var msg= importeValid(st.value,0);
	setErrorItem(st,msg);
	return msg;
}
	
// TODO - Descuentos, Bonificaciones y Rebajas
function validarDbr(dbr){
	calculate(dbr);
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
	
// TODO - Debito Fiscal
function validarDf(df){
	var msg= importeValid(df.value,0);
	setErrorItem(df,msg);
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

function getNumber(n){
	return Math.floor(n.replace(/\,/g,''))
}
function getFormatCash(n){
	return Number(n).toLocaleString('en',{minimumFractionDigits: 2});
}

function calculate(e){
	var array=e.id.split(':');
	const costos = ['inNunIiit_input', 'inNunEoe_input','inNumVgtc_input'];
	
	let data=document.getElementById(`${array[0]}:${array[1]}:${array[2]}:inNumTF_input`).value;
	
	data=getNumber(data);
	costos.forEach(costoInput=>{
		
			var cost=document.getElementById(`${array[0]}:${array[1]}:${array[2]}:${costoInput}`).value
			data -= getNumber(cost);
		
	});
	
	document.getElementById(`${array[0]}:${array[1]}:${array[2]}:inNumSt`).value=getFormatCash(data);
	
	var inNumDbr= document.getElementById(`${array[0]}:${array[1]}:${array[2]}:inNumDbr_input`).value;
	inNumDbr=getNumber(inNumDbr);
	
	document.getElementById(`${array[0]}:${array[1]}:${array[2]}:inNumIbdf`).value=getFormatCash(data-inNumDbr);
	
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
	
	// TODO FIX CLEAN INPUT AFTER PASTE
	setTimeout(function() {
		PF('wVPaste').jq.val("");
	}, 700);
	
});

// TODO RETORNA ERROES ENCONTRADOS EN EL OBJECTO  ..... FIX ME .. VERIFICAR LA INUTILIDAD DEL METODO convertirStringToInporte
function getObjetct(text){
	const excel = text.toString().split('\n');
    let ventas = new Array();
    excel.forEach(x => {
	if (x){
			const textLine = x.toString().split('\t');
	
			const fchFactura = textLine[0] ? textLine[0].trim() :'';			// FECHA FACTURA 16
			const nroFactura= textLine[1] ? textLine[1].trim() :'';			// Nro DE FACTURA
			const codAutorCuf= textLine[2] ? textLine[2].trim() :'';			// Nro AUTORIZACION/CUF
			const estado= textLine[3] ? textLine[3].trim() :'';					// ESTADO 
			const nitCliente= textLine[4] ? textLine[4].trim() :'';					// NIT/CI CLIENTE
			const razonSocial= textLine[5] ? textLine[5] :'';						// NOMBRE O RAZON SOCIAL
			const imprtTotal= convertirStringToInporte(textLine[6].trim());			// IMPORTE TOTAL FACTURA o LA VENTA
			const imprtTasas= convertirStringToInporte(textLine[7].trim());			// IMPORTE ICE/IEHD/TASAS
			const imprtExntas= convertirStringToInporte(textLine[8].trim());			// IMPORTACIONES Y OPERACIONES EXENTAS
			const vntasTsaCero= convertirStringToInporte(textLine[9].trim());			// VENTAS GRABADAS A TASA CERO
			const subTotal= convertirStringToInporte(textLine[10].trim());				// SUBTOTAL
			const descuento= convertirStringToInporte(textLine[11].trim());			// DESCUENTOS
			const imprtBaseDf= convertirStringToInporte(textLine[12].trim());			// DEBITO FISCAL
			const dbitoFiscal= convertirStringToInporte(textLine[13].trim());			// IMPORTE BASE PARA DEBITO FISCAL	
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
				imprtTasas: {
	                valor:  imprtTasas,
	                mensaje: importeValid(imprtTasas,0)
	            },
				imprtExntas: {
	                valor:  imprtExntas,
	                mensaje: importeValid(imprtExntas,0)
	            },
				vntasTsaCero: {
	                valor:  vntasTsaCero,
	                mensaje: importeValid(vntasTsaCero,0)
	            },
				subTotal: {
	                valor:  subTotal,
	                mensaje: importeValid(subTotal,0)
	            },
				descuento: {
	                valor:  descuento,
	                mensaje: importeValid(descuento,0)
	            },
				imprtBaseDf: {
	                valor:  imprtBaseDf,
	                mensaje: importeValid(imprtBaseDf,0)
	            },
				dbitoFiscal: {
	                valor:  dbitoFiscal,
	                mensaje: importeValid(dbitoFiscal,0)
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
	
	var errors='\n'; 
	ventas.forEach(function(data,i){
		errors+=checkContents(data,i);
	});
	if(errors) {
		download("errores.txt",errors);
	}else{
		// enviar a la consulta
		enviarRows([{name: 'ventas', value: JSON.stringify(ventas)}]);

	}
	
}

// TODO RETORNA ERROES ENCONTRADOS EN EL OBJECTO
function checkContents(data,index){
			var resultado='';
			Object.keys(data).map(function(key) { var item=data[key]; if(item.mensaje)resultado+="File "+(index+1)+" "+item.mensaje+"\n"; });
	return resultado;
}


/* *********************************** 
*	TODO VALIDACIONES - ROW
**************************************/ 
// TODO RETORNA EL PUNTERO A LA PRIMERA CASILLA DEL ROW
function firstRow(){
	setTimeout(function() {
		var row=PrimeFaces.widgets.dtWgtVenta.findRow(0);
		var input=row[0].cells[1].childNodes[0];
		input.focus();	
	}, 200);
}

function checkRow2(indx){
	var row=PrimeFaces.widgets.dtWgtVenta.findRow(indx);
	var colums=row[0].cells;
	console.log(colums.length);
	var col=colums.length-2;
	
	for(let i=1;i<col; i++){
		var data= colums.item(i).childNodes[0];
		var attrFn=data.getAttribute('onkeyup');
		if(attrFn){
			const fnstring= attrFn.slice(0, -6);
			var fn = window[fnstring];
			if (typeof fn === "function"){
				var ms=fn(data);
				if(ms) {
					data.focus();
					return false;
				}
				console.log(data.value);	
			}
		}
		else{
			var css= data.classList.contains("ui-inputnumber");
			if(css){
				var data2 =data.childNodes[0];
				if(!data2.disabled){
					
					attrFn=data2.getAttribute('onkeyup');
					if(attrFn){
						const fnstring= attrFn.slice(0, -6);
						var fn = window[fnstring];
						if (typeof fn === "function"){
							var ms=fn(data2);
							if(ms) {
								//data2.select();
								//data2.focus();
								
								return data2.select();
							}
							console.log(data.value);	
						}
					}
				}
				
			} 
		
		}
		console.log("no tiene funcion");
		
	}
	//PrimeFaces.widgets.dtWgtVenta.selectRow(indx);	
	return true;	
}

// TODO PREGUNTA AL METODO DEL IMPUT SI TIENE ERRORES  
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

// TODO VERIFICA SI EL ROW TIENE ERRORES
function checkRow(e,rowIndex){
	
	var xIndex= e.target.offsetParent.cellIndex;
	var row=PrimeFaces.widgets.dtWgtVenta.findRow(rowIndex);
	var celdas=row[0].cells;
	var col=celdas.length-2;
	
	for(let i=1;i<col; i++){
		var dat= celdas[i].childNodes[0];
				
		if(dat.classList.contains("ui-inputnumber")){
			var inputNum= dat.childNodes[0];
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
		
	}
	console.log("validacion ejecutada con exito");
	return true
}



/* *********************************** 
*	TODO EVENTOS 
**************************************/
// TODO VERIFICA LA OPCION ENTER
function saveRow(event,row){
	console.log(event.key);
	const i = parseInt(row);
	if (event.key === 'Enter') {
		
		if(checkRow(event,i)){
			var guardar = document.getElementById("frmVentas:dtVentas:"+i+":btnGuardar");
	      	guardar.click();
			event.preventDefault();
		}
	}
}



// -----------------  CARGA DE ARCHIVOS EXCEL -------------

function importFile(evt) {
  	var file = evt.files[0];
	var m=fileValid(file);
	if(m){
		toastr.info(m.msg, 'Info');
		return 
	}
	
	do_file(file,validateAjv);	

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
// TODO GET NUM FILA
function getNumFila(dataPath){
	let positions=dataPath.match(/\[([^\]]+)]/g);
	let numfila=positions[0].match(/\[(.*)\]/).pop();
	return (parseInt(numfila)+1);
}


// TODO VALIDADOR MASIVO
function validateAjv(data){
	
	if(!data){toastr.error("No registra datos que validar", 'Error');return; } 
	 	
	var ajv = new Ajv({allErrors: true});
	
	ajv.addKeyword('checkFecha', {
	    validate: function validate(tipo, data, object, dataPath) {
	        try {
	            var res=dateValid(data);
				if(res) {
					var num=getNumFila(dataPath);
					throw new TypeError(`Fila ${num} ${data} ${res}`);
				}
				return true;
	        } catch (error) {
	            if (!validate.errors) validate.errors = [];
	            validate.errors.push(error);
	            return false;
	        }
	    },
	});
	
	ajv.addKeyword('checkNit', {
	    validate: function validate(tipo, data, object, dataPath) {
	        try {
	            var res=nitValid(data);
				if(res) {
					var num=getNumFila(dataPath);
					throw new TypeError(`Fila ${num} ${data} ${res}`);
				}
				return true;
	        } catch (error) {
	            if (!validate.errors) validate.errors = [];
	            validate.errors.push(error);
	            return false;
	        }
	    },
	});
	
	var val = ajv.validate(ventaJsc, data);
	
	if (val) {
		enviarFile();
	} else {
		toastr.error("Se encontraron datos incorrectos en su Archivo", 'Error');
		var errors = localize_es(ajv.errors);
		var err='\n';
		errors.forEach(function(error) { err +=  error.message +'\n'; });

		download("error-ventas-estandar.txt",err);
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

