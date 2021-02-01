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
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
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
import bo.gob.sin.sre.fac.frvcc.jsf.model.RegistroComprasEstandarModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;


@ManagedBean(name = "registroComprasEstandarController")
@ViewScoped
public class RegistroComprasEstandarController implements Serializable {
    private static String PLANTILLA_EXCEL ="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";
    private static String FACTURA = "Factura";
    private static String ERRORES = "Errores";
    private static String FORMATO_FECHA = "dd/MM/yyyy";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
    private static List<String> componentes = Arrays.asList("pnlMiFactura", "pnlErrores",
            "frmRegistroCompras:idGrilla");

    private StreamedContent file;
    private DefaultStreamedContent download;

    @ManagedProperty(value = "#{contextoUsuarioModel}")
    private ContextoUsuarioModel contextoUsuarioModel;

    @ManagedProperty(value = "#{registroComprasEstandarModel}")
    private RegistroComprasEstandarModel registroComprasEstandarModel;

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
        this.registroComprasEstandarModel.addMiFactura(new ComprasCvDto(null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
                ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(), contextoUsuarioModel.getNitCi(),
                contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null));
    }

    public void agregarCompraVaciaInicio() {
        this.registroComprasEstandarModel.addMiFactura(new ComprasCvDto(UUID.randomUUID().toString(), null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
                ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(), contextoUsuarioModel.getNitCi(),
                contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null));
    }

    public ComprasCvDto crearCompraVacia() {
        ComprasCvDto compraNueva = new ComprasCvDto(UUID.randomUUID().toString(), null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, contextoUsuarioModel.getNombreRazonSocial(),
                ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, contextoUsuarioModel.getTipoNitCi(), contextoUsuarioModel.getNitCi(),
                contextoUsuarioModel.getComplementoDocumentoIdentidad(), null, null);
        return compraNueva;
    }

    private void cargarListaNitAutocompletado() {
        if (nitAutoCompletado.size() == 0) {
            FiltroCriteria filtros = new FiltroCriteria();
            filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoUsuarioModel.getNitCi());
            filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
            List<ComprasCvDto> vListaFacturas;
            RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(filtros.getFiltros());
            if (vRespuesta.isOk()) {
                vListaFacturas = vRespuesta.getComprasResponse();
            } else {
                vListaFacturas = new ArrayList<>();
                //mensajesBean.addMensajes(vRespuesta);
            }

            if (vListaFacturas.size() > 0) {
                nitAutoCompletado.addAll(vListaFacturas.stream().map(ComprasCvDto::getNitProveedor).collect(Collectors.toList()));
                numeroAutorizacionAutoCompleatado.addAll(vListaFacturas.stream().map(ComprasCvDto::getCodigoAutorizacion).collect(Collectors.toList()));
            }
        }
    }
    
	 public Integer getProgressInteger() {
		 progressInteger = updateProgress(progressInteger);
	        return progressInteger;
	    }

	private Integer updateProgress(Integer progress) {
		if (progress == null) {
			progress = 0;
		} else {
			progress = progress + (int) (Math.random() * 5);
			if (progress > 100)
				progress = 100;
		}
		return progress;
	}
	
	public void onComplete() {
		// TODO mensaje de confirmacion 
    }


    public void validarFacturasExcel(ActionEvent event) {
    	
    	progressInteger=0;

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
        this.registroComprasEstandarModel.limpiarErrores();

        try {
            for (JsonElement facturaJson : vFacturas) {

                JsonObject objJsonCompra = facturaJson.getAsJsonObject();
                ComprasExcelDto vComprasExcelDto = new ComprasExcelDto(
                        objJsonCompra.get("nro").getAsString(),
                        objJsonCompra.get("fechaEmision").getAsString(),
                        objJsonCompra.get("nitProveedor").getAsString(),
                        objJsonCompra.get("nroFactura").getAsString(),
                        objJsonCompra.get("nroDui").getAsString(),
                        objJsonCompra.get("nroAutorizacion").getAsString(),
                        objJsonCompra.get("importeTotal").getAsString(), //TODO FORMATO DECIMALES
                        objJsonCompra.get("importeNoSujetoCF").getAsString(),
                        objJsonCompra.get("dsctoBonRebajasObt").getAsString(), 
                        objJsonCompra.get("importeBaseCf").getAsString(), 
                        objJsonCompra.get("codigoControl").getAsString()
                );


                if (this.registroComprasEstandarModel.validarCompras(vComprasExcelDto)) {
                    ComprasCvDto vCompraExcel = new ComprasCvDto(
                            Integer.parseInt(vComprasExcelDto.getNumeroExcel().trim()),
                            LocalDate.parse(vComprasExcelDto.getFechaFactura().trim(), formatter),
                            Long.parseLong(vComprasExcelDto.getNitProveedor().trim()),
                            Long.parseLong(vComprasExcelDto.getNumeroFactura().trim()),
                            vComprasExcelDto.getNumeroDui().trim(),
                            vComprasExcelDto.getCodigoAutorizacion().trim(),
                            new BigDecimal(vComprasExcelDto.getImporteTotalCompra().trim()),
                            new BigDecimal(vComprasExcelDto.getImporteNoSujetoFc().trim()),
                            new BigDecimal(vComprasExcelDto.getDescuento().trim()),
                            (((new BigDecimal(vComprasExcelDto.getImporteTotalCompra().trim())).subtract(new BigDecimal(vComprasExcelDto.getImporteNoSujetoFc().trim()))).subtract(new BigDecimal(vComprasExcelDto.getDescuento().trim()))),
                            vComprasExcelDto.getCodigoControl().trim(),
                            contextoUsuarioModel.getTipoNitCi(),
                            contextoUsuarioModel.getNitCi(),
                            contextoUsuarioModel.getComplementoDocumentoIdentidad(),
                            contextoUsuarioModel.getNombreRazonSocial(),
                            ParametrosFRVCC.ORIGEN_COMPRA_APLICACION);
                    vComprasValidas++;
                    vComprasExcelLista.add(vCompraExcel);
                }
            }
            if(vFacturas.size()==0) {
            	vComprasValidas=-1;
                RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
                return;
            }
            vFila++;
        } catch (Exception e) {
        	vComprasValidas=-1;
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
							&& vComprasExcelLista.get(i).getNumeroFactura().equals(vComprasExcelLista.get(j).getNumeroFactura())
							&& vComprasExcelLista.get(i).getNumeroDui().equals(vComprasExcelLista.get(j).getNumeroDui())
							&& vComprasExcelLista.get(i).getCodigoAutorizacion().equals(vComprasExcelLista.get(j).getCodigoAutorizacion())) {
						cont++;
						break;
					}
				}
			}
            if (cont != 0) {
            	RequestContext.getCurrentInstance()
				.execute("toastr.error('Error de registro, tiene facturas repetidas', 'Error')");
            } else {
            	   ResultadoGenericoListaDto<ResultadoGenericoDto<String>> vRespuesta =this.clientesRestController.getClienteRestComprasEstandar().registrarComprasEstandar(vComprasExcelLista);

                   if (vRespuesta.isOk()) {
                       this.registroComprasEstandarModel.addMisFacturas(vComprasExcelLista.stream().map(compra -> {
                           compra.setOk(true);
                           return compra;
                       }).collect(Collectors.toList()));
                       mensajesBean.addMensajes(vRespuesta);
                       onLimpiar();
                   

                   } else {
                       mensajesBean.addMensajes(vRespuesta);
                       RequestContext.getCurrentInstance().execute("toastr.error('Error de registro', 'Error')");
                       List<ErrorDto> errores = this.registroComprasEstandarModel.getErrores();
                       vListaCompras = vRespuesta.getResultadoLista();

                       if (!vListaCompras.isEmpty() && vListaCompras != null) {
                           for (ResultadoGenericoDto<String> vObjCompra : vListaCompras) {
                               String vFilaExcel = vComprasExcelLista.stream().filter(compra -> compra.getId().equals(vObjCompra.getResultadoObjeto()
                               )).map(ComprasCvDto::getNroFila).findFirst().orElse(0).toString();
                               for (StrMensajeAplicacionDto msj : vObjCompra.getMensajes()) {
                                   errores.add(new ErrorDto(msj.getDescripcion(), vFilaExcel, msj.getDescripcionUi(),
                                           "El " + msj.getDescripcionUi() + "  es invalido."));
                               }
                           }
                           this.registroComprasEstandarModel.setErrores(errores);
                       }
                   }
            }
        }
        RequestContext.getCurrentInstance().update(componentes);
        progressInteger=98;
		progressInteger= updateProgress(progressInteger);
        
    }

    private void getMensajeFormatoInvalido() {
        StrMensajeAplicacionDto mensajeAplicacionDto = new StrMensajeAplicacionDto();
        mensajeAplicacionDto.setCodigo(3001);
        mensajeAplicacionDto.setDescripcionUi("Formato no válido");
        mensajeAplicacionDto.setDescripcion("Formato no válido");

        class LocalClass extends ListaMensajesAplicacion {
            public LocalClass() {
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.addMensaje(mensajeAplicacionDto);

        mensajesBean.addMensajes(localClass);
    }


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

        this.registroComprasEstandarModel.setErrores(vMensajes);
        RequestContext.getCurrentInstance().update(componentes);

    }

    public void onLimpiar() {
        this.registroComprasEstandarModel.clear();
    }

   

    public RegistroComprasEstandarModel getRegistroComprasEstandarModel() {
		return registroComprasEstandarModel;
	}

	public void setRegistroComprasEstandarModel(RegistroComprasEstandarModel registroComprasEstandarModel) {
		this.registroComprasEstandarModel = registroComprasEstandarModel;
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
            RequestContext
                    .getCurrentInstance()
                    .execute("toastr.error('Error la compra no tiene valores', 'Error')");
            return;
        }
        ResultadoGenericoDto<String> vRespuesta = this.clientesRestController.getClienteRestComprasEstandar().modificarCompraEstandar(pComprasCvDto);
        mensajesBean.addMensajes(vRespuesta);
    }

    public void descargarPlantillaExcel() throws IOException {
        try {

            byte[] vArchivoDecodificado = Base64.getDecoder().decode(PLANTILLA_EXCEL.getBytes());

            InputStream vArchivoXlsStream = new ByteArrayInputStream(vArchivoDecodificado);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            setDownload(new DefaultStreamedContent(vArchivoXlsStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;",
                    "PlantillaRegistroComprasEstandar" + ".xlsx"));
            RequestContext.getCurrentInstance()
                    .execute("toastr.success('Se descarg� el archivo exitosamente', 'Informacion')");


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
        vCompra.setImporteBaseCf((vCompra.getImporteTotalCompra().subtract(vCompra.getImporteNoSujetoCf())).subtract(vCompra.getDescuento()));

        if (vCompra.getNumeroFactura() == null) {
        	vCompra.setNumeroFactura(0L);

        }
		if (vCompra.getNumeroDui() == null) {
			vCompra.setNumeroDui("0");
		}
        if (vCompra == null) {
            RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
            return;
        }
        ResultadoGenericoDto<String> vRespuesta = null;
        if (vCompra.isOk()) {
            vRespuesta = this.clientesRestController.getClienteRestComprasEstandar().modificarCompraEstandar(vCompra);
        } else {
            vRespuesta = this.clientesRestController.getClienteRestComprasEstandar().registrarCompraEstandar(vCompra);
        }

        if (vRespuesta.isOk()) {

            mensajesBean.addMensajes(vRespuesta);
            this.registroComprasEstandarModel.limpiarErrores();
            this.nitAutoCompletado.add(vCompra.getNitProveedor());
            if (!vCompra.isOk()) {
                vCompra.setOk(true);
                agregarCompraVaciaInicio();
                onLimpiar();
            }

        } else {
            mensajesBean.addMensajes(vRespuesta);

            RequestContext.getCurrentInstance().execute("toastr.error(Error al guardar su Registro de factura', 'Error')");
            if (!vRespuesta.getMensajes().isEmpty()) {
                List<StrMensajeAplicacionDto> vMensajesLista = vRespuesta.getMensajes();
                List<ErrorDto> errores = new ArrayList<>();

                for (StrMensajeAplicacionDto msj : vMensajesLista) {
                    errores.add(new ErrorDto(msj.getDescripcion(), String.valueOf(registroComprasEstandarModel.getMisFacturas().indexOf(vCompra))
                            , msj.getDescripcionUi(),
                            "El " + msj.getDescripcionUi() + " es invalido."));
                }
                RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
                this.registroComprasEstandarModel.setErrores(errores);
            }

        }


    }
    
    public void eliminarCompra(ComprasCvDto dato) {
    	ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestCompras().eliminarCompra(dato);
		
		if(resp.isOk()) {
			mensajesBean.addMensajes(resp);
			this.registroComprasEstandarModel.getMisFacturas().removeIf(x -> x.equals(dato));
			RequestContext.getCurrentInstance().update("frmRegistroCompras:dtMisFacturas");
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('Proceso invalido', 'Error')");
		}

	}
}

