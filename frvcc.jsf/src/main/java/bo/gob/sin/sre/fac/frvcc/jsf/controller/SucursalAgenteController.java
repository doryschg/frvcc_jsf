package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.caco.dto.SucursalDto;
import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaSucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaUsuariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.SucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.SucursalUsuarioRelacionadoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.UsuarioConsolidadorDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.caut.dto.UsuarioOVDto;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;

@ManagedBean(name = "sucursalAgenteController")
@ViewScoped
public class SucursalAgenteController implements Serializable {

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;

	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;

	private DatosContribuyenteEscritorioDto datosContribuyente;
	private UsuarioConsolidadorDto usuarioConsolidadorSeleccionado;
	private List<SucursalDto> sucursalesAgente;
	private List<DepartamentoDto> listaDepartamentos = new ArrayList<>();
	private List<SucursalUsuarioDto> listaSucursalesUsuarios = new ArrayList<>();
	private List<SucursalUsuarioRelacionadoDto> listaSucursalesUsuariosRelacionados = new ArrayList<>();
	private List<SucursalUsuarioRelacionadoDto> listaSUfiltrado = new ArrayList<>();
	private List<UsuarioConsolidadorDto> listaUsuariosConsolidadores=new ArrayList<>();

	private Integer departamentoId;
	private Integer nroSucursal;
	private String asignacion;

	@PostConstruct
	public void init() {
		obtenerDatosContribuyente();
		sucursalesAgente = datosContribuyente.getSucursales();
		obtenerSucursalesUsuariosMfc();
		relacionarSucursalUsuario();
		obtenerUsuariosConsolidadores();
		listaSUfiltrado = listaSucursalesUsuariosRelacionados;
	}

	private void obtenerUsuariosConsolidadores() {
		ContextoJSF contexto = new ContextoJSF();
		if (contexto.getUsuario() != null) {
		RespuestaUsuariosDto vRespuesta=clientesRestController.getClienteRestSucursalUsuario()
				.obtenerUsuariosConsolidadoresActivos(contexto.getUsuario().getPersonaId());
			if(vRespuesta.isOk())
			{
				listaUsuariosConsolidadores=vRespuesta.getUsuariosReponse();
			}
			else
			{
				mensajesBean.addMensajes(vRespuesta);
			}
		}else {
			RequestContext.getCurrentInstance()
			.execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
		}
		
	}

	public void obtenerDatosContribuyente() {
		ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vRespuesta = clientesRestController.getClienteRestPadron()
				.consultaContribuyente(Long.parseLong(contextoModel.getNitCi()));
		listaDepartamentos = clientesRestController.getClienteDepartamentos().getListaDepartamentoActivos();
		if (vRespuesta.isOk()) {
			datosContribuyente = vRespuesta.getResultadoObjeto();
		} else {
			datosContribuyente = new DatosContribuyenteEscritorioDto();
			datosContribuyente.setSucursales(new ArrayList<>());
			mensajesBean.addMensajes(vRespuesta);
		}
	}

	public void onBuscar() {
		sucursalesAgente = datosContribuyente.getSucursales();
		obtenerSucursalesUsuariosMfc();
		relacionarSucursalUsuario();
		listaSUfiltrado = listaSucursalesUsuariosRelacionados;
		if (departamentoId != null) {
			listaSUfiltrado = listaSUfiltrado.stream()
					.filter(s -> s.getSucursal().getDepartamentoId() == departamentoId).collect(Collectors.toList());
		}
		if (nroSucursal != null) {
			listaSUfiltrado = listaSUfiltrado.stream()
					.filter(s -> Integer.parseInt(s.getSucursal().getNumeroSucursal()) == nroSucursal)
					.collect(Collectors.toList());
		}
		if (asignacion != null && !asignacion.equals("")) {
			if (asignacion.equals("SI")) {
				listaSUfiltrado = listaSUfiltrado.stream().filter(s -> s.getIdUsuarioAsig() != null)
						.collect(Collectors.toList());
			} else {
				listaSUfiltrado = listaSUfiltrado.stream().filter(s -> s.getIdUsuarioAsig() == null)
						.collect(Collectors.toList());
			}
		}
	}

	public void obtenerSucursalesUsuariosMfc() {
		LinkedHashMap<String, Serializable> vFiltros = obtenerCriterios();

		RespuestaSucursalUsuarioDto vRespuesta = clientesRestController.getClienteRestSucursalUsuario()
				.obtenerSucursalUsuarioActivos(vFiltros);
		if (vRespuesta.isOk()) {
			listaSucursalesUsuarios = vRespuesta.getSucursalesUsuariosReponse();
		} else {
			listaSucursalesUsuarios = new ArrayList<>();
		}
	}

	public void onLimpiar() {
		asignacion = null;
		nroSucursal = null;
		departamentoId = null;
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		ContextoJSF contexto = new ContextoJSF();
		if (contexto.getUsuario() != null) {
			filtros.adicionar("nitAgente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
			filtros.adicionar("estadoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_ID);
			filtros.adicionar("estadoAsignacionId", OperadorCriteria.IGUAL, "AC");

		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
		}
		return filtros.getFiltros();
	}

	public void relacionarSucursalUsuario() {
		listaSucursalesUsuariosRelacionados=new ArrayList<>();
		for (SucursalDto sucursal : sucursalesAgente) {
			SucursalUsuarioRelacionadoDto vSucursalRelacionada = new SucursalUsuarioRelacionadoDto();
			vSucursalRelacionada.setSucursal(sucursal);
			Optional<SucursalUsuarioDto> sucursalUsuario = listaSucursalesUsuarios.stream()
					.filter(s -> s.getNroSucursal() == Integer.parseInt(sucursal.getNumeroSucursal())).findFirst();
			if (sucursalUsuario.isPresent()) {
				vSucursalRelacionada.setId(sucursalUsuario.get().getId());
				vSucursalRelacionada.setNombreUsuarioAsig(sucursalUsuario.get().getNombreUsuarioReceptor());
				vSucursalRelacionada.setIdUsuarioAsig(sucursalUsuario.get().getUsuarioReceptor());
			}
			listaSucursalesUsuariosRelacionados.add(vSucursalRelacionada);
		}
	}
	public void asignarSucursalUsuario(SucursalUsuarioRelacionadoDto pSucursal)
	{
		cambiarUsuario(pSucursal);
		
		SucursalUsuarioDto vSucursalUsuario=new SucursalUsuarioDto();
		vSucursalUsuario.setId(pSucursal.getId());
		vSucursalUsuario.setIfcAgente(contextoModel.getUsuario().getPersonaId());
		vSucursalUsuario.setNitAgente(Long.parseLong(contextoModel.getNitCi()));
		vSucursalUsuario.setNroSucursal(Integer.parseInt(pSucursal.getSucursal().getNumeroSucursal()));
		vSucursalUsuario.setUsuarioReceptor(pSucursal.getIdUsuarioAsig());
		vSucursalUsuario.setLoginUsuarioReceptor(pSucursal.getLoginUsuarioAsig());
		vSucursalUsuario.setNombreUsuarioReceptor(pSucursal.getNombreUsuarioAsig());
		
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestSucursalUsuario().guardarSucursalUsuario(vSucursalUsuario);
		if(vRespuesta.isOk())
		{
			usuarioConsolidadorSeleccionado=null;
		}
		mensajesBean.addMensajes(vRespuesta);
		
	}
	public void desAsignarSucursalUsuario(SucursalUsuarioRelacionadoDto pSucursal)
	{
		SucursalUsuarioDto vSucursalUsuario=new SucursalUsuarioDto();
		vSucursalUsuario.setId(pSucursal.getId());
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestSucursalUsuario().inhabilitarSucursalUsuario(vSucursalUsuario);
		if(vRespuesta.isOk())
		{
			pSucursal.setId(null);

		}
		mensajesBean.addMensajes(vRespuesta);
	}
	public void cambiarUsuario(SucursalUsuarioRelacionadoDto pSucursal)
	{
		pSucursal.setId(UUID.randomUUID().toString());
		pSucursal.setIdUsuarioAsig(usuarioConsolidadorSeleccionado.getUsuarioId());
		pSucursal.setNombreUsuarioAsig(usuarioConsolidadorSeleccionado.getDatosPersona().getNombreUsuario());
		pSucursal.setNroDocumentoUsuarioAsig(usuarioConsolidadorSeleccionado.getDatosPersona().getNroDocumento());
		pSucursal.setLoginUsuarioAsig(usuarioConsolidadorSeleccionado.getLogin());
		
	}
	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public ReportesController getReportesController() {
		return reportesController;
	}

	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}

	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
	}

	public DatosContribuyenteEscritorioDto getDatosContribuyente() {
		return datosContribuyente;
	}

	public void setDatosContribuyente(DatosContribuyenteEscritorioDto datosContribuyente) {
		this.datosContribuyente = datosContribuyente;
	}

	public List<SucursalDto> getSucursalesAgente() {
		return sucursalesAgente;
	}

	public void setSucursalesAgente(List<SucursalDto> sucursalesAgente) {
		this.sucursalesAgente = sucursalesAgente;
	}

	public List<DepartamentoDto> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<DepartamentoDto> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public Integer getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}

	public Integer getNroSucursal() {
		return nroSucursal;
	}

	public void setNroSucursal(Integer nroSucursal) {
		this.nroSucursal = nroSucursal;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public List<SucursalUsuarioDto> getListaSucursalesUsuarios() {
		return listaSucursalesUsuarios;
	}

	public void setListaSucursalesUsuarios(List<SucursalUsuarioDto> listaSucursalesUsuarios) {
		this.listaSucursalesUsuarios = listaSucursalesUsuarios;
	}

	public List<SucursalUsuarioRelacionadoDto> getListaSucursalesUsuariosRelacionados() {
		return listaSucursalesUsuariosRelacionados;
	}

	public void setListaSucursalesUsuariosRelacionados(
			List<SucursalUsuarioRelacionadoDto> listaSucursalesUsuariosRelacionados) {
		this.listaSucursalesUsuariosRelacionados = listaSucursalesUsuariosRelacionados;
	}

	public List<SucursalUsuarioRelacionadoDto> getListaSUfiltrado() {
		return listaSUfiltrado;
	}

	public void setListaSUfiltrado(List<SucursalUsuarioRelacionadoDto> listaSUfiltrado) {
		this.listaSUfiltrado = listaSUfiltrado;
	}

	public List<UsuarioConsolidadorDto> getListaUsuariosConsolidadores() {
		return listaUsuariosConsolidadores;
	}

	public void setListaUsuariosConsolidadores(List<UsuarioConsolidadorDto> listaUsuariosConsolidadores) {
		this.listaUsuariosConsolidadores = listaUsuariosConsolidadores;
	}

	public UsuarioConsolidadorDto getUsuarioConsolidadorSeleccionado() {
		return usuarioConsolidadorSeleccionado;
	}

	public void setUsuarioConsolidadorSeleccionado(UsuarioConsolidadorDto usuarioConsolidadorSeleccionado) {
		this.usuarioConsolidadorSeleccionado = usuarioConsolidadorSeleccionado;
	}
	
}
