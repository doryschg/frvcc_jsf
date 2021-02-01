package bo.gob.sin.sre.fac.frvcc.jsf.controller;
import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaFormulariosModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;
import org.primefaces.model.LazyDataModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@ManagedBean(name = "componenteFormsConsolidadosController")
@ViewScoped
public class ComponenteFormulariosConsolidadosController implements Serializable {
	private static final long serialVersionUID = 1L;
	private LazyDataModel<FormularioCvDto> formulariosLazy;
	private Integer periodoMes;
	private Integer periodoAnio;
	private List<FormularioCvDto> formularios = new ArrayList<>();
	private boolean consultaRealizada=false;
	private ConsolidadorDto consolidadorPrincipal=new ConsolidadorDto();
	private List<ConsolidadorDto> listaConsolidaciones=new ArrayList<>();
	private Boolean esUsuarioConsolidador=false;
	private String administracionDescripcion="";
	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;
	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;
	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;
	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@PostConstruct
	public void init(){
		Calendar fecha = new GregorianCalendar();
		periodoAnio = fecha.get(Calendar.YEAR);
		periodoMes = fecha.get(Calendar.MONTH) + 1;
		onLimpiar();
	}
	public void onLimpiar() {
		FiltroCriteria filtros = new FiltroCriteria();
		setFormularios(new ArrayList<FormularioCvDto>());
		setFormulariosLazy(new LazyConsultaFormulariosModel(new ArrayList<FormularioCvDto>(),
				filtros.getFiltros(), this.clientesRestController.getClienteRestFormularios(), false, mensajesBean));
		setConsultaRealizada(false);
		periodoMes=null;

	}
	public void onBuscar() {
		try{
			generarConsolidacion();
			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
			this.setFormulariosLazy(
					new LazyConsultaFormulariosModel(new ArrayList<FormularioCvDto>(), pFilters, this.clientesRestController.getClienteRestFormularios(), true,mensajesBean));
			consultaRealizada=true;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		if (this.periodoMes != null) {
			filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, this.getPeriodoMes());
			
		}
		if (this.periodoAnio != null) {
			filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.getPeriodoAnio());
			
		}
		filtros.adicionar("nitEmpleador", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110);
		filtros.adicionar("estadoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_FORMULARIO_ACEPTADO);
		filtros.adicionar("consolidacionId", OperadorCriteria.IGUAL,consolidadorPrincipal.getId()!=null?consolidadorPrincipal.getId():"");

		return filtros.getFiltros();
	}

	public void obtenerFormularios(){
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		long vCantidad=clientesRestController.getClienteRestFormularios().obtenerTotalFormularios(pFilters);
		if(vCantidad>0) {
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "numeroOrden");
			pFilters.put("order", "DESC");
		}
		RespuestaFormulariosDto vRespuesta=clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
		formularios=vRespuesta.getFormulariosReponse();
		if(!formularios.isEmpty()){
			imprimirConsolidado();
		}

	}

	public void limpiarConsolidado(){
		listaConsolidaciones.clear();
	}
	public void generarConsolidacion(){
		try {
			LinkedHashMap<String, Serializable> pFilters = obtenerCriteriosConsolidacion();
			RespuestaConsolidacionesDto vRespuesta=clientesRestController.getClienteRestFormularios().obtenerConsolidaciones(pFilters);
			if(vRespuesta.isOk()){
				limpiarConsolidado();
				this.setConsolidadorPrincipal(vRespuesta.getConsolidacionesResponse().stream().filter(f->f.getTipoId().equals(ParametrosFRVCC.TIPO_CONSOLIDACION_PRINCIPAL)).collect(Collectors.toList()).get(0));
				consolidadorPrincipal.setFechaImpresion(LocalDate.now());
				consolidadorPrincipal.setPeriodoDescripcion(obtenerMes(consolidadorPrincipal.getPeriodo()));
				consolidadorPrincipal.setAdministracionDescripcion(consolidadorPrincipal.getAdministracion()+" - "+this.getAdministracionDescripcion());
				listaConsolidaciones=vRespuesta.getConsolidacionesResponse().stream().filter(f->f.getTipoId().equals(ParametrosFRVCC.TIPO_CONSOLIDACION_SECUENDARIA)).collect(Collectors.toList());
				obtenerFormularios();

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	private LinkedHashMap<String, Serializable> obtenerCriteriosConsolidacion() {
		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("periodo", OperadorCriteria.IGUAL, this.getPeriodoMes());
		filtros.adicionar("gestion", OperadorCriteria.IGUAL, this.getPeriodoAnio());
		filtros.adicionar("nitAgente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId",OperadorCriteria.IGUAL,ParametrosFRVCC.ESTADO_ID);
		filtros.adicionar("estadoConsolidadoId",OperadorCriteria.IGUAL,ParametrosFRVCC.ESTADO_CONSOLIDACION_ID);
		return filtros.getFiltros();
	}

	public void imprimirConsolidado(){
		reportesController.reporteResumenConsolidados(listaConsolidaciones, this.consolidadorPrincipal);
	}
	public String obtenerMes(Integer periodo) {
		String mesPeriodo = "";
		switch (periodo) {
			case 1: {mesPeriodo = "ENERO";break;}
			case 2: {mesPeriodo = "FEBRERO";break;}
			case 3: {mesPeriodo = "MARZO";break;}
			case 4: {mesPeriodo = "ABRIL";break;}
			case 5: {mesPeriodo = "MAYO";break;}
			case 6: {mesPeriodo = "JUNIO";break;}
			case 7: {mesPeriodo = "JULIO";break;}
			case 8: {mesPeriodo = "AGOSTO";break;}
			case 9: {mesPeriodo = "SEPTIEMBRE";break;}
			case 10: {mesPeriodo = "OCTUBRE";break;}
			case 11: {mesPeriodo = "NOVIEMBRE";break;}
			case 12: {mesPeriodo = "DICIEMBRE";break;}
			default: {mesPeriodo = "";break;}
		}
		return mesPeriodo;
	}

	public LazyDataModel<FormularioCvDto> getFormulariosLazy() {
		return formulariosLazy;
	}
	public void setFormulariosLazy(LazyDataModel<FormularioCvDto> formulariosLazy) {
		this.formulariosLazy = formulariosLazy;
	}
	public List<FormularioCvDto> getFormularios() {
		return formularios;
	}
	public void setFormularios(List<FormularioCvDto> formularios) {
		this.formularios = formularios;
	}
	public Integer getPeriodoMes() {
		return periodoMes;
	}
	public void setPeriodoMes(Integer periodoMes) {
		this.periodoMes = periodoMes;
	}
	public ReportesController getReportesController() {
		return reportesController;
	}
	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}
	public boolean isConsultaRealizada() {
		return consultaRealizada;
	}
	public void setConsultaRealizada(boolean consultaRealizada) {
		this.consultaRealizada = consultaRealizada;
	}
	public Integer getPeriodoAnio() {
		return periodoAnio;
	}
	public void setPeriodoAnio(Integer periodoAnio) {
		this.periodoAnio = periodoAnio;
	}
	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}
	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
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

	public Boolean getEsUsuarioConsolidador() {
		return esUsuarioConsolidador;
	}

	public void setEsUsuarioConsolidador(Boolean esUsuarioConsolidador) {
		this.esUsuarioConsolidador = esUsuarioConsolidador;
	}

	public String getAdministracionDescripcion() {
		return administracionDescripcion;
	}

	public void setAdministracionDescripcion(String administracionDescripcion) {
		this.administracionDescripcion = administracionDescripcion;
	}

	public ConsolidadorDto getConsolidadorPrincipal() {
		return consolidadorPrincipal;
	}

	public void setConsolidadorPrincipal(ConsolidadorDto consolidadorPrincipal) {
		this.consolidadorPrincipal = consolidadorPrincipal;
	}

	public List<ConsolidadorDto> getListaConsolidaciones() {
		return listaConsolidaciones;
	}

	public void setListaConsolidaciones(List<ConsolidadorDto> listaConsolidaciones) {
		this.listaConsolidaciones = listaConsolidaciones;
	}
}
