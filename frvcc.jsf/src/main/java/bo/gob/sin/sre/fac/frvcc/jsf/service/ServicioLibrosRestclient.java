package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ContribuyenteNewtonDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.LibroCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.LibroPeticionDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.PeticionDeclaracionFormularioDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaConsultaResumenLCVDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaLibroSingleDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaLibrosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServicioLibrosRestclient extends PeticionesRest {
	private static final Logger LOG = LoggerFactory.getLogger(ServicioFormulariosRestclient.class);


	public ServicioLibrosRestclient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioFormulariosRestclient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioFormulariosRestclient");
	}
	public ServicioLibrosRestclient()
	{
		
	}
	
	public RespuestaLibrosDto obtenerLibrosActivos(LinkedHashMap<String, Serializable> pFilters) {
		RespuestaLibrosDto vResultado=new RespuestaLibrosDto();
				
		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libros/";

		try {
			TypeReference<RespuestaLibrosDto> listTypeRef = new TypeReference<RespuestaLibrosDto>() {
			};
			//System.out.println("imprime formularios................."+vRuta);
			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			//System.out.println("imprime formularios................."+response);

			if (response.code() == StatusCodes.OK) {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaLibrosDto();
		}

	

		return vResultado;
	}
	
	public RespuestaLibroSingleDto obtenerLibroPorId(String pIdFormulario) {
		RespuestaLibroSingleDto vResultado=new RespuestaLibroSingleDto();
				
		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/"+pIdFormulario;

		try {
			TypeReference<RespuestaLibroSingleDto> listTypeRef = new TypeReference<RespuestaLibroSingleDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND || response.code() == StatusCodes.BAD_REQUEST) {
				vResultado = new RespuestaLibroSingleDto();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaLibroSingleDto();
		}

	

		return vResultado;
	}
	
	public ResultadoGenericoDto<String> guardarLibroNuevo(LibroCvDto pLibro)
	{
		ResultadoGenericoDto vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pLibro);
			Response response = ejecutarPostConParametroToken(vRuta, json);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}

		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<>();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String> declararLibro(LibroCvDto pLibro,Integer codAdm)
	{
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();
		PeticionDeclaracionFormularioDto pDeclararformulario=new PeticionDeclaracionFormularioDto();
		pDeclararformulario.setId(pLibro.getId());
		pDeclararformulario.setCodAdmin(codAdm);

		String vRuta = host + "/rest/misfacturas/libro/declarar/"+pLibro.getId();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pDeclararformulario);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String> seleccionarComprasLibro(String pIdLibro, List<String> compraIds)
	{
		LibroPeticionDto vLibroPet= new LibroPeticionDto();
		vLibroPet.setListaIdsCompras(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/seleccionarCompras/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdLibro;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibroPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}

	
	public ResultadoGenericoDto<String> desmarcarComprasLibro(String pIdLibro, List<String> compraIds)
	{
		LibroPeticionDto vLibroPet= new LibroPeticionDto();
		vLibroPet.setListaIdsCompras(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/desmarcarCompras/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdLibro;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibroPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	
	public ContribuyenteNewtonDto verificaContribuyenteNewton(Long pIfc) {
		ContribuyenteNewtonDto vResultado=new ContribuyenteNewtonDto();
		configMisFacturasLibros();
		String vRuta = host + "/rest/misfacturas/libro/contribuyenteNewton/"+pIfc;

		try {
			TypeReference<ContribuyenteNewtonDto> listTypeRef = new TypeReference<ContribuyenteNewtonDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND || response.code() == StatusCodes.BAD_REQUEST) {
				vResultado = new ContribuyenteNewtonDto();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ContribuyenteNewtonDto();
		}
		return vResultado;
	}
	public ResultadoGenericoDto<String> seleccionarComprasNotasLibro(String pIdLibro, List<String> compraIds)
	{
		LibroPeticionDto vLibroPet= new LibroPeticionDto();
		vLibroPet.setListaIdsCompras(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/seleccionarComprasNotas/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdLibro;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibroPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}

	
	public ResultadoGenericoDto<String> desmarcarComprasNotasLibro(String pIdLibro, List<String> compraIds)
	{
		LibroPeticionDto vLibroPet= new LibroPeticionDto();
		vLibroPet.setListaIdsCompras(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/desmarcarComprasNotas/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdLibro;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibroPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> habilitarRectificacion(String pIdLibro)
	{
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturasLibros();

		String vRuta = host + "/rest/misfacturas/libro/habilitarectificatorio/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdLibro;
			String json = "";
			Response response = putRequestWithHeaderAndBody(vRuta,json);
			LOG.info("response {}", response);
			if (response.code() == StatusCodes.OK) {
				vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vRespuesta;
	}
	
	 public RespuestaConsultaResumenLCVDto obtenerResumenLCV(Long pNit,Integer pGestion) {
		configMisFacturasLibros();
		String vRuta = host + "/rest/misfacturas/libro/consultaResumenLcv/"+ pNit+"/"+pGestion;
		RespuestaConsultaResumenLCVDto vResultado = new RespuestaConsultaResumenLCVDto();
		
		try {
			TypeReference<RespuestaConsultaResumenLCVDto> listTypeRef = new TypeReference< RespuestaConsultaResumenLCVDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);
			

			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new RespuestaConsultaResumenLCVDto();
			} else {
				vResultado =  Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaConsultaResumenLCVDto();
		}

		return  vResultado;
	}
	
	
}
