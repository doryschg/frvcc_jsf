package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServicioFormulariosRestclient extends PeticionesRest{

	private static final Logger LOG = LoggerFactory.getLogger(ServicioFormulariosRestclient.class);


	public ServicioFormulariosRestclient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioFormulariosRestclient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioFormulariosRestclient");
	}
	public ServicioFormulariosRestclient()
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
				vResultado = new RespuestaFormulariosDto();
			}


		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaFormulariosDto();
		}



		return vResultado;
	}


	public ResultadoGenericoDto<String> guardarFormularioNuevo(FormularioCvDto pFormulario)
	{
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
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

	public ResultadoGenericoDto<String> guardarCambiosFormulario(FormularioCvDto pFormularioModificado)
	{
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/modificar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pFormularioModificado.getId();
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormularioModificado);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			if(response!=null) {
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
	public ResultadoGenericoDto<String> eliminarFormulario(FormularioCvDto pFormulario) {
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/eliminar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pFormulario.getId();
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
			Response response = ejecutarDeleteConParametrosToken(vRuta, json);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vRespuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			}
			else
			{
				vRespuesta = new ResultadoGenericoDto<>();
				RequestContext.getCurrentInstance()
				.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
			}


		} catch (Exception e) {
			e.printStackTrace();
			vRespuesta = new ResultadoGenericoDto<>();
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
				vResultado = new RespuestaFormularioSingleDto();
			}

		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaFormularioSingleDto();
		}

		return vResultado;
	}
	public ResultadoGenericoDto<String> declararFormulario(FormularioCvDto pFormulario,Integer codAdm)
	{
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();
		PeticionDeclaracionFormularioDto pDeclararformulario=new PeticionDeclaracionFormularioDto();
		pDeclararformulario.setId(pFormulario.getId());
		pDeclararformulario.setCodAdmin(codAdm);

		String vRuta = host + "/rest/misfacturas/formulario/declarar/"+pFormulario.getId();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pDeclararformulario);
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
			vRespuesta = new ResultadoGenericoDto<>();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String> rectificarFormulario(FormularioCvDto pFormulario,Integer codAdm)
	{
		ResultadoGenericoDto<String> vRespuesta =new  ResultadoGenericoDto<String>();

		configMisFacturas();
		PeticionDeclaracionFormularioDto pDeclararformulario=new PeticionDeclaracionFormularioDto();
		pDeclararformulario.setId(pFormulario.getId());
		pDeclararformulario.setCodAdmin(codAdm);

		String vRuta = host + "/rest/misfacturas/formulario/rectificar/"+pFormulario.getId();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pDeclararformulario);
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
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String> cancelarDeclaracionFormulario(FormularioCvDto pFormulario)
	{
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/cancelardeclaracion/"+pFormulario.getId();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
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
			vRespuesta = new ResultadoGenericoDto<String>();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String>  aceptarDeclaracionFormulario(FormularioCvDto pFormulario){
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<>();
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/aceptar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
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
			vRespuesta = new ResultadoGenericoDto<>();
		}
		return vRespuesta;
	}
	public ResultadoGenericoDto<String> rechazarDeclaracionFormulario(FormularioCvDto pFormulario) {
		ResultadoGenericoDto<String> vRespuesta=new ResultadoGenericoDto<>();
		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/rechazar/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
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
			vRespuesta = new ResultadoGenericoDto<>();
		}
		return vRespuesta;
	}
	public Long obtenerTotalFormularios(LinkedHashMap<String, Serializable> pFilters) {

		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/formularios/count";
		Long vResultado=0L;
		try {
			TypeReference<Long> listTypeRef = new TypeReference<Long>() {
			};

			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if (response!=null) {
				if (response.code() == StatusCodes.OK ) {
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

	public ResultadoGenericoDto<String> habilitarRectificatorioFormularioAnexo(FormularioCvDto pFormulario)
	{
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<String>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/habilitaanexorectificatorio/"+pFormulario.getId();

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pFormulario);
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

	public ResultadoGenericoDto<RespuestaReporteDto> obtieneReporteF110(FormularioCvDto pFormulario)
	{
		ResultadoGenericoDto<RespuestaReporteDto> vRespuesta = new ResultadoGenericoDto<RespuestaReporteDto>();
		vRespuesta.setOk(false);

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/reporte/"+pFormulario.getId();

		try {
			TypeReference<ResultadoGenericoDto<RespuestaReporteDto>> listTypeRef = new TypeReference<ResultadoGenericoDto<RespuestaReporteDto>>() {
			};
			Response response = ejecutarGetSinParametrosToken(vRuta);
			LOG.info("ruta {}", vRuta);
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

	public ResultadoGenericoDto<String> seleccionarComprasFormulario(String pIdFormulario, List<String> compraIds)
	{
		FormularioPeticionDto vFormularioPet= new FormularioPeticionDto();
		vFormularioPet.setCompraIds(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/seleccionarCompras/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdFormulario;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vFormularioPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
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
		}

		return vRespuesta;
	}


	public ResultadoGenericoDto<String> desmarcarComprasFormulario(String pIdFormulario, List<String> compraIds)
	{
		FormularioPeticionDto vFormularioPet= new FormularioPeticionDto();
		vFormularioPet.setCompraIds(compraIds);
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<>();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/formulario/desmarcarCompras/";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			vRuta = vRuta + pIdFormulario;
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vFormularioPet);
			Response response = putRequestWithHeaderAndBody(vRuta, json);
			LOG.info("response {}", response);
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
			vRespuesta = new ResultadoGenericoDto<>();
		}

		return vRespuesta;
	}
	public ResultadoGenericoDto<String>  consolidarFormulario(ConsolidadorDto pConsolidador){
		ResultadoGenericoDto<String>  vRespuesta = new ResultadoGenericoDto<>();
		configMisFacturas();
		String vRuta = host + "/rest/misfacturas/consolidacion/consolidar";

		try {
			TypeReference<ResultadoGenericoDto<String>> listTypeRef = new TypeReference<ResultadoGenericoDto<String>>() {
			};
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pConsolidador);
			Response response = ejecutarPostConParametroToken(vRuta, json);
			LOG.info("respuesta consolidad{}",response);
			if (response!=null && response.code() == StatusCodes.OK) {

					vRespuesta = Json.serializer().fromJson(response.body().string(), listTypeRef);
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

	public RespuestaConsolidacionesDto obtenerConsolidaciones(LinkedHashMap<String, Serializable> pFilters) {
		RespuestaConsolidacionesDto vResultado=new RespuestaConsolidacionesDto();

		configMisFacturas();

		String vRuta = host + "/rest/misfacturas/consolidaciones/";

		try {
			TypeReference<RespuestaConsolidacionesDto> listTypeRef = new TypeReference<RespuestaConsolidacionesDto>() {
			};
			Response response = ejecutarGetConParametrosQuery(vRuta, pFilters);
			if(response!=null)
			{
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromJson(response.body().string(), listTypeRef);
				}
			}
			else
			{
				RequestContext.getCurrentInstance()
						.execute("toastr.error('Se produjo un error inesperado.', 'Error!')");
				vResultado = new RespuestaConsolidacionesDto();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new RespuestaConsolidacionesDto();
		}
		return vResultado;
	}
}
