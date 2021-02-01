package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.caco.dto.SucursalDto;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaSucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RolDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.SucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "agenteRetencionController")
@ViewScoped
public class AgenteRetencionController {
	
	@ManagedProperty(value = "#{componenteFormulariosRechazadosController}")
	private ComponenteFormulariosRechazadosController componenteFormulariosRechazadosController;
	@ManagedProperty(value = "#{componenteRecepcionFormulariosController}")
	private ComponenteRecepcionFormulariosController componenteRecepcionFormulariosController;
	@ManagedProperty(value = "#{componenteFormulariosAceptadosController}")
	private ComponenteFormulariosAceptadosController componenteFormulariosAceptadosController;
	@ManagedProperty(value = "#{componenteFormsConsolidadosController}")
	private ComponenteFormulariosConsolidadosController componenteFormulariosConsolidadosController;
	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;
	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;
	private static final Logger LOG= LoggerFactory.getLogger(AgenteRetencionController.class);
	private List<RolDto> listaRoles= new ArrayList<>();
	private List<SucursalDto> listaSucursales=new ArrayList<SucursalDto>();
	private List<SucursalDto> listaSucursalesPadron=new ArrayList<SucursalDto>();
	private List<SucursalUsuarioDto> listaSucursalesUsuarios = new ArrayList<>();
	private Integer numeroSucursal;
	private Boolean esConsolidador=false;
	private Boolean esUsuarioReceptor=false;
	private Boolean esGestorUsuarios=false;
	private String administracionDescripcion="";
	private Long administracion=0L;
	private List<ClasificadorDto> listaTipoUso=new ArrayList<>();
	@PostConstruct
	public void init(){
		listaTipoUso=clientesRestController.getClienteRestClasificadores().obtenerListaClasificadoresPorGrupo((ParametrosFRVCC.CODIGO_AGRUPADOR_AGENTE_TIPO_USO));
		componenteRecepcionFormulariosController.setListaTipoUso(listaTipoUso);
		componenteFormulariosAceptadosController.setListaTipoUso(listaTipoUso);
		componenteFormulariosRechazadosController.setListaTipoUso(listaTipoUso);

		LOG.info("lista tipo uso {}", listaTipoUso);
		obtenerRolesUsuario();
		if(esConsolidador){
			consultaSucursalesContribuyente();
			componenteFormulariosAceptadosController.setAdministracionDescripcion(administracionDescripcion);
			componenteFormulariosAceptadosController.setAdministracion(administracion);
			componenteFormulariosAceptadosController.setListaSucuConsolidaciones(listaSucursalesPadron);
			componenteFormulariosConsolidadosController.setAdministracionDescripcion(administracionDescripcion);
		}
		if(esUsuarioReceptor){
			obtenerSucursalesUsuarios();
			if(!listaSucursalesUsuarios.isEmpty()){
				componenteFormulariosAceptadosController.setListaSucursales(listaSucursales);
				componenteFormulariosAceptadosController.setListaSucursalesUsuarios(listaSucursalesUsuarios);

				componenteFormulariosRechazadosController.setListaSucursales(listaSucursales);
				componenteFormulariosRechazadosController.setListaSucursalesUsuarios(listaSucursalesUsuarios);

				componenteRecepcionFormulariosController.setListaSucursales(listaSucursales);
				componenteRecepcionFormulariosController.setListaSucursalesUsuarios(listaSucursalesUsuarios);

				componenteRecepcionFormulariosController.setNumeroSucursal(numeroSucursal);
				componenteFormulariosRechazadosController.setNumeroSucursal(numeroSucursal);
				componenteFormulariosAceptadosController.setNumeroSucursal(numeroSucursal);
			}
		}

	}

	public void onTabChanged(TabChangeEvent event) {
			this.componenteRecepcionFormulariosController.onLimpiar();
			this.componenteFormulariosAceptadosController.onLimpiar();
			this.componenteFormulariosRechazadosController.onLimpiar();
			this.componenteFormulariosConsolidadosController.onLimpiar();
	}

	public void obtenerRolesUsuario() {
		try{
			listaRoles= clientesRestController.getClienteRestRoles().obtenerRolesPorUsuario(contextoModel.getUsuario().getUsuarioId());
			if(!listaRoles.isEmpty()){
				esConsolidador=listaRoles.stream().filter(rol->rol.getRolId().equals(ParametrosFRVCC.ROL_AG_CONSOLIDADOR_ID)).count()>0L;
				esUsuarioReceptor=listaRoles.stream().filter(rol->rol.getRolId().equals(ParametrosFRVCC.ROL_AG_RECEPTOR_ID)).count()>0L;
				esGestorUsuarios=listaRoles.stream().filter(rol->rol.getRolId().equals(ParametrosFRVCC.ROL_AG_SUCURSAL_USUARIO_ID)).count()>0L;

				if(esConsolidador || esUsuarioReceptor || esGestorUsuarios){

					this.componenteRecepcionFormulariosController.setEsUsuarioReceptor(esUsuarioReceptor);
					this.componenteRecepcionFormulariosController.setEsGestorUsuarios(esGestorUsuarios);

					this.componenteFormulariosAceptadosController.setEsUsuarioReceptor(esUsuarioReceptor);
					this.componenteFormulariosAceptadosController.setEsUsuarioConsolidador(esConsolidador);
					this.componenteFormulariosAceptadosController.setEsGestorUsuarios(esGestorUsuarios);

					this.componenteFormulariosRechazadosController.setEsUsuarioReceptor(esUsuarioReceptor);
					this.componenteFormulariosRechazadosController.setEsGestorUsuarios(esGestorUsuarios);

					this.componenteFormulariosConsolidadosController.setEsUsuarioConsolidador(esConsolidador);
				}
				else {
					RequestContext.getCurrentInstance()
							.execute("toastr.error('No cuenta con roles asignados', 'Error!')");
				}

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	public void obtenerSucursalesUsuarios() {
		try{
			LinkedHashMap<String, Serializable> vFiltros = obtenerCriteriosUsuarios();

			RespuestaSucursalUsuarioDto vRespuesta = clientesRestController.getClienteRestSucursalUsuario()
					.obtenerSucursalUsuarioActivos(vFiltros);
			if (vRespuesta.isOk()) {
				listaSucursalesUsuarios = vRespuesta.getSucursalesUsuariosReponse();
				if(!listaSucursalesUsuarios.isEmpty()){
					consultaSucursalesContribuyente();
					obtenerSucursalesPadronUsuario();
					numeroSucursal=listaSucursalesUsuarios.get(0).getNroSucursal();
				}
			} else {
				listaSucursalesUsuarios.clear();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void consultaSucursalesContribuyente() {
		ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vDatoscontribuyenteEmpleador = clientesRestController.getClienteRestPadron()
				.consultaContribuyente(contextoModel.getContribuyente().getNit());
		if (vDatoscontribuyenteEmpleador.isOk()) {
			listaSucursalesPadron=vDatoscontribuyenteEmpleador.getResultadoObjeto().getSucursales();
			administracion= Long.valueOf(vDatoscontribuyenteEmpleador.getResultadoObjeto().getAdministracionId()+"");
		administracionDescripcion=vDatoscontribuyenteEmpleador.getResultadoObjeto().getAdministracionDescripcion();
		}

	}
	public void obtenerSucursalesPadronUsuario(){
		try {
			List<SucursalDto> vListaSucursales =
					listaSucursalesPadron.stream()
							.filter(suc -> listaSucursalesUsuarios.stream().map(SucursalUsuarioDto::getNroSucursal).filter(usu -> usu.equals(Integer.valueOf(suc.getNumeroSucursal()))).findFirst().isPresent())
							.collect(Collectors.toList());
			listaSucursales=vListaSucursales;
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	private LinkedHashMap<String, Serializable> obtenerCriteriosUsuarios() {
		FiltroCriteria filtros = new FiltroCriteria();
		ContextoJSF contexto = new ContextoJSF();
		if (contexto.getUsuario() != null) {
			filtros.adicionar("estadoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_ID);
			filtros.adicionar("estadoAsignacionId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_ASIGNACION_ID);
			Long usuarioReceptor=contexto.getUsuario().getUsuarioId();
			filtros.adicionar("usuarioReceptor", OperadorCriteria.IGUAL, usuarioReceptor);

		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
		}
		return filtros.getFiltros();
	}

	public ComponenteFormulariosRechazadosController getComponenteFormulariosRechazadosController() {
		return componenteFormulariosRechazadosController;
	}
	public void setComponenteFormulariosRechazadosController(
			ComponenteFormulariosRechazadosController componenteFormulariosRechazadosController) {
		this.componenteFormulariosRechazadosController = componenteFormulariosRechazadosController;
	}
	public ComponenteRecepcionFormulariosController getComponenteRecepcionFormulariosController() {
		return componenteRecepcionFormulariosController;
	}
	public void setComponenteRecepcionFormulariosController(
			ComponenteRecepcionFormulariosController componenteRecepcionFormulariosController) {
		this.componenteRecepcionFormulariosController = componenteRecepcionFormulariosController;
	}

	public ComponenteFormulariosAceptadosController getComponenteFormulariosAceptadosController() {
		return componenteFormulariosAceptadosController;
	}
	public void setComponenteFormulariosAceptadosController(
			ComponenteFormulariosAceptadosController componenteFormulariosAceptadosController) {
		this.componenteFormulariosAceptadosController = componenteFormulariosAceptadosController;
	}

	public ComponenteFormulariosConsolidadosController getComponenteFormulariosConsolidadosController() {
		return componenteFormulariosConsolidadosController;
	}

	public void setComponenteFormulariosConsolidadosController(ComponenteFormulariosConsolidadosController componenteFormulariosConsolidadosController) {
		this.componenteFormulariosConsolidadosController = componenteFormulariosConsolidadosController;
	}

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}

	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
	}

	public Integer getNumeroSucursal() {
		return numeroSucursal;
	}

	public void setNumeroSucursal(Integer numeroSucursal) {
		this.numeroSucursal = numeroSucursal;
	}

	public Boolean getEsConsolidador() {
		return esConsolidador;
	}

	public void setEsConsolidador(Boolean esConsolidador) {
		this.esConsolidador = esConsolidador;
	}

	public Boolean getEsUsuarioReceptor() {
		return esUsuarioReceptor;
	}

	public void setEsUsuarioReceptor(Boolean esUsuarioReceptor) {
		this.esUsuarioReceptor = esUsuarioReceptor;
	}

	public Boolean getEsGestorUsuarios() {
		return esGestorUsuarios;
	}

	public void setEsGestorUsuarios(Boolean esGestorUsuarios) {
		this.esGestorUsuarios = esGestorUsuarios;
	}


}
