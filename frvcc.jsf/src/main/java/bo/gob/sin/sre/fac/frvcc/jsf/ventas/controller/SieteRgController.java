package bo.gob.sin.sre.fac.frvcc.jsf.ventas.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.VentaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.service.SieteRgService;
import bo.gob.sin.str.util.compresion.CompresorZip;

@Named
@SessionScoped
public class SieteRgController implements Serializable {
	private static final long serialVersionUID = 1L;

	// TODO INSTANCIA LOCAL
	DefaultStreamedContent file;
	private Resource srcfile;
	private List<VentaDto> sieteRgs;
	long ifc;
	long nit;
	
    @Inject
    private SieteRgService service;
    
    
	
	public SieteRgController() {
		this.sieteRgs = new ArrayList<>(0);
	}



	@PostConstruct
    public void init() {
		
		sieteRgs.add(0,addNuevVenta());
			 
    }
	
	
	
	// -------------------------- METODOS Y OPERACIONES ------------------------------
	
	
	
		// TODO ENVIO IDIVIDUAL
		public void guardarVenta(VentaDto venta) {
				//getContext();
				int index = sieteRgs.indexOf(venta);
				if(index==0) {
					List<VentaDto> list=Arrays.asList(venta);
					//nuevaVenta(list);				
				}else {
					// TODO add update service
					//actualizarVenta(venta);
					//ventas.set(index, venta);		
				}
	
		}
		
		
		// TODO Remover venta
		public void removerVenta(VentaDto venta) {
//			ResultadoGenericoDto<String> res = service.removeVenta(venta.getId());
//			if(res.isOk()) {
//				sieteRgs.remove(venta);
//				//RequestContext.getCurrentInstance().execute("toastr.success('Registro eliminado', 'Informacion's)");
//				RequestContext.getCurrentInstance().execute("toastr.success('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Informacion')");
//				RequestContext.getCurrentInstance().update("frmVentas:dtVentas");
//				//firtsRow();
//			}else {
//				RequestContext.getCurrentInstance().execute("toastr.error('Error en la consulta', 'Error')");
//			}
		}
	
	
		// TODO CALCULA EL IMPORTE
		public void calcImporteBase(VentaDto venta) {
			BigDecimal a =venta.getImprtTotal()!=null?venta.getImprtTotal():BigDecimal.ZERO;
			BigDecimal b =venta.getDescuento()!=null?venta.getDescuento():BigDecimal.ZERO;
			if (a.compareTo(b) > 0) {
				venta.setImprtBaseDf(a.subtract(b));	
			}else {
				venta.setDescuento(null);
				venta.setImprtBaseDf(null);
				RequestContext.getCurrentInstance().execute("toastr.error('El descuento debe ser menor al Importe Total', 'Error')");
			}
			
		}
		
		// TODO ADICIONA NUEVA VENTA VACIA 
		public VentaDto addNuevVenta() {
			VentaDto nuevo= new VentaDto();
			nuevo.setId(UUID.randomUUID().toString());
			
			nuevo.setEstado("V");
			
			return nuevo;
		};
		
		// ------------------------- FILE UPLOAD / DONWLOAD -----------------------------------------
		
		//  TODO descarga de archivo 
		public void dwnldVentaExcel() throws IOException {
			String PLANILLA_EXCEL=service.getSieteRgForm();
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
			//getContext();
			
			String uId=UUID.randomUUID().toString();
			System.out.println(">>>>>> UID "+ uId);
			
//			ResultadoGenericoDto<String> resRecepcion = service.saveFileRecepcion(uId, "V", "FAC", ifc, nit);
//			
//			ResultadoGenericoDto<String> res = service.saveFile(UUID.randomUUID().toString(),uId, srcfile);
////			
//			System.out.println(">>>>>> "+res+"\n resRecepcion >>>>>>>"+resRecepcion);
//			if(res.isOk()) {
//				RequestContext.getCurrentInstance().execute("toastr.success('"+res.getMensajes().get(0).getDescripcionUi()+"', 'Informacion')");	
//			}else {
//				RequestContext.getCurrentInstance().execute("toastr.error('Error fuera de control', 'Error')");
//			}
			
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
		
		

		// -------------------------- GETTERS AND SETTERS ------------------------------
		public List<VentaDto> getSieteRgs() {
			return sieteRgs;
		}

		public void setSieteRgs(List<VentaDto> sieteRgs) {
			this.sieteRgs = sieteRgs;
		}
		
		public DefaultStreamedContent getFile() {
	        return file;
	    }

		public void setFile(DefaultStreamedContent file) {
			this.file = file;
		}

}
