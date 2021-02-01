package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormulariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaSucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaUsuariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.SucursalUsuarioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.SucursalUsuarioRelacionadoDto;
import io.undertow.util.StatusCodes;
import okhttp3.Response;

public class ServicioSucursalUsuario extends PeticionesRest {
	
	public RespuestaSucursalUsuarioDto obtenerSucursalUsuarioActivos(LinkedHashMap<String, Serializable> pFilters) {
		RespuestaSucursalUsuarioDto vResultado=new RespuestaSucursalUsuarioDto();
				
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/sucursalesUsuarios";

		try {
			TypeReference<RespuestaSucursalUsuarioDto> listTypeRef = new TypeReference<RespuestaSucursalUsuarioDto>() {
			};
			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			}
			else
			{
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaSucursalUsuarioDto();
			}
				
			 
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaSucursalUsuarioDto();
		}

	

		return vResultado;
	}
	public RespuestaUsuariosDto obtenerUsuariosConsolidadoresActivos(Long pIfc)
	{
		RespuestaUsuariosDto vResultado=new RespuestaUsuariosDto();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/usuarios/"+pIfc;
		try {
			TypeReference<RespuestaUsuariosDto> listTypeRef = new TypeReference<RespuestaUsuariosDto>() {
			};
			Response response = ejecutarGetSinParametrosToken(vRuta);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}	
			}
			else
			{
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaUsuariosDto();
			}
				
			 
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaUsuariosDto();
		}

		return vResultado;
	}
	
	public ResultadoGenericoDto<String> guardarSucursalUsuario(SucursalUsuarioDto pSucursalUsuario)
	{
		ResultadoGenericoDto<String> vRespuesta=new ResultadoGenericoDto<>();
		configMisFacturas();
		

		String vRuta = host + "/rest/misfacturas/sucursalUsuario/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pSucursalUsuario);
			Response response = ejecutarPostConParametroToken(vRuta, json);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
				}
			}
			else
			{
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vRespuesta = new ResultadoGenericoDto<>();
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<>();
		}
		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> inhabilitarSucursalUsuario(SucursalUsuarioDto pSucursalUsuario)
	{
		ResultadoGenericoDto<String> vRespuesta=new ResultadoGenericoDto<>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/sucursalUsuario/inhabilitar/"+pSucursalUsuario.getId();
		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pSucursalUsuario);
			Response response = putRequestWithHeaderAndBody(vRuta,json);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}	
			}
			else
			{
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vRespuesta = new ResultadoGenericoDto<String>();
			}
				
			 
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}
		return vRespuesta;
	}
	
}
