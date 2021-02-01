const fechaNota0 = document.getElementById('frmRegistroCompras:dtMisFacturas:0:txtFechaNota');
const tablaRegistro='frmRegistroCompras:dtMisFacturas';
const botonGuardar = 'btnGuardar';
const txtNitProveedor='txtNitProveedor';

const txtFechaNota='txtFechaNota';
const txtNumeroNota='txtNumeroNota';
const txtCodigoAutorizacionNota='txtCodigoAutorizacionNota';
const txtImporteTotalNota_input='txtImporteTotalNota_input';
const txtCodigoControlNota='txtCodigoControlNota';

const txtFechaFacturaOriginal='txtFechaFacturaOriginal';
const txtNumeroFacturaOriginal='txtNumeroFacturaOriginal';
const txtCodigoAutorizacionOriginal='txtCodigoAutorizacionOriginal';
const txtImporteFacturaOriginal_input='txtImporteFacturaOriginal_input';
const txtCodigoControlOriginal='txtCodigoControlOriginal';


var mensajes={
	txtNitProveedor:"Nit no valido!",
	//txtFechaNota:'txtFechaNota';
	txtNumeroNota:'Numero Nota Invalido',
	txtCodigoAutorizacionNota:'Codigo Autorización invalido',
	txtImporteTotalNota_input:'Importe no valido',
	txtCodigoControlNota:'Codigo de Control Invalido',

	txtNumeroFacturaOriginal:'Nro Factura Invalido',
	txtCodigoAutorizacionOriginal:'Codigo Autorización invalido',
	txtImporteFacturaOriginal_input:'Importe invalido',
	txtCodigoControlOriginal:'Codigo de control invalido'
}
function campareDates(dateStart, dateEnd,days){
	var date1 = dateStart.split('/');
	var date2 = dateEnd.split('/');
	var fch1 = new Date(parseInt(date1[0]),parseInt(date1[1]-1),parseInt(date1[2]));
	var fch2 = new Date(parseInt(date2[0]),parseInt(date2[1]-1),parseInt(date2[2]));
	fch1.setDate(fch1.getDate() + Number(days));
	return fch1<fch2;
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
    d[12].innerHTML = "";
    if (event.key === 'Enter') {
        if (validarCompra(event)) {
            const guardar = document.getElementById(`${tablaRegistro}:${index}:${botonGuardar}`);
            guardar.click();
            event.preventDefault();
        }
        return event.preventDefault();
    }
}


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
    const fechaNota = document.getElementById(`${tablaRegistro}:${index}:${txtFechaNota}`);  
    const numeroNota = document.getElementById(`${tablaRegistro}:${index}:${txtNumeroNota}`);
    const numeroAutorizacionNota = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoAutorizacionNota}`);
    const importeTotalNota = document.getElementById(`${tablaRegistro}:${index}:${txtImporteTotalNota_input}`);
    const codigoControlNota = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoControlNota}`);
    
    const fechaFacturaOriginal = document.getElementById(`${tablaRegistro}:${index}:${txtFechaFacturaOriginal}`);  
    const numeroFacturaOriginal = document.getElementById(`${tablaRegistro}:${index}:${txtNumeroFacturaOriginal}`);
    const numeroAutorizacionOriginal = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoAutorizacionOriginal}`);
    const importeTotalOriginal = document.getElementById(`${tablaRegistro}:${index}:${txtImporteFacturaOriginal_input}`);
    const codigoControlOriginal = document.getElementById(`${tablaRegistro}:${index}:${txtCodigoControlOriginal}`);

    const valNitProveedor = validadorNit(nitProveedor);
    const valFechaNota = validatorFechaEmision(fechaNota).success;
    const valNumeroNota = validadorNumeroFactura(numeroNota);
    const valNumeroAutorizacionNota = validarNumeroAutorizacionNota(numeroAutorizacionNota);
    const valImporteTotalNota = validadorImporte(importeTotalNota);
    const valcodigoControlNota = validadorCodigoControlNota(numeroAutorizacionNota,codigoControlNota);
    
    const valFechaFacturaOriginal = validatorFechaEmision(fechaFacturaOriginal).success;
    const valNumeroFacturaOriginal = validadorNumeroFactura(numeroFacturaOriginal);
    const valNumeroAutorizacionOriginal = validarNumeroAutorizacion(numeroAutorizacionOriginal);
    const valImporteTotalOriginal = validadorImporte(importeTotalOriginal);
    const valcodigoControlOriginal = validadorCodigoControl(numeroAutorizacionOriginal,codigoControlOriginal);
	
	const valCompareDates= campareDates(fechaFacturaOriginal.value, fechaNota.value,30);
	
	if(!valCompareDates){
		//var ruta= fechaFacturaOriginal.id.toString().replace(/:([^:]*)$/, ':_' + '$1');
		//document.getElementById(ruta).children[1].innerHTML="";

		toastr.warning('La Fecha Nota Credito Debito debe tener 30 dias respecto a Fecha factura', 'Observación')
		fechaFacturaOriginal.focus()
	}    

    if (!valImporteTotalOriginal) {
    	importeTotalOriginal.focus()
    }
    if (!valNumeroAutorizacionOriginal) {
    	numeroAutorizacionOriginal.focus()
    }
    if (!valNumeroFacturaOriginal) {
    	numeroFacturaOriginal.focus()
    }
    if (!valFechaFacturaOriginal) {
    	fechaFacturaOriginal.focus()
    }
    if (!valImporteTotalNota) {
    	importeTotalNota.focus()
    }
    if (!valNumeroAutorizacionNota) {
    	numeroAutorizacionNota.focus()
    }
    if (!valNumeroNota) {
    	numeroNota.focus()
    }
    if (!valFechaNota) {
    	fechaNota.focus()
    }
    if (!valNitProveedor) {
        nitProveedor.focus()
    }
    
    if (!(valNitProveedor
		&& valCompareDates
    	&& valFechaNota
        && valNumeroNota
        && valNumeroAutorizacionNota
        && valImporteTotalNota
        && valFechaFacturaOriginal
        && valNumeroFacturaOriginal
        && valNumeroAutorizacionOriginal
        && valImporteTotalOriginal)) {
        return false;
    }
     
    if (!sinCodigoControlNota(numeroAutorizacionNota)) {
        if (!valcodigoControlNota) {
        	valcodigoControlNota.focus();
            return false;
        }
    } else {
    	valcodigoControlNota.value = '';
    }
    
    if (!sinCodigoControl(numeroAutorizacionOriginal)) {
        if (!valcodigoControlOriginal) {
        	valcodigoControlOriginal.focus();
            return false;
        }
    } else {
    	valcodigoControlOriginal.value = '';
    }
    
    return true;
}


function ponerFoco(data) {
    if (data.status === "success") {
    	fechaNota0.focus();
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

const validarFormulario = e => {
	const codigoControlNota = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoControlNota}`);
	const codigoControlOriginal = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoControlOriginal}`);
    IMask(codigoControlOriginal, {
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
        case txtFechaNota:
			var fecha=validatorFechaEmision(e);
            if (fecha.success) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=fecha.mensaje;
            }
            break;
        case txtNumeroNota:
            if (validadorNumeroFactura(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtNumeroNota;
            }
            break;
        case txtCodigoAutorizacionNota:
            if (validarNumeroAutorizacionNota(e)) {
                if (!sinCodigoControlNota(e)) {
                	codigoControlNota.disabled = false;
                } else {
                	codigoControlNota.value = '';
                	codigoControlNota.className = CLASS_INPUT_CSS;
                	codigoControlNota.disabled = true;
                }
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
            	codigoControlNota.className = CLASS_INPUT_CSS;
            	codigoControlNota.disabled = true;
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoAutorizacionNota;
            }
            break;
      
        case txtImporteTotalNota_input:
            if (validadorImporte(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteTotalNota_input;
            }
            break;
        case txtCodigoControlNota:
            const numeroAutorizacionNota = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoAutorizacionNota}`);
            if (!numeroAutorizacionNota.value) {
            	numeroAutorizacionNota.focus();
            } else {
                if (!sinCodigoControlNota(numeroAutorizacionNota)) {
                    if (validadorCodigoControlNota(numeroAutorizacionNota,e)) {
                        campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
						document.getElementById(ruta).children[1].innerHTML="";
                    } else {
                        campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
						document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoControlNota;
                    }
                }
            }
            break;
        case txtFechaFacturaOriginal:
			var fchFacOrg=validatorFechaEmision(e);
            if (fchFacOrg.success) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=fchFacOrg.mensaje;
            }
            break;
        case txtNumeroFacturaOriginal:
            if (validadorNumeroFactura(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtNumeroFacturaOriginal;
            }
            break;
        case txtCodigoAutorizacionOriginal:
            if (validarNumeroAutorizacion(e)) {
                if (!sinCodigoControl(e)) {
                	codigoControlOriginal.disabled = false;
                } else {
                	codigoControlOriginal.value = '';
                	codigoControlOriginal.className = CLASS_INPUT_CSS;
                	codigoControlOriginal.disabled = true;
                }
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
            	codigoControlOriginal.className = CLASS_INPUT_CSS;
            	codigoControlOriginal.disabled = true;
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoAutorizacionOriginal;
            }
            break;
      
        case txtImporteFacturaOriginal_input:
            if (validadorImporte(e)) {
                campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
				document.getElementById(ruta).children[1].innerHTML="";
            } else {
                campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
				document.getElementById(ruta).children[1].innerHTML=mensajes.txtImporteFacturaOriginal_input;
            }
            break;
        case txtCodigoControlOriginal:
            const numeroAutorizacionOriginal = document.getElementById(`${tablaRegistro}:${e.id.toString().split(':')[2]}:${txtCodigoAutorizacionOriginal}`);
            if (!numeroAutorizacionOriginal.value) {
            	numeroAutorizacionOriginal.focus();
            } else {
                if (!sinCodigoControl(numeroAutorizacionOriginal)) {
                    if (validadorCodigoControl(numeroAutorizacionOriginal,e)) {
                        campoCambioCss(e, CLASS_INPUT_CSS, CLASS_ICON_SUCCESS);
						document.getElementById(ruta).children[1].innerHTML="";
                    } else {
                        campoCambioCss(e, CLASS_INPUT_ERROR_CSS, CLASS_ICON_ERROR_CSS);
						document.getElementById(ruta).children[1].innerHTML=mensajes.txtCodigoControlOriginal;
                    }
                }
            }
            break;
    }
}