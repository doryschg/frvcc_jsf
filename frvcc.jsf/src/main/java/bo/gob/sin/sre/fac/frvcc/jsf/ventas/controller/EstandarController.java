package bo.gob.sin.sre.fac.frvcc.jsf.ventas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.VentaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.service.EstandarService;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;
import bo.gob.sin.str.util.compresion.CompresorZip;

//@ApplicationScoped @RequestScoped
//@ViewScoped
@Named
@SessionScoped
public class EstandarController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO INSTANCIA LOCAL
	DefaultStreamedContent file;
	private Resource srcfile;
	private List<VentaDto> ventas;
	long ifc;
	long nit;
	

    @Inject
    private EstandarService service;

	// TODO CONTRUCTOR
	public EstandarController() {

		this.ventas = new ArrayList<>(0);
	}
	
	@PostConstruct
    public void init() {
		//ventas = service.createData(1);
		
		//if (!FacesContext.getCurrentInstance().isPostback()){
		
			ventas.add(0,addNuevVenta());
			
		//}
			 
    }
	
	private void getContext() {
		ContextoJSF contexto = new ContextoJSF();
		ifc = contexto.getUsuario().getPersonaId();
		nit = Long.parseLong(contexto.getUsuario().getNit());
	}
	
	public void firtsRow() {
		RequestContext.getCurrentInstance().execute("firstRow()");
	}

	// TODO ENVIO de lista
	public void guardarRows() {
		getContext();
		
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ventas");
		Gson vGson = new Gson();
		JsonArray ventas = vGson.fromJson(value, JsonArray.class);
		System.out.println(ventas);
		List<VentaDto> rVentas= new ArrayList<>(0);
		try {
			for (JsonElement jVenta : ventas) {
				JsonObject jObjt = jVenta.getAsJsonObject();
				VentaDto a =new VentaDto();
				
				a.setId(UUID.randomUUID().toString());
				
				a.setFchFactura(jObjt.getAsJsonObject("fchFactura").get("valor").getAsString());
				a.setNroFactura(jObjt.getAsJsonObject("nroFactura").get("valor").getAsLong());
				a.setNitCliente(jObjt.getAsJsonObject("nitCliente").get("valor").getAsLong());
				a.setRazonSocial(jObjt.getAsJsonObject("razonSocial").get("valor").getAsString());
				a.setImprtTotal(jObjt.getAsJsonObject("imprtTotal").get("valor").getAsBigDecimal());
				a.setImprtTasas(jObjt.getAsJsonObject("imprtTasas").get("valor").getAsBigDecimal());
				a.setImprtExntas(jObjt.getAsJsonObject("imprtExntas").get("valor").getAsBigDecimal());
				a.setVntasTsaCero(jObjt.getAsJsonObject("vntasTsaCero").get("valor").getAsBigDecimal());
				a.setSubTotal(jObjt.getAsJsonObject("subTotal").get("valor").getAsBigDecimal());
				a.setDescuento(jObjt.getAsJsonObject("descuento").get("valor").getAsBigDecimal());
				a.setImprtBaseDf(jObjt.getAsJsonObject("imprtBaseDf").get("valor").getAsBigDecimal());
				a.setDbitoFiscal(jObjt.getAsJsonObject("dbitoFiscal").get("valor").getAsBigDecimal());
				a.setCodAutorCuf(jObjt.getAsJsonObject("codAutorCuf").get("valor").getAsString());
				a.setCodControl(jObjt.getAsJsonObject("codControl").get("valor").getAsString());
				a.setEstado(jObjt.getAsJsonObject("estado").get("valor").getAsString());
				
				rVentas.add(a);

			}
			enviarVentas(rVentas);
		} catch (Exception e) {
			// TODO: handle exception
			RequestContext.getCurrentInstance().execute("toastr.error('"+e.getMessage()+"', 'Error')");
		}
		
	}
	
	// 	TODO envio de al servicio
	public void enviarVentas(List<VentaDto> sVentas) {
		getContext();
		List<StrMensajeAplicacionDto> res = service.saveVenta(sVentas,ifc,nit);
		if(res.get(0).getCodigo()==2129) {
			//adicionar(sVentas);
			ventas.addAll(sVentas);
			RequestContext.getCurrentInstance().update("frmVentas:dtVentas");
			RequestContext.getCurrentInstance().execute("toastr.success('"+res.get(0).getDescripcionUi()+"', 'Informacion')");
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('"+res.get(0).getDescripcionUi()+"', 'Error')");		
		}
	}
	
	void adicionar(List<VentaDto> vnts) {
		if(vnts.size()>1) {
			ventas.addAll(vnts);
		}else {
			int index = ventas.indexOf(vnts.get(0));
			if(index==0) {
				ventas.add(0,addNuevVenta());	
			}else {
				ventas.set(index, vnts.get(0));		
			}
		}
		RequestContext.getCurrentInstance().update("frmVentas:dtVentas");
	}
	
	// TODO envio de una nueva venta
	public void nuevaVenta(List<VentaDto>list) {
		List<StrMensajeAplicacionDto> res = service.saveVenta(list,ifc,nit);
		if(res.get(0).getCodigo()==2129) {
			adicionar(list);
			RequestContext.getCurrentInstance().execute("toastr.success('"+res.get(0).getDescripcionUi()+"', 'Informacion')");
			firtsRow();
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('"+res.get(0).getDescripcionUi()+"', 'Error')");		
		}
	}
	
	public void actualizarVenta(VentaDto venta) {
		//ResultadoGenericoDto<String> res = service.updateVenta(venta);//(list,ifc,nit);
		//if(res.isOk()) {
			adicionar(Arrays.asList(venta));
			//RequestContext.getCurrentInstance().execute("toastr.success('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Informacion')");
			RequestContext.getCurrentInstance().execute("toastr.success('Se actualizo de manera correcta', 'Informacion')");
		//}else {
			//RequestContext.getCurrentInstance().execute("toastr.error('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Error')");		
		//}
			
			firtsRow();
	}
	
	// TODO ENVIO IDIVIDUAL
	public void guardarVenta(VentaDto venta) {
			getContext();
			int index = ventas.indexOf(venta);
			if(index==0) {
				List<VentaDto> list=Arrays.asList(venta);
				nuevaVenta(list);
				
			}else {
				// TODO add update service
				actualizarVenta(venta);
				//ventas.set(index, venta);		
			}
			// TODO no remover aun n pruebas
			/*List<VentaDto> sendVentas=new ArrayList<VentaDto>(0);	
			sendVentas.add(venta);
			int index = ventas.indexOf(venta);
			List<StrMensajeAplicacionDto> res = service.saveVenta(sendVentas,ifc,nit);
			if(res.get(0).getCodigo()==2129) {
				
				if(index==0) {
					ventas.add(0,addNuevVenta());	
				}else {
					// TODO add update service
					ventas.set(index, venta);		
				}
				RequestContext.getCurrentInstance().execute("toastr.success('"+res.get(0).getDescripcionUi()+"', 'Informacion')");

				RequestContext.getCurrentInstance().update("frmVentas:dtVentas");
				
				firtsRow();
			}else {
				if(index!=0) ventas.set(index, venta);//ventas.remove(venta);
				RequestContext.getCurrentInstance().execute("toastr.error('"+res.get(0).getDescripcionUi()+"', 'Error')");		
			}
			*/
			//sendVentas.clear();
	}
	
	
	// TODO Remover venta
	public void removerVenta(VentaDto venta) {
		ResultadoGenericoDto<String> res = service.removeVenta(venta.getId());
		if(res.isOk()) {
			ventas.remove(venta);
			//RequestContext.getCurrentInstance().execute("toastr.success('Registro eliminado', 'Informacion's)");
			RequestContext.getCurrentInstance().execute("toastr.success('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Informacion')");
			RequestContext.getCurrentInstance().update("frmVentas:dtVentas");
			firtsRow();
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('Error en la consulta', 'Error')");
		}
	}
	
	
	// TODO ADICIONA NUEVA VENTA VACIA 
	public VentaDto addNuevVenta() {
		VentaDto nuevo= new VentaDto();
		nuevo.setId(UUID.randomUUID().toString());
		
		nuevo.setEstado("VIG");
		
		return nuevo;
	};
	
	// TODO CALCULA EL IMPORTE
	public void calcImporteBase(VentaDto venta) {
		BigDecimal a =venta.getImprtTotal()!=null?venta.getImprtTotal():BigDecimal.ZERO;
		BigDecimal b =venta.getImprtTasas()!=null?venta.getImprtTasas():BigDecimal.ZERO;
		BigDecimal c =venta.getImprtExntas()!=null?venta.getImprtExntas():BigDecimal.ZERO;
		BigDecimal d =venta.getVntasTsaCero()!=null?venta.getVntasTsaCero():BigDecimal.ZERO;
		
		//venta.setImprtBaseDf(venta.getImprtTotal().subtract(venta.getDescuento()));
		venta.setSubTotal(a.subtract(b).subtract(c).subtract(d));
		
		BigDecimal e =venta.getSubTotal()!=null?venta.getSubTotal():BigDecimal.ZERO;
		BigDecimal f =venta.getDescuento()!=null?venta.getDescuento():BigDecimal.ZERO;
		venta.setImprtBaseDf(e.subtract(f));
	}
	
	
	//  TODO descarga de archivo 
	public void dwnldVentaExcel() throws IOException {
		String PLANILLA_EXCEL=service.getEstandarForm();
		try {
			byte[] vArchivoDecodificado = Base64.getDecoder().decode(PLANILLA_EXCEL.getBytes());
			InputStream vArchivoXlsStream = new ByteArrayInputStream(vArchivoDecodificado);
			setFile(new DefaultStreamedContent(vArchivoXlsStream,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;","PlantillaRegistroVentas" + ".xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	//  TODO LEER ARCHIVO
	public void getFileData(FileUploadEvent event) {
		
		try {
			UploadedFile upldFile = event.getFile();
			srcfile = getUserFileResource(upldFile);
			//byte[] vArchivo =comprimirArchivo(file);
			System.out.println("Archivos size "+upldFile.getSize() +""+upldFile.getFileName());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//  TODO ENVIAR ARCHIVO
	public void sendFileData() {
		getContext();
		
		String uId=UUID.randomUUID().toString();
		System.out.println(">>>>>> UID "+ uId);
		
		ResultadoGenericoDto<String> resRecepcion = service.saveFileRecepcion(uId, "V", "FAC", ifc, nit);
		
		ResultadoGenericoDto<String> res = service.saveFile(UUID.randomUUID().toString(),uId, srcfile);
		
		System.out.println(">>>>>> "+res+"\n resRecepcion >>>>>>>"+resRecepcion);
		if(res.isOk()) {
			RequestContext.getCurrentInstance().execute("toastr.success('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Informacion')");	
		}else {
			RequestContext.getCurrentInstance().execute("toastr.error('Error fuera de control', 'Error')");
		}
		
	}
	
	//  TODO CONVIERTE ARCHIVO A ZIP 	
	public static Resource getUserFileResource(UploadedFile pFile) throws IOException {
		String vFilename = pFile.getFileName().replace(".xlsx", "");
		Path tempFile = Files.createTempFile(vFilename, ".zip");

		Files.write(tempFile,CompresorZip.zipBytes(pFile.getFileName(), pFile.getContents()));
		System.out.println("uploading: " + tempFile);
		File file = tempFile.toFile();
		return new FileSystemResource(file);
	}
	
	
//	private byte[] comprimirArchivo(UploadedFile file) throws IOException {
//		
//		String fileName = file.getFileName();
//		
//		byte[] vArchivoCompreso = file.getContents();
//		vArchivoCompreso = CompresorZip.zipBytes(fileName, file.getContents());
//		String vEncodedBase64 = new String(Base64.getEncoder().encode(vArchivoCompreso));
//		// this.fileContentEncode64Compress = vEncodedBase64;
//		return vArchivoCompreso;
//		
//	}
	
	
	

	// -------------------------- GETTERS AND SETTERS ------------------------------
	
	public List<VentaDto> getVentas() {
		return ventas;
	}

	public void setVentas(List<VentaDto> ventas) {
		this.ventas = ventas;
	}

	public DefaultStreamedContent getFile() {
        return file;
    }
	
	public void setFile(DefaultStreamedContent file) {
		this.file = file;
	}

//	public void onRowSelect(VentaDto event) {
//		slctdVenta=event;
//		//slctdVenta=((VentaDto) event.getObject());
//        //FacesMessage msg = new FacesMessage("Car Selected", event.getObject().getId());
//        //FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
	

}
