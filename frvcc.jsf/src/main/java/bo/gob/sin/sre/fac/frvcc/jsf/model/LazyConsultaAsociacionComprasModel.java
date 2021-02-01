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

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoListaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;

public class LazyConsultaAsociacionComprasModel extends LazyDataModel <ComprasCvDto> implements Serializable {

	private static final long serialVersionUID = 5750539988871060143L;

	private static final Logger LOG = LoggerFactory.getLogger(LazyConsultaAsociacionComprasModel.class);
	private List<ComprasCvDto> datasource;
	private LinkedHashMap<String, Serializable> filtros;
	private Long totalRegistros;
	private ServicioComprasRestClient clienteRest;
	private boolean obtenerTotal;
	private MensajesBean mensajesBean;
	public LazyConsultaAsociacionComprasModel(List<ComprasCvDto> datasource, LinkedHashMap<String, Serializable> pFiltros,
			ServicioComprasRestClient clienteRest, boolean obtenerTotal,MensajesBean mensajesBean) {
		super();
		this.datasource = datasource;
		this.filtros = pFiltros;
		this.clienteRest = clienteRest;
		this.obtenerTotal = obtenerTotal;
		this.totalRegistros = 0L;
		this.mensajesBean=mensajesBean;
	}

	@Override
	public Object getRowKey(ComprasCvDto pSolicitud) {
		return pSolicitud.getId();
	}

	@Override
	public ComprasCvDto getRowData(String rowKey) {
		for (ComprasCvDto filaDatoCompras : datasource) {
			if (filaDatoCompras.getId().equals(rowKey))
				return filaDatoCompras;
		}
		return null;
	}

	@Override
	public List<ComprasCvDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		LOG.info("Ingresando load first ={}, pageSize ={}, sortField ={}, sortOrder ={}, filters ={} ", first, pageSize,
				sortField, sortOrder, filters);

		RespuestaComprasDto vRespuesta = new RespuestaComprasDto();
		this.filtros.put("limit", pageSize);
		this.filtros.put("offset", first/ pageSize);
		if(filters!=null && !filters.isEmpty())
		{
			FiltroCriteria filtros=new FiltroCriteria();
			filtros.adicionar("nitProveedor", OperadorCriteria.CONTAINS, filters.get("nitProveedor").toString());
			this.filtros.putAll(filtros.getFiltros());
		}
		if(sortField!=null)
		{
			filtros.put("order_by", sortField);
			if(sortOrder.name().equals("ASCENDING"))
			{
				filtros.put("order", "ASC");
			}
			else if(sortOrder.name().equals("DESCENDING"))
			{
				filtros.put("order", "DESC");
			}else {
				
			}
			
			
		}
		vRespuesta = this.clienteRest.obtenerCompras(this.filtros);
		if (vRespuesta.isOk()) {
			
			if (this.obtenerTotal) {
				this.totalRegistros = vRespuesta.getTotalElementos();
				this.setObtenerTotal(false);
			}

			this.setRowCount(this.totalRegistros.intValue());
			this.mensajesBean.addMensajes(vRespuesta);
		} else {
			//this.mensajesBean.addMensajes(vRespuesta);
			vRespuesta=new RespuestaComprasDto();
		}
	
		this.datasource = vRespuesta.getComprasResponse();
		
 		return datasource;
	}

	public List<ComprasCvDto> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<ComprasCvDto> datasource) {
		this.datasource = datasource;
	}

	
	public ServicioComprasRestClient getClienteRest() {
		return clienteRest;
	}

	public void setClienteRest(ServicioComprasRestClient clienteRest) {
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
