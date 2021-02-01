package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormulariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaReporteDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaAsociacionComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;

@ManagedBean(name = "formulario110Anexo702Controller")
@ViewScoped
public class Formulario110Anexo702Controller implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(Formulario110Anexo702Controller.class);
	private static final long serialVersionUID = 1L;
	private List<ComprasCvDto> vListaAsociadas = new ArrayList<>();
	private List<ComprasCvDto> vListaComprasFueraRango = new ArrayList<>();
	private List<FormularioCvDto> vListaFormularios = new ArrayList<>();

	private List<ComprasCvDto> vListaDisponibles = new ArrayList<>();
	private List<ComprasCvDto> vListaSeleccionables = new ArrayList<>();
	private List<ComprasCvDto> vListaSeleccioandosSinGuardar = new ArrayList<>();
	private List<ComprasCvDto> vListaDesseleccionadosSinGuardar = new ArrayList<>();

	private Integer CantidadComprasIpn = 0;
	private Integer CantidadComprasOtras = 0;
	private Integer CantidadComprasSieteRg = 0;
	private BigDecimal vSumaTotalMontosIpn = new BigDecimal("0");
	private BigDecimal vSumaTotalMontosOtras = new BigDecimal("0");
	private BigDecimal vSumaTotalMontosSieteRg = new BigDecimal("0");
	private BigDecimal vPagoaCuentaIpn = new BigDecimal("0");
	private BigDecimal vPagoaCuentaOtras = new BigDecimal("0");
	private BigDecimal vPagoaCuentaSieteRg = new BigDecimal("0");
	private BigDecimal vPagoaCuentaCf = new BigDecimal("0");

	private BigDecimal vSumaTotalMontos;
	private Integer cantidadCompras;
	private BigDecimal vPagoaCuenta;
	private boolean verDetalle;
	private FormularioCvDto formulario;
	private FormularioCvDto formularioNuevo;
	private FormularioCvDto formularioAuxiliar;
	private int mesPeriodo = 0;
	private int anioPeriodo = 2020;
	private DepartamentoDto depFormulario;
	private List<DepartamentoDto> listaDepartamentos = new ArrayList<>();
	private List<ClasificadorDto> listaTipoDocumento = new ArrayList<>();

	private LazyDataModel<ComprasCvDto> facturasComprasLazy;
	private ComprasCvDto compraEdicion = new ComprasCvDto();
	private List<ComprasCvDto> facturasAscSelect = new ArrayList<>();
	private List<String> vComprasIds = new ArrayList<>();

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;
	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;

	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@PostConstruct
	public void init() {
		Calendar fecha = new GregorianCalendar();
		LOG.info("fecha actual {}", fecha);
		anioPeriodo = fecha.get(Calendar.YEAR);
		mesPeriodo = fecha.get(Calendar.MONTH) + 1;
		int vAuxMes = mesPeriodo;
		switch (vAuxMes) {
		case 2:
		case 3:
		case 4:
			mesPeriodo = 3;
			break;
		case 5:
		case 6:
		case 7:
			mesPeriodo = 6;
			break;
		case 8:
		case 9:
		case 10:
			mesPeriodo = 9;
			break;
		case 11:
		case 12:
		case 1:
			mesPeriodo = 12;
			break;

		}
		cargarFormularios(mesPeriodo, anioPeriodo);
		setVerDetalle(false);
		nuevoFormulario();
		listaDepartamentos = clientesRestController.getClienteDepartamentos().getListaDepartamentoActivos();
		listaTipoDocumento = clientesRestController.getClienteRestClasificadores()
				.obtenerListaClasificadoresPorGrupo(ParametrosFRVCC.ID_AGRUPADOR_TIPO_DOCUMENTO);
		cargarComprasDisponibles();
	}

	public void cargarComprasDisponibles() {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

		long vCantidad = clientesRestController.getClienteRestCompras().obtenerTotalCompras(pFilters);
		if (vCantidad > 0) {
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "fechaFactura");
			pFilters.put("order", "ASC");
			RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters);
			if (vRespuesta.isOk()) {
				vListaDisponibles = vRespuesta.getComprasResponse();
			} else {
				mensajesBean.addMensajes(vRespuesta);
				vListaDisponibles = new ArrayList<>();
			}
		} else {
			vListaDisponibles = new ArrayList<>();
		}
	}

	public void cargarFormularios(int mesPeriodo, int gestionPeriodo) {
		vListaFormularios = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, gestionPeriodo);
		filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, mesPeriodo);
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F702);
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
		pFilters.put("order_by", "numeroOrden");
		pFilters.put("order", "DESC");
		RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios()
				.obtenerFormulariosActivos(pFilters);
		if (lista.isOk()) {
			vListaFormularios = lista.getFormulariosReponse();
		} else {
			// mensajesBean.addMensajes(lista);
		}
	}

	public void cargarFormularios() {
		setVerDetalle(false);
		vListaFormularios = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.anioPeriodo);
		if (mesPeriodo != 13) {
			filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, this.mesPeriodo);
		}
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F702);
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
		pFilters.put("order_by", "numeroOrden");
		pFilters.put("order", "DESC");
		RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios()
				.obtenerFormulariosActivos(pFilters);
		if (lista.isOk()) {
			vListaFormularios = lista.getFormulariosReponse();
		} else {
			mensajesBean.addMensajes(lista);
		}

	}

	public void nuevoFormulario() {
		formularioNuevo = new FormularioCvDto();
		formularioNuevo.setAnioPeriodo(this.anioPeriodo);
		formularioNuevo.setMesPeriodo(this.mesPeriodo);
		formularioNuevo.setNitCi(Long.parseLong(contextoModel.getNitCi()));
		formularioNuevo.getDatosEspecificos().setNumeroDocumento(contextoModel.getNumeroDocumentoIdentidad());
		formularioNuevo.setTipoUsoId(ParametrosFRVCC.TIPO_USO_FORMULARIO_ANEXO);
		formularioNuevo.setNumeroSucursal(0);
	}

	public static BigDecimal percentaje(BigDecimal base, BigDecimal pct) {
		return ((new BigDecimal(100)).divide(pct, 4, RoundingMode.HALF_UP).multiply(base)).setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
	}

	public void adicionarFormulario() {
		if (getvListaFormularios() == null) {
			setvListaFormularios(new ArrayList<FormularioCvDto>());
		}
		FormularioCvDto vNuevoFormulario = getFormularioNuevo();
		guardarDescripciondocumento(vNuevoFormulario);
		guardarDescripcionDepartamento(vNuevoFormulario);
		if (vNuevoFormulario.getDatosEspecificos().getTipoDocumento()
				.equals(ParametrosFRVCC.TIPO_DOC_CLIENTE_COMPRA_CI)) {
			guardarDescripcionLugarExpedicion(vNuevoFormulario);
		}
		vNuevoFormulario.setId(UUID.randomUUID().toString());
		vListaFormularios = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, vNuevoFormulario.getAnioPeriodo());
		if (mesPeriodo != 13) {
			filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, vNuevoFormulario.getMesPeriodo());
		}
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F702);
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios()
				.obtenerFormulariosActivos(filtros.getFiltros());

		if (lista.getFormulariosReponse().isEmpty()) {

			ContextoJSF contexto = new ContextoJSF();

			vNuevoFormulario.setTipoFormularioId(ParametrosFRVCC.TIPO_FORMULARIO_110_ANEXO_F702);
			vNuevoFormulario.setUsuarioRegistro(contexto.getUsuario().getUsuarioId());
			vNuevoFormulario.setUsuarioUltimaModificacion(contexto.getUsuario().getUsuarioId());
			vNuevoFormulario.setIfc(contexto.getUsuario().getPersonaId());
			vNuevoFormulario.setNitCi(Long.parseLong(contexto.getUsuario().getNit()));
			vNuevoFormulario.setFechaPresentacion(LocalDate.now());
			vNuevoFormulario.setRazonSocial(contexto.getUsuario().getNombreCompleto());
			vNuevoFormulario.setTipoPresentacionId(ParametrosFRVCC.TIPO_PRESENTACION_FORMULARIO_ORIGINAL);
			vNuevoFormulario.setPeriodicidadId(ParametrosFRVCC.PERIODICIDAD_TRIMESTRAL);
			vNuevoFormulario.setCantidadPeriodicidad(ParametrosFRVCC.CANTIDAD_PERIODICIDAD_FORMULARIO_TRIMESTRAL);
			vNuevoFormulario.setNombreFormulario("F110_" + vNuevoFormulario.getAnioPeriodo() + "_0"
					+ vNuevoFormulario.getMesPeriodo() + "_" + vNuevoFormulario.getNitCi() + "_702");
			ResultadoGenericoDto<String> rep = clientesRestController.getClienteRestFormularios()
					.guardarFormularioNuevo(vNuevoFormulario);
			this.getvListaFormularios().add(vNuevoFormulario);
			this.nuevoFormulario();
			mensajesBean.addMensajes(rep);
		} else {
			this.nuevoFormulario();
			RequestContext.getCurrentInstance()
					.execute("toastr.error('Ya cuenta con un formulario para el periodo introducido', 'Error')");
		}
		cargarFormularios();
	}

	public void cargarListaComprasRegistradas() {

		try {
			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

			LazyConsultaAsociacionComprasModel lazyConsulAsModel=new LazyConsultaAsociacionComprasModel(new ArrayList<ComprasCvDto>(), pFilters, this.clientesRestController.getClienteRestCompras(), true,mensajesBean);
			this.setFacturasComprasLazy(lazyConsulAsModel);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			// RequestContext.getCurrentInstance()
			// .execute("toastr.error('Ocurrio un error al obtener facturas.', 'Error!')");

		}

	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_COMPRA_REGISTRADO);

		ContextoJSF contexto = new ContextoJSF();
		if (contexto.getUsuario() != null) {
			filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
			filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");

			filtros.adicionar("creditoFiscal", OperadorCriteria.MAYOR, "0");
			filtros.adicionar("estadoCompraId", OperadorCriteria.DISTINTO, "AN");
//			filtros.getFiltros().put("order_by", "fechaFactura");
//			filtros.getFiltros().put("order", "ASC");
		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
		}

		return filtros.getFiltros();
	}

	public LocalDate fechaFacturaValidoHasta() {

		LocalDate periodoActual = LocalDate.of(this.formulario.getAnioPeriodo(), this.formulario.getMesPeriodo(), 1);
		LOG.info("fecha valida hasta {}", periodoActual.withDayOfMonth(periodoActual.lengthOfMonth()));
		return periodoActual.withDayOfMonth(periodoActual.lengthOfMonth());
	}

	public LocalDate fechaFacturaValidaDesde() {
		LocalDate fechaFacturaDesde = LocalDate.of(this.formulario.getAnioPeriodo(),
				this.formulario.getMesPeriodo() , 1);
		LOG.info("fecha valida desde {}",
				fechaFacturaDesde.plusMonths(-Integer.valueOf(formulario.getCantidadPeriodicidad())));
		return fechaFacturaDesde.plusMonths(-Integer.valueOf(formulario.getCantidadPeriodicidad())+1);

	}

	public void actualizartotales() {
		vSumaTotalMontosIpn = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN))
				.map(factura -> factura.getImporteBaseCf()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		vPagoaCuentaIpn = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN))
				.map(factura -> factura.getCreditoFiscal()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		CantidadComprasIpn = (int) vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN)).count();

		vSumaTotalMontosOtras = vListaAsociadas.stream()
				.filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
				.map(factura -> factura.getImporteBaseCf()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		vPagoaCuentaOtras = vListaAsociadas.stream()
				.filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
				.map(factura -> factura.getCreditoFiscal()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		CantidadComprasOtras = (int) vListaAsociadas.stream()
				.filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL)).count();
		vSumaTotalMontosSieteRg = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG))
				.map(factura -> factura.getImporteTotalCompra()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		vPagoaCuentaSieteRg = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG))
				.map(factura -> factura.getCreditoFiscal()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		;
		CantidadComprasSieteRg = (int) vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId() != null)
				.filter(fa -> fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG)).count();

		vPagoaCuentaCf = vPagoaCuentaOtras.add(vPagoaCuentaIpn);
		formulario.setTotalComprasCfIpn(vSumaTotalMontosIpn);
		formulario.setTotalComprasCfOtras(vSumaTotalMontosOtras);
		formulario.setTotalComprasSdCf(vSumaTotalMontosSieteRg);
	}

	public void actualizartotalesOnFormularioRectificado() {
		vSumaTotalMontosIpn = formulario.getTotalComprasCfIpn().setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaIpn = formulario.getDeterminacionPagoCfIpn().setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasIpn = formulario.getCantidadComprasCfIpn();

		vSumaTotalMontosOtras = formulario.getTotalComprasCfOtras().setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaOtras = formulario.getDeterminacionPagoCfOtras().setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasOtras = formulario.getCantidadComprasCfOtras();
		vSumaTotalMontosSieteRg = formulario.getTotalComprasSdCf().setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaSieteRg = formulario.getDeterminacionPagoSdCf().setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasSieteRg = formulario.getCantidadComprasSdCf();

		vPagoaCuentaCf = vPagoaCuentaOtras.add(vPagoaCuentaIpn);
		formulario.setTotalComprasCfIpn(vSumaTotalMontosIpn);
		formulario.setTotalComprasCfOtras(vSumaTotalMontosOtras);
		formulario.setTotalComprasSdCf(vSumaTotalMontosSieteRg);
	}

	public void cargarDetalleFormulario(FormularioCvDto pFormulario) {
		this.formulario = pFormulario;
		cargarComprasDisponibles();
		if (this.formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO)) {
			this.formulario.setFechaPresentacion(LocalDate.now());
		}

		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("formularioId", OperadorCriteria.IGUAL, pFormulario.getId());
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		if (formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO)) {
			filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_COMPRA_ASOCIADO);
		}
		filtros.getFiltros().put("order_by", "fechaFactura");
		filtros.getFiltros().put("order", "ASC");
		List<ComprasCvDto> vLista = new ArrayList<>();
		if(formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECTIFICADO)) {
			ComprasCvDto vCompra=new ComprasCvDto();
			RespuestaComprasDto vCompras=new RespuestaComprasDto();
			for (String compraId : formulario.getCompras()) {
				vCompras=clientesRestController.getClienteRestHistoriales().obtenerComprasHistoricas(compraId);
				if(vCompras.getComprasResponse().size()>0)
				{
					Optional<ComprasCvDto> vOpCompra=vCompras.getComprasResponse().stream().filter(c->c.getEstadoUsoId().equals("ASC") && c.getFormularioId().equals(formulario.getId())).findFirst();
					if(vOpCompra.isPresent())
						vLista.add(vOpCompra.get());
				}

			}
			vListaAsociadas=vLista;
			actualizartotalesOnFormularioRectificado();
		} else {
			long vCantidad = clientesRestController.getClienteRestCompras().obtenerTotalCompras(filtros.getFiltros());
			if (vCantidad > 0) {
				LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
				pFilters.put("limit", vCantidad);
				pFilters.put("offset", 0);

				List<ComprasCvDto> vLista2 = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters)
						.getComprasResponse();
				vListaAsociadas = new ArrayList<>();
				vListaAsociadas = vLista2;
			} else {
				vListaAsociadas = new ArrayList<>();
			}
			actualizartotales();
		}
		setVerDetalle(true);

		generarReporteF110();

	}

	public void eliminarformulario() {
		if (vListaAsociadas.isEmpty()) {
			if (this.getvListaFormularios().stream().anyMatch(factura -> factura.getId() == formulario.getId())) {
				long vContadorInicial = this.getvListaFormularios().size();
				this.getvListaFormularios().remove(formulario);
				if (vContadorInicial > this.getvListaFormularios().size()) {
					RequestContext.getCurrentInstance().execute("toastr.info('Formulario eliminado', 'Información')");
				} else {
					RequestContext.getCurrentInstance()
							.execute("toastr.warning('Formulario no se pudo eliminar', 'Advertencia')");
				}
			}
		} else
			RequestContext.getCurrentInstance()
					.execute("toastr.warning('El formulario contiene facturas', 'Advertencia')");
	}

	public void declarar() {
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestFormularios()
				.declararFormulario(formulario, contextoModel.getCodigoAdministracion());
		if (vRespuesta.isOk()) {
			cargarFormularios();
			this.formularioAuxiliar = clientesRestController.getClienteRestFormularios()
					.obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
			this.formulario = this.formularioAuxiliar;
			generarReporteF110();
			RequestContext.getCurrentInstance().execute("controlClick('formPrincipal:reportePdf:btnVisorPdf');");
//			RequestContext.getCurrentInstance().execute("toastr.info('Formulario Declarado', 'Información')");
			mensajesBean.addMensajes(vRespuesta);
		} else {
//			RequestContext.getCurrentInstance().execute("toastr.warning('No se pudo Completar la declaracion', 'Advertencia')");
			mensajesBean.addMensajes(vRespuesta);
		}

	}

	public void rectificar() {
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestFormularios()
				.rectificarFormulario(formulario, contextoModel.getCodigoAdministracion());
		if (vRespuesta.isOk()) {
			cargarFormularios();
			this.formularioAuxiliar = clientesRestController.getClienteRestFormularios()
					.obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
			this.formulario.setNumeroOrden(formularioAuxiliar.getNumeroOrden());
			generarReporteF110();
			mensajesBean.addMensajes(vRespuesta);
		} else {
			mensajesBean.addMensajes(vRespuesta);
		}

	}

	public void cancelarDeclaracion() {
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestFormularios()
				.cancelarDeclaracionFormulario(formulario);
		mensajesBean.addMensajes(vRespuesta);
		cargarFormularios();
	}

	public void generarReporteF110() {

		try {
			if (!formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO)) {
				ResultadoGenericoDto<RespuestaReporteDto> vRespuestaReport = clientesRestController
						.getClienteRestFormularios().obtieneReporteF110(this.formulario);
				if (vRespuestaReport.isOk()) {
					reportesController.setRespuestaBase64(vRespuestaReport.getResultadoObjeto().getArchivoBase64());
					// mensajesBean.addMensajes(vRespuestaReport);
				} else {
					mensajesBean.addMensajes(vRespuestaReport);

				}
			}

		} catch (Exception e) {

			RequestContext.getCurrentInstance().execute("toastr.error('Servicio de reporte no disponible', 'Error')");

		}

	}

	public void cambiarFechaPresentacionFormulario() {
		desasociarComprasfueraRango();
		ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestFormularios()
				.guardarCambiosFormulario(formulario);
		mensajesBean.addMensajes(vResp);

	}

	public void desasociarComprasfueraRango() {
		int contador = 0;
		List<ComprasCvDto> listaComprasAuxiliar = vListaComprasFueraRango;
		if (vListaAsociadas.size() > 0) {

			for (ComprasCvDto compra : listaComprasAuxiliar) {
				contador = contador + 1;
				vComprasIds.add(compra.getId());

			}
			if (clientesRestController.getClienteRestFormularios()
					.desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {
				RequestContext.getCurrentInstance()
						.execute("toastr.info('Se desmarcaron " + contador + " Facturas', 'Informacion')");
				actualizartotales();
				formulario.setTotalCompras(vSumaTotalMontos);
			}
			this.vListaAsociadas = new ArrayList<>();
			vComprasIds.clear();

		}

	}

	public void modificarCamposFormulario() {
		try {
			ResultadoGenericoDto<String> vResp=clientesRestController.getClienteRestFormularios().guardarCambiosFormulario(this.formulario);
			mensajesBean.addMensajes(vResp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void cambiarTipoDocumentoLugarExp(){
		guardarDescripciondocumento(this.formulario);
		if(formulario.getDatosEspecificos().getTipoDocumento().equals(ParametrosFRVCC.TIPO_DOCUMENTO)
				&& formulario.getDatosEspecificos().getLugarExpedicion()!=null)
		{
			guardarDescripcionLugarExpedicion(formulario);
		}
		else {
			formulario.getDatosEspecificos().setLugarExpedicion(null);
			formulario.getDatosEspecificos().setDescripcionLugarExpedicion(null);
			formulario.getDatosEspecificos().setComplementoDocumento(null);
		}
		modificarCamposFormulario();
	}

	public void editarCamposCompras(ComprasCvDto pCompra) {
		this.setCompraEdicion(pCompra);
		compraEdicion.setImporteBaseCf(compraEdicion.getImporteTotalCompra());
		vComprasIds.add(compraEdicion.getId());

		try {
			LocalDate fechaValidaHasta = fechaFacturaValidoHasta();
			if (fechaEmisionValida(pCompra.getFechaFactura(), fechaValidaHasta)) {
				if (clientesRestController.getClienteRestFormularios()
						.desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {
					ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestCompras()
							.modificarCompra(compraEdicion);
					if (clientesRestController.getClienteRestFormularios()
							.seleccionarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {

						mensajesBean.addMensajes(vRespuesta);
						if (vRespuesta.isOk()) {
							actualizartotales();
						} else {
							cancelarEdicionCamposCompras();
						}
					}

				}

			} else {
				RequestContext.getCurrentInstance().execute(
						"toastr.warning('La fecha ingresada no corresponde a una fecha valida de seleccion', 'Advertencia!')");
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmFechaEdicion').show()");
			}

			vComprasIds.clear();
		} catch (Exception e) {
			e.printStackTrace();
			vComprasIds.clear();
		}

	}

	public void siCambiarFechaEmision() {
		vComprasIds.add(compraEdicion.getId());
		if (clientesRestController.getClienteRestFormularios()
				.desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {
			ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestCompras()
					.modificarCompra(compraEdicion);
			if (vRespuesta.isOk()) {
				mensajesBean.addMensajes(vRespuesta);
				actualizartotales();
			} else {
				clientesRestController.getClienteRestFormularios().seleccionarComprasFormulario(formulario.getId(),
						vComprasIds);
				mensajesBean.addMensajes(vRespuesta);
				cancelarEdicionCamposCompras();

			}

		}
		vComprasIds.clear();
	}

	public void habilitarFormularioAnexoRectificatorio() {
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestFormularios()
				.habilitarRectificatorioFormularioAnexo(formulario);
		mensajesBean.addMensajes(vRespuesta);
		cargarFormularios();

	}

	public void cancelarEdicionCamposCompras() {
		cargarDetalleFormulario(this.formulario);
	}

	public Boolean fechaEmisionValida(LocalDate fechaEmisionFac, LocalDate fechaHasta) {
		LocalDate fechaValidaDesde = fechaFacturaValidaDesde();
		return (fechaEmisionFac.isAfter(fechaValidaDesde) || fechaEmisionFac.isEqual(fechaValidaDesde))
				&& (fechaEmisionFac.isBefore(fechaHasta) || fechaEmisionFac.isEqual(fechaHasta));
	}

	public void retornaValores() {
		this.formulario = this.formularioAuxiliar;
	}

	public void salvarValores() {
		this.formularioAuxiliar = clientesRestController.getClienteRestFormularios()
				.obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();

		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_COMPRA_ASOCIADO);
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		filtros.adicionar("fechaFactura", OperadorCriteria.MENOR,
				this.formulario.getFechaPresentacion().plusDays(-120));
		filtros.adicionar("formularioId", OperadorCriteria.IGUAL, this.formulario.getId());
		RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras()
				.obtenerCompras(filtros.getFiltros());
		if (vRespuesta.isOk()) {
			this.vListaComprasFueraRango = vRespuesta.getComprasResponse();
		} else {
			this.vListaComprasFueraRango = new ArrayList<>();
			mensajesBean.addMensajes(vRespuesta);
		}

	}

	public void asociarfacturas() {

		vComprasIds.clear();

		if (!vListaSeleccioandosSinGuardar.isEmpty()) {
			for (ComprasCvDto factura : vListaSeleccioandosSinGuardar) {
				vComprasIds.add(factura.getId());
			}
			if (clientesRestController.getClienteRestFormularios()
					.seleccionarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {

				RequestContext.getCurrentInstance().execute("toastr.success('Se adicionaron " +vListaSeleccioandosSinGuardar.size()+" facturas al formulario .', 'Exitoso!')");
				vComprasIds.clear();
				vListaSeleccioandosSinGuardar = new ArrayList<>();
			}

		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('Debe seleccionar facturas para adicionar al formulario', 'Error!')");
		}

	}

	public void adicionarSeleccionados() {
		vListaAsociadas.addAll(facturasAscSelect);
		vListaDisponibles.removeAll(facturasAscSelect);

		for (ComprasCvDto vCompra : facturasAscSelect) {
			if (!vListaDesseleccionadosSinGuardar.stream().anyMatch(c -> c.getId().equals(vCompra.getId()))) {
				vListaSeleccioandosSinGuardar.add(vCompra);
			}
		}
		vListaDesseleccionadosSinGuardar.removeAll(facturasAscSelect);
		facturasAscSelect.clear();
		filtrarComprasPosibles();
		actualizartotales();

	}

	public void filtrarComprasPosibles() {
		LocalDate fechaFacturaHasta = fechaFacturaValidoHasta();
		LocalDate fechaFacturaDesde = fechaFacturaValidaDesde();
		vListaSeleccionables = vListaDisponibles.stream()
				.filter(compra -> compra.getFechaFactura().isBefore(fechaFacturaHasta))
				.filter(c -> c.getFechaFactura().isAfter(fechaFacturaDesde)).collect(Collectors.toList());
	}

	public void desmarcarCompra(ComprasCvDto pFactura) {
		if (!vListaSeleccioandosSinGuardar.stream().anyMatch(c -> c.getId().equals(pFactura.getId()))) {
			vListaDesseleccionadosSinGuardar.add(pFactura);
		}
		vListaAsociadas.removeIf(compra -> compra.getId().equals(pFactura.getId()));
		vListaSeleccioandosSinGuardar.removeIf(compra -> compra.getId().equals(pFactura.getId()));
		vListaDisponibles.add(pFactura);
		filtrarComprasPosibles();
		actualizartotales();
	}

	public void desmarcarTodasLasCompras() {

		for (ComprasCvDto vCompra : vListaAsociadas) {
			if (!vListaSeleccioandosSinGuardar.stream().anyMatch(c -> c.getId().equals(vCompra.getId()))) {
				vListaDesseleccionadosSinGuardar.add(vCompra);
			}
		}
		vListaSeleccioandosSinGuardar.removeAll(vListaAsociadas);
		vListaDisponibles.addAll(vListaAsociadas);
		vListaAsociadas.clear();
		filtrarComprasPosibles();
		actualizartotales();
	}

	public void persistirCambios() {
		if(!vListaDesseleccionadosSinGuardar.isEmpty())
		{
			devolverFacturas();

		}
		if(!vListaSeleccioandosSinGuardar.isEmpty()) {
			asociarfacturas();
		}

	}

	public void devolverFacturas() {
		vComprasIds.clear();
		int contador = 0;
		if (vListaDesseleccionadosSinGuardar.size() > 0) {

			for (ComprasCvDto compra : vListaDesseleccionadosSinGuardar) {
				contador = contador + 1;
				vComprasIds.add(compra.getId());

			}
			if (clientesRestController.getClienteRestFormularios()
					.desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {

				RequestContext.getCurrentInstance()
						.execute("toastr.info('Se desmarcaron " + contador + " Facturas', 'Información')");
				this.vListaDesseleccionadosSinGuardar = new ArrayList<>();
				actualizartotales();

			}

		}
		this.vComprasIds.clear();

	}
	public void guardarDescripciondocumento(FormularioCvDto formulario) {
		Optional<ClasificadorDto> clasificador= listaTipoDocumento.stream()
				.filter(s -> formulario.getDatosEspecificos().getTipoDocumento().equals(s.getCodigoClasificador()))
				.findFirst();
		formulario.getDatosEspecificos().setDescripcionTipoDocumento(clasificador.get().getDescripcion());
	}
	public void guardarDescripcionDepartamento(FormularioCvDto formulario) {
		Optional<DepartamentoDto> departamento= listaDepartamentos.stream()
				.filter(s ->formulario.getLugarDepartamento().shortValue()==(s.getDepartamentoId()))
				.findFirst();
		formulario.getDatosEspecificos().setDepartamentoDescripcion(departamento.get().getNombre());
	}
	public void guardarDescripcionLugarExpedicion(FormularioCvDto formulario) {
		Optional<DepartamentoDto> departamento= listaDepartamentos.stream()
				.filter(s -> Integer.parseInt(formulario.getDatosEspecificos().getLugarExpedicion())==(s.getDepartamentoId()))
				.findFirst();
		formulario.getDatosEspecificos().setDescripcionLugarExpedicion(departamento.get().getNombre());
	}

	public List<ComprasCvDto> getvListaAsociadas() {
		return vListaAsociadas;
	}

	public void setvListaAsociadas(List<ComprasCvDto> vListaAsociadas) {
		this.vListaAsociadas = vListaAsociadas;
	}

	public BigDecimal getvSumaTotalMontos() {
		return vSumaTotalMontos;
	}

	public void setvSumaTotalMontos(BigDecimal vSumaTotalMontos) {
		this.vSumaTotalMontos = vSumaTotalMontos;
	}

	public BigDecimal getvPagoaCuenta() {
		return vPagoaCuenta;
	}

	public void setvPagoaCuenta(BigDecimal vPagoaCuenta) {
		this.vPagoaCuenta = vPagoaCuenta;
	}

	public List<FormularioCvDto> getvListaFormularios() {
		return vListaFormularios;
	}

	public void setvListaFormularios(List<FormularioCvDto> vListaFormularios) {
		this.vListaFormularios = vListaFormularios;
	}

	public boolean isVerDetalle() {
		return verDetalle;
	}

	public void setVerDetalle(boolean verDetalle) {
		this.verDetalle = verDetalle;
	}

	public FormularioCvDto getFormulario() {
		return formulario;
	}

	public void setFormulario(FormularioCvDto formulario) {
		this.formulario = formulario;
	}

	public FormularioCvDto getFormularioNuevo() {
		return formularioNuevo;
	}

	public void setFormularioNuevo(FormularioCvDto formularioNuevo) {
		this.formularioNuevo = formularioNuevo;
	}

	public int getMesPeriodo() {
		return mesPeriodo;
	}

	public void setMesPeriodo(int mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}

	public int getAnioPeriodo() {
		return anioPeriodo;
	}

	public void setAnioPeriodo(int anioPeriodo) {
		this.anioPeriodo = anioPeriodo;
	}

	public List<DepartamentoDto> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<DepartamentoDto> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public Integer getCantidadCompras() {
		return cantidadCompras;
	}

	public void setCantidadCompras(Integer cantidadCompras) {
		this.cantidadCompras = cantidadCompras;
	}

	public ReportesController getReportesController() {
		return reportesController;
	}

	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}

	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}

	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
	}

	public LazyDataModel<ComprasCvDto> getFacturasComprasLazy() {
		return facturasComprasLazy;
	}

	public void setFacturasComprasLazy(LazyDataModel<ComprasCvDto> facturasComprasLazy) {
		this.facturasComprasLazy = facturasComprasLazy;
	}

	public List<ComprasCvDto> getFacturasAscSelect() {
		return facturasAscSelect;
	}

	public void setFacturasAscSelect(List<ComprasCvDto> facturasAscSelect) {
		this.facturasAscSelect = facturasAscSelect;
	}

	public FormularioCvDto getFormularioAuxiliar() {
		return formularioAuxiliar;
	}

	public void setFormularioAuxiliar(FormularioCvDto formularioAuxiliar) {
		this.formularioAuxiliar = formularioAuxiliar;
	}

	public List<ComprasCvDto> getvListaComprasFueraRango() {
		return vListaComprasFueraRango;
	}

	public void setvListaComprasFueraRango(List<ComprasCvDto> vListaComprasFueraRango) {
		this.vListaComprasFueraRango = vListaComprasFueraRango;
	}

	public ComprasCvDto getCompraEdicion() {
		return compraEdicion;
	}

	public void setCompraEdicion(ComprasCvDto compraEdicion) {
		this.compraEdicion = compraEdicion;
	}

	public DepartamentoDto getDepFormulario() {
		return depFormulario;
	}

	public void setDepFormulario(DepartamentoDto depFormulario) {
		this.depFormulario = depFormulario;
	}

	public List<ClasificadorDto> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<ClasificadorDto> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
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

	public Integer getCantidadComprasIpn() {
		return CantidadComprasIpn;
	}

	public void setCantidadComprasIpn(Integer cantidadComprasIpn) {
		CantidadComprasIpn = cantidadComprasIpn;
	}

	public Integer getCantidadComprasOtras() {
		return CantidadComprasOtras;
	}

	public void setCantidadComprasOtras(Integer cantidadComprasOtras) {
		CantidadComprasOtras = cantidadComprasOtras;
	}

	public Integer getCantidadComprasSieteRg() {
		return CantidadComprasSieteRg;
	}

	public void setCantidadComprasSieteRg(Integer cantidadComprasSieteRg) {
		CantidadComprasSieteRg = cantidadComprasSieteRg;
	}

	public BigDecimal getvSumaTotalMontosIpn() {
		return vSumaTotalMontosIpn;
	}

	public void setvSumaTotalMontosIpn(BigDecimal vSumaTotalMontosIpn) {
		this.vSumaTotalMontosIpn = vSumaTotalMontosIpn;
	}

	public BigDecimal getvSumaTotalMontosOtras() {
		return vSumaTotalMontosOtras;
	}

	public void setvSumaTotalMontosOtras(BigDecimal vSumaTotalMontosOtras) {
		this.vSumaTotalMontosOtras = vSumaTotalMontosOtras;
	}

	public BigDecimal getvSumaTotalMontosSieteRg() {
		return vSumaTotalMontosSieteRg;
	}

	public void setvSumaTotalMontosSieteRg(BigDecimal vSumaTotalMontosSieteRg) {
		this.vSumaTotalMontosSieteRg = vSumaTotalMontosSieteRg;
	}

	public BigDecimal getvPagoaCuentaIpn() {
		return vPagoaCuentaIpn;
	}

	public void setvPagoaCuentaIpn(BigDecimal vPagoaCuentaIpn) {
		this.vPagoaCuentaIpn = vPagoaCuentaIpn;
	}

	public BigDecimal getvPagoaCuentaOtras() {
		return vPagoaCuentaOtras;
	}

	public void setvPagoaCuentaOtras(BigDecimal vPagoaCuentaOtras) {
		this.vPagoaCuentaOtras = vPagoaCuentaOtras;
	}

	public BigDecimal getvPagoaCuentaSieteRg() {
		return vPagoaCuentaSieteRg;
	}

	public void setvPagoaCuentaSieteRg(BigDecimal vPagoaCuentaSieteRg) {
		this.vPagoaCuentaSieteRg = vPagoaCuentaSieteRg;
	}

	public BigDecimal getvPagoaCuentaCf() {
		return vPagoaCuentaCf;
	}

	public void setvPagoaCuentaCf(BigDecimal vPagoaCuentaCf) {
		this.vPagoaCuentaCf = vPagoaCuentaCf;
	}

	public List<ComprasCvDto> getvListaDisponibles() {
		return vListaDisponibles;
	}

	public void setvListaDisponibles(List<ComprasCvDto> vListaDisponibles) {
		this.vListaDisponibles = vListaDisponibles;
	}

	public List<ComprasCvDto> getvListaSeleccionables() {
		return vListaSeleccionables;
	}

	public void setvListaSeleccionables(List<ComprasCvDto> vListaSeleccionables) {
		this.vListaSeleccionables = vListaSeleccionables;
	}

	public List<ComprasCvDto> getvListaSeleccioandosSinGuardar() {
		return vListaSeleccioandosSinGuardar;
	}

	public void setvListaSeleccioandosSinGuardar(List<ComprasCvDto> vListaSeleccioandosSinGuardar) {
		this.vListaSeleccioandosSinGuardar = vListaSeleccioandosSinGuardar;
	}

	public List<ComprasCvDto> getvListaDesseleccionadosSinGuardar() {
		return vListaDesseleccionadosSinGuardar;
	}

	public void setvListaDesseleccionadosSinGuardar(List<ComprasCvDto> vListaDesseleccionadosSinGuardar) {
		this.vListaDesseleccionadosSinGuardar = vListaDesseleccionadosSinGuardar;
	}

	public List<String> getvComprasIds() {
		return vComprasIds;
	}

	public void setvComprasIds(List<String> vComprasIds) {
		this.vComprasIds = vComprasIds;
	}

}
