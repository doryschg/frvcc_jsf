package bo.gob.sin.sre.fac.frvcc.jsf.reports;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sre.fac.frvcc.jsf.controller.ClientesRestController;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("deprecation")
@ManagedBean(name = "reportesController")
@ViewScoped
public class ReportesController implements Serializable {
	private static final long serialVersionUID = 2049496448720776643L;
	private static final Logger LOG = LoggerFactory.getLogger(ReportesController.class);
	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;
	public void reporteFormularios(List<FormularioCvDto> pListaFormularios, String pTituloReporte, String pTituloDetalle) {
		LOG.info("lista formularios {}. titulo reporte {}. titulo detalle {}.", pListaFormularios.toString(), pTituloReporte, pTituloDetalle);
		try {
			for(FormularioCvDto form: pListaFormularios) {
				if (form.getMesPeriodo() < 9) {
					form.setPeriodo("0" + form.getMesPeriodo() + "/" + form.getAnioPeriodo()); }
				else { form.setPeriodo(form.getMesPeriodo() + "/" + form.getAnioPeriodo()); }
			}
		byte[] reportePdf = null;
		File vReporte = null;
			JasperPrint vReporteJasper = null;
			Map<String, Object> vParametrosReporte = new HashMap<String, Object>();
			vParametrosReporte.put("tituloReporte", pTituloReporte);
			vParametrosReporte.put("cantidadComprasCfIpnSum",
					pListaFormularios.stream().mapToInt(form ->
							form.getCantidadComprasCfIpn()).sum());
			vParametrosReporte.put("cantidadComprasCfOtrasSum",
					pListaFormularios.stream().mapToInt(form ->
							form.getCantidadComprasCfOtras()).sum());
			vParametrosReporte.put("cantidadComprasSdCfSum",
					pListaFormularios.stream().mapToInt(form ->
							form.getCantidadComprasSdCf()).sum());
			vParametrosReporte.put("totalComprasCfIpnRedSum",
					pListaFormularios.stream().map(form ->
							form.getTotalComprasCfIpnRed()).reduce(BigInteger.ZERO, BigInteger::add));
			vParametrosReporte.put("totalComprasCfOtrasRedSum",
					pListaFormularios.stream().map(form ->
							form.getTotalComprasCfOtrasRed()).reduce(BigInteger.ZERO, BigInteger::add));
			vParametrosReporte.put("totalComprasSdCfRedSum",
					pListaFormularios.stream().map(form ->
							form.getTotalComprasSdCfRed()).reduce(BigInteger.ZERO, BigInteger::add));
			vParametrosReporte.put("determinacionPagoCfRedSum",
					pListaFormularios.stream().map(form ->
							form.getDeterminacionPagoCfRed()).reduce(BigInteger.ZERO, BigInteger::add));
			vParametrosReporte.put("determinacionPagoSdCfRedSum",
					pListaFormularios.stream().map(form ->
							form.getDeterminacionPagoSdCfRed()).reduce(BigInteger.ZERO, BigInteger::add));
			String vImgCabecera = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("resources/images/ISOLOGOTIPO-SIN-HP.png");
			vParametrosReporte.put("logoPath", vImgCabecera);
			vParametrosReporte.put(JRParameter.REPORT_LOCALE, Locale.US);
			String vRutaReporte = "reports/AgenteRetencion/ReporteFormulariosAgente.jasper";
			vRutaReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath(vRutaReporte);
			vReporte = new File(vRutaReporte);
			vReporteJasper = JasperFillManager.fillReport(vReporte.getPath(), vParametrosReporte,
					new JRBeanCollectionDataSource(pListaFormularios));
			reportePdf = JasperExportManager.exportReportToPdf(vReporteJasper);
			respuestaBase64 = Base64.getEncoder().encodeToString(reportePdf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void reporteResumenConsolidados(List<ConsolidadorDto> pListaConsolidados, ConsolidadorDto pConsolidador) {
		LOG.info("lista formularios {}. titulo reporte {}.", pListaConsolidados.toString(), pConsolidador.getTotalDeterminacionPagoCuenta());
		try {
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String fechaImpresionCadena = pConsolidador.getFechaImpresion().format(formatters);
			byte[] reportePdf = null;
			File vReporte = null;
			JasperPrint vReporteJasper = null;
			Map<String, Object> vParametrosReporte = new HashMap<String, Object>();
			vParametrosReporte.put("nitAgente", pConsolidador.getNitAgente());
			vParametrosReporte.put("razonSocialAgente", pConsolidador.getRazonSocialAgente());
			vParametrosReporte.put("fechaImpresion", fechaImpresionCadena);
			vParametrosReporte.put("periodo", pConsolidador.getPeriodoDescripcion());
			vParametrosReporte.put("gestion", pConsolidador.getGestion());
			vParametrosReporte.put("administracion", pConsolidador.getAdministracionDescripcion());
			vParametrosReporte.put("totalDeterminacionPagoCuentaCf", pConsolidador.getTotalDeterminacionPagoCuenta());
			vParametrosReporte.put("totalPagoCuenta", pConsolidador.getTotalPagoCuenta());
			vParametrosReporte.put("cantidadFormularios", pConsolidador.getCantidadFormularios());
			vParametrosReporte.put(JRParameter.REPORT_LOCALE, Locale.US);
			String vImgCabecera = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("resources/images/ISOLOGOTIPO-SIN-HP.png");
			vParametrosReporte.put("logoPath", vImgCabecera);
			String vRutaReporte = "reports/AgenteRetencion/ReporteConsolidacionAgente.jasper";
			vRutaReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath(vRutaReporte);
			vReporte = new File(vRutaReporte);
			vReporteJasper = JasperFillManager.fillReport(vReporte.getPath(), vParametrosReporte,
					new JRBeanCollectionDataSource(pListaConsolidados));
			reportePdf = JasperExportManager.exportReportToPdf(vReporteJasper);
			respuestaBase64 = Base64.getEncoder().encodeToString(reportePdf);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reporteFacturaElectronica(String pCuf, Long pNitEmisor, Long pNumeroFactura) throws JRException {
		byte[] reportePdf = null;
		File vReporte = null;
		RespuestaRepresentacionGraficaDto vResultado = clientesRestController.getClienteRestSfe()
				.obtenerRepresentacionGraficaFe(pCuf, pNitEmisor, pNumeroFactura);
		if (vResultado.isTransaccion()) {
			respuestaBase64 = vResultado.getArchivoBase64();
		} else {
			respuestaBase64 ="";
			RequestContext.getCurrentInstance().execute("toastr.info('"+vResultado.getRespuestaMensajes()+"', 'info')");
		}

	}
	public void reporteEnvioLibrocompras(String userName,LibroCvDto pLibro, List<ComprasCvDto> pCompras, 
			List<ComprasNotasCvDto> pComprasNotas) throws JRException{
		LOG.info("libro {}.", pLibro);
		byte[] reportePdf = null;
		File vReporte = null;
		try {
			JasperPrint vReporteJasper = null;
			Map<String, Object> vParamReporte = new HashMap<String, Object>();
			vParamReporte.put(JRParameter.REPORT_LOCALE, Locale.US);
			vParamReporte.put("usuarioGenerador", userName);
			vParamReporte.put("nitSolicitante", pLibro.getNit());
			vParamReporte.put("razonSocial", pLibro.getRazonSocial());
			vParamReporte.put("periodoEnviado", pLibro.getMesPeriodo()+"");
			vParamReporte.put("cantidadCompras", (int)pCompras.stream().count());
			vParamReporte.put("comprasActGravadas", pCompras.stream().filter(
					f->f.getTipoCompraId().equals(ParametrosFRVCC.TIPO_COMPRA_ACTIVIDADES_GRAVADAS)).
					map(factura -> factura.getImporteBaseCf())
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			vParamReporte.put("importeTotalCompras", pLibro.getTotalCompras());
			vParamReporte.put("totalComprasGravadas", pCompras.stream().filter(
					f->f.getTipoCompraId().equals(ParametrosFRVCC.TIPO_COMPRA_ACTIVIDADES_GRAVADAS)|| f.getTipoCompraId().equals(ParametrosFRVCC.TIPO_COMPRA_ACTIVIDADES_NO_GRAVADAS)).
					map(factura -> factura.getImporteBaseCf())
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			vParamReporte.put("comprasNoDiscrimi", pCompras.stream().filter(
					f->!f.getTipoCompraId().equals(ParametrosFRVCC.TIPO_COMPRA_ACTIVIDADES_GRAVADAS)&&!f.getTipoCompraId().equals(ParametrosFRVCC.TIPO_COMPRA_ACTIVIDADES_NO_GRAVADAS)).
					map(factura -> factura.getImporteBaseCf())
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			vParamReporte.put("devolucionesPeriodo", pComprasNotas.stream().map(nota -> nota.getCreditoFiscalNota())
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			vParamReporte.put("descuentosPeriodos", pCompras.stream().map(factura -> factura.getDescuento())
					.reduce(BigDecimal.ZERO, BigDecimal::add));

			String vImgCabecera = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("resources/images/logo.png");
			vParamReporte.put("logoPath", vImgCabecera);
			String vRutaReporte = "reports/compras/ReporteDetalleEnvioLCompras.jasper";
			vRutaReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath(vRutaReporte);
			vReporte = new File(vRutaReporte);
			vReporteJasper = JasperFillManager.fillReport(vReporte.getPath(), vParamReporte, new JREmptyDataSource());
			reportePdf = JasperExportManager.exportReportToPdf(vReporteJasper);
			respuestaBase64 = Base64.getEncoder().encodeToString(reportePdf);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String respuestaBase64 = "";

	public String getRespuestaBase64() {
		return respuestaBase64;
	}

	public void setRespuestaBase64(String respuestaBase64) {
		this.respuestaBase64 = respuestaBase64;
	}

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

}
