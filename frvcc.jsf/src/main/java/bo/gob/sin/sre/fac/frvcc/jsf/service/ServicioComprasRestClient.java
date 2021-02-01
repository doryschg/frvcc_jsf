package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasNotasCvDto;
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

public class ServicioComprasRestClient extends PeticionesRest{
	private static final Logger LOG = LoggerFactory.getLogger(ServicioComprasRestClient.class);
	static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public ServicioComprasRestClient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioAsociacionComprasRestClient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioAsociacionComprasRestClient");
	}
	public ServicioComprasRestClient()
	{
		
	}
	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}
	

public RespuestaComprasDto obtenerCompras(LinkedHashMap<String, Serializable> pFilters) {
		
		configMisFacturas();
		RespuestaComprasDto vResultado = new RespuestaComprasDto();

		String vRuta = host + "/rest/misfacturas/compras/";

		ObjectMapper mapper = new ObjectMapper();

		try {
			TypeReference<RespuestaComprasDto> listTypeRef = new TypeReference<RespuestaComprasDto>() {
			};
			
			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromJson(response.body().string(), listTypeRef);
				} 
			} else {
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaComprasDto();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaComprasDto();
		}
		return vResultado;
	}
	
	public RespuestaCompraSingleDto obtenerCompraPorId(String pIdFormulario) {
		RespuestaCompraSingleDto vResultado=new RespuestaCompraSingleDto();
				
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compra/"+pIdFormulario;

		try {
			TypeReference<RespuestaCompraSingleDto> listTypeRef = new TypeReference<RespuestaCompraSingleDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
				
					vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} else {
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaCompraSingleDto();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaCompraSingleDto();
		}

	

		return vResultado;
	}
	public ResultadoGenericoDto<String> asociarLibroCompra(String pIdLibro,String pIdCompra,String ptipoCompraId)
	{
		PeticionAsociarCompraLibroDto vLibrPeticion=new PeticionAsociarCompraLibroDto();
		vLibrPeticion.setLibroId(pIdLibro);
		vLibrPeticion.setTipoCompraId(ptipoCompraId);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compra/asociarlibro/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdCompra;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vLibrPeticion);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} else {
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
	public ResultadoGenericoDto<String> desasociarLibroCompra(String pIfLibro,String pIdCompra)
	{
		LibroPeticionDto vFompeti=new LibroPeticionDto();
		vFompeti.setLibroId(pIfLibro);
		ResultadoGenericoDto<String> vRespuesta =new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compra/desasociarlibro/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdCompra;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vFompeti);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} else {
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
	
	public Long obtenerTotalCompras(LinkedHashMap<String, Serializable> pFilters) {

		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compras/count";
		Long vResultado = 0L;
		try {
			TypeReference<Long> listTypeRef = new TypeReference<Long>() {
			};

			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
				
					vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} else {
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = 0L;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = 0L;
		}
		return vResultado;
	}

	
	
	public ResultadoGenericoDto<String> modificarCompra(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String> vRespuesta =new ResultadoGenericoDto<String>();
		vRespuesta.setOk(false);
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compra/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
				vRuta = vRuta + pCompra.getId();
				String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompra);
				Response response = putRequestWithHeaderAndBody(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) {
						vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef);

					}
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta = new ResultadoGenericoDto<>();
				}
			

		} catch (Exception e) {
			e.printStackTrace();
			return vRespuesta;
		}

		return vRespuesta;
	}


	
	public ResultadoGenericoDto<String> eliminarCompra(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compra/eliminar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pCompra.getId();
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pCompra);
			Response response = ejecutarDeleteConParametrosToken(vRuta, json);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} else {
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

	public ResultadoGenericoListaDto<ResultadoGenericoDto<String>> registrarCompras(List<ComprasCvDto> pCompras) {
		ResultadoGenericoListaDto<ResultadoGenericoDto<String>> vRespuesta = new ResultadoGenericoListaDto<ResultadoGenericoDto<String>>();
		List<ResultadoGenericoDto<String>> vObj =new  ArrayList<>();
		vRespuesta.setResultadoLista(vObj);
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compras";
		try {
			TypeReference<ResultadoGenericoListaDto<ResultadoGenericoDto<String>>> typeRef = new TypeReference<ResultadoGenericoListaDto<ResultadoGenericoDto<String>>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompras);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) {
						vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef); 
					}
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta = new ResultadoGenericoListaDto<>();
				}
				
	
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoListaDto<ResultadoGenericoDto<String>>();
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> registrarCompra(ComprasCvDto pCompras) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compra";
		try {
			TypeReference<ResultadoGenericoDto<String>> typeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompras);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) {
						vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef);
					}
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta = new ResultadoGenericoDto<>();
				}
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	


	
	public ResultadoGenericoDto<String> registrarCompraNota(ComprasNotasCvDto pCompras) {
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compraNota";
		try {
			TypeReference<ResultadoGenericoDto<String>> typeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompras);
				Response response = ejecutarPostConParametroMapperToken(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) {
						vRespuesta = Json.serializer().fromJson(response.body().string(), typeRef);
					}
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta = new ResultadoGenericoDto<>();
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	
	
	public Boolean modificarCompraNota(ComprasNotasCvDto pCompra) {
		Boolean vRespuesta = false;
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compraNota/";
		try {
			
				vRuta = vRuta + pCompra.getId();
				String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompra);
				Response response = putRequestWithHeaderAndBody(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) 
						vRespuesta = true;
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta =false;
				}
				

			}

		catch (Exception e) {
			e.printStackTrace();
			vRespuesta = false;
		}

		return vRespuesta;
	}
	
	public boolean eliminarCompraNota(ComprasNotasCvDto pCompraNota) {
		Boolean vRespuesta = false;

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/compraNota/eliminar/";

		try {

			vRuta = vRuta + pCompraNota.getId();
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pCompraNota);
			Response response = ejecutarDeleteConParametrosToken(vRuta, json);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) 
					vRespuesta = true;
			} else {
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vRespuesta =false;
			}

			} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = false;
		}

		return vRespuesta;
	}
	
	public ResultadoGenericoDto<RespuestaComprasNotaDto> obtenerComprasNota(
			LinkedHashMap<String, Serializable> pFilters) {
		ResultadoGenericoDto<RespuestaComprasNotaDto> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/comprasNota/";
		try {
			TypeReference<ResultadoGenericoDto<RespuestaComprasNotaDto>> listTypeRef = new TypeReference<ResultadoGenericoDto<RespuestaComprasNotaDto>>() {
			};

			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					LOG.info("respuesta servicio {}", response.body());
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			}
			else
			{	
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vRespuesta=new  ResultadoGenericoDto<>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return vRespuesta;		
		}
		return vRespuesta;
	}
	
	public ResultadoGenericoDto<String> modificarCompraTipoCompraId(ComprasCvDto pCompra) {
		ResultadoGenericoDto<String> vRespuesta =new ResultadoGenericoDto<String>();
		vRespuesta.setOk(false);
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/compra/tipoCompra/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
				vRuta = vRuta + pCompra.getId();
				String json = "";
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(pCompra);
				Response response = putRequestWithHeaderAndBody(vRuta, json);
				if (response!=null) {
					if (response.code() == StatusCodes.OK) {
						vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef);
				} else {
					RequestContext.getCurrentInstance()
					.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
					vRespuesta=new  ResultadoGenericoDto<>();
				}
				

			}

		} catch (Exception e) {
			e.printStackTrace();
			return vRespuesta;
		}

		return vRespuesta;
	}
	

}
