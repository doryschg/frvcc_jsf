/**
 * 
 */
package bo.gob.sin.sre.fac.frvcc.jsf.controller;

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
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
//import org.apache.ignite.internal.util.typedef.X;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaCompraSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import net.sf.jasperreports.engine.JRException;

/**
 * @author sergio.ichaso
 *
 */

@ManagedBean(name = "componenteBusquedaComprasController")
@ViewScoped
public class ComponenteBusquedaComprasController implements Serializable {
	private static final Logger LOG= LoggerFactory.getLogger(ComponenteBusquedaComprasController.class);


	/**
	 * 
	 */
	private static final long serialVersionUID = -1390719855003851314L;

	private String numeroDocumento;
	private Integer periodo;
	private Integer gestion;
	private String estadoUsoId;
	private LocalDate fechaFactura;
	private Long nitProveedor;
	private String conDerechoCf;
	private Long nroFactura;

	private String modalidad = "";
	private LazyDataModel<ComprasCvDto> facturasLazy;
	private List<ComprasCvDto> facturas = new ArrayList<>();

	private List<ComprasCvDto> facturasSeleccionadas = new ArrayList<>();

	private List<ComprasCvDto> misFacturas = new ArrayList<>();

	private StreamedContent file;
	private DefaultStreamedContent download;
	private boolean consultaRealizada = false;

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;
	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;
	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;
	
	@ManagedProperty(value = "#{clasificadorController}")
	private ClasificadorController clasificadorController;
	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	/**
	 * 
	 */
	public void onLoad()
	{
		Calendar fecha = new GregorianCalendar();
		gestion = fecha.get(Calendar.YEAR);
		onBuscar();
	}
	public ComponenteBusquedaComprasController() {
		super();

	}

	public void removeFacturas(List<ComprasCvDto> pFacturas) {
		this.facturas.removeAll(pFacturas);
	}


	/* Mis Facturas */

	public void onLimpiar() {
		FiltroCriteria filtros = new FiltroCriteria();
		this.setPeriodo(null);
		this.setModalidad(null);
		this.setGestion(null);
		this.setEstadoUsoId(null);
		this.setNitProveedor(null);
		this.setFechaFactura(null);
		this.setConDerechoCf(null);
		this.setNroFactura(null);
		this.setFacturasLazy(new LazyConsultaComprasModel(new ArrayList<ComprasCvDto>(), filtros.getFiltros(),
				this.clientesRestController.getClienteRestCompras(), false,mensajesBean));
		this.setConsultaRealizada(false);
	}

	public void onBuscar() {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

		this.setFacturasLazy(
				new LazyConsultaComprasModel(new ArrayList<ComprasCvDto>(), pFilters, this.clientesRestController.getClienteRestCompras(), true,mensajesBean));
		consultaRealizada = true;
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		if (this.gestion != null && this.periodo == null) {
			LocalDate fechaInicio=LocalDate.of(this.gestion, 1, 1);
			LocalDate fechaFin=LocalDate.of(this.gestion, 12, 31);
			filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaInicio);
			filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFin);
		}
		if (this.gestion != null && this.periodo != null) {
			boolean biciesto;
			if ((gestion % 4 == 0) && ((gestion % 100 != 0) || (gestion % 400 == 0)))
				biciesto=false;
			else
				biciesto=true;
			Integer dias=0;
			switch (periodo) {
			  case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			    dias = 31;
			    break;
			  case 4: case 6: case 9: case 11:
				  dias = 30;
			    break;
			  case 2:	
				  if(biciesto)
			    dias = 29;
			    else
			    	dias=28;
			    break;			
			}
			
			LocalDate fechaInicio=LocalDate.of(this.gestion, this.periodo, 1);
			LocalDate fechaFin=LocalDate.of(this.gestion, this.periodo, dias);
			
			filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaInicio);
			filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFin);
		}

		if (this.modalidad != null && this.modalidad != "") {
			if(this.getModalidad().equals(ParametrosFRVCC.MODALIDAD_EN_LINEA)) {
				filtros.adicionar("modalidadId", OperadorCriteria.IN,"FCL,FPW,FEL");
			}
			else {
				filtros.adicionar("modalidadId", OperadorCriteria.IGUAL, this.getModalidad());

			}
			
		}
		if(this.nroFactura!=null && this.nroFactura!=0)
		{
			filtros.adicionar("numeroFactura", OperadorCriteria.IGUAL, this.getNroFactura());
		}

		if (this.estadoUsoId != null && this.estadoUsoId != "") {
			filtros.adicionar("estadoUsoId", OperadorCriteria.IGUAL, this.getEstadoUsoId());
		}
		if (this.nitProveedor != null && this.nitProveedor != 0) {
			filtros.adicionar("nitProveedor", OperadorCriteria.IGUAL, this.getNitProveedor());
		}
		if (this.fechaFactura != null) {
			filtros.adicionar("fechaFactura", OperadorCriteria.IGUAL, this.getFechaFactura());
		}
		if(this.conDerechoCf!=null&&this.conDerechoCf!="") {
			filtros.adicionar("conDerechoCf", OperadorCriteria.IGUAL, this.getConDerechoCf());
		}
	
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL,contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		return filtros.getFiltros();
	}

	public void eliminarCompra(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestCompras().eliminarCompra(pCompra);
		mensajesBean.addMensajes(resp);

	}

	public void verDetalleFacturaElectronica(ComprasCvDto pFactura) throws JRException {
		//TODO agregar servicio de respuesta reporte
		reportesController.reporteFacturaElectronica(pFactura.getCodigoAutorizacion(),pFactura.getNitProveedor(),pFactura.getNumeroFactura());
//		RequestContext.getCurrentInstance().execute("toastr.info('entra al ver detalle', 'info')");
	}

	public void ExcelComprasConsultadas() throws IOException {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		
		long vCantidad=clientesRestController.getClienteRestCompras().obtenerTotalCompras(pFilters);
		if(vCantidad>0)
		{
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "fechaFactura");
			pFilters.put("order", "DESC");
			RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters);
			if(vRespuesta.isOk())
			{
				misFacturas=vRespuesta.getComprasResponse();
			}
			else {
				mensajesBean.addMensajes(vRespuesta);
				misFacturas=new ArrayList<>();
			}
		}
		else
		{
			misFacturas=new ArrayList<>();
		}
		
	
		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");
		// cabecera de la hoja de excel
		String[] header = new String[] { "NRO.", "NIT PROVEEDOR", "RAZON SOCIAL PROVEEDOR", "NRO. FACTURA",
				"NRO. AUTORIZACION / CUF", "COD. DE CONTROL", "FECHA EMISION", "IMPORTE TOTAL",
				"CON DERECHO A CREDITO FISCAL", "TIPO FACTURA", "ESTADO FACTURA", "ESTADO USO",
				"FORMULARIO USO" };
		// poner negrita a la cabecera
		CellStyle style = libro.createCellStyle();
		Font font = libro.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		CellStyle style2 = libro.createCellStyle();
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
//		style2.setFillPattern(fp);
		// generar los datos para el documento
		for (int i = 0; i <= misFacturas.size(); i++) {
			XSSFRow row = hoja1.createRow(i);// se crea las filas

			if (i == 0) {// para la cabecera
				XSSFCell cell1 = row.createCell(0);
				XSSFCell cell2 = row.createCell(1);
				XSSFCell cell3 = row.createCell(2);
				XSSFCell cell4 = row.createCell(3);
				XSSFCell cell5 = row.createCell(4);
				XSSFCell cell6 = row.createCell(5);
				XSSFCell cell7 = row.createCell(6);
				XSSFCell cell8 = row.createCell(7);
				XSSFCell cell9 = row.createCell(8);
				XSSFCell cell10 = row.createCell(9);
				XSSFCell cell11 = row.createCell(10);
				XSSFCell cell12 = row.createCell(11);
				XSSFCell cell13 = row.createCell(12);
				cell1.setCellStyle(style);
				cell1.setCellValue(header[0]);
				cell2.setCellStyle(style);
				cell2.setCellValue(header[1]);
				cell3.setCellStyle(style);
				cell3.setCellValue(header[2]);
				cell4.setCellStyle(style);
				cell4.setCellValue(header[3]);
				cell5.setCellStyle(style);
				cell5.setCellValue(header[4]);
				cell6.setCellStyle(style);
				cell6.setCellValue(header[5]);
				cell7.setCellStyle(style);
				cell7.setCellValue(header[6]);
				cell8.setCellStyle(style);
				cell8.setCellValue(header[7]);
				cell9.setCellStyle(style);
				cell9.setCellValue(header[8]);
				cell10.setCellStyle(style);
				cell10.setCellValue(header[9]);
				cell11.setCellStyle(style);
				cell11.setCellValue(header[10]);
				cell12.setCellStyle(style);
				cell12.setCellValue(header[11]);
				cell13.setCellStyle(style);
				cell13.setCellValue(header[12]);

			} else {// para el contenido
				XSSFCell cell1 = row.createCell(0);cell1.setCellStyle(style2);
				XSSFCell cell2 = row.createCell(1);cell2.setCellStyle(style2);
				XSSFCell cell3 = row.createCell(2);cell3.setCellStyle(style2);
				XSSFCell cell4 = row.createCell(3);cell4.setCellStyle(style2);
				XSSFCell cell5 = row.createCell(4);cell5.setCellStyle(style2);
				XSSFCell cell6 = row.createCell(5);cell6.setCellStyle(style2);
				XSSFCell cell7 = row.createCell(6);cell7.setCellStyle(style2);
				XSSFCell cell8 = row.createCell(7);cell8.setCellStyle(style2);
				XSSFCell cell9 = row.createCell(8);cell9.setCellStyle(style2);
				XSSFCell cell10 = row.createCell(9);cell10.setCellStyle(style2);
				XSSFCell cell11 = row.createCell(10);cell11.setCellStyle(style2);
				XSSFCell cell12 = row.createCell(11);cell12.setCellStyle(style2);
				XSSFCell cell13 = row.createCell(12);cell13.setCellStyle(style2);
				cell1.setCellValue(i);
				cell2.setCellValue(misFacturas.get(i - 1).getNitProveedor());
				cell3.setCellValue(misFacturas.get(i - 1).getRazonSocialProveedor());
				cell4.setCellValue(misFacturas.get(i - 1).getNumeroFactura());
				cell5.setCellValue(misFacturas.get(i - 1).getCodigoAutorizacion());
				cell6.setCellValue(misFacturas.get(i - 1).getCodigoControl());
				cell7.setCellValue(misFacturas.get(i - 1).getFechaFactura().toString());
				cell8.setCellValue(misFacturas.get(i - 1).getImporteTotalCompra().doubleValue());
				cell9.setCellValue(misFacturas.get(i - 1).getConDerechoCf());
				String tipoFactura=misFacturas.get(i - 1).getModalidadId().equals("FEL")||misFacturas.get(i - 1).getModalidadId().equals("FPW")
						||misFacturas.get(i - 1).getModalidadId().equals("FCL")? "LIN " : "";
				tipoFactura=tipoFactura+(misFacturas.get(i - 1).getModalidadId().equals("ELE")||misFacturas.get(i - 1).getModalidadId().equals("ELE")?"E ":"");
				tipoFactura=tipoFactura+(misFacturas.get(i - 1).getMarcaEspecialId().equals("IPN")?"PROD-NAL ":"");
				tipoFactura=tipoFactura+(misFacturas.get(i - 1).getMarcaEspecialId().equals("7RG")?"7RG ":"");
				
				cell10.setCellValue(tipoFactura);
				cell11.setCellValue(clasificadorController.descripcionEstadoCompraByCodigo(misFacturas.get(i - 1).getEstadoCompraId()));
				cell12.setCellValue(clasificadorController.descripcionEstadoUsoCompraByCodigo(misFacturas.get(i - 1).getEstadoUsoId()));
				cell13.setCellValue(misFacturas.get(i - 1).getNombreFormularioUso());
			}
			

		}
		hoja1.autoSizeColumn(0);
		hoja1.autoSizeColumn(1);
		hoja1.autoSizeColumn(2);
		hoja1.autoSizeColumn(3);
		hoja1.autoSizeColumn(4);
		hoja1.autoSizeColumn(5);
		hoja1.autoSizeColumn(6);
		hoja1.autoSizeColumn(7);
		hoja1.autoSizeColumn(8);
		hoja1.autoSizeColumn(9);
		hoja1.autoSizeColumn(10);
		hoja1.autoSizeColumn(11);
		hoja1.autoSizeColumn(12);
		File vArchivoTempXlsx = File.createTempFile("FacturasComprasConsulta", ".xlsx");
		String outputFile = vArchivoTempXlsx.getAbsolutePath();
		try (FileOutputStream fileOuS = new FileOutputStream(vArchivoTempXlsx)) {
			libro.write(fileOuS);
			fileOuS.flush();
			fileOuS.close();
			
			Path vArchivoXlsxPath = Paths.get(vArchivoTempXlsx.getPath());
			byte[] vArchivoXlsxByte = Files.readAllBytes(vArchivoXlsxPath);
			InputStream vArchivoXlsStream = new ByteArrayInputStream(vArchivoXlsxByte);
			vArchivoTempXlsx.deleteOnExit();
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			setDownload(new DefaultStreamedContent(vArchivoXlsStream, externalContext.getMimeType(vArchivoTempXlsx.getName()),
					"ReporteComprasConsultadas" + ".xlsx"));
			RequestContext.getCurrentInstance()
					.execute("toastr.success('Se descargó el archivo exitosamente', 'Informacion')");
			fileOuS.close();
			libro.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			vArchivoTempXlsx.delete();
		}
		
		
	}
	
	public void editarCamposCompras(ComprasCvDto pCompra)
	{  
	try {
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestCompras().modificarCompra(pCompra);
				
								if(vRespuesta!=null) {
									mensajesBean.addMensajes(vRespuesta);
									obtenerCompraActualizada(pCompra);	
								}
		
	} catch (Exception e) {
		obtenerCompraActualizada(pCompra);	
		e.printStackTrace();
	}
		
	}
	public void cancelarEdicionCamposCompras(ComprasCvDto pCompra) {
		obtenerCompraActualizada(pCompra);
	}
	public void obtenerCompraActualizada(ComprasCvDto pCompra)
	{
		try {
		RespuestaCompraSingleDto vRespuesta=clientesRestController.getClienteRestCompras().obtenerCompraPorId(pCompra.getId());
		ComprasCvDto compraActualizada=vRespuesta.getCompraResponse();
		if(vRespuesta.isOk()) {
				for (ComprasCvDto filaDatoCompra : facturasLazy) {
				if (filaDatoCompra.getId().equals(compraActualizada.getId())) {
					filaDatoCompra.setFechaFactura(compraActualizada.getFechaFactura());
					filaDatoCompra.setImporteTotalCompra(compraActualizada.getImporteTotalCompra());
					filaDatoCompra.setImporteBaseCf(compraActualizada.getImporteBaseCf());
					filaDatoCompra.setCodigoControl(compraActualizada.getCodigoControl());
					
				}
					
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public LazyDataModel<ComprasCvDto> getFacturasLazy() {
		return facturasLazy;
	}

	public void setFacturasLazy(LazyDataModel<ComprasCvDto> facturasLazy) {
		this.facturasLazy = facturasLazy;
	}

	public List<ComprasCvDto> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<ComprasCvDto> facturas) {
		this.facturas = facturas;
	}

	public List<ComprasCvDto> getFacturasSeleccionadas() {
		return facturasSeleccionadas;
	}

	public void setFacturasSeleccionadas(List<ComprasCvDto> facturasSeleccionadas) {
		this.facturasSeleccionadas = facturasSeleccionadas;
	}

	public List<ComprasCvDto> getMisFacturas() {
		return misFacturas;
	}

	public void setMisFacturas(List<ComprasCvDto> misFacturas) {
		this.misFacturas = misFacturas;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}

	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
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

	public Long getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}
}
