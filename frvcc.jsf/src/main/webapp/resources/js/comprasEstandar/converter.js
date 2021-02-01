//
//  converter.js
//  Mr-Data-Converter
//
//  Created by Shan Carter on 2010-09-01.
//




function DataConverter(nodeId) {

  // ---------------------------------------
  // PUBLIC PROPERTIES
  // ---------------------------------------

  this.nodeId                 = nodeId;
  this.node                   = $("#"+nodeId);

  this.outputDataTypes        = [                               
                                {"text":"JSON - Properties",      "id":"json",             "notes":""}
                                ];
  this.outputDataType         = "json";

  this.columnDelimiter        = "\t";
  this.rowDelimiter           = "\n";

  this.inputTextArea          = {};
  this.outputTextArea         = {};

  this.inputHeader            = {};
  this.outputHeader           = {};
  this.dataSelect             = {};

  this.inputText              = "";
  this.outputText             = "";

  this.newLine                = "\n";
  this.indent                 = "  ";

  this.commentLine            = "//";
  this.commentLineEnd         = "";
  this.tableName              = "MrDataConverter"

  this.useUnderscores         = true;
  this.headersProvided        = false;
  this.downcaseHeaders        = true;
  this.upcaseHeaders          = false;
  this.includeWhiteSpace      = true;
  this.useTabsForIndent       = false;

}

// ---------------------------------------
// PUBLIC METHODS
// ---------------------------------------

DataConverter.prototype.create = function(w,h) {
  var self = this;

  // build HTML for converter
  this.inputHeader = '';// $('<div class="groupHeader" id="inputHeader"><p
						// class="groupHeadline">Input CSV or tab-delimited
						// data. <span class="subhead"> Using Excel? Simply copy
						// and paste. No data on hand? <a href="#"
						// id="insertSample">Use sample</a></span></p></div>');
  this.inputTextArea = $('<textarea class="textInputs"  placeholder="Pegar Aquí"  style="text-align: center;" id="dataInput"></textarea>');
  var outputHeaderText = '';// '<div class="groupHeader" id="inputHeader"><p
							// class="groupHeadline">Output as <select
							// name="Data Types" id="dataSelector" >';
    for (var i=0; i < this.outputDataTypes.length; i++) {

      outputHeaderText += '<option value="'+this.outputDataTypes[i]["id"]+'" '
              + (this.outputDataTypes[i]["id"] == this.outputDataType ? 'selected="selected"' : '')
              + '>'
              + this.outputDataTypes[i]["text"]+'</option>';
    };
    outputHeaderText += '</select><span class="subhead" id="outputNotes"></span></p></div>';
  this.outputHeader = $(outputHeaderText);
  this.outputTextArea = $('<textarea class="textInputs" id="dataOutput"></textarea>');

  this.node.append(this.inputHeader);
  this.node.append(this.inputTextArea);
  this.node.append(this.outputHeader);
 // this.node.append(this.outputTextArea);

  this.dataSelect = this.outputHeader.find("#dataSelector");

  $("#insertSample").bind('click',function(evt){
    evt.preventDefault();
    self.insertSampleData();
    self.convert();
    _gaq.push(['_trackEvent', 'SampleData','InsertGeneric']);
  });

  $("#dataInput").keyup(function() {self.convert()});
  $("#dataInput").change(function() {
    self.convert();
    _gaq.push(['_trackEvent', 'DataType',self.outputDataType]);
  });

  $("#dataSelector").bind('change',function(evt){
       self.outputDataType = $(this).val();
       self.convert();
     });

  this.resize(w,h);
}

DataConverter.prototype.resize = function(w,h) {

  var paneWidth = w;
  var paneHeight = (h-90)/2-20;

  this.node.css({width:w+'%'});
  this.inputTextArea.css({width:w+'%',height:h+'px'});
 // this.outputTextArea.css({width: paneWidth-20, height:paneHeight});
}

DataConverter.prototype.convert = function() {

  this.inputText = this.inputTextArea.val();
  this.inputTextArea.val('');
  this.outputText = "";




  // make sure there is input data before converting...
  if (this.inputText.length > 0) {

    if (this.includeWhiteSpace) {
      this.newLine = "\n";
      // console.log("yes")
    } else {
      this.indent = "";
      this.newLine = "";
      // console.log("no")
    }

    CSVParser.resetLog();
    var parseOutput = CSVParser.parse(this.inputText, this.headersProvided, this.delimiter, this.downcaseHeaders, this.upcaseHeaders);

    var dataGrid = parseOutput.dataGrid;
    var headerNames = parseOutput.headerNames;
    var headerTypes = parseOutput.headerTypes;
    var errors = parseOutput.errors;

    this.outputText = DataGridRenderer[this.outputDataType](dataGrid, headerNames, headerTypes, this.indent, this.newLine);

    
    // TODO: sergio.ichaso Insertamos cabecera de la plantilla de compras en el objeto JSON, refactorizar a obtencion de cabecera en parametricas
    if(!this.headersProvided)  
    {
    	var camposCabecera = {};
    	camposCabecera.val0 = "Nro.";
    	camposCabecera.val1 = "NIT Proveedor";
    	camposCabecera.val2 = "Nro Factura";
    	camposCabecera.val3 = "Nro DUI";
        camposCabecera.val4 = "Número Autorización / CUF";
        camposCabecera.val5 = "Fecha Emisión";
        camposCabecera.val6 = "Importe Total Compra";
        camposCabecera.val7 = "Importe No Sujeto a Crédito Fiscal";
        camposCabecera.val8 = "Descuentos, Bonificaciones y Rebajas Obtenidas";
        camposCabecera.val9 = "Importe Base para Crédito Fiscal";
        camposCabecera.val10 = "Código de Control";
//    	Object.keys(camposCabecera).map(key => this.outputText = this.outputText.replaceAll(key, camposCabecera[key]));
    	Object.keys(camposCabecera).map(key => this.outputText = this.outputText.split(key).join(camposCabecera[key]));
    	console.log("llega: "+Object.keys.map);
    	//Limpiar propiedades vacias o nulas
    	let jsonObject = JSON.parse(this.outputText)
    	jsonObject.forEach(function(data, index) {
    		$.each(data, function(key, value){
        	    if (value === "" || value === null){
        	        delete data[key];
        	    }
        	});
    	});
    	
    	this.outputText = JSON.stringify(jsonObject);
    } 
    
    if(validarCsvJsonAjv)
    	validarCsvJsonAjv(this.outputText);
    // this.outputTextArea.val(errors + this.outputText);

  } // end test for existence of input text
}


DataConverter.prototype.insertSampleData = function() {
  this.inputTextArea.val("NAME\tVALUE\tCOLOR\tDATE\nAlan\t12\tblue\tSep. 25, 2009\nShan\t13\t\"green\tblue\"\tSep. 27, 2009\nJohn\t45\torange\tSep. 29, 2009\nMinna\t27\tteal\tSep. 30, 2009");
}
