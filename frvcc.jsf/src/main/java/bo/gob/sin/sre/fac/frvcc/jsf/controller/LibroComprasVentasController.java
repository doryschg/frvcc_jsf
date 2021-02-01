package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.LibroCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaCompraSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaLibrosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.TotalLibroComprasEstandarDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.TotalesLibroComprasEstandarDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.TotalesLibroComprasNotasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaAsociacionComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaNotasDebitoCreditoModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
import net.sf.jasperreports.engine.JRException;

@ManagedBean(name = "libroComprasVentasController")
@ViewScoped
public class LibroComprasVentasController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(LibroComprasVentasController.class);
	private static final long serialVersionUID = 1L;
	private List<LibroCvDto> vListaLibros = new ArrayList<>();
	private List<ComprasCvDto> vListaAsociadas = new ArrayList<>();
	private List<ComprasNotasCvDto> vListaNotasAsociadas = new ArrayList<>();
	private LibroCvDto libroSeleccionado;
	private LibroCvDto libroNuevo;
	private LibroCvDto libroAuxiliar;
	private TotalLibroComprasEstandarDto totalesEstandar;
	private List<TotalesLibroComprasEstandarDto> vListTotales;
	private List<TotalesLibroComprasNotasDto> vListTotalesNotas;
	private boolean esNewton;
	private boolean verDetalle;
	private boolean conMovimiento;
	private int mesPeriodo = 0;
	private int anioPeriodo = 2020;
	private LazyDataModel<ComprasCvDto> facturasComprasLazy;
	private LazyDataModel<ComprasNotasCvDto> facturasComprasNotasLazy;
	private List<ComprasCvDto> facturasAscSelect = new ArrayList<>();
	private List<String> vListaComprasIds = new ArrayList<>();
	private List<String> vListaComprasNotasIds = new ArrayList<>();
	private List<ComprasNotasCvDto> notasAscSelect = new ArrayList<>();
	private List<String> vListaMeses=new ArrayList<>();
	

	private BigDecimal vSumaTotalImportesTotales = BigDecimal.ZERO;
	private BigDecimal vSumaTotalImportesNSCF = BigDecimal.ZERO;
	private BigDecimal vSumaTotalDesceuntos = BigDecimal.ZERO;
	private BigDecimal vSumaTotalImportesBCF = BigDecimal.ZERO;
	private BigDecimal vSumaTotalCF = BigDecimal.ZERO;
	private BigDecimal vSumaTotalSubTotales = BigDecimal.ZERO;

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;

	@ManagedProperty(value = "#{clasificadorController}")
	private ClasificadorController clasificadorController;

	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@PostConstruct
	public void init() {
		System.err.println("enta al init");
		cargarMeses();
		Calendar fecha = new GregorianCalendar();
		anioPeriodo = fecha.get(Calendar.YEAR);
		mesPeriodo = fecha.get(Calendar.MONTH) + 1;
		cargarLibros(mesPeriodo, anioPeriodo);
		vListTotales = new ArrayList<>();
		for (ClasificadorDto clasificador : clasificadorController.getvListaClasificadoresTiposCompra()) {
			vListTotales.add(new TotalesLibroComprasEstandarDto(clasificador.getCodigoClasificador(),
					clasificador.getDescripcion(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
					BigDecimal.ZERO, BigDecimal.ZERO));
		}
		esNewton=contextoModel.getEsNewton().equals("S")?true:false;
		
		setVerDetalle(false);
		setConMovimiento(true);
		nuevoLibro();
	}

	public void cargarLibros(int mesPeriodo, int gestionPeriodo) {
		vListaLibros = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, gestionPeriodo);
		filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, mesPeriodo);
		filtros.adicionar("nit", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");


		RespuestaLibrosDto lista = clientesRestController.getClienteRestLibros()
				.obtenerLibrosActivos(filtros.getFiltros());
		if(lista.isOk())
		vListaLibros = lista.getLibrosReponse();
		System.err.println(vListaLibros.size());

	}

	public void cargarLibrosPeriodoActual() {
		setVerDetalle(false);
		vListaLibros = new ArrayList<>();
		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.anioPeriodo);
		if (mesPeriodo != 13) {
			if(esNewton)
			{
				filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, this.mesPeriodo);
			}
			else
			{
				filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, 12);
			}
			
		}
		filtros.adicionar("nit", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		RespuestaLibrosDto lista = clientesRestController.getClienteRestLibros()
				.obtenerLibrosActivos(filtros.getFiltros());
		if(lista.isOk())
		{
			vListaLibros = lista.getLibrosReponse();
		}

		System.err.println(vListaLibros.size());

	}

	public void cargarDetallerLibro(LibroCvDto pLibro) {
		setLibroSeleccionado(pLibro);
		totalesEstandar = new TotalLibroComprasEstandarDto();
		totalesEstandar.inicializar();
		FiltroCriteria filtros = new FiltroCriteria();

		filtros.adicionar("libroId", OperadorCriteria.IGUAL, pLibro.getId());
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");

		vListaAsociadas = new ArrayList<>();
		cargarComprasAsociados(filtros);
		cargarComprasNotasAsociados(filtros);
		actualizartotales();
		setVerDetalle(true);
		generarReporteLibro();
	}
	
	
public void cargarComprasAsociados(FiltroCriteria filtros) {
		
	List<ComprasCvDto> vLista= new ArrayList<ComprasCvDto>();
	
	if (libroSeleccionado.getEstadoLibroId().equals(ParametrosFRVCC.ESTADO_LIBRO_RECTIFICADO)) {
		for (String compraId : libroSeleccionado.getComprasId()) {
			LOG.info("libro rectifia {}", libroSeleccionado.getComprasId());
			ComprasCvDto vCompra = clientesRestController.getClienteRestCompras().obtenerCompraPorId(compraId)
					.getCompraResponse();
			if (vCompra != null)
				vLista.add(vCompra);
		}

	}
	else {
		RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras()
				.obtenerCompras(filtros.getFiltros());
		if (vRespuesta.isOk()) {
			vLista = vRespuesta.getComprasResponse();
		} else {
			vLista = new ArrayList<>();
		
		}
			
	}
	vListaAsociadas.clear();
	vListaAsociadas = vLista;

	}
	
	
	public void cargarComprasNotasAsociados(FiltroCriteria filtros) {
		
		List<ComprasNotasCvDto> vListaNotas= new ArrayList<ComprasNotasCvDto>();
		if (libroSeleccionado.getEstadoLibroId().equals(ParametrosFRVCC.ESTADO_LIBRO_RECTIFICADO)) {
			for (String notaId : libroSeleccionado.getComprasNotasId()) {
				LOG.info("NOTA ID {}", libroSeleccionado.getComprasNotasId());
				ComprasNotasCvDto vNota = clientesRestController.getClienteRestComprasNotas().obtenerCompraNotaPorId(notaId).getCompraNotaResponse();
				RespuestaCompraSingleDto compra=clientesRestController.getClienteRestCompras().obtenerCompraPorId(vNota.getCompraId());
				if(compra!=null && compra.isOk()) {
					vNota.setFechaFacturaOriginal(compra.getCompraResponse().getFechaFactura());
					vNota.setNumeroFacturaOriginal(compra.getCompraResponse().getNumeroFactura());
					vNota.setCodigoAutorizacionOriginal(compra.getCompraResponse().getCodigoAutorizacion());
					vNota.setImporteTotalOriginal(compra.getCompraResponse().getImporteTotalCompra());
				}
				if (vNota != null)
					vListaNotas.add(vNota);
			}
		
		}
		else {
			vListaNotas = clientesRestController.getClienteRestComprasNotas()
					.obtenerComprasNota(filtros.getFiltros()).getComprasNotasResponse();
			for(ComprasNotasCvDto compraNota: vListaNotas) {
				RespuestaCompraSingleDto compra=clientesRestController.getClienteRestCompras().obtenerCompraPorId(compraNota.getCompraId());
				mensajesBean.addMensajes(compra);
				if(compra!=null && compra.isOk()) {
					compraNota.setFechaFacturaOriginal(compra.getCompraResponse().getFechaFactura());
					compraNota.setNumeroFacturaOriginal(compra.getCompraResponse().getNumeroFactura());
					compraNota.setCodigoAutorizacionOriginal(compra.getCompraResponse().getCodigoAutorizacion());
					compraNota.setImporteTotalOriginal(compra.getCompraResponse().getImporteTotalCompra());
				}
			}
			
		}
		
		vListaNotasAsociadas.clear();
		vListaNotasAsociadas = vListaNotas;
		
	}

	public void actualizartotales() {
		System.out.println("lista asociados......................." + vListaAsociadas.toString());
		for (TotalesLibroComprasEstandarDto vTotal : vListTotales) {
			vSumaTotalImportesTotales = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getImporteTotalCompra()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vSumaTotalImportesNSCF = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getImporteNoSujetoCf()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vSumaTotalDesceuntos = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getDescuento()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vSumaTotalImportesBCF = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getImporteBaseCf()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vSumaTotalCF = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getCreditoFiscal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vSumaTotalSubTotales = vListaAsociadas.stream()
					.filter(obj -> obj.getTipoCompraId().equals(vTotal.getTipoCompraId()))
					.map(factura -> factura.getSubtotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			vTotal.setTotalCompras(vSumaTotalImportesTotales);
			vTotal.setImporteNoSujetoCf(vSumaTotalImportesNSCF);
			vTotal.setDescuentos(vSumaTotalDesceuntos);
			vTotal.setImporteBaseCf(vSumaTotalImportesBCF);
			vTotal.setCreditoFiscal(vSumaTotalCF);
			vTotal.setSubtotal(vSumaTotalSubTotales);

		}
		vSumaTotalImportesTotales = vListaAsociadas.stream().map(factura -> factura.getImporteTotalCompra())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		vSumaTotalImportesNSCF = vListaAsociadas.stream().map(factura -> factura.getImporteNoSujetoCf())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		vSumaTotalDesceuntos = vListaAsociadas.stream().map(factura -> factura.getDescuento()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		vSumaTotalImportesBCF = vListaAsociadas.stream().map(factura -> factura.getImporteBaseCf())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		vSumaTotalCF = vListaAsociadas.stream().map(factura -> factura.getCreditoFiscal()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		vSumaTotalSubTotales = vListaAsociadas.stream().map(factura -> factura.getSubtotal()).reduce(BigDecimal.ZERO,
				BigDecimal::add);

	}

	public void adicionarLibro() {
		if (getvListaLibros() == null) {
			setvListaLibros(new ArrayList<LibroCvDto>());
		}
		LibroCvDto vNuevoLibro = getLibroNuevo();
		vNuevoLibro.setId(UUID.randomUUID().toString());
		Optional<?> vFormularioEncontrado = this.getvListaLibros().stream()
				.filter(x -> x.getId().equals(vNuevoLibro.getId())).findFirst();

		if (!vFormularioEncontrado.isPresent()) {
			ContextoJSF contexto = new ContextoJSF();
			vNuevoLibro.setAnioPeriodo(this.getAnioPeriodo());
			
			vNuevoLibro.setUsuarioRegistro(contexto.getUsuario().getUsuarioId());
			vNuevoLibro.setUsuarioUltimaModificacion(contexto.getUsuario().getUsuarioId());
			vNuevoLibro.setIfc(contexto.getUsuario().getPersonaId());
			vNuevoLibro.setNit(Long.parseLong(contexto.getUsuario().getNit()));
			vNuevoLibro.setRazonSocial(contexto.getUsuario().getRazonSocial());
			vNuevoLibro.setCodAdministracion((long)contextoModel.getCodigoAdministracion());
			vNuevoLibro.setAdministracion(contextoModel.getDescripcionAdministracion());
//			vNuevoLibro.setFechaPresentacion(LocalDate.now());
			vNuevoLibro.setTipoPresentacionId(ParametrosFRVCC.TIPO_PRESENTACION_FORMULARIO_ORIGINAL);
			if(esNewton)
			{
				vNuevoLibro.setPeriodicidadId(ParametrosFRVCC.PERIODICIDAD_MENSUAL);
				vNuevoLibro.setCantidadPeriodicidad(ParametrosFRVCC.CANTIDAD_PERIODICIDAD_LIBRO_MENSUAL);
				vNuevoLibro.setMesPeriodo(this.getMesPeriodo());
			}
			else
			{
				vNuevoLibro.setPeriodicidadId(ParametrosFRVCC.PERIODICIDAD_ANUAL);
				vNuevoLibro.setCantidadPeriodicidad(ParametrosFRVCC.CANTIDAD_PERIODICIDAD_LIBRO_ANUAL);
				vNuevoLibro.setMesPeriodo(12);
			}
			
			String vCad=vNuevoLibro.getMesPeriodo()<10?"0":"";
			vNuevoLibro.setNombreLibro("LC_" + vNuevoLibro.getAnioPeriodo() + "_"+ vCad+ vNuevoLibro.getMesPeriodo() + "_"
					+ vNuevoLibro.getNit());
			vNuevoLibro.setEstadoId(ParametrosFRVCC.ESTADO_FORMULARIO_REGISTRADO);
			ResultadoGenericoDto<String> rep = clientesRestController.getClienteRestLibros()
					.guardarLibroNuevo(vNuevoLibro);
			if (rep.isOk()) {
				this.getvListaLibros().add(vNuevoLibro);
			}
			mensajesBean.addMensajes(rep);
			this.nuevoLibro();
		} else {
			this.nuevoLibro();
			RequestContext.getCurrentInstance().execute("toastr.info('El libro ya existe', 'Informacion')");
		}
		cargarLibrosPeriodoActual();
//		vListaLibros.add(vNuevoLibro);
	}

	public void cargarListaComprasRegistradas() {

		try {
			System.out.println("libro..............." + libroSeleccionado.toString());
			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

			LazyConsultaAsociacionComprasModel lazyConsulAsModel = new LazyConsultaAsociacionComprasModel(
					new ArrayList<ComprasCvDto>(), pFilters, this.clientesRestController.getClienteRestCompras(), true,
					mensajesBean);
			this.setFacturasComprasLazy(lazyConsulAsModel);
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance()
					.execute("toastr.error('Ocurrio un error al obtener facturas.', 'Error!')");

		}

	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();

//		filtros.adicionar("libroId", OperadorCriteria.IS_NULL, "null");
		filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_REGISTRADO);
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		LocalDate fechaFacturaHasta = LocalDate.of(libroSeleccionado.getAnioPeriodo(),
				libroSeleccionado.getMesPeriodo(), 30);
		filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFacturaHasta.toString());
//		filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaFacturaValidaDesde(fechaFacturaHasta).toString());
		filtros.adicionar("creditoFiscal", OperadorCriteria.MAYOR, "0");
		filtros.adicionar("marcaEspecialId", OperadorCriteria.DISTINTO, ParametrosFRVCC.MARCA_ESPECIAL_IPN);

		return filtros.getFiltros();
	}

	public void cargarListaComprasNotasRegistradas() {

		try {
			System.out.println("libro..............." + libroSeleccionado.toString());
			LinkedHashMap<String, Serializable> pFilters = obtenerCriteriosNotas();

			LazyConsultaNotasDebitoCreditoModel lazyConsulAsModel = new LazyConsultaNotasDebitoCreditoModel(
					new ArrayList<ComprasNotasCvDto>(), pFilters,
					this.clientesRestController.getClienteRestComprasNotas(), true, mensajesBean);
			this.setFacturasComprasNotasLazy(lazyConsulAsModel);
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance().execute("toastr.error('Ocurrio un error al obtener notas.', 'Error!')");

		}

	}

	private LinkedHashMap<String, Serializable> obtenerCriteriosNotas() {
		FiltroCriteria filtros = new FiltroCriteria();

//		filtros.adicionar("libroId", OperadorCriteria.IS_NULL, "null");
		filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_USO_REGISTRADO);
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		LocalDate fechaFacturaHasta = LocalDate.of(libroSeleccionado.getAnioPeriodo(),
				libroSeleccionado.getMesPeriodo(), 30);
		filtros.adicionar("fechaNota", OperadorCriteria.MENOR_IGUAL, fechaFacturaHasta.toString());
//		filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaFacturaValidaDesde(fechaFacturaHasta).toString());

		return filtros.getFiltros();
	}

	public void asociarfacturas() {
		if (!facturasAscSelect.isEmpty()) {

			for (ComprasCvDto factura : facturasAscSelect) {
				vListaComprasIds.add(factura.getId());
			}

			ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestLibros()
					.seleccionarComprasLibro(libroSeleccionado.getId(), vListaComprasIds);
			if (vResp.isOk()) {
				vListaComprasIds.clear();
				cargarListaComprasRegistradas();
				cargarDetallerLibro(libroSeleccionado);
				RequestContext.getCurrentInstance().execute("toastr.success('Se adicionaron " + facturasAscSelect.size()
						+ " facturas al libro .', 'Exitoso!')");
				facturasAscSelect = new ArrayList<>();
			} else
				mensajesBean.addMensajes(vResp);

		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('Debe seleccionar facturas para adicionar al libro', 'Error!')");
		}

	}

	public void devolverFactura(ComprasCvDto pFactura) {
		
		vListaComprasIds.add(pFactura.getId());
		ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestLibros().desmarcarComprasLibro
				(libroSeleccionado.getId(), vListaComprasIds);

		if (this.getvListaAsociadas().stream().anyMatch(factura -> factura.getId() == pFactura.getId())
				&& resp.isOk()) {
			vListaComprasIds.clear();
			this.getvListaAsociadas().remove(pFactura);
		}
		mensajesBean.addMensajes(resp);
		cargarDetallerLibro(libroSeleccionado);

	}

	public void devolverNota(ComprasNotasCvDto pCompraNota) {

		vListaComprasNotasIds.add(pCompraNota.getId());
		ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestLibros().desmarcarComprasNotasLibro
				(libroSeleccionado.getId(), vListaComprasNotasIds);

		if (this.getvListaNotasAsociadas().stream().anyMatch(nota -> nota.getId() == pCompraNota.getId())
				&& resp.isOk()) {
			vListaComprasIds.clear();
			this.getvListaNotasAsociadas().remove(pCompraNota);
		}
		mensajesBean.addMensajes(resp);
		cargarDetallerLibro(libroSeleccionado);

	}

	public void asociarNotas() {
		if (!notasAscSelect.isEmpty()) {

			for (ComprasNotasCvDto factura : notasAscSelect) {
				vListaComprasNotasIds.add(factura.getId());
			}

			ResultadoGenericoDto<String> vResp = clientesRestController.getClienteRestLibros()
					.seleccionarComprasNotasLibro(libroSeleccionado.getId(), vListaComprasNotasIds);
			if (vResp.isOk()) {
				vListaComprasNotasIds.clear();
				cargarListaComprasNotasRegistradas();
				cargarDetallerLibro(libroSeleccionado);
				RequestContext.getCurrentInstance().execute("toastr.success('Se adicionaron " + notasAscSelect.size()
						+ " notas al libro .', 'Exitoso!')");
				notasAscSelect = new ArrayList<>();
			} else
				mensajesBean.addMensajes(vResp);

		} else {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('Debe seleccionar notas para adicionar al libro', 'Error!')");
		}
	}
	public void devolverFacturasTodas() {
		vListaComprasIds.clear();
		int contador=0;
		List<ComprasCvDto> listaComprasAuxiliar=vListaAsociadas;
		if(vListaAsociadas.size()>0)
		{
			
			for (ComprasCvDto compra : listaComprasAuxiliar) {
				contador=contador+1;
				System.err.println("entra al borrar todas las facturas "+contador);
				vListaComprasIds.add(compra.getId());
				
			}
			if(clientesRestController.getClienteRestLibros().desmarcarComprasLibro(libroSeleccionado.getId(), vListaComprasIds).isOk())
			{
				
				RequestContext.getCurrentInstance().execute("toastr.info('Se desmarcaron "+contador+" Facturas', 'Información')");
				this.vListaAsociadas=new ArrayList<>();
				actualizartotales();
				
			}
			
			
		
		}
		this.vListaComprasIds.clear();
		
		
	}
	public void devolverNotasTodas() {
		vListaComprasNotasIds.clear();
		int contador=0;
		List<ComprasNotasCvDto> listaComprasAuxiliar=vListaNotasAsociadas;
		if(vListaNotasAsociadas.size()>0)
		{
			
			for (ComprasNotasCvDto compra : listaComprasAuxiliar) {
				contador=contador+1;
				System.err.println("entra al borrar todas las facturas "+contador);
				vListaComprasNotasIds.add(compra.getId());
				
			}
			if(clientesRestController.getClienteRestLibros().desmarcarComprasNotasLibro(libroSeleccionado.getId(), vListaComprasNotasIds).isOk())
			{
				
				RequestContext.getCurrentInstance().execute("toastr.info('Se desmarcaron "+contador+" Notas', 'Información')");
				this.vListaNotasAsociadas=new ArrayList<>();
				actualizartotales();
				
			}
			
			
		
		}
		this.vListaComprasNotasIds.clear();
		
		
	}
	public void declarar() {
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestLibros()
				.declararLibro(libroSeleccionado, contextoModel.getCodigoAdministracion());
		if (vRespuesta.isOk()) {
			cargarLibrosPeriodoActual();
			this.libroAuxiliar = clientesRestController.getClienteRestLibros()
					.obtenerLibroPorId(this.libroSeleccionado.getId()).getLibroResponse();
			this.libroSeleccionado = this.libroAuxiliar;
			mensajesBean.addMensajes(vRespuesta);
			generarReporteLibro();
			RequestContext.getCurrentInstance().execute("controlClick('formPrincipal:reportePdf:btnVisorPdf');");

		} else {
			mensajesBean.addMensajes(vRespuesta);
		}

	}

	public void modificarTipoCompra(ComprasCvDto pCompra) {

		LOG.info("compra a editar {} ", pCompra.getTipoCompraId());
		ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestCompras()
				.modificarCompraTipoCompraId(pCompra);
		if (vRespuesta.isOk()) {
			mensajesBean.addMensajes(vRespuesta);
		}
	}
	
	public void habilitarRectificacion() {
		
	LOG.info("rectificar {}", libroSeleccionado.getId());
	ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestLibros().habilitarRectificacion(libroSeleccionado.getId());
		if(vRespuesta.isOk())
		{
			mensajesBean.addMensajes(vRespuesta);
			cargarLibrosPeriodoActual();
			
		}
		else
		{
			mensajesBean.addMensajes(vRespuesta);

		}
		
	}
	
	public void generarReporteLibro() {
		
		try {
			LOG.info("reporte archivo {} ",reportesController.getRespuestaBase64());

			reportesController.reporteEnvioLibrocompras(contextoModel.getUsuario().getNombreCompleto(),
					this.libroSeleccionado, vListaAsociadas, vListaNotasAsociadas);
			LOG.info("reporte archivo {} ",reportesController.getRespuestaBase64());
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void nuevoLibro() {
		libroNuevo = new LibroCvDto();
	}
	public void cargarMeses()
	{
		vListaMeses.add("ENERO");
		vListaMeses.add("FEBRERO");
		vListaMeses.add("MARZO");
		vListaMeses.add("ABRIL");
		vListaMeses.add("MAYO");
		vListaMeses.add("JUNIO");
		vListaMeses.add("JULIO");
		vListaMeses.add("AGOSTO");
		vListaMeses.add("SEPTIEMBRE");
		vListaMeses.add("OCTUBRE");
		vListaMeses.add("NOVIEMBRE");
		vListaMeses.add("DICIEMBRE");
	}
	public String obtenerDescripcionByNroMes(int pNroMes)
	{
		return vListaMeses.get(pNroMes-1);
	}

	public List<LibroCvDto> getvListaLibros() {
		return vListaLibros;
	}

	public void setvListaLibros(List<LibroCvDto> vListaLibros) {
		this.vListaLibros = vListaLibros;
	}

	public LibroCvDto getLibroSeleccionado() {
		return libroSeleccionado;
	}

	public void setLibroSeleccionado(LibroCvDto libroSeleccionado) {
		this.libroSeleccionado = libroSeleccionado;
	}

	public LibroCvDto getLibroNuevo() {
		return libroNuevo;
	}

	public void setLibroNuevo(LibroCvDto libroNuevo) {
		this.libroNuevo = libroNuevo;
	}

	public boolean isVerDetalle() {
		return verDetalle;
	}

	public void setVerDetalle(boolean verDetalle) {
		this.verDetalle = verDetalle;
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

	public TotalLibroComprasEstandarDto getTotalesEstandar() {
		return totalesEstandar;
	}

	public void setTotalesEstandar(TotalLibroComprasEstandarDto totalesEstandar) {
		this.totalesEstandar = totalesEstandar;
	}

	public List<ComprasCvDto> getvListaAsociadas() {
		return vListaAsociadas;
	}

	public void setvListaAsociadas(List<ComprasCvDto> vListaAsociadas) {
		this.vListaAsociadas = vListaAsociadas;
	}

	public boolean isConMovimiento() {
		return conMovimiento;
	}

	public void setConMovimiento(boolean conMovimiento) {
		this.conMovimiento = conMovimiento;
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

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public BigDecimal getvSumaTotalImportesTotales() {
		return vSumaTotalImportesTotales;
	}

	public void setvSumaTotalImportesTotales(BigDecimal vSumaTotalImportesTotales) {
		this.vSumaTotalImportesTotales = vSumaTotalImportesTotales;
	}

	public BigDecimal getvSumaTotalImportesNSCF() {
		return vSumaTotalImportesNSCF;
	}

	public void setvSumaTotalImportesNSCF(BigDecimal vSumaTotalImportesNSCF) {
		this.vSumaTotalImportesNSCF = vSumaTotalImportesNSCF;
	}

	public BigDecimal getvSumaTotalDesceuntos() {
		return vSumaTotalDesceuntos;
	}

	public void setvSumaTotalDesceuntos(BigDecimal vSumaTotalDesceuntos) {
		this.vSumaTotalDesceuntos = vSumaTotalDesceuntos;
	}

	public BigDecimal getvSumaTotalImportesBCF() {
		return vSumaTotalImportesBCF;
	}

	public void setvSumaTotalImportesBCF(BigDecimal vSumaTotalImportesBCF) {
		this.vSumaTotalImportesBCF = vSumaTotalImportesBCF;
	}

	public BigDecimal getvSumaTotalCF() {
		return vSumaTotalCF;
	}

	public void setvSumaTotalCF(BigDecimal vSumaTotalCF) {
		this.vSumaTotalCF = vSumaTotalCF;
	}

	public ClasificadorController getClasificadorController() {
		return clasificadorController;
	}

	public void setClasificadorController(ClasificadorController clasificadorController) {
		this.clasificadorController = clasificadorController;
	}

	public List<TotalesLibroComprasEstandarDto> getvListTotales() {
		return vListTotales;
	}

	public void setvListTotales(List<TotalesLibroComprasEstandarDto> vListTotales) {
		this.vListTotales = vListTotales;
	}

	public BigDecimal getvSumaTotalSubTotales() {
		return vSumaTotalSubTotales;
	}

	public void setvSumaTotalSubTotales(BigDecimal vSumaTotalSubTotales) {
		this.vSumaTotalSubTotales = vSumaTotalSubTotales;
	}

	public LazyDataModel<ComprasNotasCvDto> getFacturasComprasNotasLazy() {
		return facturasComprasNotasLazy;
	}

	public void setFacturasComprasNotasLazy(LazyDataModel<ComprasNotasCvDto> facturasComprasNotasLazy) {
		this.facturasComprasNotasLazy = facturasComprasNotasLazy;
	}

	public List<ComprasNotasCvDto> getNotasAscSelect() {
		return notasAscSelect;
	}

	public void setNotasAscSelect(List<ComprasNotasCvDto> notasAscSelect) {
		this.notasAscSelect = notasAscSelect;
	}

	public List<ComprasNotasCvDto> getvListaNotasAsociadas() {
		return vListaNotasAsociadas;
	}

	public void setvListaNotasAsociadas(List<ComprasNotasCvDto> vListaNotasAsociadas) {
		this.vListaNotasAsociadas = vListaNotasAsociadas;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public LibroCvDto getLibroAuxiliar() {
		return libroAuxiliar;
	}

	public void setLibroAuxiliar(LibroCvDto libroAuxiliar) {
		this.libroAuxiliar = libroAuxiliar;
	}

	public List<String> getvListaComprasIds() {
		return vListaComprasIds;
	}

	public void setvListaComprasIds(List<String> vListaComprasIds) {
		this.vListaComprasIds = vListaComprasIds;
	}

	public boolean isEsNewton() {
		return esNewton;
	}

	public void setEsNewton(boolean esNewton) {
		this.esNewton = esNewton;
	}

	public List<String> getvListaComprasNotasIds() {
		return vListaComprasNotasIds;
	}

	public void setvListaComprasNotasIds(List<String> vListaComprasNotasIds) {
		this.vListaComprasNotasIds = vListaComprasNotasIds;
	}

	public List<String> getvListaMeses() {
		return vListaMeses;
	}

	public void setvListaMeses(List<String> vListaMeses) {
		this.vListaMeses = vListaMeses;
	}

	public List<TotalesLibroComprasNotasDto> getvListTotalesNotas() {
		return vListTotalesNotas;
	}

	public void setvListTotalesNotas(List<TotalesLibroComprasNotasDto> vListTotalesNotas) {
		this.vListTotalesNotas = vListTotalesNotas;
	}
	

}
