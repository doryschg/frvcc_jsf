package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioComprasRestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import net.sf.jasperreports.engine.JRException;

@ManagedBean(name = "libroComprasController")
@ViewScoped
public class LibroComprasController implements Serializable {

	private static final long serialVersionUID = 1L;
	private String numeroDocumento;
	private String razonSocial;
	private Integer periodo;
	private Integer gestion;
	
	private LazyDataModel<ComprasCvDto> facturasLazy;
	private List<ComprasCvDto> facturas = new ArrayList<>();

	private List<ComprasCvDto> facturasSeleccionadas = new ArrayList<>();

	private List<ComprasCvDto> misFacturas = new ArrayList<>();

	private StreamedContent file;
	private DefaultStreamedContent download;
	private boolean consultaRealizada = false;

	private transient ServicioComprasRestClient clienteRest = new ServicioComprasRestClient();

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoModel;
	@ManagedProperty(value = "#{reportesController}")
	private ReportesController reportesController;
	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@PostConstruct
	public void init()
	{
		System.err.println("enta al init");
		Calendar fecha = new GregorianCalendar();
		gestion = fecha.get(Calendar.YEAR);
		periodo = fecha.get(Calendar.MONTH) + 1;
		numeroDocumento=contextoModel.getNitCi();
		razonSocial=contextoModel.getNombreRazonSocial();
		onBuscar();
	}

	public LibroComprasController() {
		super();

	}

	public void removeFacturas(List<ComprasCvDto> pFacturas) {
		this.facturas.removeAll(pFacturas);
	}

	
	/* Mis Facturas */

	public void onLimpiar() {
		FiltroCriteria filtros = new FiltroCriteria();
		this.setPeriodo(null);
		this.setGestion(null);
		
		this.setFacturasLazy(new LazyConsultaComprasModel(new ArrayList<ComprasCvDto>(), filtros.getFiltros(),
				this.clienteRest, false,mensajesBean));
		this.setConsultaRealizada(false);
	}

	public void onBuscar() {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();

		this.setFacturasLazy(
				new LazyConsultaComprasModel(new ArrayList<ComprasCvDto>(), pFilters, this.clienteRest, true,mensajesBean));
		consultaRealizada = true;
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		if (this.gestion != null && this.periodo == null) {
			LocalDate fechaInicio=LocalDate.of(this.gestion, 1, 1);
			LocalDate fechaFin=LocalDate.of(this.gestion, 12, 31);
			System.out.println("FECHAAA "+fechaInicio+" "+fechaFin);
			
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
			
			System.out.println("FECHAAA "+fechaInicio+" "+fechaFin);
			
			filtros.adicionar("fechaFactura", OperadorCriteria.MAYOR_IGUAL, fechaInicio);
			filtros.adicionar("fechaFactura", OperadorCriteria.MENOR_IGUAL, fechaFin);
		}

	
	
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL,contextoModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		System.err.println("Filtros: "+filtros.toString());
		System.err.println("Filtros: "+filtros.getFiltros().toString());
		return filtros.getFiltros();
	}

	public void eliminarCompra(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String> resp = clienteRest.eliminarCompra(pCompra);
		

	}
	public void ExcelComprasConsultadas() throws IOException {
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		pFilters.put("order_by", "fechaFactura");
		pFilters.put("order", "DESC");
//		misFacturas 
		
		RespuestaComprasDto vRespuesta = clienteRest.obtenerCompras(pFilters);
		if(vRespuesta.isOk())
		{
			misFacturas=vRespuesta.getComprasResponse();
		}
		else {
			mensajesBean.addMensajes(vRespuesta);
			misFacturas=new ArrayList<>();
		}

		File temp = File.createTempFile("FacturasComprasConsulta", ".xlsx");
		String outputFile = temp.getAbsolutePath();
		
		System.err.println("ruta: "+outputFile);
		boolean alreadyExists = new File(outputFile).exists();

		if (alreadyExists) {
			File ficheroUsuarios = new File(outputFile);
			ficheroUsuarios.delete();
		}

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");
		// cabecera de la hoja de excel
		String[] header = new String[] { "Nro.", "Nit Proveedor", "Razón Social Proveedor", "Nro Factura",
				"Número Factura / CUF", "Codigo de Control", "Fecha Emisión", "Importe total",
				"Importe Base a Cr�dito Fiscal", "Factura Electrónica", "Estado Factura", "Estado Uso",
				"Formulario Uso" };
		// poner negrita a la cabecera
		CellStyle style = libro.createCellStyle();
		Font font = libro.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
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
				cell1.setCellValue(i);
				cell2.setCellValue(misFacturas.get(i - 1).getNitProveedor());
				cell3.setCellValue(misFacturas.get(i - 1).getRazonSocialProveedor());
				cell4.setCellValue(misFacturas.get(i - 1).getNumeroFactura());
				cell5.setCellValue(misFacturas.get(i - 1).getCodigoAutorizacion());
				cell6.setCellValue(misFacturas.get(i - 1).getCodigoControl());
				cell7.setCellValue(misFacturas.get(i - 1).getFechaFactura());
				cell8.setCellValue(misFacturas.get(i - 1).getImporteTotalCompra().doubleValue());
				cell9.setCellValue(misFacturas.get(i - 1).getImporteBaseCf().doubleValue());
				cell10.setCellValue(misFacturas.get(i - 1).getVentaId() != null ? "E" : "");
				cell11.setCellValue(misFacturas.get(i - 1).getEstadoCompraId());
				cell12.setCellValue(misFacturas.get(i - 1).getEstadoUsoId());
				cell13.setCellValue(misFacturas.get(i - 1).getNombreFormularioUso());
			}

		}

		File file;
		file = new File(outputFile);
		try (FileOutputStream fileOuS = new FileOutputStream(file)) {
			if (file.exists()) {// si el archivo existe se elimina
				file.delete();
				System.out.println("Archivo eliminado");
			}
			libro.write(fileOuS);
			fileOuS.flush();
			fileOuS.close();
			System.out.println("Archivo Creado");
//			libro.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()),
				"ReporteComoprasConsultadas" + ".xlsx"));
		System.out.println("PREP = " + download.getName());
		RequestContext.getCurrentInstance()
				.execute("toastr.success('Se descargó el archivo exitosamente', 'Información')");
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

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}
	
	
}
