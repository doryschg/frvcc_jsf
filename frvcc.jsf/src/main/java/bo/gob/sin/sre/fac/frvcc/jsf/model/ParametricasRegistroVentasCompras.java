package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServiciosClasificadoresRest;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;

@ManagedBean(name = "parametricasRegistroVentasCompras")
@ViewScoped
public class ParametricasRegistroVentasCompras implements Serializable {
	private static final long serialVersionUID = 1L;

	private ServiciosClasificadoresRest vServiciosParametricaRest;
	private ServicioComprasRestClient vServicioRegistroComprasRestClient;
	private List<ClasificadorDto> listaTiposDocumentosFiscales;
	private List<ClasificadorDto> listaTiposDocumentoSector;
	private List<ClasificadorDto> listaMotivoAnulacion;
	private List<Integer> listaGestiones;

	@PostConstruct
	void init() {
		vServiciosParametricaRest = new ServiciosClasificadoresRest();
		vServicioRegistroComprasRestClient = new ServicioComprasRestClient();
		this.listaTiposDocumentosFiscales = new ArrayList<>();
		this.listaMotivoAnulacion = new ArrayList<>();
	}

	public void obtenerTipoDocumentoFiscal() {
		List<ClasificadorDto> resultado = new ArrayList<>();
		resultado = vServiciosParametricaRest.obtenerClasificadoresPorTipo("tipo_documento_fiscal_id");

		this.listaTiposDocumentosFiscales = resultado;
	}

	public void obtenerTipoDocumentoSector() {
		List<ClasificadorDto> resultado = new ArrayList<>();
		resultado = vServiciosParametricaRest.obtenerClasificadoresPorTipo("tipo_documento_sector_id");
		this.listaTiposDocumentoSector = resultado;
	}

	public List<ClasificadorDto> obtenerEstadoResumenVentas() {
		List<ClasificadorDto> resultado = new ArrayList<>();
		resultado = vServiciosParametricaRest.obtenerClasificadoresPorTipo("estado_venta_resumen_manual_id");
		return this.listaTiposDocumentoSector = resultado;
	}

	public List<ClasificadorDto> obtenerEstadoResumenCompras() {
		List<ClasificadorDto> resultado = new ArrayList<>();
		resultado = vServiciosParametricaRest.obtenerClasificadoresPorTipo("estado_compra_resumen_id");
		return this.listaTiposDocumentoSector = resultado;
	}

	public List<ClasificadorDto> obtenerMotivoAnulacion() {
		List<ClasificadorDto> resultado = new ArrayList<>();
		resultado = vServiciosParametricaRest.obtenerClasificadoresPorTipo("motivo_anulacion_id");
		return this.listaMotivoAnulacion = resultado;
	}

	public ServiciosClasificadoresRest getvServiciosParametricaRest() {
		return vServiciosParametricaRest;
	}

	public void setvServiciosParametricaRest(ServiciosClasificadoresRest vServiciosParametricaRest) {
		this.vServiciosParametricaRest = vServiciosParametricaRest;
	}

	public List<ClasificadorDto> getListaTiposDocumentosFiscales() {
		return listaTiposDocumentosFiscales;
	}

	public void setListaTiposDocumentosFiscales(List<ClasificadorDto> listaTiposDocumentosFiscales) {
		this.listaTiposDocumentosFiscales = listaTiposDocumentosFiscales;
	}

	public List<ClasificadorDto> getListaTiposDocumentoSector() {
		return listaTiposDocumentoSector;
	}

	public void setListaTiposDocumentoSector(List<ClasificadorDto> listaTiposDocumentoSector) {
		this.listaTiposDocumentoSector = listaTiposDocumentoSector;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Integer> getListaGestiones() {
		return listaGestiones;
	}

	public void setListaGestiones(List<Integer> listaGestiones) {
		this.listaGestiones = listaGestiones;
	}

	public List<ClasificadorDto> getListaMotivoAnulacion() {
		return listaMotivoAnulacion;
	}

	public void setListaMotivoAnulacion(List<ClasificadorDto> listaMotivoAnulacion) {
		this.listaMotivoAnulacion = listaMotivoAnulacion;
	}

}
