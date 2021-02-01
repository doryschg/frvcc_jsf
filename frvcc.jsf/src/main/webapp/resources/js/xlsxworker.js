/**
 * 
 */
/* xlsx.js (C) 2013-present SheetJS -- http://sheetjs.com */
// var XLSX = require('../../'); // test against development version
// var XLSX = require('../../'); // test against development version
// var XLSX = require('xlsx'); // use in production
// importScripts('../javax.faces.resource/require.js.xhtml?ln=js');

importScripts('../javax.faces.resource/xlsx.full.min.js.xhtml?ln=js');

postMessage({
	t : "ready"
});
onmessage = function(evt) {
	var v;
	try {
		console.log('onmessage ' + evt.data.b);
		v = XLSX.read(evt.data.d, {
			  type: 'binary',
			  cellDates : true,
              blankrows : false,
              defval: "",
              cellNF : false,
              dateNF: 'DD"/"MM"/"YYYY',
              cellText:false,
              raw:true
		});
		console.log(v);
		postMessage({
			t : "xlsx",
			d : JSON.stringify(v)
		});
	} catch (e) {
		postMessage({
			t : "e",
			d : e.stack || e
		});
	}
};
