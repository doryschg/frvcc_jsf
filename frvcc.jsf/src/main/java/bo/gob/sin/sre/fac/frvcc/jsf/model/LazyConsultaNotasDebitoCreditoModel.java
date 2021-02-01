package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaCompraSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasNotaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasNotasRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasRestClient;

public class LazyConsultaNotasDebitoCreditoModel extends LazyDataModel<ComprasNotasCvDto> implements Serializable {

	private static final long serialVersionUID = 5750539988871060143L;

	private static final Logger LOG = LoggerFactory.getLogger(LazyConsultaNotasDebitoCreditoModel.class);
	private List<ComprasNotasCvDto> datasource;
	private LinkedHashMap<String, Serializable> filtros;
	private Long totalRegistros;
	private ServicioComprasNotasRestClient clienteRest;
	private boolean obtenerTotal;
	private MensajesBean mensajesBean;
	public LazyConsultaNotasDebitoCreditoModel(List<ComprasNotasCvDto> datasource, LinkedHashMap<String, Serializable> pFiltros,
			ServicioComprasNotasRestClient clienteRest, boolean obtenerTotal, MensajesBean mensajesBean) {
		super();
		this.datasource = datasource;
		this.filtros = pFiltros;
		this.clienteRest = clienteRest;
		this.obtenerTotal = obtenerTotal;
		this.totalRegistros = 0L;
		this.mensajesBean=mensajesBean;

	}

	@Override
	public Object getRowKey(ComprasNotasCvDto pSolicitud) {
		return pSolicitud.getId();
	}

	@Override
	public ComprasNotasCvDto getRowData(String rowKey) {
		for (ComprasNotasCvDto filaDatoCompras : datasource) {
			if (filaDatoCompras.getId().equals(rowKey))
				return filaDatoCompras;
		}
		return null;
	}

	@Override
	public List<ComprasNotasCvDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		
		LOG.info("Ingresando load first ={}, pageSize ={}, sortField ={}, sortOrder ={}, filters ={} ", first, pageSize,
				sortField, sortOrder, filters);

		ServicioComprasRestClient comprasRestClient=new ServicioComprasRestClient();
		RespuestaComprasNotaDto vRespuesta = new RespuestaComprasNotaDto();
		this.filtros.put("limit", pageSize);
		this.filtros.put("offset", first/ pageSize);
		
		vRespuesta = this.clienteRest.obtenerComprasNota(this.filtros);
		if (!vRespuesta.getComprasNotasResponse().isEmpty()) {
			List<ComprasNotasCvDto> vListComprasNotas=vRespuesta.getComprasNotasResponse();
			for(ComprasNotasCvDto compraNota: vListComprasNotas) {
			
				RespuestaCompraSingleDto compra=comprasRestClient.obtenerCompraPorId(compraNota.getCompraId());
				mensajesBean.addMensajes(compra);
				if(compra!=null && compra.isOk()) {
					compraNota.setFechaFacturaOriginal(compra.getCompraResponse().getFechaFactura());
					compraNota.setNumeroFacturaOriginal(compra.getCompraResponse().getNumeroFactura());
					compraNota.setCodigoAutorizacionOriginal(compra.getCompraResponse().getCodigoAutorizacion());
					compraNota.setImporteTotalOriginal(compra.getCompraResponse().getImporteTotalCompra());
				}
				
			}
			if (this.obtenerTotal) {
				this.totalRegistros = vRespuesta.getTotalElementos();
				this.setObtenerTotal(false);
			}

			this.setRowCount(this.totalRegistros.intValue());

		} else {
			LOG.info("mensajes {}.", vRespuesta.getMensajes());
			this.mensajesBean.addMensajes(vRespuesta);
			vRespuesta=new RespuestaComprasNotaDto();
		}
	
		this.datasource = vRespuesta.getComprasNotasResponse();
		
 		return datasource;
	}

	public List<ComprasNotasCvDto> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<ComprasNotasCvDto> datasource) {
		this.datasource = datasource;
	}

	
	public ServicioComprasNotasRestClient getClienteRest() {
		return clienteRest;
	}

	public void setClienteRest(ServicioComprasNotasRestClient clienteRest) {
		this.clienteRest = clienteRest;
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