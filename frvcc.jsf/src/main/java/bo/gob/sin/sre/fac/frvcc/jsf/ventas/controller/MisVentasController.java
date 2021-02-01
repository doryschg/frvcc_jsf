package bo.gob.sin.sre.fac.frvcc.jsf.ventas.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.controller.ClasificadorController;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaCompraSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.LazyConsultaVentasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.VentasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.service.EstandarService;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import net.sf.jasperreports.engine.JRException;

/**
 * @author sergio.ichaso
 *
 */

//@ManagedBean(name = "misVentasController")
//@ViewScoped
@Named
@ViewScoped
public class MisVentasController implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MisVentasController.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1390719855003851314L;
	long ifc;
	long nit;

	private String numeroDocumento;
	private Integer periodo;
	private Integer gestion;
	private String estadoUsoId;
	private LocalDate fechaFactura;
	private Long documentoCliente;
	private String conDerechoCf;
	private Long nroFactura;

	private String modalidad = "";
	private LazyDataModel<VentasCvDto> facturasLazy;
	private List<VentasCvDto> facturas = new ArrayList<>();

	private List<VentasCvDto> facturasSeleccionadas = new ArrayList<>();

	private List<VentasCvDto> misFacturas = new ArrayList<>();

	private StreamedContent file;
	private DefaultStreamedContent download;
	private boolean consultaRealizada = false;

	@Inject
	private EstandarService service;

	private ReportesController reportesController;
	private ClasificadorController clasificadorController;
	private MensajesBean mensajesBean;

	/**
	 * 
	 */
	public void onLoad() {
		Calendar fecha = new GregorianCalendar();
		gestion = fecha.get(Calendar.YEAR);

		ContextoJSF contexto = new ContextoJSF();
		ifc = contexto.getUsuario().getPersonaId();
		nit = Long.parseLong(contexto.getUsuario().getNit());
		onBuscar();
	}

	public MisVentasController() {
		super();
		this.reportesController = new ReportesController();
		this.mensajesBean = new MensajesBean();
	}

	public void removeFacturas(List<VentasCvDto> pFacturas) {
		this.facturas.removeAll(pFacturas);
	}

	/* Mis Facturas */

	public void onLimpiar() {
		FiltroCriteria filtros = new FiltroCriteria();
		this.setPeriodo(null);
		this.setModalidad(null);
		this.setGestion(null);
		this.setEstadoUsoId(null);
		this.setDocumentoCliente(null);
		this.setFechaFactura(null);
		this.setConDerechoCf(null);
		this.setNroFactura(null);
		this.setFacturasLazy(new LazyConsultaVentasModel(new ArrayList<VentasCvDto>(), filtros.getFiltros(), service,
				false, mensajesBean));
		this.setConsultaRealizada(false);
	}

	public void onBuscar() {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

		this.setFacturasLazy(
				new LazyConsultaVentasModel(new ArrayList<VentasCvDto>(), pFilters, service, true, mensajesBean));
		consultaRealizada = true;
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		if (this.gestion != null && this.periodo == null) {
			LocalDate fechaInicio = LocalDate.of(this.gestion, 1, 1);
			LocalDate fechaFin = LocalDate.of(this.gestion, 12, 31);
			System.out.println("FECHAAA " + fechaInicio + " " + fechaFin);

			filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaInicio);
			filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFin);
		}
		if (this.gestion != null && this.periodo != null) {
			boolean biciesto;
			if ((gestion % 4 == 0) && ((gestion % 100 != 0) || (gestion % 400 == 0)))
				biciesto = false;
			else
				biciesto = true;
			Integer dias = 0;
			switch (periodo) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dias = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				dias = 30;
				break;
			case 2:
				if (biciesto)
					dias = 29;
				else
					dias = 28;
				break;
			}

			LocalDate fechaInicio = LocalDate.of(this.gestion, this.periodo, 1);
			LocalDate fechaFin = LocalDate.of(this.gestion, this.periodo, dias);

			System.out.println("FECHAAA " + fechaInicio + " " + fechaFin);

			filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaInicio);
			filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFin);
		}

		if (this.modalidad != null && this.modalidad != "") {
			if (this.getModalidad().equals(ParametrosFRVCC.MODALIDAD_EN_LINEA)) {
				filtros.adicionar("modalidadId", OperadorCriteria.IN, "FCL,FPW,FEL");
			} else {
				filtros.adicionar("modalidadId", OperadorCriteria.IGUAL, this.getModalidad());
			}

		}
		if (this.nroFactura != null && this.nroFactura != 0) {
			filtros.adicionar("numeroFactura", OperadorCriteria.IGUAL, this.getNroFactura());
		}

		if (this.estadoUsoId != null && this.estadoUsoId != "") {
			filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, this.getEstadoUsoId());
		}
		if (this.documentoCliente != null && this.documentoCliente != 0) {
			filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, this.getDocumentoCliente());
		}
		if (this.fechaFactura != null) {
			filtros.adicionar("fechaFactura", OperadorCriteria.IGUAL, this.getFechaFactura());
		}
		if (this.conDerechoCf != null && this.conDerechoCf != "") {
			filtros.adicionar("conDerechoCf", OperadorCriteria.IGUAL, this.getConDerechoCf());
		}

//		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		System.err.println("Filtros: " + filtros.toString());
		System.err.println("Filtros: " + filtros.getFiltros().toString());
		return filtros.getFiltros();
	}

	public void eliminarCompra(VentasCvDto pCompra) {
		ResultadoGenericoDto<String> resp = service.anularVenta(pCompra);
	mensajesBean.addMensajes(resp);

	}

	public void verDetalleFacturaElectronica(VentasCvDto pFactura) throws JRException {
		// TODO agregar servicio de respuesta reporte
		reportesController.reporteFacturaElectronica(pFactura.getCodigoAutorizacion(), pFactura.getNitProveedor(),
				pFactura.getNumeroFactura());
//		RequestContext.getCurrentInstance().execute("toastr.info('entra al ver detalle', 'info')");
	}

	public void editarCamposCompras(VentasCvDto pCompra) {
//		try {
//			ResultadoGenericoDto<String> vRespuesta = clientesRestController.getClienteRestCompras()
//					.modificarCompra(pCompra);
//
//			if (vRespuesta != null) {
//				mensajesBean.addMensajes(vRespuesta);
//				obtenerCompraActualizada(pCompra);
//			}
//
//		} catch (Exception e) {
//			obtenerCompraActualizada(pCompra);
//			e.printStackTrace();
//		}

	}

	public void cancelarEdicionCamposCompras(VentasCvDto pCompra) {
		obtenerCompraActualizada(pCompra);
	}

	public void obtenerCompraActualizada(VentasCvDto pCompra) {
//		try {
//			RespuestaCompraSingleDto vRespuesta = clientesRestController.getClienteRestCompras()
//					.obtenerCompraPorId(pCompra.getId());
//			VentasCvDto compraActualizada = vRespuesta.getCompraResponse();
//			if (vRespuesta.isOk()) {
//				for (VentasCvDto filaDatoCompra : facturasLazy) {
//					if (filaDatoCompra.getId().equals(compraActualizada.getId())) {
//						filaDatoCompra.setFechaFactura(compraActualizada.getFechaFactura());
//						filaDatoCompra.setImporteTotalCompra(compraActualizada.getImporteTotalCompra());
//						filaDatoCompra.setCodigoControl(compraActualizada.getCodigoControl());
//
//					}
//
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getGestion() {
		return gestion;
	}

	public void setGestion(Integer gestion) {
		this.gestion = gestion;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getEstadoUsoId() {
		return estadoUsoId;
	}

	public void setEstadoUsoId(String estadoUsoId) {
		this.estadoUsoId = estadoUsoId;
	}

	public LazyDataModel<VentasCvDto> getFacturasLazy() {
		return facturasLazy;
	}

	public void setFacturasLazy(LazyDataModel<VentasCvDto> facturasLazy) {
		this.facturasLazy = facturasLazy;
	}

	public List<VentasCvDto> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<VentasCvDto> facturas) {
		this.facturas = facturas;
	}

	public List<VentasCvDto> getFacturasSeleccionadas() {
		return facturasSeleccionadas;
	}

	public void setFacturasSeleccionadas(List<VentasCvDto> facturasSeleccionadas) {
		this.facturasSeleccionadas = facturasSeleccionadas;
	}

	public List<VentasCvDto> getMisFacturas() {
		return misFacturas;
	}

	public void setMisFacturas(List<VentasCvDto> misFacturas) {
		this.misFacturas = misFacturas;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public DefaultStreamedContent getDownload() {
		return download;
	}

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public boolean isConsultaRealizada() {
		return consultaRealizada;
	}

	public void setConsultaRealizada(boolean consultaRealizada) {
		this.consultaRealizada = consultaRealizada;
	}

	public ReportesController getReportesController() {
		return reportesController;
	}

	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}

	public ClasificadorController getClasificadorController() {
		return clasificadorController;
	}

	public void setClasificadorController(ClasificadorController clasificadorController) {
		this.clasificadorController = clasificadorController;
	}

	public String getConDerechoCf() {
		return conDerechoCf;
	}

	public void setConDerechoCf(String conDerechoCf) {
		this.conDerechoCf = conDerechoCf;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public Long getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Long getDocumentoCliente() {
		return documentoCliente;
	}

	public void setDocumentoCliente(Long documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

}
