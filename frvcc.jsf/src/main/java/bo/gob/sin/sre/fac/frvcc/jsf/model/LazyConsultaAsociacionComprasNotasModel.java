package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasNotaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoListaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasNotasRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasRestClient;

public class LazyConsultaAsociacionComprasNotasModel extends LazyDataModel<ComprasNotasCvDto> implements Serializable {

	private static final long serialVersionUID = 5750539988871060143L;

	private static final Logger LOG = LoggerFactory.getLogger(LazyConsultaAsociacionComprasNotasModel.class);
	private List<ComprasNotasCvDto> datasource;
	private LinkedHashMap<String, Serializable> filtros;
	private Long totalRegistros;
	private ServicioComprasNotasRestClient clienteRest;
	private boolean obtenerTotal;
	public LazyConsultaAsociacionComprasNotasModel(List<ComprasNotasCvDto> datasource, LinkedHashMap<String, Serializable> pFiltros,
			ServicioComprasNotasRestClient clienteRest, boolean obtenerTotal) {
		super();
		this.datasource = datasource;
		this.filtros = pFiltros;
		this.clienteRest = clienteRest;
		this.obtenerTotal = obtenerTotal;
		this.totalRegistros = 0L;
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

		RespuestaComprasNotaDto vRespuesta = new RespuestaComprasNotaDto();
		this.filtros.put("limit", pageSize);
		this.filtros.put("offset", first/ pageSize);
		
		vRespuesta = this.clienteRest.obtenerComprasNota(this.filtros);
		if (!vRespuesta.getComprasNotasResponse().isEmpty()) {
			if (this.obtenerTotal) {
				this.totalRegistros = vRespuesta.getTotalElementos();
				this.setObtenerTotal(false);
			}

			this.setRowCount(this.totalRegistros.intValue());
			this.datasource = vRespuesta.getComprasNotasResponse();

		} else {
			vRespuesta=new RespuestaComprasNotaDto();
			vRespuesta.setComprasNotasResponse(new ArrayList<>());
			this.datasource = new ArrayList<ComprasNotasCvDto>();
		}
	
		
		
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
