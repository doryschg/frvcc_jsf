package bo.gob.sin.sre.fac.frvcc.jsf.ventas.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.RespuestaVentasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.VentaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.ventas.model.VentasCvDto;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;
import io.undertow.util.StatusCodes;
import okhttp3.Response;


//@ApplicationScoped
@RequestScoped
public class EstandarService {

	@Inject
	 PeticionesRest config;
	
	public String getEstandarForm() {
		return "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";
	}

	
	// TODO REGISTRAR VENTA POR LISTA
	public List<StrMensajeAplicacionDto> saveVenta(List<VentaDto> lista, long nitProveedor, long ifcProveedor ) {
		Map<String, Object> data = new HashMap<>();
		data.put("ifcProveedor", ifcProveedor);
		data.put("nitProveedor", nitProveedor);
		data.put("origen", "APL");
		data.put("ventas", lista);
		
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();
		TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {};
		try {
			String response = config.sendPost("/rest/misfacturas/ventas", data);
			
			vRespuesta = Json.serializer().fromJson(response, listTypeRef);
			msg=vRespuesta.getMensajes();
		
		} catch (IOException e) {
			//res.put("IOException", e.getCause());
			msg.add(new StrMensajeAplicacionDto(e.hashCode(),e.hashCode(),e.getCause().toString(),e.getCause().toString(),"",""));
		}

		return msg;
	}
	
	// TODO REGISTRA VENTA MASIVA - ARCHIVO ZIP
	public ResultadoGenericoDto<String> saveFile(String uid, String id, Resource file){
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();
		TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {};
		try {
			String res=config.sendPutFile("/rest/misfacturas/venta/archivo/"+uid, id, file);
			vRespuesta = Json.serializer().fromJson(res, listTypeRef);
			msg=vRespuesta.getMensajes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			msg.add(new StrMensajeAplicacionDto(e.hashCode(),e.hashCode(),e.getCause().toString(),"","",""));
		}
		return vRespuesta;
	}
	
	// TODO REGISTRAR VENTA MASIVA - RECEPCION
	public ResultadoGenericoDto<String> saveFileRecepcion(String id, String tipoRecepcionId, String tipoDocumentoId, Long ifc, Long nit){
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();
		TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {};
		JSONObject obj = new JSONObject();
		obj.put("tipoRecepcionId", tipoRecepcionId);
		obj.put("tipoDocumentoId", tipoDocumentoId);
		obj.put("ifcProveedor", ifc);
		obj.put("nitProveedor", nit);
				
		try {
			String res=config.sendPut("/rest/misfacturas/recepcion/"+id,obj );
			vRespuesta = Json.serializer().fromJson(res, listTypeRef);
			msg=vRespuesta.getMensajes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			msg.add(new StrMensajeAplicacionDto(e.hashCode(),e.hashCode(),e.getCause().toString(),"","",""));
		}
		return vRespuesta;
	}

	
	// TODO ANULAR VENTA
	public ResultadoGenericoDto<String> removeVenta(String id){
		// /venta/anular/{id}
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();
		TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {};
		try {
			String res=config.sendPut("/rest/misfacturas/venta/anular/"+id,new JSONObject());
			vRespuesta = Json.serializer().fromJson(res, listTypeRef);
			msg=vRespuesta.getMensajes();
		} catch (Exception e) {
			msg.add(new StrMensajeAplicacionDto(e.hashCode(),e.hashCode(),e.getCause().toString(),"","",""));
		}		
		return vRespuesta;
	}
	
	
	// TODO REMOVER VENTA
	public ResultadoGenericoDto<String> updateVenta(String id){
			// /venta/anular/{id}
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();
		
		return vRespuesta;
	}
	
	public RespuestaVentasDto obtenerVentas(LinkedHashMap<String, Serializable> pFilters) {

		RespuestaVentasDto vResultado = new RespuestaVentasDto();

		String vRuta = "/rest/misfacturas/ventas/";

		ObjectMapper mapper = new ObjectMapper();

		try {
			TypeReference<RespuestaVentasDto> listTypeRef = new TypeReference<RespuestaVentasDto>() {
			};

			Response response = config.ejecutarGetConParametrosQuery(vRuta, pFilters);
			if (response != null) {
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromJson(response.body().string(), listTypeRef);
				}
			} else {
//				RequestContext.getCurrentInstance()
//						.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
//				vResultado = new RespuestaVentasDto();
			}

		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaVentasDto();
		}
		// System.err.println(vResultado);
		return vResultado;
	}
	
	public ResultadoGenericoDto<String> anularVenta(VentasCvDto pVenta) {
		List<StrMensajeAplicacionDto> msg = new ArrayList<>();
		ResultadoGenericoDto<String> vResultado = new ResultadoGenericoDto();

		String vRuta = "/rest/misfacturas/venta/anular/";

		ObjectMapper mapper = new ObjectMapper();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			json = mapper.writeValueAsString(pVenta);
			JSONObject request = new JSONObject(json);
			String res = config.sendPutFileExtra(vRuta, pVenta.getId(), request);

			vResultado = Json.serializer().fromJson(res, listTypeRef);
			if (vResultado != null) {
				msg = vResultado.getMensajes();
			} else {
//				RequestContext.getCurrentInstance()
//						.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
			}

		} catch (Exception e) {
			msg.add(new StrMensajeAplicacionDto(e.hashCode(), e.hashCode(), e.getCause().toString(), "", "", ""));
		}
		return vResultado;
	}
	
}
