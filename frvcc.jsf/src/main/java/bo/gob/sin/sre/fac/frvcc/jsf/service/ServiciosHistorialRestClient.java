package bo.gob.sin.sre.fac.frvcc.jsf.service;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaComprasDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RolDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ServiciosHistorialRestClient extends PeticionesRest implements Serializable{
	private static final Logger LOG = LoggerFactory.getLogger(ServiciosHistorialRestClient.class);

	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}

	public RespuestaComprasDto obtenerComprasHistoricas(String pIdCompra) {

		configMisFacturas();
		RespuestaComprasDto vResultado = new RespuestaComprasDto();

		String vRuta = host + "/rest/misfacturas/compras/historial/"+pIdCompra;

		ObjectMapper mapper = new ObjectMapper();

		try {
			TypeReference<RespuestaComprasDto> listTypeRef = new TypeReference<RespuestaComprasDto>() {
			};

			Response response = ejecutarGetSinParametrosToken(vRuta);
			if (response!=null) {
				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromJson(response.body().string(), listTypeRef);
				}
				else
				{
					vResultado=new RespuestaComprasDto();
					vResultado.setComprasResponse(new ArrayList<>());
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
}
