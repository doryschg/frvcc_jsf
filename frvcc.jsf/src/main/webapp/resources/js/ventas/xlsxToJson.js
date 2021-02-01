
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
		
		return output;
		

	};
})();




var do_file = (function() {
	var rABS = typeof FileReader !== "undefined" && (FileReader.prototype || {}).readAsBinaryString;
	
	var use_worker = typeof Worker !== 'undefined';


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

		worker.postMessage({d : data, b : rABS ? 'binary' : 'array'});
	};

	return function do_file(f,callback) {
		localStorage.clear();
		//var f = files[0];
		
		var reader = new FileReader();
		reader.onload = function(e) {
			if (typeof console !== 'undefined')
				console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if (!rABS)
				data = new Uint8Array(data);
//			if (false){
//				xw(data, ejecutar_webworkers);
//				alert('webworkers');	
//			}
			else
			var jObj =ejecutar_webworkers(X.read(data, {
					type : rABS ? 'binary' : 'array',
							cellDates : true,
						    blankrows : false,
						    defval: '',
						    cellNF : false,
						    dateNF: 'DD"/"MM"/"YYYY',
						    cellText:false,
						    raw:true
				}));
			return callback(jObj);
		};
		if (rABS)
			reader.readAsBinaryString(f);
		else
			reader.readAsArrayBuffer(f);
	};
})();