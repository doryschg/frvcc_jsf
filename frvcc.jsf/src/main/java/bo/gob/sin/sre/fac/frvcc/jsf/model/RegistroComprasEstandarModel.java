package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasExcelDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ErrorDto;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosMensajes;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorCodigoAutorizacion;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorCodigoControl;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorFechaFactura;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorImporte;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorImporteConCeros;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorImporteDescuento;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorImporteNoSujetoCf;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorNitProveedor;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorNumeroDui;
import bo.gob.sin.sre.fac.frvcc.jsf.validator.ValidadorNumeroFactura;

@ManagedBean(name = "registroComprasEstandarModel")
@ViewScoped
public class RegistroComprasEstandarModel  implements Serializable {
	private static final long serialVersionUID = -4963091160256373819L;
	
	
	private List<ComprasCvDto> facturas = new ArrayList<>();
	private List<ComprasCvDto> misFacturas;
	private List<ErrorDto> errores = new ArrayList<>();
	private Map<String, String>mapErrores;

	@PostConstruct
	public void Load() {
		misFacturas = new ArrayList<>();
	}

	public RegistroComprasEstandarModel() {
		super();
	}

	
	public void limpiarErrores() {
		this.errores = new ArrayList<>();
	}
	
	public void clear() {
		//this.misFacturas = new ArrayList<>();
		this.errores = new ArrayList<>();
	}

	public void addFacturas(List<ComprasCvDto> pFacturas) {
		this.facturas.addAll(pFacturas);
	}
	
	public void addFactura(ComprasCvDto pFactura) {
		this.facturas.add(pFactura);
	}

	public void removeFactura(ComprasCvDto pFactura) {
		this.facturas.remove(pFactura);
	}

	public void removeFacturas(List<ComprasCvDto> pFacturas) {
		this.facturas.removeAll(pFacturas);
	}
	
	public void removeRow(int pFila) {
		if(misFacturas.size()<=1) {
			this.misFacturas = new ArrayList<>();
		}else {
			this.misFacturas.remove(pFila);
		}	
	}

	public List<ComprasCvDto> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<ComprasCvDto> facturas) {
		this.facturas = facturas;
	}

	public void addMisFacturas(List<ComprasCvDto> pFacturas) {
		this.misFacturas.addAll(pFacturas);
	}

	public void addMiFactura(ComprasCvDto pFactura) {
		this.misFacturas.add(0,pFactura);
	}

	public void removeMiFactura(ComprasCvDto pFactura) {
		this.misFacturas.remove(pFactura);
	}

	public List<ComprasCvDto> getMisFacturas() {
		return misFacturas;
	}

	public void setMisFacturas(List<ComprasCvDto> misFacturas) {
		this.misFacturas = misFacturas;
	}

	public List<ErrorDto> getErrores() {
		return errores;
	}

	public void setErrores(List<ErrorDto> errores) {
        List<ErrorDto> resultado = errores.stream().sorted((o1, o2) -> Integer.valueOf(o1.getFila()).compareTo(Integer.valueOf(o2.getFila()))).collect(Collectors.toList());
		this.errores = resultado;
	}

	public boolean validarCompras(ComprasExcelDto pCompra) {
		mapErrores = new HashMap<String, String>();
		mapErrores.put(ParametrosMensajes.COLUMNA_NIT_PROVEEDOR, ValidadorNitProveedor.validarNIT(pCompra.getNitProveedor().trim())?"":ValidadorNitProveedor.errorInvalido());
		 if (pCompra.getNumeroFactura().trim().equals("0")) {
			mapErrores.put(ParametrosMensajes.COLUMNA_NUMERO_DUI, ValidadorNumeroDui.validarNumeroDui(pCompra.getNumeroDui().trim())?"":ValidadorNumeroDui.errorInvalido());
        }
        
        if (pCompra.getNumeroDui().trim().equals("0")) {
        	mapErrores.put(ParametrosMensajes.COLUMNA_NUMERO_FACTURA, ValidadorNumeroFactura.validarNumeroFactura(pCompra.getNumeroFactura().trim())?"":ValidadorNumeroFactura.errorInvalido());
        }
		
		mapErrores.put(ParametrosMensajes.COLUMNA_CODIGO_AUTORIZACION, ValidadorCodigoAutorizacion.validarCodigoAutorizacion(pCompra.getCodigoAutorizacion().trim())?"":ValidadorCodigoAutorizacion.errorInvalido());
		mapErrores.put(ParametrosMensajes.COLUMNA_FECHA_FACTURA, ValidadorFechaFactura.validarFechaFactura(pCompra.getFechaFactura().trim())?"":ValidadorFechaFactura.errorInvalido());
		mapErrores.put(ParametrosMensajes.COLUMNA_IMPORTE_TOTAL, ValidadorImporte.validarImporte(pCompra.getImporteTotalCompra().trim())?"":ValidadorImporte.errorInvalidoImporteTotal());
		mapErrores.put(ParametrosMensajes.COLUMNA_IMPORTE_NO_SUJETO_CF, ValidadorImporteNoSujetoCf.validarImporteNoSujetoCf(pCompra.getImporteTotalCompra().trim(),pCompra.getImporteNoSujetoFc().trim(),pCompra.getDescuento().trim())?"":ValidadorImporteNoSujetoCf.errorInvalido());
		mapErrores.put(ParametrosMensajes.COLUMNA_DESCUENTO, ValidadorImporteDescuento.validarImporteDescuento(pCompra.getImporteTotalCompra().trim(),pCompra.getImporteNoSujetoFc().trim(),pCompra.getDescuento().trim())?"":ValidadorImporteDescuento.errorInvalido());
		mapErrores.put(ParametrosMensajes.COLUMNA_CODIGO_CONTROL, ValidadorCodigoControl.validarCodigoControl(pCompra.getCodigoControl().trim(),pCompra.getCodigoAutorizacion().trim())?"":ValidadorCodigoControl.errorInvalido());
		
		String descripciones="";
		String columnas="";	
		
		for (Map.Entry<String, String> entry : mapErrores.entrySet()) {
			if(entry.getValue()!="") {
				columnas=columnas+entry.getKey()+" , ";
				descripciones=descripciones+entry.getValue()+" , ";
			}
		}
		
		
		if(!descripciones.equals("")) {
			errores.add(new ErrorDto(descripciones, pCompra.getNumeroExcel(),columnas, ParametrosMensajes.MSJ_FORMATO_INVALIDO));
		}
		return errores.size() == 0;
	}
}
