package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormulariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaReporteDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ConsultaFormulario110Model;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;

@ManagedBean(name = "consultaFormulario110Controller")
@ViewScoped
public class ConsultaFormulario110Controller implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoUsuarioModel;

	@ManagedProperty(value = "#{consultaFormulario110Model}")
	private ConsultaFormulario110Model consultaFormulario110Model;

	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;
	
	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	private List<FormularioCvDto> listaFormularios;
	private List<ClasificadorDto> listaClasificadorFormularios;

	private List<FormularioCvDto> vListaFormularios = new ArrayList<>();
	private List<ClasificadorDto> listaClasificador;

	private FormularioCvDto formularioSeleccionado;

		
	public void onLoad() {
		formularioSeleccionado = new FormularioCvDto();
		listaFormularios = new ArrayList<>();
		listaClasificadorFormularios = new ArrayList<>();
		vListaFormularios = new ArrayList<>();
		cargarFormularios();
	}

	public ConsultaFormulario110Controller() {
		super();
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.getConsultaFormulario110Model().getAnioPeriodo());
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoUsuarioModel.getNitCi());
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, this.getConsultaFormulario110Model().getTipoFormularioId());
		if(getConsultaFormulario110Model().getTipoFormularioId().equals(ParametrosFRVCC.TIPO_FORMULARIO_110)
		||getConsultaFormulario110Model().getTipoFormularioId().equals(ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F610))
		{
			filtros.adicionar("nitEmpleador", OperadorCriteria.IGUAL,this.getConsultaFormulario110Model().getNitEmpleador());// 1003579028
		}
		
		
		
		
		filtros.adicionar("estadoFormularioId", OperadorCriteria.IN,"DEC,ACP,RECH" );
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		

		return filtros.getFiltros();
	}

	public void obtieneResumenFacturas(){

		RespuestaFormulariosDto vResultado = new RespuestaFormulariosDto();
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		pFilters.put("order_by", "mesPeriodo");
		pFilters.put("order", "ASC");
		vResultado = clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);

		if (vResultado.isOk()) {
			if (!vResultado.getFormulariosReponse().isEmpty()) {
				for (FormularioCvDto form : vResultado.getFormulariosReponse()) {
					String mes = obtenerMes(form.getMesPeriodo());
					form.setDescripcionMes(mes);
				}
				listaFormularios = vResultado.getFormulariosReponse();
				
			}else {
//				RequestContext.getCurrentInstance().execute("toastr.info('No se encontraron registros con los criterios de b�squeda ', 'Info')");
				mensajesBean.addMensajes(vResultado);
			}
		}else {
			mensajesBean.addMensajes(vResultado);
//			RequestContext.getCurrentInstance().execute("toastr.info('No se encontraron registros con los criterios de b�squeda ', 'Info')");
		}
	}
	
	public void cargarFormularios() {
		List<ClasificadorDto> vResultado = clientesRestController.getClienteRestClasificadores().obtenerClasificadoresPorTipo("tipo_formulario_id_vc");
		if (!vResultado.isEmpty()) {
			listaClasificadorFormularios = vResultado;
			Collections.sort(listaClasificadorFormularios, (x,y) ->	x.getClasificadorId().compareTo(y.getClasificadorId()));
			tipoFormulario();
		} else {
			this.setListaClasificadorFormularios(new ArrayList<>());
			FacesContext.getCurrentInstance().addMessage("errorMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					" Error ", " El servicio de datos Parametrricos no esta disponible "));
		}
	}

	public void tipoFormulario() {
		List<ClasificadorDto> vResultado = new ArrayList<ClasificadorDto>();
		for (ClasificadorDto tipo : listaClasificadorFormularios) {
			if (!(tipo.getCodigoClasificador().equals(ParametrosFRVCC.TIPO_FORMULARIO_LCV))) {
				vResultado.add(tipo);
			}
		}
		listaClasificadorFormularios = vResultado;
	}
	
	
	
	public void obtenerReporte(FormularioCvDto pFormulario) {

		try {
			if(!pFormulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO))
			{
				ResultadoGenericoDto<RespuestaReporteDto> vRespuestaReport=clientesRestController.getClienteRestFormularios().obtieneReporteF110(pFormulario);
				if(vRespuestaReport.isOk())
				{
					reportesController.setRespuestaBase64(vRespuestaReport.getResultadoObjeto().getArchivoBase64());
				}
				else
				{
					mensajesBean.addMensajes(vRespuestaReport);
				
				}
			}
			
		} catch (Exception e) {
		
			RequestContext.getCurrentInstance()
			.execute("toastr.error('Servicio de reporte no disponible', 'Error')");
		
		}
		
		}
	
	public void nuevaBusqueda(){
		this.consultaFormulario110Model.limpiar(); 
		listaFormularios = new ArrayList<>();
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

	public List<FormularioCvDto> getListaFormularios() {
		return listaFormularios;
	}

	public void setListaFormularios(List<FormularioCvDto> listaFormularios) {
		this.listaFormularios = listaFormularios;
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

	public List<FormularioCvDto> getvListaFormularios() {
		return vListaFormularios;
	}

	public void setvListaFormularios(List<FormularioCvDto> vListaFormularios) {
		this.vListaFormularios = vListaFormularios;
	}

	public FormularioCvDto getFormularioSeleccionado() {
		return formularioSeleccionado;
	}

	public void setFormularioSeleccionado(FormularioCvDto formularioSeleccionado) {
		this.formularioSeleccionado = formularioSeleccionado;
	}


	public ConsultaFormulario110Model getConsultaFormulario110Model() {
		return consultaFormulario110Model;
	}

	public void setConsultaFormulario110Model(ConsultaFormulario110Model consultaFormulario110Model) {
		this.consultaFormulario110Model = consultaFormulario110Model;
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

	


}
