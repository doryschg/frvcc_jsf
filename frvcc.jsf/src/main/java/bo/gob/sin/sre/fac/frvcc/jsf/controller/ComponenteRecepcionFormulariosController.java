package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.caco.dto.SucursalDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
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
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.LazyConsultaFormulariosModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.reports.ReportesController;
import bo.gob.sin.sre.fac.frvcc.jsf.util.FiltroCriteria;
import bo.gob.sin.sre.fac.frvcc.jsf.util.OperadorCriteria;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "componenteRecepcionFormulariosController")
@ViewScoped
public class ComponenteRecepcionFormulariosController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG= LoggerFactory.getLogger(ComponenteRecepcionFormulariosController.class);
	private Integer periodoMes;
	private Integer periodoAnio;
	private Long ciDependiente;
	private LocalDate fechaRecepcion;
	private Long numeroOrden;
	private String tipoUso=null;
	private LazyDataModel<FormularioCvDto> formulariosLazy;
	private List<FormularioCvDto> formularios = new ArrayList<>();
	private FormularioCvDto formularioSeleccionado;
	private List<ComprasCvDto> listaComrasFomulario=new ArrayList<>();
	private StreamedContent file;
	private DefaultStreamedContent download;
	private boolean consultaRealizada=false;
	private DepartamentoDto depFormulario;
	private List<DepartamentoDto> listaDepartamentos = new ArrayList<>();
	private List<String> listaMeses=new ArrayList<>();
	private Integer numeroSucursal;
	private List<SucursalDto> listaSucursales=new ArrayList<SucursalDto>();
	private Integer lugarDepartamento;
	private Boolean esUsuarioReceptor=false;
	private Boolean esGestorUsuarios=false;
	private List<ClasificadorDto> listaTipoUso=new ArrayList<>();
	private List<SucursalUsuarioDto> listaSucursalesUsuarios = new ArrayList<>();
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
		periodoAnio = fecha.get(Calendar.YEAR);
		periodoMes = fecha.get(Calendar.MONTH) + 1;
		formularioSeleccionado=new FormularioCvDto();
		onLimpiar();

		/*listaMeses.add("ENERO");listaMeses.add("FEBRERO");listaMeses.add("MARZO");
		listaMeses.add("ABRIL");listaMeses.add("MAYO");listaMeses.add("JUNIO");
		listaMeses.add("JULIO");listaMeses.add("AGOSTO");listaMeses.add("SEPTIEMBRE");
		listaMeses.add("OCTUBRE");listaMeses.add("NOVIEMBRE");listaMeses.add("DICIEMBRE");*/
	}
	public void consultaSucursalesContribuyente() {
		ResultadoGenericoDto<DatosContribuyenteEscritorioDto> vDatoscontribuyenteEmpleador = clientesRestController.getClienteRestPadron()
				.consultaContribuyente(contextoModel.getContribuyente().getNit());
		if (vDatoscontribuyenteEmpleador.isOk()) {
			listaSucursales=vDatoscontribuyenteEmpleador.getResultadoObjeto().getSucursales();
			if(lugarDepartamento!=null){
				List<SucursalDto> vSucursalesPorDpto = listaSucursales.stream()
						.filter(suc -> suc.getDepartamentoId()==lugarDepartamento)
						.collect(Collectors.toList());
				if(vSucursalesPorDpto.isEmpty()){
					RequestContext.getCurrentInstance()
							.execute("toastr.warning('No se encontraron sucursales para el departamento seleccionado.', 'Advertencia!')");

				}
				setListaSucursales(vSucursalesPorDpto);
			}

		}

	}
	public FormularioCvDto usuarioReceptor(FormularioCvDto pFormulario){

		pFormulario.setLoginUsuarioReceptor(contextoModel.getUsuario().getLogin());
		if(!listaSucursalesUsuarios.isEmpty()){
			pFormulario.setNombreReceptor(listaSucursalesUsuarios.get(0).getNombreUsuarioReceptor());
		}
		else{
			pFormulario.setNombreReceptor(contextoModel.getUsuario().getRazonSocial());
		}
		return pFormulario;

	}
	public void aceptarFormulario(FormularioCvDto pFormulario)
	{
		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestFormularios().aceptarDeclaracionFormulario(usuarioReceptor(pFormulario));
		if(vRespuesta!=null){
			mensajesBean.addMensajes(vRespuesta);
		}
		onBuscar();
	}
	public void rechazarFormulario(FormularioCvDto pFormulario) {

		ResultadoGenericoDto<String> vRespuesta=clientesRestController.getClienteRestFormularios().rechazarDeclaracionFormulario(usuarioReceptor(pFormulario));
		if(vRespuesta!=null)
		{
			mensajesBean.addMensajes(vRespuesta);
		}

		onBuscar();
	}
	public void obtenerFacturasSeleccionadas(FormularioCvDto pFormulario)
	{
		Optional<DepartamentoDto> departamentoForm = this.getListaDepartamentos().stream()
				.filter(x -> x.getDepartamentoId().equals(pFormulario.getLugarDepartamento().shortValue())).findFirst();
		depFormulario=departamentoForm.isPresent()?departamentoForm.get():new DepartamentoDto();
		
		formularioSeleccionado=pFormulario;

		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, pFormulario.getNitCi());
		filtros.adicionar("formularioId", OperadorCriteria.IGUAL, formularioSeleccionado.getId());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
		long vCantidad=clientesRestController.getClienteRestCompras().obtenerTotalCompras(filtros.getFiltros());
		if(vCantidad>0) {
			filtros.getFiltros().put("limit", vCantidad);
			filtros.getFiltros().put("offset", 0);
			filtros.getFiltros().put("order_by", "fechaFactura");
			filtros.getFiltros().put("order", "ASC");
			RespuestaComprasDto vRespuesta=clientesRestController.getClienteRestCompras().obtenerCompras(filtros.getFiltros());
			if(vRespuesta.isOk())
			{
				listaComrasFomulario=vRespuesta.getComprasResponse();
			}
			else
			{
				listaComrasFomulario=new ArrayList<>();
				mensajesBean.addMensajes(vRespuesta);
			}
		}

	}
	public void onLimpiar() {
		FiltroCriteria filtros = new FiltroCriteria();
		Calendar fecha = new GregorianCalendar();
		setFormularios(new ArrayList<FormularioCvDto>());
		setPeriodoMes(null);
		setPeriodoAnio(fecha.get(Calendar.YEAR));
		setCiDependiente(null);
		setFechaRecepcion(null);
		setNumeroOrden(null);
		setLugarDepartamento(null);
		setTipoUso(null);
		setFormulariosLazy(new LazyConsultaFormulariosModel(new ArrayList<FormularioCvDto>(),
				filtros.getFiltros(), this.clientesRestController.getClienteRestFormularios(), false, mensajesBean));
		setConsultaRealizada(false);
		if(this.getEsGestorUsuarios()){
			listaSucursales.clear();
		}
	}
	public void onBuscar() {

			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
			this.setFormulariosLazy(
					new LazyConsultaFormulariosModel(new ArrayList<FormularioCvDto>(), pFilters, this.clientesRestController.getClienteRestFormularios(), true,mensajesBean));
			consultaRealizada=true;
	}

	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		if (this.lugarDepartamento != null && this.lugarDepartamento!=10) {
			filtros.adicionar("lugarDepartamento", OperadorCriteria.IGUAL, this.getLugarDepartamento());
		}
		
		if (this.periodoMes != null) {
			filtros.adicionar("mesPeriodo", OperadorCriteria.IGUAL, this.getPeriodoMes());
			
		}
		if (this.periodoAnio != null) {
			filtros.adicionar("anioPeriodo", OperadorCriteria.IGUAL, this.getPeriodoAnio());
			
		}
		if (this.ciDependiente != null) {
			filtros.adicionar("nitCi", OperadorCriteria.IGUAL, this.getCiDependiente());
			
		}
		if (this.fechaRecepcion != null) {
		filtros.adicionar("fechaPresentacion", OperadorCriteria.IGUAL, this.getFechaRecepcion());
			
		}
		if (this.numeroOrden != null) {
			filtros.adicionar("numeroOrden", OperadorCriteria.IGUAL, this.getNumeroOrden());
		}
		if (this.numeroSucursal != null) {
			filtros.adicionar("numeroSucursal", OperadorCriteria.IGUAL, this.getNumeroSucursal());
		}
		if (this.tipoUso != null && !this.tipoUso.equals("")) {
			filtros.adicionar("tipoUsoId", OperadorCriteria.IGUAL, this.getTipoUso());
		}
	
		filtros.adicionar("nitEmpleador", OperadorCriteria.IGUAL, contextoModel.getNitCi());
		filtros.adicionar("tipoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.TIPO_FORMULARIO_110);
		filtros.adicionar("estadoFormularioId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_FORMULARIO_DECLARADO);

		return filtros.getFiltros();
	}
	public void exportarFormulariosGeneradosExcel() throws IOException
	{	
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		long vCantidad=clientesRestController.getClienteRestFormularios().obtenerTotalFormularios(pFilters);
		if(vCantidad>0)
		{
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "numeroOrden");
			pFilters.put("order", "DESC");
			RespuestaFormulariosDto lista=clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
			if(lista.isOk())
			{
				formularios = lista.getFormulariosReponse();
			}
			else {
				formularios=new ArrayList<>();
				mensajesBean.addMensajes(lista);
			}	
		}
		else
		{
			formularios=new ArrayList<>();
		}		
		
		

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");
		// cabecera de la hoja de excel
		String[] header = new String[] { "NRO.","PERIODO","NRO. ORDEN", "CI DEPENDIENTE", "COD. DEPENDIENTE", "NOMBRES Y APELLIDOS",
				"CANTIDAD FACTURAS IPN","CANTIDAD OTRAS FACTURAS","CANTIDAD FACTURAS SIETE-RG",
				"TOTAL IMPORTE FACTURAS IPN","TOTAL IMPORTE OTRAS FACTURAS","TOTAL IMPORTE FACTURAS SIETE-RG",
				"DETERMINACION DE PAGO A CUENTA","PAGO A CUENTA FACTURAS 7RG","FECHA PRESENTACION"};
		// poner negrita a la cabecera
		CellStyle style = libro.createCellStyle();
		Font font = libro.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    int contador=0;
		// generar los datos para el documento
		
	    if(contador==0)// para la cabecera
	    {	    
	    XSSFRow row = hoja1.createRow(contador);
		XSSFCell cell1 = row.createCell(0);XSSFCell cell2 = row.createCell(1);XSSFCell cell3 = row.createCell(2);
		XSSFCell cell4 = row.createCell(3);XSSFCell cell5 = row.createCell(4);XSSFCell cell6 = row.createCell(5);
		XSSFCell cell7 = row.createCell(6);XSSFCell cell8 = row.createCell(7);XSSFCell cell9 = row.createCell(8);
		XSSFCell cell10 = row.createCell(9);XSSFCell cell11 = row.createCell(10);XSSFCell cell12 = row.createCell(11);
		XSSFCell cell13 = row.createCell(12);XSSFCell cell14 = row.createCell(13);XSSFCell cell15 = row.createCell(14);
		cell1.setCellStyle(style);cell1.setCellValue(header[0]);
		cell2.setCellStyle(style);cell2.setCellValue(header[1]);
		cell3.setCellStyle(style);cell3.setCellValue(header[2]);
		cell4.setCellStyle(style);cell4.setCellValue(header[3]);
		cell5.setCellStyle(style);cell5.setCellValue(header[4]);
		cell6.setCellStyle(style);cell6.setCellValue(header[5]);
		cell7.setCellStyle(style);cell7.setCellValue(header[6]);
		cell8.setCellStyle(style);cell8.setCellValue(header[7]);
		cell9.setCellStyle(style);cell9.setCellValue(header[8]);
		cell10.setCellStyle(style);cell10.setCellValue(header[9]);
		cell11.setCellStyle(style);cell11.setCellValue(header[10]);
		cell12.setCellStyle(style);cell12.setCellValue(header[11]);
		cell13.setCellStyle(style);cell13.setCellValue(header[12]);
		cell14.setCellStyle(style);cell14.setCellValue(header[13]);
		cell15.setCellStyle(style);cell15.setCellValue(header[14]);
	    }			

		
		for (FormularioCvDto Formulario : formularios) {// para el contenido
			XSSFRow row = hoja1.createRow(contador+1);
			XSSFCell cell1 = row.createCell(0);XSSFCell cell2 = row.createCell(1);XSSFCell cell3 = row.createCell(2);
			XSSFCell cell4 = row.createCell(3);XSSFCell cell5 = row.createCell(4);XSSFCell cell6 = row.createCell(5);
			XSSFCell cell7 = row.createCell(6);XSSFCell cell8 = row.createCell(7);XSSFCell cell9 = row.createCell(8);
			XSSFCell cell10 = row.createCell(9);XSSFCell cell11 = row.createCell(10);XSSFCell cell12 = row.createCell(11);
			XSSFCell cell13 = row.createCell(12);XSSFCell cell14 = row.createCell(13);XSSFCell cell15 = row.createCell(14);
			cell1.setCellValue(contador+1);
			String vCero=Formulario.getMesPeriodo()<10?"0":"";
			cell2.setCellValue(vCero+Formulario.getMesPeriodo()+"/"+Formulario.getAnioPeriodo()); 
			cell3.setCellValue(Formulario.getNumeroOrden());
			cell4.setCellValue(Formulario.getNitCi()); 
			cell5.setCellValue(Formulario.getCodigoDependiente()!=null?Formulario.getCodigoDependiente()+"":"");
			cell6.setCellValue(Formulario.getRazonSocial());
			cell7.setCellValue(Formulario.getCantidadComprasCfIpn()); 
			cell8.setCellValue(Formulario.getCantidadComprasCfOtras());
			cell9.setCellValue(Formulario.getCantidadComprasSdCf());
			cell10.setCellValue(Formulario.getTotalComprasCfIpn().setScale(0, BigDecimal.ROUND_HALF_UP).longValue()); 
			cell11.setCellValue(Formulario.getTotalComprasCfOtras().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
			cell12.setCellValue(Formulario.getTotalComprasSdCf().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
			cell13.setCellValue(Formulario.getDeterminacionPagoCf().setScale(0, BigDecimal.ROUND_HALF_UP).longValue()); 
			cell14.setCellValue(Formulario.getDeterminacionPagoSdCf().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
			cell15.setCellValue(Formulario.getFechaPresentacion().toString());
			contador++;
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
		hoja1.autoSizeColumn(13);
		hoja1.autoSizeColumn(14);
		

		File temp = File.createTempFile("FormulariosRecepcionados", ".xlsx");		
		try (FileOutputStream fileOuS = new FileOutputStream(temp)) {
			libro.write(fileOuS);
			fileOuS.flush();
			fileOuS.close();
			libro.close();
			
			InputStream input = new FileInputStream(temp);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(temp.getName()),"ReporteFormularios110Declarados" + ".xlsx"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			temp.delete();
		}
		
		
	}
	public void generarReporteFormulario()
	{
		LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
		long vCantidad=clientesRestController.getClienteRestFormularios().obtenerTotalFormularios(pFilters);
		if(vCantidad>0) {
			pFilters.put("limit", vCantidad);
			pFilters.put("offset", 0);
			pFilters.put("order_by", "numeroOrden");
			pFilters.put("order", "DESC");
		}
		RespuestaFormulariosDto lista=clientesRestController.getClienteRestFormularios().obtenerFormulariosActivos(pFilters);
		
		if(lista.isOk())
		{
			this.formularios = lista.getFormulariosReponse();
		}
		else {
			this.formularios=new ArrayList<>();
		}		
		reportesController.reporteFormularios(formularios,"REPORTE F-110 DECLARADOS", "DETALLE F-110 DECLARADOS");

	}

	public List<FormularioCvDto> getFormularios() {
		return formularios;
	}
	public void setFormularios(List<FormularioCvDto> formularios) {
		this.formularios = formularios;
	}
	public LazyDataModel<FormularioCvDto> getFormulariosLazy() {
		return formulariosLazy;
	}
	public void setFormulariosLazy(LazyDataModel<FormularioCvDto> formulariosLazy) {
		this.formulariosLazy = formulariosLazy;
	}
	public FormularioCvDto getFormularioSeleccionado() {
		return formularioSeleccionado;
	}
	public void setFormularioSeleccionado(FormularioCvDto formularioSeleccionado) {
		this.formularioSeleccionado = formularioSeleccionado;
	}
	public List<ComprasCvDto> getListaComrasFomulario() {
		return listaComrasFomulario;
	}
	public void setListaComrasFomulario(List<ComprasCvDto> listaComrasFomulario) {
		this.listaComrasFomulario = listaComrasFomulario;
	}
	public Integer getPeriodoMes() {
		return periodoMes;
	}
	public void setPeriodoMes(Integer periodoMes) {
		this.periodoMes = periodoMes;
	}
	public Integer getPeriodoAnio() {
		return periodoAnio;
	}
	public void setPeriodoAnio(Integer periodoAnio) {
		this.periodoAnio = periodoAnio;
	}
	public Long getCiDependiente() {
		return ciDependiente;
	}
	public void setCiDependiente(Long ciDependiente) {
		this.ciDependiente = ciDependiente;
	}
	public LocalDate getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(LocalDate fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public Long getNumeroOrden() {
		return numeroOrden;
	}
	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
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
	public ReportesController getReportesController() {
		return reportesController;
	}
	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}
	public boolean isConsultaRealizada() {
		return consultaRealizada;
	}
	public void setConsultaRealizada(boolean consultaRealizada) {
		this.consultaRealizada = consultaRealizada;
	}
	public ContextoUsuarioModel getContextoModel() {
		return contextoModel;
	}
	public void setContextoModel(ContextoUsuarioModel contextoModel) {
		this.contextoModel = contextoModel;
	}
	public DepartamentoDto getDepFormulario() {
		return depFormulario;
	}
	public void setDepFormulario(DepartamentoDto depFormulario) {
		this.depFormulario = depFormulario;
	}
	public List<DepartamentoDto> getListaDepartamentos() {
		return listaDepartamentos;
	}
	public void setListaDepartamentos(List<DepartamentoDto> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}
	public List<String> getListaMeses() {
		return listaMeses;
	}
	public void setListaMeses(List<String> listaMeses) {
		this.listaMeses = listaMeses;
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

	public List<SucursalDto> getListaSucursales() {
		return listaSucursales;
	}

	public void setListaSucursales(List<SucursalDto> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}

	public Integer getNumeroSucursal() {
		return numeroSucursal;
	}

	public void setNumeroSucursal(Integer numeroSucursal) {
		this.numeroSucursal = numeroSucursal;
	}

	public Integer getLugarDepartamento() {
		return lugarDepartamento;
	}

	public void setLugarDepartamento(Integer lugarDepartamento) {
		this.lugarDepartamento = lugarDepartamento;
	}

	public List<SucursalUsuarioDto> getListaSucursalesUsuarios() {
		return listaSucursalesUsuarios;
	}

	public void setListaSucursalesUsuarios(List<SucursalUsuarioDto> listaSucursalesUsuarios) {
		this.listaSucursalesUsuarios = listaSucursalesUsuarios;
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

	public List<ClasificadorDto> getListaTipoUso() {
		return listaTipoUso;
	}
	public void setListaTipoUso(List<ClasificadorDto> listaTipoUso) {
		this.listaTipoUso = listaTipoUso;
	}

	public String getTipoUso() {
		return tipoUso;
	}

	public void setTipoUso(String tipoUso) {
		this.tipoUso = tipoUso;
	}


}
