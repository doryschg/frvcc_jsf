package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sen.enmo.jsf.model.HttpClient;
import bo.gob.sin.sen.enmo.jsf.service.RestClient;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.str.ccs.jsfconf.RestUrl;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PeticionesRest {
	private static final Logger LOG = LoggerFactory.getLogger(PeticionesRest.class);
	static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public OkHttpClient client;
	public String host;

	public List<StrMensajeAplicacionDto> mensajes;

	public Response ejecutarPostConParametro(String hostDireccion, JSONObject json) {
		Response response;

		try {
			RequestBody body = RequestBody.create(JSON, json.toString());
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).post(body).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostConParametroToken(String hostDireccion, String json) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();

		try {
			RequestBody body = RequestBody.create(JSON, json);
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).post(body).addHeader("Authorization", vToken).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostConParametroMapper(String hostDireccion, String json) {
		Response response;

		try {
			RequestBody body = RequestBody.create(JSON, json);
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).post(body).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostConParametroMapperToken(String hostDireccion, String json) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			RequestBody body = RequestBody.create(JSON, json);
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).post(body).addHeader("Authorization", vToken).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostMultiPartConParametroMapperToken(String hostDireccion, String json, File file,
			String fileName) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			RequestBody requestBodyArchivo = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("file", fileName,
							RequestBody.create(MediaType.parse("application/vnd.ms-excel"), file))
					.addFormDataPart("recepcion", json).build();

//			Multipart m = new Multipart.Builder().type(Multipart.Type.FORM)
//					.addPart(new Part.Builder().body("value").contentDisposition("form-data; name=\"non_file_field\"")
//							.build())
//					.addPart(new Part.Builder().contentType("text/csv").body(file)
//							.contentDisposition("form-data; name=\"file_field\"; filename=\"file1\"").build())
//					.addFormDataPart("recepcion", json).build();

			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).post(requestBodyArchivo)
					.addHeader("Authorization", vToken).build();

			//

			// RequestBody body = RequestBody.create(JSON, json);

			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostSinParametro(String hostDireccion) {
		Response response;

		try {
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarPostSinParametroToken(String hostDireccion) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).addHeader("Authorization", vToken).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	/*
	 * 
	 * .appendQueryParameter("param1", foo) .appendQueryParameter("param2", bar)
	 * 
	 */

	public Response ejecutarGetConParametros(String hostDireccion, Map<String, String> params) {
		Response response;

		try {
			HttpUrl.Builder route = HttpUrl.parse(hostDireccion).newBuilder();

			for (Map.Entry<String, String> param : params.entrySet()) {
				route.addPathSegment(param.getValue());
			}

			ContextoUsuarioModel contexto;

			Request request = new Request.Builder().url(route.build()).get().build();

			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarGetConParametrosQuery(String hostDireccion, Map<String, Serializable> params) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			HttpUrl.Builder httpBuilder = HttpUrl.parse(hostDireccion).newBuilder();
			if (params != null) {
				for (Map.Entry<String, Serializable> param : params.entrySet()) {
					String vAux = param.getKey().replace("[", "%5B").replace("]", "%5D");
					httpBuilder.addEncodedQueryParameter(vAux, param.getValue().toString());
				}
			}
			Request request = new Request.Builder().url(httpBuilder.build()).addHeader("Authorization", vToken).get().build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarGetConParametrosToken(String hostDireccion, Map<String, String> params) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			HttpUrl.Builder route = HttpUrl.parse(hostDireccion).newBuilder();

			for (Map.Entry<String, String> param : params.entrySet()) {
				route.addPathSegment(param.getValue());
			}
			Request request = new Request.Builder().url(route.build()).addHeader("Authorization", vToken).get().build();

			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarGetSinParametrosToken(String hostDireccion) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		try {
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).addHeader("Authorization", vToken).get().build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response ejecutarGetSinParametros(String hostDireccion) {
		Response response;

		try {
			HttpUrl route = HttpUrl.parse(hostDireccion).newBuilder().build();
			Request request = new Request.Builder().url(route).get().build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}

	public Response putRequestWithHeaderAndBody(String url, String jsonBody) {
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		Response response;
		RequestBody body = RequestBody.create(JSON, jsonBody);
		try {
			Request request = new Request.Builder().url(url).addHeader("Authorization", vToken).put(body).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}
		return response;
	}
	public Response ejecutarDeleteConParametrosToken(String url, String jsonBody) {
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		Response response;
		RequestBody body = RequestBody.create(JSON, jsonBody);
		try {
			Request request = new Request.Builder().url(url).addHeader("Authorization", vToken).delete(body).build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}
		return response;
	}

	public void configBeneficiarioQuery() {
		RestUrl vConf = new RestUrl();
		String vUrlClasificador = vConf.getPropetyValue("sre_sfe_gpri_query");
		client = HttpClient.globalClient();
		this.host = vUrlClasificador;
	}

	public void configClasificadores() {
		RestUrl vConf = new RestUrl();
		String vUrlClasificador = vConf.getPropetyValue("str_cps_clas_query_rest");
		client = HttpClient.globalClient();
		this.host = vUrlClasificador;
	}
	public void configParametricasDepartamento() {
		RestUrl vConf = new RestUrl();
		String vUrlParametricasDepartamento= vConf.getPropetyValue("str_ccs_dpto_query_rest");
		client = HttpClient.globalClient();
		this.host = vUrlParametricasDepartamento;
	}
	public void configFacturacionPadronContribuyentesQueryRest()
	{
		RestUrl vConf = new RestUrl();
		String vUrlContribuyentesQuery= vConf.getPropetyValue("scn_emp_caco_query_rest");
		client = HttpClient.globalClient();
		this.host = vUrlContribuyentesQuery;
	}
	public void configFacturacionPadronPersonasQueryRest()
	{
		RestUrl vConf = new RestUrl();
		String vUrlPersonasQuery= vConf.getPropetyValue("scn_emp_aper_query_rest");
		client = HttpClient.globalClient();
		this.host = vUrlPersonasQuery;
	}

	public void configMisFacturas() {
		RestUrl vConf = new RestUrl();
		String vUrl = vConf.getPropetyValue("sre_fac_frvcc_mfc_rest");
		RestClient cl = new RestClient();
		this.client = cl.obtenerCliente();
		host = vUrl;
	}
	public void configMisFacturasLibros() {
		RestUrl vConf = new RestUrl();
		String vUrl = vConf.getPropetyValue("sre_fac_frvcc_mfc_lib_rest");
		RestClient cl = new RestClient();
		this.client = cl.obtenerCliente();
		host = vUrl;
	}
	public void configSfe() {
		RestUrl vConf = new RestUrl();
		String vUrl = vConf.getPropetyValue("sre_sfe_con_query");
		RestClient cl = new RestClient();
		this.client = cl.obtenerCliente();
		host = vUrl;
	}

	public void configRoles() {
		RestUrl vConf = new RestUrl();
		String vUrlRoles = vConf.getPropetyValue("sau_arol_rest");
		client = HttpClient.globalClient();
		this.host = vUrlRoles;
	}
	
}
