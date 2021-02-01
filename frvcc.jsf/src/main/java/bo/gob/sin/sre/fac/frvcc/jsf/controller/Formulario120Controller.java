package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.caco.dto.SucursalDto;
import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.beneficiario.BeneficiarioPersonaRivDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaAsociacionComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServiciosBeneficiarioRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(name = "formulario120Controller")
@ViewScoped
public class Formulario120Controller implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(Formulario120Controller.class);


    private List<ComprasCvDto> vListaAsociadas = new ArrayList<>();
    private List<ComprasCvDto> vListaComprasFueraRango = new ArrayList<>();
    private List<FormularioCvDto> vListaFormularios = new ArrayList<>();


    private List<ComprasCvDto> vListaDisponibles = new ArrayList<>();
    private List<ComprasCvDto> vListaSeleccionables = new ArrayList<>();
    private List<ComprasCvDto> vListaSeleccioandosSinGuardar = new ArrayList<>();
    private List<ComprasCvDto> vListaDesseleccionadosSinGuardar = new ArrayList<>();


    private Integer CantidadComprasOtras;

    private BigDecimal vSumaTotalMontosOtras;
    private BigDecimal vPagoaCuentaOtras;
    private BigDecimal vPagoaCuentaCf;

    private boolean verDetalle;
    private boolean nitEmpleadorValido = false;
    private FormularioCvDto formulario;
    private FormularioCvDto formularioNuevo;
    private FormularioCvDto formularioAuxiliar;
    private int mesPeriodo = 0;
    private int anioPeriodo = 2020;
    private DepartamentoDto depFormulario;
    private List<DepartamentoDto> listaDepartamentos = new ArrayList<>();
    private List<ClasificadorDto> listaTipoDocumento = new ArrayList<>();

    private BeneficiarioPersonaRivDto beneficiario;

    private LazyDataModel<ComprasCvDto> facturasComprasLazy;
    private ComprasCvDto compraEdicion = new ComprasCvDto();
    private List<ComprasCvDto> facturasAscSelect = new ArrayList<>();
    private List<String> vComprasIds = new ArrayList<>();

    private LocalDate minFechaPresentacion;
    private LocalDate maxFechaPresentacion;


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
        LocalDate fecha = LocalDate.now();
        anioPeriodo = fecha.getYear();
        mesPeriodo = fecha.getMonthValue();
        this.beneficiario = clientesRestController.getClienteBeneficiarioRestClient().getBeneficiario(contextoModel.getUsuario().getPersonaId());
        cargarFormularios(mesPeriodo, anioPeriodo);
        setVerDetalle(false);
        listaDepartamentos = clientesRestController.getClienteDepartamentos().getListaDepartamentoActivos();
        listaTipoDocumento = clientesRestController.getClienteRestClasificadores().obtenerListaClasificadoresPorGrupo(ParametrosFRVCC.ID_AGRUPADOR_TIPO_DOCUMENTO);


    }

    public void cargarComprasDisponibles() {
        LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

        long vCantidad = clientesRestController.getClienteRestCompras().obtenerTotalCompras(pFilters);
        if (vCantidad > 0) {
            pFilters.put("limit", vCantidad);
            pFilters.put("offset", 0);
            pFilters.put("order_by", "fechaFactura");
            pFilters.put("order", "ASC");
            RespuestaComprasDto vRespuesta = clientesRestController
                    .getClienteRestCompras()
                    .obtenerCompras(pFilters);
            if (vRespuesta.isOk()) {
                vListaDisponibles = vRespuesta.getComprasResponse().stream().filter(x -> verificarSector(x.getTipoSectorId())).collect(Collectors.toList());
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
        filtros.adicionar("nitCi", OperadorCriteria.IGUAL, this.beneficiario.getPersonaBeneficiario().getNumeroDocumento());

        filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_111);
        filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");

        LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
        pFilters.put("order_by", "mesPeriodo");
        pFilters.put("order", "ASC");
        System.out.println(pFilters);
        RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
        if (lista.isOk()) {
            vListaFormularios = lista.getFormulariosReponse();
        } else {
            vListaFormularios = new ArrayList<>();
            LOG.info("mensaje resultado vacio {} ", lista.getMensajes());
        }
    }

    public void cargarFormulariosPeriodoSeleccionado() {
        setVerDetalle(false);
        vListaFormularios = new ArrayList<>();
        FiltroCriteria filtros = new FiltroCriteria();
        filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.anioPeriodo);
        if (mesPeriodo != 13) {
            filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, this.mesPeriodo);
        }
        filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_111);
        filtros.adicionar("nitCi", OperadorCriteria.IGUAL, this.beneficiario.getPersonaBeneficiario().getNumeroDocumento());
        filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
        LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
        pFilters.put("order_by", "mesPeriodo");
        pFilters.put("order", "ASC");
        RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
        if (lista.isOk()) {
            vListaFormularios = lista.getFormulariosReponse();
        } else {
            vListaFormularios = new ArrayList<>();
            mensajesBean.addMensajes(lista);
        }
    }

    public BeneficiarioPersonaRivDto getBeneficiario() {
        return beneficiario;
    }

    public void nuevoFormulario() {
        formularioNuevo = new FormularioCvDto();
        formularioNuevo.setAnioPeriodo(this.anioPeriodo);
        formularioNuevo.setMesPeriodo(this.mesPeriodo);
        formularioNuevo.setNitCi(Long.valueOf(this.beneficiario.getPersonaBeneficiario().getNumeroDocumento()));
        formularioNuevo.setRazonSocial(this.beneficiario.getPersonaBeneficiario().getNombreCompleto());
        formularioNuevo.setDireccion(this.beneficiario.getDireccion());
        formularioNuevo.setCodigoDependiente(this.beneficiario.getCodigoPersona());
        formularioNuevo.setIfc(this.beneficiario.getPersonaId());
        formularioNuevo.setTipoUsoId(this.beneficiario.getTipoBeneficiarioId());
        formularioNuevo.setTipoPresentacionId(ParametrosFRVCC.TIPO_PRESENTACION_FORMULARIO_ORIGINAL);
        formularioNuevo.setPeriodicidadId(ParametrosFRVCC.PERIODICIDAD_MENSUAL);
        formularioNuevo.setCantidadPeriodicidad(ParametrosFRVCC.CANTIDAD_PERIODICIDAD_FORMULARIO_DIARIA);


        formularioNuevo.setNumeroSucursal(0);
        formularioNuevo.setTipoFormularioId(ParametrosFRVCC.TIPO_FORMULARIO_111);

        this.setMinFechaPresentacion(LocalDate.of(this.formularioNuevo.getMesPeriodo() == 12 ? this.anioPeriodo + 1 : this.anioPeriodo,
                 this.formularioNuevo.getMesPeriodo() == 12 ? 1 : this.formularioNuevo.getMesPeriodo() + 1,
                1));

        this.setMaxFechaPresentacion(LocalDate.of(this.mesPeriodo == 12 ? this.anioPeriodo + 1 : this.anioPeriodo,
                this.mesPeriodo == 12 ? 1 : this.mesPeriodo + 1,
                5));

    }

    public static BigDecimal percentaje(BigDecimal base, BigDecimal pct) {
        return ((new BigDecimal(100)).divide(pct, 4, RoundingMode.HALF_UP).multiply(base)).setScale(2,
                BigDecimal.ROUND_HALF_EVEN);
    }


    public void consultaContribuyente() {
        ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vDatoscontribuyenteEmpleador = clientesRestController.getClienteRestPadron()
                .consultaContribuyente(this.formulario.getNitEmpleador());

    }


    public void adicionarFormulario() {
        if (getvListaFormularios() == null) {
            setvListaFormularios(new ArrayList<FormularioCvDto>());
        }
        FormularioCvDto vNuevoFormulario = getFormularioNuevo();
        guardarDescripcionDepartamento(vNuevoFormulario);

        vNuevoFormulario.setId(UUID.randomUUID().toString());
        String vChar = vNuevoFormulario.getMesPeriodo() > 9 ? "" : "0";
        vNuevoFormulario.setNombreFormulario(
                "F111_" + vNuevoFormulario.getAnioPeriodo() + "_" + vChar + vNuevoFormulario.getMesPeriodo() + "_"
                        + vNuevoFormulario.getNitCi() + "_" + this.beneficiario.getTipoBeneficiarioId() + "_" + ParametrosFRVCC.PAGO_MANUAL);

        ResultadoGenericoDto<String> rep = clientesRestController.getClienteRestFormularios().guardarFormularioNuevo(vNuevoFormulario);
        if (rep.isOk()) {
            this.getvListaFormularios().add(vNuevoFormulario);
            mensajesBean.addMensajes(rep);
            RequestContext.getCurrentInstance().execute("PF('dlgAgregaFormulario').hide();");
            cargarFormulariosPeriodoSeleccionado();
        } else {
            mensajesBean.addMensajes(rep);
        }

    }

    public void cargarListaComprasRegistradas() {

        try {
            LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

            LazyConsultaAsociacionComprasModel lazyConsulAsModel = new LazyConsultaAsociacionComprasModel(new ArrayList<ComprasCvDto>(), pFilters, this.clientesRestController.getClienteRestCompras(), true, mensajesBean);
            this.setFacturasComprasLazy(lazyConsulAsModel);
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance()
                    .execute("toastr.error('Ocurrio un error al obtener facturas.', 'Error!')");

        }

    }

    private boolean verificarSector(Integer tipoSectorId) {
        if(tipoSectorId == null){
            return true;
        }
        if (!tipoSectorId.equals(0) && !tipoSectorId.equals(0)
                && !tipoSectorId.equals(1) && !tipoSectorId.equals(11) && !tipoSectorId.equals(2) && !tipoSectorId.equals(15) &&
                !tipoSectorId.equals(16) && !tipoSectorId.equals(17) && !tipoSectorId.equals(22) && !tipoSectorId.equals(21)) {
            return false;
        }
        return true;
    }
    private LinkedHashMap<String, Serializable> obtenerCriterios() {
        FiltroCriteria filtros = new FiltroCriteria();

        filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_COMPRA_REGISTRADO);

        ContextoJSF contexto = new ContextoJSF();
        if (contexto.getUsuario() != null) {
            LocalDate fechaMin = LocalDate.of(this.anioPeriodo, this.mesPeriodo, 1);
            LocalDate fechaMax = LocalDate.of(this.anioPeriodo, this.mesPeriodo, fechaMin.getMonth().length(fechaMin.isLeapYear()));

            filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, this.formulario.getNitCi());
            filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
            filtros.adicionar("creditoFiscal", OperadorCriteria.MAYOR, "0");
            filtros.adicionar("estadoCompraId", OperadorCriteria.DISTINTO, "AN");
            filtros.adicionar("marcaEspecialId", OperadorCriteria.IGUAL, ParametrosFRVCC.SIN_MARCA_ESPECIAL);
            filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaMin.format(DateTimeFormatter.ISO_LOCAL_DATE));
            filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaMax.format(DateTimeFormatter.ISO_LOCAL_DATE));


        } else {
            RequestContext.getCurrentInstance()
                    .execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
        }
        return filtros.getFiltros();
    }


    public LocalDate fechaFacturaValidaDesde(LocalDate pFechaHasta) {
        return pFechaHasta.plusDays(-Integer.valueOf(formulario.getCantidadPeriodicidad()));
    }

    public void asociarfacturas() {
        vComprasIds.clear();

        for (ComprasCvDto factura : vListaSeleccioandosSinGuardar) {
            vComprasIds.add(factura.getId());
        }
        if (clientesRestController.getClienteRestFormularios().seleccionarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {

            RequestContext.getCurrentInstance()
                    .execute("toastr.success('Se adicionó " + vListaSeleccioandosSinGuardar.size() + " factura(s) al formulario .', 'Exitoso!')");
            vComprasIds.clear();
            vListaSeleccioandosSinGuardar = new ArrayList<>();
        }


    }

    public void adicionarSeleccionados() {
        if (verificaMontoFormularioRechazado()) {
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

    }

    public void filtrarComprasPosibles() {


        vListaSeleccionables = vListaDisponibles.stream().filter(this::compraValidaSeleccionar).collect(Collectors.toList());
    }

    private Boolean compraValidaSeleccionar(ComprasCvDto compra) {
        Month mes = LocalDate.of(this.anioPeriodo, this.mesPeriodo, 1).getMonth();
        return compra.getFechaFactura().getYear() == this.getAnioPeriodo() && compra.getFechaFactura().getMonth().equals(mes) && compra.getEstadoUsoId().equals(ParametrosFRVCC.ESTADO_USO_COMPRA_REGISTRADO);
    }

    public boolean verificaMontoFormularioRechazado() {
        boolean vRespuesta = true;
        if (formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO) &&
                formulario.getDatosEspecificos().getMontoOtrosFormRech() != null && formulario.getDatosEspecificos().getMontoIpnFormRech() != null
                && formulario.getDatosEspecificos().getMontoSieteRgFormRech() != null) {

            Long cantidadIpnAsociar = facturasAscSelect.stream().filter(f -> f.getMarcaEspecialId().equals("IPN")).count();
            Long cantidadSrgAsociar = facturasAscSelect.stream().filter(f -> f.getMarcaEspecialId().equals("7RG")).count();
            Long cantidadOtrasAsociar = facturasAscSelect.stream().filter(f -> f.getMarcaEspecialId().equals("SM")).count();

            if ((cantidadIpnAsociar + cantidadOtrasAsociar) <= formulario.getDatosEspecificos().getCantidadFacIpnFormRech() + formulario.getDatosEspecificos().getCantidadFacOtrasFormRech()) {
                vRespuesta = true;

                if (cantidadSrgAsociar <= formulario.getDatosEspecificos().getCantidadFacSrgFormRech()) {
                    vRespuesta = true;
                } else {
                    vRespuesta = false;
                    RequestContext.getCurrentInstance()
                            .execute("toastr.error('La cantidad de facturas de proveedores pertenecientes al regimen Siete-Rg no debe superar la cantidad declarada en el formulario Original (" + formulario.getDatosEspecificos().getCantidadFacSrgFormRech() + ")', 'Error!')");
                }

            } else {
                vRespuesta = false;
                RequestContext.getCurrentInstance()
                        .execute("toastr.error('La cantidad de facturas no debe superar la cantidad declarada en el formulario Original (" + formulario.getDatosEspecificos().getCantidadFacSrgFormRech() + ")', 'Error!')");
            }
        }


        return vRespuesta;
    }

    public void actualizartotales() {


        vSumaTotalMontosOtras = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
                .map(factura -> factura.getImporteBaseCf())
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
        vPagoaCuentaOtras = vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
                .map(factura -> factura.getCreditoFiscal())
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
        CantidadComprasOtras = (int) vListaAsociadas.stream().filter(f -> f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL)).count();

        formulario.setTotalComprasCfOtras(vSumaTotalMontosOtras);
    }

    public void devolverFactura(ComprasCvDto pFactura) {
        vComprasIds.clear();
        vComprasIds.add(pFactura.getId());
        boolean resp = clientesRestController.getClienteRestFormularios().desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk();

        if (this.getvListaAsociadas().stream().anyMatch(factura -> factura.getId().equals(pFactura.getId())) && resp) {
            long vContadorInicial = this.getvListaAsociadas().size();
            this.getvListaAsociadas().remove(pFactura);

            if (vContadorInicial > this.getvListaAsociadas().size()) {
                RequestContext.getCurrentInstance().execute("toastr.info('Factura Desmarcada', 'Información')");
            } else {
                RequestContext.getCurrentInstance().execute("toastr.warning('Factura NO desmarcada', 'Advertencia')");
            }

        }
        actualizartotales();
        this.vComprasIds.clear();

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
        if (!vListaDesseleccionadosSinGuardar.isEmpty()) {
            devolverFacturas();

        }
        if (!vListaSeleccioandosSinGuardar.isEmpty()) {
            asociarfacturas();
        }

        if (formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO)) {
            generarReporteF110();
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
            if (clientesRestController.getClienteRestFormularios().desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk()) {

                RequestContext.getCurrentInstance().execute("toastr.info('Se desmarcó " + contador + " factura(s) del formulario.', 'Información')");
                this.vListaDesseleccionadosSinGuardar = new ArrayList<>();
                actualizartotales();

            }


        }
        this.vComprasIds.clear();


    }

    private void actulizarFechaMaxMinPresentacion(FormularioCvDto pFormulario){
        this.setMinFechaPresentacion(LocalDate.of(pFormulario.getMesPeriodo() == 12 ? pFormulario.getAnioPeriodo() + 1 : pFormulario.getAnioPeriodo(),
        pFormulario.getMesPeriodo() == 12 ? 1 : pFormulario.getMesPeriodo() + 1,
                1));

        this.setMaxFechaPresentacion(LocalDate.of(pFormulario.getMesPeriodo()== 12 ? pFormulario.getAnioPeriodo() + 1 : pFormulario.getAnioPeriodo(),
                pFormulario.getMesPeriodo() == 12 ? 1 : pFormulario.getMesPeriodo() + 1,
                5));
    }

    public void cargarDetalleFormulario(FormularioCvDto pFormulario) {

        this.formulario = pFormulario;
        actulizarFechaMaxMinPresentacion(pFormulario);

        LOG.info("numero suc {}", this.formulario.getNumeroSucursal());
        consultaContribuyente();
        cargarComprasDisponibles();
        vListaSeleccioandosSinGuardar = new ArrayList<>();
        vListaDesseleccionadosSinGuardar = new ArrayList<>();
        FiltroCriteria filtros = new FiltroCriteria();

        filtros.adicionar("formularioId", OperadorCriteria.IGUAL, pFormulario.getId());
        //	filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
        //filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
        if (formulario.getEstadoFormularioId().equals("REG")) {
            filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, "ASC");
        }
        filtros.getFiltros().put("order_by", "fechaFactura");
        filtros.getFiltros().put("order", "ASC");

        long vCantidad = clientesRestController.getClienteRestCompras().obtenerTotalCompras(filtros.getFiltros());
        if (vCantidad > 0) {
            LinkedHashMap<String, Serializable> pFilters = filtros.getFiltros();
            pFilters.put("limit", vCantidad);
            pFilters.put("offset", 0);

            List<ComprasCvDto> vLista = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters).getComprasResponse();
            //.out.println("detalle compras asociadas......................"+vLista.toString());
            vListaAsociadas = new ArrayList<>();
            vListaAsociadas = vLista;
        } else {
            vListaAsociadas = new ArrayList<>();
        }

        setVerDetalle(true);
        if (formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO))
            this.formularioAuxiliar = clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();

        actualizartotales();
        generarReporteF110();

    }


    public void declarar() {
        ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestFormularios().declararFormulario(formulario, contextoModel.getCodigoAdministracion());
        if (vRespuesta.isOk()) {
            cargarFormulariosPeriodoSeleccionado();
            this.formularioAuxiliar = clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
            this.formulario = this.formularioAuxiliar;
            generarReporteF110();

            RequestContext.getCurrentInstance().execute("controlClick('formPrincipal:reportePdf:btnVisorPdf');");
            mensajesBean.addMensajes(vRespuesta);
        } else {

            mensajesBean.addMensajes(vRespuesta);
        }


    }


    public void generarReporteF110() {

        try {
            if (!formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO)) {
                ResultadoGenericoDto<RespuestaReporteDto> vRespuestaReport = clientesRestController.getClienteRestFormularios().obtieneReporteF110(this.formulario);
                if (vRespuestaReport.isOk()) {
                    reportesController.setRespuestaBase64(vRespuestaReport.getResultadoObjeto().getArchivoBase64());
                } else {
                    mensajesBean.addMensajes(vRespuestaReport);

                }
            }

        } catch (Exception e) {
            LOG.error(e.getCause().toString());
            RequestContext.getCurrentInstance()
                    .execute("toastr.error('Servicio de reporte no disponible', 'Error')");

        }

    }

    public void cambiarFechaPresentacionFormulario() {

        ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestFormularios().guardarCambiosFormulario(this.formulario);
        if (vResp.isOk()) {
            persistirCambios();
        } else {
            this.formulario = this.formularioAuxiliar;
            cargarFormulariosPeriodoSeleccionado();
            cargarDetalleFormulario(this.formulario);
        }
        mensajesBean.addMensajes(vResp);

    }

    public void desmarcarComprasfueraRango() {
        for (ComprasCvDto vCompra : vListaComprasFueraRango) {
            if (!vListaSeleccioandosSinGuardar.stream().anyMatch(c -> c.getId().equals(vCompra.getId()))) {
                vListaDesseleccionadosSinGuardar.add(vCompra);
            }
        }
        vListaSeleccioandosSinGuardar.removeAll(vListaComprasFueraRango);
        vListaDisponibles.addAll(vListaComprasFueraRango);
        vListaAsociadas.removeAll(vListaComprasFueraRango);
        vListaComprasFueraRango.clear();
        filtrarComprasPosibles();
        actualizartotales();
    }


    public void modificarCamposFormulario() {
        try {
            ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestFormularios().guardarCambiosFormulario(this.formulario);
            mensajesBean.addMensajes(vResp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void cambiarLugarPresentacion() {
        try {
            guardarDescripcionDepartamento(formulario);
            this.formulario.setDireccionEmpleador(null);
            modificarCamposFormulario();
            consultaContribuyente();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void retornaValores() {
        this.formulario = this.formularioAuxiliar;

    }

    public void salvarValores() {
        this.formularioAuxiliar = clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
        vListaComprasFueraRango.clear();
        this.vListaComprasFueraRango = vListaAsociadas.stream().filter(c -> c.getFechaFactura().isBefore(formulario.getFechaPresentacion().plusDays(-120))).collect(Collectors.toList());
        this.vListaComprasFueraRango.addAll(vListaAsociadas.stream().filter(c -> c.getFechaFactura().isAfter(formulario.getFechaPresentacion())).collect(Collectors.toList()));

    }




    public void guardarDescripcionDepartamento(FormularioCvDto formulario) {
        DepartamentoDto departamento = listaDepartamentos.stream()
                .filter(s -> formulario.getLugarDepartamento().shortValue() == (s.getDepartamentoId()))
                .findFirst().orElse(null);
        formulario.getDatosEspecificos().setDepartamentoDescripcion(departamento != null ? departamento.getNombre() : null);
    }




    public List<ComprasCvDto> getvListaAsociadas() {
        return vListaAsociadas;
    }

    public void setvListaAsociadas(List<ComprasCvDto> vListaAsociadas) {
        this.vListaAsociadas = vListaAsociadas;
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


    public Integer getCantidadComprasOtras() {
        return CantidadComprasOtras;
    }

    public void setCantidadComprasOtras(Integer cantidadComprasOtras) {
        CantidadComprasOtras = cantidadComprasOtras;
    }


    public BigDecimal getvSumaTotalMontosOtras() {
        return vSumaTotalMontosOtras;
    }

    public void setvSumaTotalMontosOtras(BigDecimal vSumaTotalMontosOtras) {
        this.vSumaTotalMontosOtras = vSumaTotalMontosOtras;
    }

    public BigDecimal getvPagoaCuentaOtras() {
        return vPagoaCuentaOtras;
    }

    public void setvPagoaCuentaOtras(BigDecimal vPagoaCuentaOtras) {
        this.vPagoaCuentaOtras = vPagoaCuentaOtras;
    }


    public BigDecimal getvPagoaCuentaCf() {
        return vPagoaCuentaCf;
    }

    public void setvPagoaCuentaCf(BigDecimal vPagoaCuentaCf) {
        this.vPagoaCuentaCf = vPagoaCuentaCf;
    }

    public boolean isNitEmpleadorValido() {
        return nitEmpleadorValido;
    }

    public void setNitEmpleadorValido(boolean nitEmpleadorValido) {
        this.nitEmpleadorValido = nitEmpleadorValido;
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

    public List<ComprasCvDto> getvListaDisponibles() {
        return vListaDisponibles;
    }

    public void setvListaDisponibles(List<ComprasCvDto> vListaDisponibles) {
        this.vListaDisponibles = vListaDisponibles;
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

    public List<ComprasCvDto> getvListaSeleccionables() {
        return vListaSeleccionables;
    }

    public void setvListaSeleccionables(List<ComprasCvDto> vListaSeleccionables) {
        this.vListaSeleccionables = vListaSeleccionables;
    }

    public LocalDate getMinFechaPresentacion() {
        return this.minFechaPresentacion;
    }

    public LocalDate getMaxFechaPresentacion() {
        return this.maxFechaPresentacion;
    }

    public BigDecimal getReIntegroIva() {
        if (this.beneficiario.getTotalSalario().compareTo(this.getvSumaTotalMontosOtras()) < 0) {
            return this.beneficiario.getTotalSalario().multiply(new BigDecimal("0.05"));
        }
        return this.getvSumaTotalMontosOtras().multiply(new BigDecimal("0.05"));
    }


    public void updateCalendar() {

        this.setMinFechaPresentacion(LocalDate.of(this.formularioNuevo.getMesPeriodo() == 12 ? this.formularioNuevo.getAnioPeriodo() + 1 : this.formularioNuevo.getAnioPeriodo(),
                this.formularioNuevo.getMesPeriodo() == 12 ? 1 : this.formularioNuevo.getMesPeriodo() + 1,
                1));

        this.setMaxFechaPresentacion(LocalDate.of(this.formularioNuevo.getMesPeriodo() == 12 ? this.formularioNuevo.getAnioPeriodo() + 1 : this.formularioNuevo.getAnioPeriodo(),
                this.formularioNuevo.getMesPeriodo() == 12 ? 1 : this.formularioNuevo.getMesPeriodo() + 1,
                5));
    }

    public void setMinFechaPresentacion(LocalDate minFechaPresentacion) {
        this.minFechaPresentacion = minFechaPresentacion;
    }

    public void setMaxFechaPresentacion(LocalDate maxFechaPresentacion) {
        this.maxFechaPresentacion = maxFechaPresentacion;
    }

}
