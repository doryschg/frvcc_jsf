package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ConsultaLcvResponse;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaConsultaResumenLCVDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ConsultaResumenLCVModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;

@ManagedBean(name = "consultaResumenLCVController")
@ViewScoped
public class ConsultaResumenLCVController implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoUsuarioModel;

	@ManagedProperty(value = "#{consultaResumenLCVModel}")
	private ConsultaResumenLCVModel consultaResumenLCVModel;

	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;
	
	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	private List<ClasificadorDto> listaClasificadorFormularios;

	private List<ConsultaLcvResponse> listaConsultaResumenLCV = new ArrayList<>();
	private List<ClasificadorDto> listaClasificador;

	private Integer gestion;
		
	public void onLoad() {
		//listaClasificadorFormularios = new ArrayList<>();
		listaConsultaResumenLCV = new ArrayList<>();

	}

	public ConsultaResumenLCVController() {
		super();
	}


	public void obtieneResumenLCV(){

		RespuestaConsultaResumenLCVDto vResultado = new RespuestaConsultaResumenLCVDto();
//		vResultado = clientesRestController.getClienteRestLibros().obtenerResumenLCV(Long.parseLong(contextoUsuarioModel.getNitCi()),this.getGestion());
		vResultado =   clientesRestController.getClienteRestLibros().obtenerResumenLCV(141488012L,2016);		

		if (vResultado.isOk()) {
			if (!vResultado.getListaConsultaLcvResponse().isEmpty()) {
				for (ConsultaLcvResponse reg :vResultado.getListaConsultaLcvResponse()) {
					String mes = obtenerMes(reg.getMesPeriodo().intValue());
					reg.setDescripcionMes(mes);
				}
				listaConsultaResumenLCV = vResultado.getListaConsultaLcvResponse();
			}else {
				RequestContext.getCurrentInstance().execute("toastr.info('No se encontraron registros con los criterios de búsqueda ', 'Info')");
			}
		}else {
//			mensajesBean.addMensajes(vResultado);
			RequestContext.getCurrentInstance().execute("toastr.info('No se encontraron registros con los criterios de búsqueda ', 'Info')");
		}
	}
	
	
	public void nuevaBusqueda(){
		this.consultaResumenLCVModel.limpiar(); 
		listaConsultaResumenLCV = new ArrayList<>();
	}

	public String obtenerMes(Integer pMesNumeral) {
		String mes = "";
		switch (pMesNumeral) {
		case 1: {mes = "ENERO";break;}
		case 2: {mes = "FEBRERO";break;}
		case 3: {mes = "MARZO";break;}
		case 4: {mes = "ABRIL";break;}
		case 5: {mes = "MAYO";break;}
		case 6: {mes = "JUNIO";break;}
		case 7: {mes = "JULIO";break;}
		case 8: {mes = "AGOSTO";break;}
		case 9: {mes = "SEPTIEMBRE";break;}
		case 10: {mes = "OCTUBRE";break;}
		case 11: {mes = "NOVIEMBRE";break;}
		case 12: {mes = "DICIEMBRE";break;}
		default: {mes = "";break;}
		}
		return mes;
	}


	public ContextoUsuarioModel getContextoUsuarioModel() {
		return contextoUsuarioModel;
	}

	public void setContextoUsuarioModel(ContextoUsuarioModel contextoUsuarioModel) {
		this.contextoUsuarioModel = contextoUsuarioModel;
	}

	public ReportesController getReportesController() {
		return reportesController;
	}

	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}



	public List<ConsultaLcvResponse> getListaConsultaResumenLCV() {
		return listaConsultaResumenLCV;
	}

	public void setListaConsultaResumenLCV(List<ConsultaLcvResponse> listaConsultaResumenLCV) {
		this.listaConsultaResumenLCV = listaConsultaResumenLCV;
	}

	public ConsultaResumenLCVModel getConsultaResumenLCVModel() {
		return consultaResumenLCVModel;
	}

	public void setConsultaResumenLCVModel(ConsultaResumenLCVModel consultaResumenLCVModel) {
		this.consultaResumenLCVModel = consultaResumenLCVModel;
	}

	public List<ClasificadorDto> getListaClasificador() {
		return listaClasificador;
	}

	public void setListaClasificador(List<ClasificadorDto> listaClasificador) {
		this.listaClasificador = listaClasificador;
	}

	public List<ClasificadorDto> getListaClasificadorFormularios() {
		return listaClasificadorFormularios;
	}

	public void setListaClasificadorFormularios(List<ClasificadorDto> listaClasificadorFormularios) {
		this.listaClasificadorFormularios = listaClasificadorFormularios;
	}

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public Integer getGestion() {
		return gestion;
	}

	public void setGestion(Integer gestion) {
		this.gestion = gestion;
	}
}

