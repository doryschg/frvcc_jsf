package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormulariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoListaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioFormulariosRestclient;
public class LazyConsultaFormulariosModel extends LazyDataModel<FormularioCvDto> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5750539988871060143L;

	private static final Logger LOG = LoggerFactory.getLogger(LazyConsultaComprasModel.class);
	private List<FormularioCvDto> datasource;
	private LinkedHashMap<String, Serializable> filtros;
	private Long totalRegistros;
	private ServicioFormulariosRestclient clienteRest;
	private boolean obtenerTotal;
	private transient ServicioFormulariosRestclient formularioRestClient=new ServicioFormulariosRestclient();
	private MensajesBean mensajesBean;

	public LazyConsultaFormulariosModel(List<FormularioCvDto> datasource, LinkedHashMap<String, Serializable> pFiltros,
			ServicioFormulariosRestclient clienteRest, boolean obtenerTotal,MensajesBean mensajesBean) {
		super();
		this.datasource = datasource;
		this.filtros = pFiltros;
		this.clienteRest = clienteRest;
		this.obtenerTotal = obtenerTotal;
		this.totalRegistros = 0L;
		this.mensajesBean=mensajesBean;
	}

	@Override
	public Object getRowKey(FormularioCvDto pSolicitud) {
		return pSolicitud.getId();
	}

	@Override
	public FormularioCvDto getRowData(String rowKey) {
		for (FormularioCvDto filaDatoFormularios : datasource) {
			if (filaDatoFormularios.getId().equals(rowKey))
				return filaDatoFormularios;
		}
		return null;
	}

	@Override
	public List<FormularioCvDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		LOG.info("Ingresando load first ={}, pageSize ={}, sortField ={}, sortOrder ={}, filters ={} ", first, pageSize,
				sortField, sortOrder, filters);

		this.filtros.put("limit", pageSize);
		this.filtros.put("offset", first/pageSize);
		RespuestaFormulariosDto vResp=this.clienteRest.obtenerFormulariosActivos(this.filtros);
		if (vResp.isOk()) {
			if(!vResp.getFormulariosReponse().isEmpty())
			{
				if (this.obtenerTotal) {
					this.totalRegistros = vResp.getTotalElementos();
					this.setObtenerTotal(false);
				}

				this.setRowCount(this.totalRegistros.intValue());
				this.datasource =vResp.getFormulariosReponse();
				for (FormularioCvDto formularioCvDto : datasource) {
					formularioCvDto.setTotalComprasCfIpn(formularioCvDto.getTotalComprasCfIpn().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setTotalComprasCfOtras(formularioCvDto.getTotalComprasCfOtras().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setTotalComprasSdCf(formularioCvDto.getTotalComprasSdCf().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setDeterminacionPagoCfIpn(formularioCvDto.getDeterminacionPagoCfIpn().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setDeterminacionPagoCfOtras(formularioCvDto.getDeterminacionPagoCfOtras().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setDeterminacionPagoSdCf(formularioCvDto.getDeterminacionPagoSdCf().setScale(0, BigDecimal.ROUND_HALF_UP));
					formularioCvDto.setDeterminacionPagoCf(formularioCvDto.getDeterminacionPagoCf().setScale(0, BigDecimal.ROUND_HALF_UP));
				}
			}
			else {
				this.datasource = new ArrayList<>();
				mensajesBean.addMensajes(vResp);
			}
			

		} else {
			this.datasource = new ArrayList<>();
			mensajesBean.addMensajes(vResp);
			
		}
//		mensajesBean=null;
		
		
		
		return datasource;
	}

	public List<FormularioCvDto> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<FormularioCvDto> datasource) {
		this.datasource = datasource;
	}

	public ServicioFormulariosRestclient getClienteRest() {
		return clienteRest;
	}

	public void setClienteRest(ServicioFormulariosRestclient clienteRest) {
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
