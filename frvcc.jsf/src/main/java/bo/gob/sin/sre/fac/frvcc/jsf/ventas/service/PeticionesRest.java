package bo.gob.sin.sre.fac.frvcc.jsf.ventas.service;

//import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

//import javax.inject.Named;
import javax.inject.Inject;
//import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
//import com.google.gson.JsonElement;

import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.str.ccs.jsfconf.RestUrl;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PeticionesRest {

	private static final Logger LOG = LoggerFactory.getLogger(PeticionesRest.class);

	static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	static MediaType FILE = MediaType.parse("multipart/form-data");

	public OkHttpClient client;

	ContextoJSF contexto;

	private String BASE;
	private String vToken;

	Gson gson;

	public PeticionesRest() {

		client = new OkHttpClient();
		gson = new Gson();
		configContxt();
	}

	// public List<StrMensajeAplicacionDto> mensajes;

	/**************************************	 POST	 *********************************************/

	String sendPost(String ruta, Object obj) throws IOException {
		
		String url = BASE + ruta;
		String json = gson.toJson(obj);

		RequestBody body = RequestBody.create(JSON, json);

		Request request = new Request.Builder().url(url).post(body).addHeader("Authorization", vToken).build();

		try (Response response = client.newCall(request).execute()) {
			if (response.code() == 200) {

			} else {

			}
			return response.body().string();
		}

	}

	// TODO POST http://localhost:39374/rest/misfacturas/ventas
	String posteo(String ruta, Object obj) throws IOException {
		
		String url = BASE + ruta;
		String json = gson.toJson(obj);

		RequestBody body = RequestBody.create(JSON, json);

		Request request = new Request.Builder().url(url).post(body).addHeader("Authorization", vToken).build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}

	}

	/**************************************	 PUT   *********************************************/

	// TODO ENVIO DE ACTUALIZACION // CORREGIR ENVIO
	String sendPutFile(String ruta, String id, Resource file) throws IOException {
		
		String url = BASE + ruta; // TODO corregir

		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("tipoRecepcionId", id).addFormDataPart("idPadre", "")
				.addFormDataPart("archivo", file.getFilename(), RequestBody.create(FILE, file.getFile())).build();

		Request request = new Request.Builder().url(url).put(body).addHeader("Authorization", vToken).build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}

	}

	// TODO ENVIO DE ACTUALIZACION // CORREGIR ENVIO
	String sendPutFileExtra(String ruta, String id, JSONObject json) throws IOException {
		
		String url = BASE + ruta + id; // TODO corregir

		RequestBody body = RequestBody.create(JSON, json.toString());

		Request request = new Request.Builder().url(url).put(body).addHeader("Authorization", vToken).build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}

	}

	// TODO ENVIO DE ACTUALIZACION // CORREGIR ENVIO -- WORKING
	String sendPut(String ruta, JSONObject json) throws IOException {
		
		String url = BASE + ruta; // TODO corregir

		RequestBody body = RequestBody.create(JSON, json.toString());

		Request request = new Request.Builder().url(url).put(body).addHeader("Authorization", vToken).build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	/**************************************
	 * GET
	 *********************************************/
//	public Response ejecutarGetConParametrosToken(String hostDireccion, Map<String, String> params) {
//		Response response;
//		ContextoJSF contexto = new ContextoJSF();
//		String vToken = "Token " + contexto.getUsuario().getToken();
//		try {
//			HttpUrl.Builder route = HttpUrl.parse(hostDireccion).newBuilder();
//
//			for (Map.Entry<String, String> param : params.entrySet()) {
//				route.addPathSegment(param.getValue());
//			}
//			Request request = new Request.Builder().url(route.build()).addHeader("Authorization", vToken).get().build();
//			response = client.newCall(request).execute();
//			System.out.println("SUCCESS::::::::::::: GET CONTRIBUYENTE :::");
//		} catch (Exception e) {
//			System.out.println("ERROR::::::::::::: GET CONTRIBUYENTE ::: "+e.getCause());
//			return null;
//		}
//
//		return response;
//	}

	public String configEnviroment(String env) {
		RestUrl vConf = new RestUrl();
		return vConf.getPropetyValue(env);
	}

	public void configContxt() {
		BASE = configEnviroment("sre_fac_frvcc_mfv_rest");
		contexto = new ContextoJSF();
		vToken = "Token " + contexto.getUsuario().getToken();

	}

	public Response ejecutarGetConParametrosQuery(String hostDireccion, Map<String, Serializable> params) {
		Response response;
		ContextoJSF contexto = new ContextoJSF();
		String vToken = "Token " + contexto.getUsuario().getToken();
		// System.out.println(params.toString());
		hostDireccion = BASE + hostDireccion;
		try {
			HttpUrl.Builder httpBuilder = HttpUrl.parse(hostDireccion).newBuilder();
			if (params != null) {
				for (Map.Entry<String, Serializable> param : params.entrySet()) {
					String vAux = param.getKey().replace("[", "%5B").replace("]", "%5D");
					httpBuilder.addEncodedQueryParameter(vAux, param.getValue().toString());
				}
			}
			Request request = new Request.Builder().url(httpBuilder.build()).addHeader("Authorization", vToken).get()
					.build();
			response = client.newCall(request).execute();
		} catch (Exception e) {
			return null;
		}

		return response;
	}
	
}
