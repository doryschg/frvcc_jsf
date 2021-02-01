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

import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.caco.dto.SucursalDto;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bo.gob.sin.sen.enco.controller.MensajesBean;
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

@ManagedBean(name = "formulario110Controller")
@ViewScoped
public class Formulario110Controller implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG= LoggerFactory.getLogger(Formulario110Controller.class);

	private List<ComprasCvDto> vListaAsociadas = new ArrayList<>();
	private List<ComprasCvDto> vListaComprasFueraRango = new ArrayList<>();
	private List<FormularioCvDto> vListaFormularios = new ArrayList<>();
	
	
	private List<ComprasCvDto> vListaDisponibles=new ArrayList<>();
	private List<ComprasCvDto> vListaSeleccionables=new ArrayList<>();
	private List<ComprasCvDto> vListaSeleccioandosSinGuardar=new ArrayList<>();
	private List<ComprasCvDto> vListaDesseleccionadosSinGuardar=new ArrayList<>();

	private Integer CantidadComprasIpn;
	private Integer CantidadComprasOtras;
	private Integer CantidadComprasSieteRg;
	private BigDecimal vSumaTotalMontosIpn;
	private BigDecimal vSumaTotalMontosOtras;
	private BigDecimal vSumaTotalMontosSieteRg;
	private BigDecimal vPagoaCuentaIpn;
	private BigDecimal vPagoaCuentaOtras;
	private BigDecimal vPagoaCuentaSieteRg;
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
	private List<ClasificadorDto> listaTipoDocumento=new ArrayList<>();
	private List<ClasificadorDto> listaTipoUso=new ArrayList<>();
	
	private LazyDataModel<ComprasCvDto> facturasComprasLazy;
	private ComprasCvDto compraEdicion= new ComprasCvDto();
	private List<ComprasCvDto> facturasAscSelect = new ArrayList<>();
	private List<String> vComprasIds= new ArrayList<>();
	private List<SucursalDto> listaSucursales=new ArrayList<SucursalDto>();
	private SucursalDto sucursalSeleccinado=new SucursalDto();
	
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
		anioPeriodo = fecha.get(Calendar.YEAR);
		mesPeriodo = fecha.get(Calendar.MONTH) + 1;
		cargarFormularios(mesPeriodo, anioPeriodo);
		setVerDetalle(false);
		nuevoFormulario();
		listaDepartamentos = clientesRestController.getClienteDepartamentos().getListaDepartamentoActivos();
		listaTipoDocumento = clientesRestController.getClienteRestClasificadores().obtenerListaClasificadoresPorGrupo(ParametrosFRVCC.ID_AGRUPADOR_TIPO_DOCUMENTO);
		if (contextoModel.getUsuario().getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_CONTRIBUYENTE)) {
			listaTipoUso = clientesRestController.getClienteRestClasificadores().obtenerListaClasificadoresPorGrupo(ParametrosFRVCC.CODIGO_AGRUPADOR_CONTRIBUYENTE_TIPO_USO);
		} else {
			listaTipoUso = clientesRestController.getClienteRestClasificadores().obtenerListaClasificadoresPorGrupo(ParametrosFRVCC.CODIGO_AGRUPADOR_DEPENDIENTE_TIPO_USO);
		}
	}
	public void cargarComprasDisponibles()
	{
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		
		long vCantidad=clientesRestController.getClienteRestCompras().obtenerTotalCompras(pFilters);
		if(vCantidad>0)
		{
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "fechaFactura");
			pFilters.put("order", "ASC");
			RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters);
			if(vRespuesta.isOk())
			{
				vListaDisponibles=vRespuesta.getComprasResponse();
			}
			else {
				mensajesBean.addMensajes(vRespuesta);
				vListaDisponibles=new ArrayList<>();
			}
		}
		else
		{
			vListaDisponibles=new ArrayList<>();
		}
	}

	public void cargarFormularios(int mesPeriodo, int gestionPeriodo) {
		vListaFormularios = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, gestionPeriodo);
		filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, mesPeriodo);
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110);
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		
		LinkedHashMap<String, Serializable> pFilters=filtros.getFiltros();
		pFilters.put("order_by", "mesPeriodo");
		pFilters.put("order", "ASC");
		RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
		if(lista.isOk())
		{
			vListaFormularios = lista.getFormulariosReponse();
		}
		else {
			vListaFormularios=new ArrayList<>();
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
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110);
		filtros.adicionar("nitCi", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		LinkedHashMap<String, Serializable> pFilters=filtros.getFiltros();
		pFilters.put("order_by", "mesPeriodo");
		pFilters.put("order", "ASC");
		RespuestaFormulariosDto lista = clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
		if(lista.isOk())
		{
			vListaFormularios = lista.getFormulariosReponse();
		}
		else {
			vListaFormularios=new ArrayList<>();
			mensajesBean.addMensajes(lista);
		}
	}

	public void nuevoFormulario() {
		formularioNuevo = new FormularioCvDto();
		formularioNuevo.setAnioPeriodo(this.anioPeriodo);
		formularioNuevo.setMesPeriodo(this.mesPeriodo);
		formularioNuevo.setNitCi(Long.parseLong(contextoModel.getNitCi()));
		formularioNuevo.getDatosEspecificos().setNumeroDocumento(contextoModel.getNumeroDocumentoIdentidad());
		sucursalSeleccinado= new SucursalDto();
		listaSucursales.clear();
	}

	public static BigDecimal percentaje(BigDecimal base, BigDecimal pct) {
		return ((new BigDecimal(100)).divide(pct, 4, RoundingMode.HALF_UP).multiply(base)).setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
	}

	public void verificaNit(){
	try{
		if(formularioNuevo.getNitEmpleador()!=null){
			nitEmpleadorValido = false;
			ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vDatoscontribuyenteEmpleador = clientesRestController.getClienteRestPadron()
					.consultaContribuyente(formularioNuevo.getNitEmpleador());
			if (vDatoscontribuyenteEmpleador.isOk()) {
				nitEmpleadorValido = true;
				formularioNuevo
						.setNombreEmpleador(vDatoscontribuyenteEmpleador.getResultadoObjeto().getNombreRazonSocial());
				listaSucursales=vDatoscontribuyenteEmpleador.getResultadoObjeto().getSucursales();
				if(formularioNuevo.getLugarDepartamento()!=null) {
					List<SucursalDto> resultado = listaSucursales.stream()
							.filter(suc -> suc.getDepartamentoId() == formularioNuevo.getLugarDepartamento())
							.collect(Collectors.toList());
					if(resultado.size()==0)
					{
						RequestContext.getCurrentInstance()
								.execute("toastr.warning('No se encontraron sucursales para el departamento y NIT de empleador Introducidos', 'Advertencia')");
					}

					setListaSucursales(resultado);
				}

			} else {
				nitEmpleadorValido = false;
				formularioNuevo
						.setNombreEmpleador("EL NIT INTRODUCIDO ES INVALIDO");
				RequestContext.getCurrentInstance()
						.execute("toastr.warning('Debe introducir un NIT válido.', 'Advertencia')");
			}
		}
	}
		catch (Exception e){
		e.printStackTrace();
		}

	}

	public void consultaContribuyente() {
		ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vDatoscontribuyenteEmpleador = clientesRestController.getClienteRestPadron()
				.consultaContribuyente(this.formulario.getNitEmpleador());
		if (vDatoscontribuyenteEmpleador.isOk()) {
			listaSucursales=vDatoscontribuyenteEmpleador.getResultadoObjeto().getSucursales();
			if(!listaSucursales.isEmpty()){
				cargarSucursalesPorDpto();
			}
		}
	}

	public void cargarSucursalesPorDpto(){
		for(SucursalDto suc: listaSucursales){
			if(suc.getNumeroSucursal().equals(this.formulario.getNumeroSucursal().toString())){
				sucursalSeleccinado=suc;
				break;
			}
		}
		List<SucursalDto> resultado = listaSucursales.stream()
				.filter(suc -> suc.getDepartamentoId()==this.formulario.getLugarDepartamento())
				.collect(Collectors.toList());
		setListaSucursales(resultado);

	}

	public void adicionarFormulario() {
		if (getvListaFormularios() == null) {
			setvListaFormularios(new ArrayList<FormularioCvDto>());
		}
		FormularioCvDto vNuevoFormulario = getFormularioNuevo();
		guardarDescripciondocumento(vNuevoFormulario);
		guardarDescripcionDepartamento(vNuevoFormulario); 
		if(vNuevoFormulario.getDatosEspecificos().getTipoDocumento().equals(ParametrosFRVCC.TIPO_DOC_CLIENTE_COMPRA_CI))
		{
			guardarDescripcionLugarExpedicion(vNuevoFormulario);
		}
	
		vNuevoFormulario.setId(UUID.randomUUID().toString());

			ContextoJSF contexto = new ContextoJSF();

			vNuevoFormulario.setTipoFormularioId(ParametrosFRVCC.TIPO_FORMULARIO_110);
			vNuevoFormulario.setUsuarioRegistro(contexto.getUsuario().getUsuarioId());
			vNuevoFormulario.setUsuarioUltimaModificacion(contexto.getUsuario().getUsuarioId());
			vNuevoFormulario.setIfc(contexto.getUsuario().getPersonaId());
			vNuevoFormulario.setNitCi(Long.parseLong(contextoModel.getNitCi()));
			
			vNuevoFormulario.setRazonSocial(contextoModel.getNombreRazonSocial());
			if(contexto.getUsuario().getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_DEPENDIENTE))
			{
				vNuevoFormulario.setCodigoDependiente(Long.parseLong(contexto.getUsuario().getNit()));
				vNuevoFormulario.setRazonSocial(contextoModel.getNombreRazonSocial());
			}
			if(contexto.getUsuario().getTipoUsuario().equals((ParametrosFRVCC.TIPO_USUARIO_CONTRIBUYENTE))){
				vNuevoFormulario.setRazonSocial(contextoModel.getUsuario().getNombreCompleto());
			}
			
			vNuevoFormulario.setTipoPresentacionId(ParametrosFRVCC.TIPO_PRESENTACION_FORMULARIO_ORIGINAL);
			vNuevoFormulario.setPeriodicidadId(ParametrosFRVCC.PERIODICIDAD_DIARIA);
			vNuevoFormulario.setCantidadPeriodicidad(ParametrosFRVCC.CANTIDAD_PERIODICIDAD_FORMULARIO_DIARIA);
			vNuevoFormulario.setDireccionEmpleador(sucursalSeleccinado.getDireccion());
			vNuevoFormulario.setNumeroSucursal(Integer.valueOf(sucursalSeleccinado.getNumeroSucursal()));
			String vChar=vNuevoFormulario.getMesPeriodo()>9?"":"0";
			vNuevoFormulario.setNombreFormulario(
					"F110_" + vNuevoFormulario.getAnioPeriodo() + "_"+vChar+ vNuevoFormulario.getMesPeriodo() + "_"
							+ vNuevoFormulario.getNitCi() + "_" + vNuevoFormulario.getNitEmpleador()+"_"+vNuevoFormulario.getTipoUsoId());
			ResultadoGenericoDto<String> rep = clientesRestController.getClienteRestFormularios().guardarFormularioNuevo(vNuevoFormulario);
			if(rep.isOk()) {
				this.getvListaFormularios().add(vNuevoFormulario);
				mensajesBean.addMensajes(rep);
				RequestContext.getCurrentInstance().execute("PF('dlgAgregaFormulario').hide();");
				this.nuevoFormulario();
				cargarFormulariosPeriodoSeleccionado();
				
			}
			else {
				mensajesBean.addMensajes(rep);
			}
			
	}

	public void cargarListaComprasRegistradas() {
				
			try {
			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
			
			LazyConsultaAsociacionComprasModel lazyConsulAsModel=new LazyConsultaAsociacionComprasModel(new ArrayList<ComprasCvDto>(), pFilters, this.clientesRestController.getClienteRestCompras(), true,mensajesBean);
			this.setFacturasComprasLazy(lazyConsulAsModel);
			} catch (Exception e) {
				e.printStackTrace();
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Ocurrio un error al obtener facturas.', 'Error!')");
				
			}
		
		}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_COMPRA_REGISTRADO);
		
		ContextoJSF contexto = new ContextoJSF();
		if (contexto.getUsuario() != null) {
			filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
			filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
			filtros.adicionar("creditoFiscal", OperadorCriteria.MAYOR,"0");
			filtros.adicionar("estadoCompraId", OperadorCriteria.DISTINTO, "AN");
			
		}
		else {
			RequestContext.getCurrentInstance()
			.execute("toastr.error('No se puede recuperar datos del usuario.', 'Error!')");
		}
		
		return filtros.getFiltros();
	}
	
	
	public LocalDate fechaFacturaValidaDesde(LocalDate pFechaHasta) {
		 return pFechaHasta.plusDays(-Integer.valueOf(formulario.getCantidadPeriodicidad()));
	}
	
	public void asociarfacturas(){
		vComprasIds.clear();
		//if(verificaMontoFormularioRechazado())
		//{
				for (ComprasCvDto factura: vListaSeleccioandosSinGuardar) {
					vComprasIds.add(factura.getId());
				}
				if(clientesRestController.getClienteRestFormularios().seleccionarComprasFormulario(formulario.getId(), vComprasIds).isOk())
				{

					RequestContext.getCurrentInstance()
					.execute("toastr.success('Se adicionó "+vListaSeleccioandosSinGuardar.size()+" factura(s) al formulario .', 'Exitoso!')");
					vComprasIds.clear();
					vListaSeleccioandosSinGuardar=new ArrayList<>();
				}

		//}


	}
	public void adicionarSeleccionados() {
		if(verificaMontoFormularioRechazado())
		{
			vListaAsociadas.addAll(facturasAscSelect);
			vListaDisponibles.removeAll(facturasAscSelect);

			for (ComprasCvDto vCompra : facturasAscSelect) {
				if(!vListaDesseleccionadosSinGuardar.stream().anyMatch(c->c.getId().equals(vCompra.getId())))
				{
					vListaSeleccioandosSinGuardar.add(vCompra);
				}
			}
						
			vListaDesseleccionadosSinGuardar.removeAll(facturasAscSelect);
			facturasAscSelect.clear();
			filtrarComprasPosibles();
			actualizartotales();
		}
		
	}
	public void filtrarComprasPosibles()
	{
		vListaSeleccionables=vListaDisponibles.stream().filter(compra->compra.getFechaFactura()
				.isBefore(formulario.getFechaPresentacion().plusDays(1))).filter(compra->compra.getFechaFactura()
						.isAfter(formulario.getFechaPresentacion().plusDays(-120))).collect(Collectors.toList());

	}
	public boolean verificaMontoFormularioRechazado()
	{
		boolean vRespuesta=true;
		if(formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO) &&
		formulario.getDatosEspecificos().getMontoOtrosFormRech()!=null && formulario.getDatosEspecificos().getMontoIpnFormRech()!=null
		&&formulario.getDatosEspecificos().getMontoSieteRgFormRech()!=null && formulario.getDatosEspecificos().getCantidadFacIpnFormRech()!=null
		&&formulario.getDatosEspecificos().getCantidadFacOtrasFormRech()!=null&&formulario.getDatosEspecificos().getCantidadFacSrgFormRech()!=null) {

			Long cantidadIpnAsociar=facturasAscSelect.stream().filter(f->f.getMarcaEspecialId().equals("IPN")).count();
			Long cantidadSrgAsociar=facturasAscSelect.stream().filter(f->f.getMarcaEspecialId().equals("7RG")).count();
			Long cantidadOtrasAsociar=facturasAscSelect.stream().filter(f->f.getMarcaEspecialId().equals("SM")).count();

			Long cantidadIpnAExistente=vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals("IPN")).count();
			Long cantidadSrgExistente=vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals("7RG")).count();
			Long cantidadOtrasExistente=vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals("SM")).count();

			Long cantidadOtrasIpn=(cantidadIpnAsociar+cantidadOtrasAsociar)+(cantidadIpnAExistente+cantidadOtrasExistente);
			Long cantidadSrg=cantidadSrgAsociar+cantidadSrgExistente;

					if(cantidadOtrasIpn<=formulario.getDatosEspecificos().getCantidadFacIpnFormRech()+formulario.getDatosEspecificos().getCantidadFacOtrasFormRech())
					{
						vRespuesta=true;

							if(cantidadSrg<=formulario.getDatosEspecificos().getCantidadFacSrgFormRech())
							{
								vRespuesta=true;
							}
							else {
								vRespuesta=false;

								RequestContext.getCurrentInstance()
								.execute("toastr.error('La cantidad de facturas de proveedores pertenecientes al regimen Siete-Rg no debe superar la cantidad declarada en el formulario Original ("+formulario.getDatosEspecificos().getCantidadFacSrgFormRech()+")', 'Error!')");
							}

					}
					else {
						vRespuesta=false;
						Integer valor=0;
						valor=formulario.getDatosEspecificos().getCantidadFacOtrasFormRech()+formulario.getDatosEspecificos().getCantidadFacIpnFormRech();
						RequestContext.getCurrentInstance()
						.execute("toastr.error('La cantidad de facturas no debe superar la cantidad declarada en el formulario Original ("+valor+")', 'Error!')");
					}
		}
		
		
		return vRespuesta;
	}
	
	public void actualizartotales() {
		vSumaTotalMontosIpn = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN))
				.map(factura -> factura.getImporteBaseCf())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaIpn = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN))
				.map(factura -> factura.getCreditoFiscal())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasIpn = (int)vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN)).count();
		
		vSumaTotalMontosOtras = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
				.map(factura -> factura.getImporteBaseCf())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaOtras = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
				.map(factura -> factura.getCreditoFiscal())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasOtras = (int)vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL)).count();
		vSumaTotalMontosSieteRg = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG))
				.map(factura -> factura.getImporteTotalCompra())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		vPagoaCuentaSieteRg = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG))
				.map(factura -> factura.getCreditoFiscal())
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, BigDecimal.ROUND_HALF_UP);
		CantidadComprasSieteRg = (int)vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG)).count();
		
		vPagoaCuentaCf=vPagoaCuentaOtras.add(vPagoaCuentaIpn);
		formulario.setTotalComprasCfIpn(vSumaTotalMontosIpn);
		formulario.setTotalComprasCfOtras(vSumaTotalMontosOtras);
		formulario.setTotalComprasSdCf(vSumaTotalMontosSieteRg);
	}

	public void devolverFactura(ComprasCvDto pFactura) {
		vComprasIds.clear();
		vComprasIds.add(pFactura.getId());
		boolean resp = clientesRestController.getClienteRestFormularios().desmarcarComprasFormulario(formulario.getId(),vComprasIds).isOk();

		if (this.getvListaAsociadas().stream().anyMatch(factura -> factura.getId() == pFactura.getId()) && resp) {
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
	public void desmarcarCompra(ComprasCvDto pFactura)
	{
		if(!vListaSeleccioandosSinGuardar.stream().anyMatch(c->c.getId().equals(pFactura.getId())))
		{
			vListaDesseleccionadosSinGuardar.add(pFactura);
		}
		
		vListaAsociadas.removeIf(compra->compra.getId().equals(pFactura.getId()));
		vListaSeleccioandosSinGuardar.removeIf(compra->compra.getId().equals(pFactura.getId()));
		vListaDisponibles.add(pFactura);
		filtrarComprasPosibles();
		actualizartotales();
	}
	public void desmarcarTodasLasCompras()
	{
		
		for (ComprasCvDto vCompra : vListaAsociadas) {
			if(!vListaSeleccioandosSinGuardar.stream().anyMatch(c->c.getId().equals(vCompra.getId())))
			{
				vListaDesseleccionadosSinGuardar.add(vCompra);
			}
		}
		vListaSeleccioandosSinGuardar.removeAll(vListaAsociadas);
		vListaDisponibles.addAll(vListaAsociadas);
		vListaAsociadas.clear();
		filtrarComprasPosibles();
		actualizartotales();
	}
	public void persistirCambios()
	{
		if(!vListaDesseleccionadosSinGuardar.isEmpty())
		{
			devolverFacturas();

		}
		if(!vListaSeleccioandosSinGuardar.isEmpty()) {
			asociarfacturas();
		}
		
		if(formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO)) {
			generarReporteF110();
		}
		
	}
	public void devolverFacturas() {
		vComprasIds.clear();
		int contador=0;
		if(vListaDesseleccionadosSinGuardar.size()>0)
		{
			for (ComprasCvDto compra : vListaDesseleccionadosSinGuardar) {
				contador=contador+1;
				vComprasIds.add(compra.getId());
				
			}
			if(clientesRestController.getClienteRestFormularios().desmarcarComprasFormulario(formulario.getId(), vComprasIds).isOk())
			{
				RequestContext.getCurrentInstance().execute("toastr.info('Se desmarcó "+contador+" factura(s) del formulario.', 'Información')");
				this.vListaDesseleccionadosSinGuardar=new ArrayList<>();
				actualizartotales();
				
			}
		}
		this.vComprasIds.clear();
	}

	public void cargarDetalleFormulario(FormularioCvDto pFormulario) {
		this.formulario = pFormulario;
		LOG.info("numero suc {}",this.formulario.getNumeroSucursal());
		obtenerDescripcionTipoUso(this.formulario);
		consultaContribuyente();
		cargarComprasDisponibles();
		vListaSeleccioandosSinGuardar=new ArrayList<>();
		vListaDesseleccionadosSinGuardar=new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();
	
		filtros.adicionar("formularioId", OperadorCriteria.IGUAL, pFormulario.getId());
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		if (formulario.getEstadoFormularioId().equals("REG")) {
			filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, "ASC");
		}
		filtros.getFiltros().put("order_by", "fechaFactura");
		filtros.getFiltros().put("order", "ASC");
		
		long vCantidad=clientesRestController.getClienteRestCompras().obtenerTotalCompras(filtros.getFiltros());
		if(vCantidad>0)
		{	LinkedHashMap<String, Serializable> pFilters=filtros.getFiltros();
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			
			List<ComprasCvDto> vLista = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters).getComprasResponse();
			//.out.println("detalle compras asociadas......................"+vLista.toString());
			vListaAsociadas = new ArrayList<>();
			vListaAsociadas = vLista;
		}
		else
		{
			vListaAsociadas = new ArrayList<>();
		}
		
		setVerDetalle(true);
		if(formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_RECHAZADO))
		this.formularioAuxiliar=clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
		
		actualizartotales();
		generarReporteF110();	
		
	}

	public void declarar() {
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestFormularios().declararFormulario(formulario,contextoModel.getCodigoAdministracion());
		if (vRespuesta.isOk()) {
			cargarFormulariosPeriodoSeleccionado();
			this.formularioAuxiliar=clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
			this.formulario=this.formularioAuxiliar;
			generarReporteF110();
			
			RequestContext.getCurrentInstance().execute("controlClick('formPrincipal:reportePdf:btnVisorPdf');");
			mensajesBean.addMensajes(vRespuesta);
		} else {

			mensajesBean.addMensajes(vRespuesta);
		}
		

	}
	public void rectificar() {
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestFormularios().rectificarFormulario(formulario,contextoModel.getCodigoAdministracion());
		if (vRespuesta.isOk()) {
			cargarFormulariosPeriodoSeleccionado();
			this.formularioAuxiliar=clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
			this.formulario=formularioAuxiliar;
			generarReporteF110();
			mensajesBean.addMensajes(vRespuesta);
			RequestContext.getCurrentInstance().execute("controlClick('formPrincipal:reportePdf:btnVisorPdf');");
		} else {
			mensajesBean.addMensajes(vRespuesta);
		}
		

	}
	public void cancelarDeclaracion()
	{
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestFormularios().cancelarDeclaracionFormulario(formulario);
		mensajesBean.addMensajes(vRespuesta);
		cargarFormulariosPeriodoSeleccionado();
	}
	public void generarReporteF110() {

		try {
			if(!formulario.getEstadoFormularioId().equals(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO))
			{
				ResultadoGenericoDto<RespuestaReporteDto> vRespuestaReport=clientesRestController.getClienteRestFormularios().obtieneReporteF110(this.formulario);
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
			LOG.error(e.getCause().toString());
			RequestContext.getCurrentInstance()
			.execute("toastr.error('Servicio de reporte no disponible', 'Error')");
		
		}
		
		}

	public void cambiarFechaPresentacionFormulario(){
		ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestFormularios().guardarCambiosFormulario(this.formulario);
		if(vResp.isOk())
		{
			desmarcarComprasfueraRango();
			persistirCambios();
		}
		else
		{
			this.formulario=this.formularioAuxiliar;
			cargarFormulariosPeriodoSeleccionado();
			cargarDetalleFormulario(this.formulario);
		}
		mensajesBean.addMensajes(vResp);
		
	}
	public void desmarcarComprasfueraRango()
	{
		for (ComprasCvDto vCompra : vListaComprasFueraRango) {
			if(!vListaSeleccioandosSinGuardar.stream().anyMatch(c->c.getId().equals(vCompra.getId())))
			{
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
	public void desasociarComprasfueraRango()
	{
		vComprasIds.clear();
		int contador=0;
		List<ComprasCvDto> listaComprasAuxiliar=vListaComprasFueraRango;
		if(!vListaAsociadas.isEmpty())
		{
			for (ComprasCvDto compra : listaComprasAuxiliar) {
				contador=contador+1;
				vComprasIds.add(compra.getId());
				}
			if(clientesRestController.getClienteRestFormularios().desmarcarComprasFormulario(formulario.getId(),
					vComprasIds).isOk()) {
				RequestContext.getCurrentInstance().execute("toastr.info('Se desmarcó "+contador+" factura(s) del formulario.', 'Información')");
				actualizartotales();
				
			}
			this.vComprasIds.clear();
		}
		

	}
	public void modificarTipoUso(){
		obtenerDescripcionTipoUso(this.formulario);
		modificarCamposFormulario();
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

	public void cambiarDireccionSucursal(){
		this.formulario.setDireccionEmpleador(sucursalSeleccinado.getDireccion());

		this.formulario.setNumeroSucursal(Integer.valueOf(sucursalSeleccinado.getNumeroSucursal()));
		modificarCamposFormulario();
	}
	public void cambiarLugarPresentacion(){
	try {
		guardarDescripcionDepartamento(formulario);
		this.formulario.setDireccionEmpleador(null);
		modificarCamposFormulario();
		consultaContribuyente();
	}
	catch (Exception e){
		e.printStackTrace();
	}

	}

	public boolean validaMontoformuRechazado()
	{
		boolean vRespuesta=true;
		if(formulario.getEstadoFormularioId().equals("RECH"))
		{
			BigDecimal vSumaMontosIpn = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_IPN))
					.map(factura -> factura.getImporteBaseCf())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
			BigDecimal vSumaMontosOtras = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId().equals(ParametrosFRVCC.SIN_MARCA_ESPECIAL))
					.map(factura -> factura.getImporteBaseCf())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal vSumaMontosSieteRg = vListaAsociadas.stream().filter(f->f.getMarcaEspecialId()!=null).filter(fa->fa.getMarcaEspecialId().equals(ParametrosFRVCC.MARCA_ESPECIAL_7RG))
					.map(factura -> factura.getImporteTotalCompra())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
			
			if(vSumaMontosOtras.compareTo(formulario.getDatosEspecificos().getMontoOtrosFormRech())==0
					||vSumaMontosOtras.compareTo(formulario.getDatosEspecificos().getMontoOtrosFormRech())==-1)
			{
				vRespuesta=true;
				if(vSumaMontosIpn.compareTo(formulario.getDatosEspecificos().getMontoIpnFormRech())==0
						||vSumaMontosIpn.compareTo(formulario.getDatosEspecificos().getMontoIpnFormRech())==-1)
				{
					vRespuesta=true;
					if(vSumaMontosSieteRg.compareTo(formulario.getDatosEspecificos().getMontoSieteRgFormRech())==0
							||vSumaMontosSieteRg.compareTo(formulario.getDatosEspecificos().getMontoSieteRgFormRech())==-1)
					{
						vRespuesta=true;
					}
					else {
						vRespuesta=false;
						RequestContext.getCurrentInstance()
						.execute("toastr.error('El monto cambiado de proveedores pertenecientes al regimen Siete-Rg no debe superar la sumatoria del monto declarado en el formulario Original', 'Error!')");
						cargarDetalleFormulario(this.formulario);
					}
				}
				else {
					vRespuesta=false;
					RequestContext.getCurrentInstance()
					.execute("toastr.error('El monto cambiado de proveedores nacionales no debe superar la sumatoria el monto declarado en el formulario Original', 'Error!')");
					cargarDetalleFormulario(this.formulario);
				}
			}
			else {
				vRespuesta=false;
				RequestContext.getCurrentInstance()
				.execute("toastr.error('El monto cambiado no debe superar la sumatoria del monto declarado en el formulario Original', 'Error!')");
				cargarDetalleFormulario(this.formulario);
			}
		}
		return vRespuesta;
	}

	public void retornaValores()
	{
		this.formulario=this.formularioAuxiliar;
		
	}
	public void salvarValores() {
		this.formularioAuxiliar=clientesRestController.getClienteRestFormularios().obtenerFormularioPorId(this.formulario.getId()).getFormularioResponse();
		vListaComprasFueraRango.clear();
		this.vListaComprasFueraRango=vListaAsociadas.stream().filter(c->c.getFechaFactura().isBefore(formulario.getFechaPresentacion().plusDays(-120))).collect(Collectors.toList());
		this.vListaComprasFueraRango.addAll(vListaAsociadas.stream().filter(c->c.getFechaFactura().isAfter(formulario.getFechaPresentacion())).collect(Collectors.toList()));
		
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
	public void obtenerDescripcionTipoUso(FormularioCvDto formulario) {
		Optional<ClasificadorDto> clasificador= listaTipoUso.stream()
				.filter(s -> (formulario.getTipoUsoId()).equals(s.getCodigoClasificador()))
				.findFirst();
		formulario.setTipoUsoDescripcion(clasificador.get().getDescripcion());
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

	public List<SucursalDto> getListaSucursales() {
		return listaSucursales;
	}

	public void setListaSucursales(List<SucursalDto> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}
	public SucursalDto getSucursalSeleccinado() {
		return sucursalSeleccinado;
	}

	public void setSucursalSeleccinado(SucursalDto sucursalSeleccinado) {
		this.sucursalSeleccinado = sucursalSeleccinado;
	}

	public List<ClasificadorDto> getListaTipoUso() {
		return listaTipoUso;
	}

	public void setListaTipoUso(List<ClasificadorDto> listaTipoUso) {
		this.listaTipoUso = listaTipoUso;
	}
}
