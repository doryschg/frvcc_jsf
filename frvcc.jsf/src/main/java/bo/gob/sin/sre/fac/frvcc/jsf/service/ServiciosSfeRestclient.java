package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sen.enmo.jsf.service.RestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioPeticionDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.PeticionDeclaracionFormularioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.PeticionRepresentacionGraficaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormularioSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaFormulariosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaRepresentacionGraficaDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.str.ccs.jsfconf.RestUrl;
import io.undertow.util.StatusCodes;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiciosSfeRestclient extends PeticionesRest{
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiciosSfeRestclient.class);


	public ServiciosSfeRestclient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioFormulariosRestclient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioFormulariosRestclient");
	}
	public ServiciosSfeRestclient()
	{
		
	}
	
	public RespuestaFormulariosDto obtenerFormulariosActivos(LinkedHashMap<String, Serializable> pFilters) {
		RespuestaFormulariosDto vResultado=new RespuestaFormulariosDto();
				
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formularios/";

		try {
			TypeReference<RespuestaFormulariosDto> listTypeRef = new TypeReference<RespuestaFormulariosDto>() {
			};
			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);

			if (response.code() == StatusCodes.OK) {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaFormulariosDto();
		}

	

		return vResultado;
	}

	
	public RespuestaRepresentacionGraficaDto obtenerRepresentacionGraficaFe(String pCuf,Long pNitEmisor,Long pNumeroFactura)
	{
		RespuestaRepresentacionGraficaDto vRespuesta = new RespuestaRepresentacionGraficaDto();
		PeticionRepresentacionGraficaDto vPeticion=new PeticionRepresentacionGraficaDto(pCuf,pNitEmisor,pNumeroFactura,2);
		

		configSfe();

		String vRuta = host + "/rest/facturacion/consultas/representacion/factura/";

		try {

			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vPeticion);
			TypeReference<RespuestaRepresentacionGraficaDto> listTypeRef = new TypeReference<RespuestaRepresentacionGraficaDto>() {
			};
			Response response = ejecutarPostConParametroToken(vRuta, json);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);;
			}

		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new RespuestaRepresentacionGraficaDto();
		}

		return vRespuesta;
	}

	public RespuestaFormularioSingleDto obtenerFormularioPorId(String pIdFormulario) {
		RespuestaFormularioSingleDto vResultado=new RespuestaFormularioSingleDto();
				
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/"+pIdFormulario;

		try {
			TypeReference<RespuestaFormularioSingleDto> listTypeRef = new TypeReference<RespuestaFormularioSingleDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND || response.code() == StatusCodes.BAD_REQUEST) {
				vResultado = new RespuestaFormularioSingleDto();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaFormularioSingleDto();
		}

	

		return vResultado;
	}
	
	
}
