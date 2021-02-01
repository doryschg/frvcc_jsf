/**
 * Script Lector plantilla odt Excel.
 * 
 * @author: sergio.ichaso
 * @Fecha: 11/06/2019
 */

/*
 * TODO: Sergio.Ichaso llevar a tabla el siguiente objecto.
 */

var X = XLSX; 
var XW = {
	msg : 'xlsx',
	worker : '../javax.faces.resource/xlsxworker.js.xhtml?ln=js'
};

/**
 * Funcion de jecucion de webworkers
 * 
 * @author: sergio.ichaso
 * @Fecha: 11/06/2019
 * @param object,
 *            value
 * @return Devuelve el valor de un objeto lista
 */
var webworkers;
var ejecutar_webworkers = (function() {
	var to_json = function to_json(workbook) {
		var result = {};
		workbook.SheetNames.forEach(function(sheetName) {
			var sheet = workbook.Sheets[sheetName];		
			Object.keys(sheet).forEach(function(s) {
			    if(sheet[s].t === 'n') {
			    	// Para que no redonde numeros flotantes y en casos de tener notacion cientifica realize el control
			    	if(!sheet[s].v.toString().includes(',') && !sheet[s].v.toString().includes('.')){
			    		sheet[s].z = '0';
			    		sheet[s].t = 's';
			    	}
			    }
			});
			var roa = X.utils.sheet_to_json(sheet, {
				  dateNF: 'DD"/"MM"/"YYYY'
			});
			if (roa.length)				
				result = roa;
		});
		
		result = result.filter(element => element['Nro.'] != null);
		return JSON.stringify(result);
	};

	return function ejecutar_webworkers(wb) {
		webworkers = wb;
		var output = "";
		output = to_json(wb);

		validarCsvJsonAjv(output);
		if (typeof console !== 'undefined')			
			console.log("output", new Date());
	};
})();


var do_file = (function() {
	var rABS = typeof FileReader !== "undefined"
			&& (FileReader.prototype || {}).readAsBinaryString;
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

	return function do_file(files) {
		localStorage.clear();
		var f = files[0];
		
		var reader = new FileReader();
		reader.onload = function(e) {
			if (typeof console !== 'undefined')
				console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if (!rABS)
				data = new Uint8Array(data);
			if (false){
				xw(data, ejecutar_webworkers);
				alert('webworkers');	
			}
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

/**
 * Funcion para obtener el valor de un objeto dada una lista
 * 
 * @author: sergio.ichaso
 * @Fecha: 11/06/2019
 * @param object,
 *            value
 * @return Devuelve el valor de un objeto lista
 */
function getKeyByValue(object, value) {
	  return Object.keys(object).find(key => object[key] === value);
	}


