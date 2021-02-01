package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import bo.gob.sin.sre.fac.frvcc.jsf.model.DescargarPlantillaModel;
import bo.gob.sin.sre.fac.frvcc.jsf.util.*;
import bo.gob.sin.str.cmsj.mapl.dto.ListaMensajesAplicacion;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.RegistroComprasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;

/**
 * @author sergio.ichaso
 */
@ManagedBean(name = "registroComprasController")
@ViewScoped
public class RegistroComprasController implements Serializable {

	private static String FACTURA = "Factura";
	private static String ERRORES = "Errores";
	private static String FORMATO_FECHA = "dd/MM/yyyy";

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
	private static List<String> componentes = Arrays.asList("pnlMiFactura", "pnlErrores",
			"frmRegistroCompras:idGrilla");

	private StreamedContent file;
	private DefaultStreamedContent download;

	@ManagedProperty(value = "#{descargarPlantillaModel}")
	private DescargarPlantillaModel plantillaModel;

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoUsuarioModel;

	@ManagedProperty(value = "#{registroComprasModel}")
	private RegistroComprasModel registroComprasModel;

	@ManagedProperty(value = "#{fileUploadBean}")
	private FileUploadBean fileUploadBean;

	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	private Set<Long> nitAutoCompletado = new HashSet<>();
	private Set<String> numeroAutorizacionAutoCompleatado = new HashSet<>();

	public Set<String> getNumeroAutorizacionAutoCompleatado() {
		return numeroAutorizacionAutoCompleatado;
	}

	public void setNumeroAutorizacionAutoCompleatado(Set<String> numeroAutorizacionAutoCompleatado) {
		this.numeroAutorizacionAutoCompleatado = numeroAutorizacionAutoCompleatado;
	}

	private Map<Integer, String> diccionarioNit;
	private List<String> resultadoNit = new ArrayList<>();
	private Map<Integer, String> diccionarioNroAutorizacion;
	private List<String> resultadoNroAutorizacion = new ArrayList<>();
	private LinkedHashMap<String, Serializable> filtros;
	private boolean isVisibleEditor;

	private String modoEdicion;
	
	private Integer progressInteger;

	public void init() {
		agregarCompraVaciaInicio();
		cargarListaNitAutocompletado();
	}

	public Set<Long> getNitAutoCompletado() {
		return nitAutoCompletado;
	}

	public void setNitAutoCompletado(Set<Long> nitAutoCompletado) {
		this.nitAutoCompletado = nitAutoCompletado;
	}

	public void agregarCompraVacia() {
		this.registroComprasModel.addMiFactura(new ComprasCvDto(null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
				ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(),
				contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null));
	}

	public void agregarCompraVaciaInicio() {
		this.registroComprasModel.addMiFactura(new ComprasCvDto(UUID.randomUUID().toString(), null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
				ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(),
				contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null));
	}

	public ComprasCvDto crearCompraVacia() {
		ComprasCvDto compraNueva = new ComprasCvDto(UUID.randomUUID().toString(), null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
				ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(),
				contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null);
		return compraNueva;
	}

	private void cargarListaNitAutocompletado() {

		if (nitAutoCompletado.size() == 0) {
			LinkedHashMap<String, Serializable> pFilters = obtenerCriterios();
			pFilters.put("order_by", "fechaRegistro");
			pFilters.put("order", "DESC");
		
			List<ComprasCvDto> vListaFacturas;
			RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(pFilters);
			if (vRespuesta.isOk()) {
				vListaFacturas = vRespuesta.getComprasResponse();
			} else {
				vListaFacturas = new ArrayList<>();
				// mensajesBean.addMensajes(vRespuesta);
			}

			if (vListaFacturas.size() > 0) {
				numeroAutorizacionAutoCompleatado.addAll(vListaFacturas.stream().map(ComprasCvDto::getCodigoAutorizacion).collect(Collectors.toList()));
				nitAutoCompletado.addAll(vListaFacturas.stream().map(ComprasCvDto::getNitProveedor).collect(Collectors.toList()));
				
			}
		}

	}
	
	private LinkedHashMap<String, Serializable> obtenerCriterios() {
		FiltroCriteria filtros = new FiltroCriteria();
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoUsuarioModel.getNitCi());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
	
		return filtros.getFiltros();
	}

	
//	 public Integer getProgressInteger() {
//		 progressInteger = updateProgress(progressInteger);
//	        return progressInteger;
//	    }
//
//	private Integer updateProgress(Integer progress) {
//		if (progress == null) {
//			progress = 0;
//		} else {
//			progress = progress + (int) (Math.random() * 5);
//			if (progress > 100)
//				progress = 100;
//		}
//		return progress;
//	}
//	
//	public void onComplete() {
//		// TODO mensaje de confirmacion 
//    }
 
	public void validarFacturasExcel(ActionEvent event) {

	
                
		ContextoJSF contexto = new ContextoJSF();
		String numeroDocumento = "";
		if (contexto.getUsuario() != null) {
			numeroDocumento = contexto.getUsuario().getNumeroDocumento();
		}

		List<String> vMensajes = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String vFacturasJsonString = params.get(FACTURA);

		int vComprasValidas = 0;
		int vFila = 0;
		List<ComprasCvDto> vComprasExcelLista = new ArrayList<>();

		Gson vGson = new Gson();
		JsonArray vFacturas = vGson.fromJson(vFacturasJsonString, JsonArray.class);
		this.registroComprasModel.limpiarErrores();

		try {
			for (JsonElement facturaJson : vFacturas) {

				JsonObject objJsonCompra = facturaJson.getAsJsonObject();
				ComprasExcelDto vComprasExcelDto = new ComprasExcelDto(objJsonCompra.get("nro").getAsString(),
						objJsonCompra.get("fechaEmision").getAsString(),
						objJsonCompra.get("nitProveedor").getAsString(), objJsonCompra.get("nroFactura").getAsString(),
						objJsonCompra.get("nroAutorizacion").getAsString(),
						objJsonCompra.get("importeTotal").getAsString(), // TODO FORMATO DECIMALES
						objJsonCompra.get("codigoControl").getAsString());

				if (this.registroComprasModel.validarCompras(vComprasExcelDto)) {
					ComprasCvDto vCompraExcel = new ComprasCvDto(
							Integer.parseInt(vComprasExcelDto.getNumeroExcel().trim()),
							LocalDate.parse(vComprasExcelDto.getFechaFactura().trim(), formatter),
							Long.parseLong(vComprasExcelDto.getNitProveedor().trim()),
							Long.parseLong(vComprasExcelDto.getNumeroFactura().trim()),
							vComprasExcelDto.getCodigoAutorizacion().trim(),
							new BigDecimal(vComprasExcelDto.getImporteTotalCompra().trim()),
							new BigDecimal(vComprasExcelDto.getImporteTotalCompra().trim()),
							vComprasExcelDto.getCodigoControl().trim(), contextoUsuarioModel.getTipoNitCi(),
							contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(),
							contextoUsuarioModel.getNombreRazonSocial(), ParametrosFRVCC.ORIGEN_COMPRA_APLICACION);
					vComprasValidas++;
					vComprasExcelLista.add(vCompraExcel);
				}
			}
			if (vFacturas.size() == 0) {
				vComprasValidas = -1;
				RequestContext.getCurrentInstance()
						.execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
				return;
			}
			vFila++;
		} catch (Exception e) {
			vComprasValidas = -1;
			e.printStackTrace();
			getMensajeFormatoInvalido();
			RequestContext.getCurrentInstance().update(componentes);
			return;
		}
		if (vFacturas.size() == vComprasValidas) {
			List<ResultadoGenericoDto<String>> vListaCompras = new ArrayList<>();

			int cont = 0;
			for (int i = 0; i < vComprasExcelLista.size(); i++) {
				for (int j = 1 + i; j < vComprasExcelLista.size(); j++) {
					if (vComprasExcelLista.get(i).getNitProveedor().equals(vComprasExcelLista.get(j).getNitProveedor())
							&& vComprasExcelLista.get(i).getNumeroFactura()
									.equals(vComprasExcelLista.get(j).getNumeroFactura())
							&& vComprasExcelLista.get(i).getCodigoAutorizacion()
									.equals(vComprasExcelLista.get(j).getCodigoAutorizacion())) {
						cont++;
						break;
					}
				}
			}

			if (cont != 0) {
				RequestContext.getCurrentInstance()
						.execute("toastr.error('Error de registro, tiene facturas repetidas', 'Error')");
			} else {
				ResultadoGenericoListaDto<ResultadoGenericoDto<String>> vRespuesta = this.clientesRestController.getClienteRestCompras().registrarCompras(vComprasExcelLista);

				if (vRespuesta.isOk()) {
					this.registroComprasModel.addMisFacturas(vComprasExcelLista.stream().map(compra -> {
						compra.setOk(true);
						this.nitAutoCompletado.add(compra.getNitProveedor());
						this.numeroAutorizacionAutoCompleatado.add(compra.getCodigoAutorizacion());
						return compra;
					}).collect(Collectors.toList()));
					mensajesBean.addMensajes(vRespuesta);
					onLimpiar();

				} else {
					RequestContext.getCurrentInstance().execute("toastr.warning('Verificar los datos ingresados de la(s) factura(s)', 'Advertencia')");
					List<ErrorDto> errores = this.registroComprasModel.getErrores();
					vListaCompras = vRespuesta.getResultadoLista();

					if (!vListaCompras.isEmpty() && vListaCompras != null) {
						for (ResultadoGenericoDto<String> vObjCompra : vListaCompras) {
							String vFilaExcel = vComprasExcelLista.stream()
									.filter(compra -> compra.getId().equals(vObjCompra.getResultadoObjeto()))
									.map(ComprasCvDto::getNroFila).findFirst().orElse(0).toString();
							for (StrMensajeAplicacionDto msj : vObjCompra.getMensajes()) {
								errores.add(new ErrorDto(msj.getDescripcion(), vFilaExcel, msj.getDescripcionUi(),
										"El " + msj.getDescripcionUi() + "  es invalido."));
							}
						}
						this.registroComprasModel.setErrores(errores);
					}
				}
			}

		}
		
		RequestContext.getCurrentInstance().update(componentes);
		
		
	}

	public void eliminarCompra(ComprasCvDto dato) {

		//ComprasCvDto vCompra = (ComprasCvDto) actionEvent.getComponent().getAttributes().get("compra");
		ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestCompras().eliminarCompra(dato);
		onLimpiar();
		// mensajesBean.addMensajes(resp);
		if(resp.isOk()) {
			mensajesBean.addMensajes(resp);
		//	this.eliminarCompra(dato);
			//this.registroComprasModel.removeFactura(dato);
			this.registroComprasModel.getMisFacturas().removeIf(x -> x.equals(dato));
			RequestContext.getCurrentInstance().update("frmRegistroCompras:dtMisFacturas");
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('Proceso invalido', 'Error')");
		}

	}

	private void getMensajeFormatoInvalido() {
		StrMensajeAplicacionDto mensajeAplicacionDto = new StrMensajeAplicacionDto();
		mensajeAplicacionDto.setCodigo(3001);
		mensajeAplicacionDto.setDescripcionUi("Formato No Valido");
		mensajeAplicacionDto.setDescripcion("Formato No Valido");

		class LocalClass extends ListaMensajesAplicacion {
			public LocalClass() {
			}
		}
		LocalClass localClass = new LocalClass();
		localClass.addMensaje(mensajeAplicacionDto);

		mensajesBean.addMensajes(localClass);
	}

	/**
	 * Metodo para mostrar lista de errores.
	 *
	 * @author sergio.ichaso
	 * @Fecha: 02/01/2019
	 */
	public void mostrarErrores() {
		RequestContext.getCurrentInstance().execute("toastr.error('Error de registro', 'Error')");
		onLimpiar();
		List<ErrorDto> vMensajes = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String vFacturasJsonString = params.get(this.ERRORES);

		Gson vGson = new Gson();
		JsonArray vErroresJson = vGson.fromJson(vFacturasJsonString, JsonArray.class);
		Type listType = new TypeToken<List<ErrorDto>>() {
		}.getType();
		vMensajes = new Gson().fromJson(vErroresJson, listType);

		this.registroComprasModel.setErrores(vMensajes);
		RequestContext.getCurrentInstance().update(componentes);

	}

	public void onLimpiar() {
		this.registroComprasModel.clear();
	}

	public RegistroComprasModel getRegistroComprasModel() {
		return registroComprasModel;
	}

	public void setRegistroComprasModel(RegistroComprasModel registroComprasModel) {
		this.registroComprasModel = registroComprasModel;
	}

	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

	public Map<Integer, String> getDiccionarioNit() {
		return diccionarioNit;
	}

	public void setDiccionarioNit(Map<Integer, String> diccionarioNit) {
		this.diccionarioNit = diccionarioNit;
	}

	public List<String> getResultadoNit() {
		return resultadoNit;
	}

	public void setResultadoNit(List<String> resultadoNit) {
		this.resultadoNit = resultadoNit;
	}

	public Map<Integer, String> getDiccionarioNroAutorizacion() {
		return diccionarioNroAutorizacion;
	}

	public void setDiccionarioNroAutorizacion(Map<Integer, String> diccionarioNroAutorizacion) {
		this.diccionarioNroAutorizacion = diccionarioNroAutorizacion;
	}

	public List<String> getResultadoNroAutorizacion() {
		return resultadoNroAutorizacion;
	}

	public void setResultadoNroAutorizacion(List<String> resultadoNroAutorizacion) {
		this.resultadoNroAutorizacion = resultadoNroAutorizacion;
	}

	public LinkedHashMap<String, Serializable> getFiltros() {
		return filtros;
	}

	public void setFiltros(LinkedHashMap<String, Serializable> filtros) {
		this.filtros = filtros;
	}

	public boolean isVisibleEditor() {
		return isVisibleEditor;
	}

	public void setVisibleEditor(boolean isVisibleEditor) {
		this.isVisibleEditor = isVisibleEditor;
	}

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public ContextoUsuarioModel getContextoUsuarioModel() {
		return contextoUsuarioModel;
	}

	public void setContextoUsuarioModel(ContextoUsuarioModel contextoUsuarioModel) {
		this.contextoUsuarioModel = contextoUsuarioModel;
	}

	public String getModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(String modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public void actulizarCompra(ComprasCvDto pComprasCvDto) {
		if (pComprasCvDto == null) {
			RequestContext.getCurrentInstance().execute("toastr.error('Error la compra no tiene valores', 'Error')");
			return;
		}
		ResultadoGenericoDto<String> vRespuesta = this.clientesRestController.getClienteRestCompras()
				.modificarCompra(pComprasCvDto);
		mensajesBean.addMensajes(vRespuesta);
	}



		public void descargarPlantillaExcel() throws IOException {

			try {

				byte[] vArchivoDecodificado = Base64.getDecoder().decode(plantillaModel.getPLANTILLA_EXCEL());

				InputStream vArchivoXlsStream = new ByteArrayInputStream(vArchivoDecodificado);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				setDownload(new DefaultStreamedContent(vArchivoXlsStream,
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;",
						"PlantillaRegistroCompras" + ".xlsx"));


			} catch (Exception e) {
				e.printStackTrace();
			}



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

	public void guardarCompra(ActionEvent actionEvent) {
		ComprasCvDto vCompra = (ComprasCvDto) actionEvent.getComponent().getAttributes().get("compra");

		if (vCompra == null) {
			RequestContext.getCurrentInstance()
					.execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
			return;
		}
		ResultadoGenericoDto<String> vRespuesta = null;
		int index= this.registroComprasModel.getMisFacturas().indexOf(vCompra);
		if (index==0) {
			vRespuesta = this.clientesRestController.getClienteRestCompras().registrarCompra(vCompra);
			if (vRespuesta.isOk()) {
				agregarCompraVaciaInicio();
				onLimpiar();
			}
		} else {
			vRespuesta = this.clientesRestController.getClienteRestCompras().modificarCompra(vCompra);
		}
		if (vRespuesta.isOk()) {
			mensajesBean.addMensajes(vRespuesta);
			this.registroComprasModel.limpiarErrores();
			this.nitAutoCompletado.add(vCompra.getNitProveedor());
			this.numeroAutorizacionAutoCompleatado.add(vCompra.getCodigoAutorizacion());
			//RequestContext.getCurrentInstance().update(componentes);
			
			if (!vCompra.isOk()) {
				vCompra.setOk(true);
				//agregarCompraVaciaInicio();
				onLimpiar();
			}
		} else {
			vCompra.setOk(vRespuesta.isOk());
			RequestContext.getCurrentInstance().update("frmRegistroCompras:dtMisFacturas");
			
			if (!vRespuesta.getMensajes().isEmpty()) {
				List<StrMensajeAplicacionDto> vMensajesLista = vRespuesta.getMensajes();
				List<ErrorDto> errores = new ArrayList<>();

				for (StrMensajeAplicacionDto msj : vMensajesLista) {
					errores.add(new ErrorDto(msj.getDescripcion(),
							String.valueOf(registroComprasModel.getMisFacturas().indexOf(vCompra)),
							msj.getDescripcionUi(), "El " + msj.getDescripcionUi() + " es invalido."));
				}
				RequestContext.getCurrentInstance().execute("toastr.warning('Verificar los datos ingresados de la factura', 'Advertencia')");
				this.registroComprasModel.setErrores(errores);
			}else {
				RequestContext.getCurrentInstance().execute("toastr.error('Error de registro', 'Error')");
			}
		}
	}

	public DescargarPlantillaModel getPlantillaModel() {
		return plantillaModel;
	}

	public void setPlantillaModel(DescargarPlantillaModel plantillaModel) {
		this.plantillaModel = plantillaModel;
	}
}
