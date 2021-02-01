package bo.gob.sin.sre.fac.frvcc.jsf.ventas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.controller.MensajesBean;

import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioFormulariosRestclient;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.service.EstandarService;

public class LazyConsultaVentasModel extends LazyDataModel<VentasCvDto> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5750539988871060143L;

	private static final Logger LOG = LoggerFactory.getLogger(LazyConsultaVentasModel.class);
	private List<VentasCvDto> datasource;
	private LinkedHashMap<String, Serializable> filtros;
	private Long totalRegistros;
	private EstandarService clienteRest;
	private boolean obtenerTotal;
	private transient ServicioFormulariosRestclient formularioRestClient = new ServicioFormulariosRestclient();
	private MensajesBean mensajesBean;

	public LazyConsultaVentasModel(List<VentasCvDto> datasource, LinkedHashMap<String, Serializable> pFiltros,
			EstandarService clienteRest, boolean obtenerTotal, MensajesBean mensajesBean) {
		super();
		this.datasource = datasource;
		this.filtros = pFiltros;
		this.clienteRest = clienteRest;
		this.obtenerTotal = obtenerTotal;
		this.totalRegistros = 0L;
		this.mensajesBean = mensajesBean;
	}

	@Override
	public Object getRowKey(VentasCvDto pSolicitud) {
		return pSolicitud.getId();
	}

	@Override
	public VentasCvDto getRowData(String rowKey) {
		for (VentasCvDto filaDatoCompra : datasource) {
			if (filaDatoCompra.getId().equals(rowKey))
				return filaDatoCompra;
		}
		return null;
	}

	@Override
	public List<VentasCvDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		LOG.info("Ingresando load first ={}, pageSize ={}, sortField ={}, sortOrder ={}, filters ={} ", first, pageSize,
				sortField, sortOrder, filters);

		RespuestaVentasDto vRespuesta = new RespuestaVentasDto();
		this.filtros.put("limit", pageSize);
		this.filtros.put("offset", first / pageSize);
		this.filtros.put("order_by", "fechaFactura");
		this.filtros.put("order", "DESC");
		vRespuesta = this.clienteRest.obtenerVentas(this.filtros);
		if (vRespuesta.isOk()) {
			if (!vRespuesta.getVentasResponse().isEmpty()) {
				if (this.obtenerTotal) {
					this.totalRegistros = vRespuesta.getTotalElementos();
					this.setObtenerTotal(false);
				}

				this.setRowCount(this.totalRegistros.intValue());
				this.datasource = vRespuesta.getVentasResponse();

			} else {
				mensajesBean.addMensajes(vRespuesta);
				vRespuesta = new RespuestaVentasDto();

				this.datasource = new ArrayList<VentasCvDto>();
			}
		} else {
			this.mensajesBean.addMensajes(vRespuesta);
		}

		return datasource;
	}

	public List<VentasCvDto> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<VentasCvDto> datasource) {
		this.datasource = datasource;
	}

	public LinkedHashMap<String, Serializable> getFiltros() {
		return filtros;
	}

	public void setFiltros(LinkedHashMap<String, Serializable> filtros) {
		this.filtros = filtros;
	}

	public boolean isObtenerTotal() {
		return obtenerTotal;
	}

	public void setObtenerTotal(boolean obtenerTotal) {
		this.obtenerTotal = obtenerTotal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLog() {
		return LOG;
	}

}
