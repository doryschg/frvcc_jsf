package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioPeticionDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.LibroPeticionDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.PeticionAsociarCompraLibroDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaCompraSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasNotaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoListaDto;
import io.undertow.util.StatusCodes;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServicioComprasEstandarRestClient extends PeticionesRest{
	private static final Logger LOG = LoggerFactory.getLogger(ServicioComprasRestClient.class);
	static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public ServicioComprasEstandarRestClient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioComprasEstandarRestClient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioAsociacionComprasRestClient");
	}
	public ServicioComprasEstandarRestClient()
	{
		
	}
	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}
	

	
	public ResultadoGenericoDto<String> modificarCompraEstandar(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String> vRespuesta =new ResultadoGenericoDto<String>();
		vRespuesta.setOk(false);
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compra_libro/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
				vRuta = vRuta + pCompra.getId();
				String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompra);
				Response response = putRequestWithHeaderAndBody(vRuta, json);
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return vRespuesta;
		}

		return vRespuesta;
	}




	public ResultadoGenericoListaDto<ResultadoGenericoDto<String>> registrarComprasEstandar(List<ComprasCvDto> pCompras) {
		ResultadoGenericoListaDto<ResultadoGenericoDto<String>> vRespuesta = new ResultadoGenericoListaDto<ResultadoGenericoDto<String>>();
		List<ResultadoGenericoDto<String>> vObj =new  ArrayList<>();
		vRespuesta.setResultadoLista(vObj);
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compras_libro";
		try {
			TypeReference<ResultadoGenericoListaDto<ResultadoGenericoDto<String>>> typeRef = new TypeReference<ResultadoGenericoListaDto<ResultadoGenericoDto<String>>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompras);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);

				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef); 
//							fromInputStream(response.body().byteStream(), typeRef);
				}
				//LOG.info("------------- Registros de Compra :" + vRespuesta);
		//CommandNotRegisteredError, CommandHandlerExecutionError
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoListaDto<ResultadoGenericoDto<String>>();
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> registrarCompraEstandar(ComprasCvDto pCompras) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compra_libro";
		try {
			TypeReference<ResultadoGenericoDto<String>> typeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompras);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef);
				}
				//LOG.info("------------- Registro de Compra :" + vRespuesta);
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	

}
