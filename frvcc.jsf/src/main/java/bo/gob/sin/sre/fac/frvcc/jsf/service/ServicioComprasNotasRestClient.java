package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.LibroPeticionDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasNotaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaNotaSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import io.undertow.util.StatusCodes;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServicioComprasNotasRestClient extends PeticionesRest{
	private static final Logger LOG = LoggerFactory.getLogger(ServicioComprasNotasRestClient.class);
	static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public ServicioComprasNotasRestClient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioCompraNotasRestClient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioCompraNotasRestClient");
	}
	public ServicioComprasNotasRestClient()
	{
		
	}
	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}

	public ResultadoGenericoDto<String> registrarCompraNota(ComprasNotasCvDto pComprasNotas) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compraNota";
		try {
			TypeReference<ResultadoGenericoDto<String>> typeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pComprasNotas);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef);
				}
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	
	
	public ResultadoGenericoDto<String> modificarCompraNota(ComprasNotasCvDto pCompra) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compraNota/";
		try {
			
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {};

				vRuta = vRuta + pCompra.getId();
				String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompra);
				Response response = putRequestWithHeaderAndBody(vRuta, json);
				//System.out.println("respuesta modificacion compra nota.................."+response);
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> eliminarCompraNota(ComprasNotasCvDto pCompraNota) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compraNota/eliminar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {} ;

			vRuta = vRuta + pCompraNota.getId();
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pCompraNota);

			Response response = ejecutarDeleteConParametrosToken(vRuta, json);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	
	public RespuestaComprasNotaDto obtenerComprasNota(
			LinkedHashMap<String, Serializable> pFilters) {
		RespuestaComprasNotaDto vRespuesta = new RespuestaComprasNotaDto();

		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/comprasNota/";
		try {
			TypeReference<RespuestaComprasNotaDto> listTypeRef = new TypeReference<RespuestaComprasNotaDto>() {
			};

			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if (response.code() == StatusCodes.OK) {
				LOG.info("respuesta servicio {}", response.body());
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return vRespuesta;		
		}
		return vRespuesta;
	}
	public ResultadoGenericoDto<String> asociarLibroCompraNota(String pIdLibro,String pIdCompra)
	{
		LibroPeticionDto vLibrPeticion=new LibroPeticionDto();
		vLibrPeticion.setLibroId(pIdLibro);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compraNota/asociar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdCompra;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibrPeticion);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			//System.out.println("factura asociada....................."+ response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}

		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> desAsociarLibroCompraNota(String pIdLibro,String pIdCompra)
	{
		LibroPeticionDto vLibrPeticion=new LibroPeticionDto();
		vLibrPeticion.setLibroId(pIdLibro);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compraNota/des_asociar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdCompra;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibrPeticion);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			//System.out.println("factura asociada....................."+ response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}

		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	
	
	public RespuestaNotaSingleDto obtenerCompraNotaPorId(String pIdNota) {
		RespuestaNotaSingleDto vResultado=new RespuestaNotaSingleDto();
				
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compraNota/"+pIdNota;

		try {
			TypeReference<RespuestaNotaSingleDto> listTypeRef = new TypeReference<RespuestaNotaSingleDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
				
					//vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
					LOG.info("response {} ", response.body());
					vResultado = Json.serializer().fromJson(response.body().string(), listTypeRef);
					LOG.info("resultado {} ", vResultado.getCompraNotaResponse());

				}
			} else {
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaNotaSingleDto();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaNotaSingleDto();
		}

	

		return vResultado;
	}

}
