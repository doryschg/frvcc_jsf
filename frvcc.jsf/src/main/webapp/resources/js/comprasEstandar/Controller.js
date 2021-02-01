var _gaq = _gaq || [];

$(document).ready(function(){
	initController();
 })



function initController(){


	 var widthOffset = 345;
	  var heightOffset = 35

	  var d = new DataConverter('converter');

	  const converter = document.getElementById('converter');

	  var sidebar = $('#header');

	  var win = $(window);
	  var w = win.width() - widthOffset;
	  var h = win.height() - heightOffset;

	  d.create(100,20);

	  $(".settingsElement").change(updateSettings);

	  $(window).bind('resize',function() {  

	      w = win.width() - widthOffset;
	      h = win.height() - heightOffset;
	      d.resize(100,20);
	      sidebar.height(h);

	    });


	  function updateSettings (evt) {
	    
	    if (evt) {
	      _gaq.push(['_trackEvent', 'Settings',evt.currentTarget.id ]);
	    };

	    d.includeWhiteSpace = true;//$('#includeWhiteSpaceCB').attr('checked');
	    d.indent = "  ";
	    /*if (d.includeWhiteSpace) {
	      $("input[name=indentType]").removeAttr("disabled");
	      var indentType = $('input[name=indentType]:checked').val();
	      if (indentType === "tabs") {
	        d.indent = "\t";
	      } else if (indentType === "spaces") {
	        d.indent = "  "
	      }
	    } else {
	      $("input[name=indentType]").attr("disabled", "disabled");
	    }*/

	    d.headersProvided = false;//$('#headersProvidedCB').attr('checked');
	    d.downcaseHeaders = false;
	    d.upcaseHeaders = false;
	    /* if (d.headersProvided) {
	    	d.downcaseHeaders = false;
	        d.upcaseHeaders = false;
	      $("input[name=headerModifications]").removeAttr("disabled");

	      var hm = $('input[name=headerModifications]:checked').val();
	      if (hm === "downcase") {
	        d.downcaseHeaders = true;
	        d.upcaseHeaders = false;
	      } else if (hm === "upcase") {
	        d.downcaseHeaders = false;
	        d.upcaseHeaders = true;
	      } else if (hm === "none") {
	        d.downcaseHeaders = false;
	        d.upcaseHeaders = false;
	      }
	    } else {
	      $("input[name=headerModifications]").attr("disabled", "disabled");
	    }*/
	    
	    d.delimiter = 'auto';//$('input[name=delimiter]:checked').val();
	    d.decimal = 'dot';//$('input[name=decimal]:checked').val();
	    
	    d.useUnderscores = true;
	    
	    d.convert();
	  };

	  updateSettings();
}

