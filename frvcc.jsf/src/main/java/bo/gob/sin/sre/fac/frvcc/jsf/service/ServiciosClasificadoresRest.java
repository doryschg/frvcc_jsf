package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServiciosClasificadoresRest extends PeticionesRest implements Serializable{
	private static final Logger LOG = LoggerFactory.getLogger(ServiciosClasificadoresRest.class);

	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}

	/**
	 * @autor wilson.limachi
	 * @descripción obtencion de paramétrica
	 * @return lista dto de tipo ClasificadorDto
	 * @fecha 06/11/2018
	 */
	public List<ClasificadorDto> obtenerClasificadoresPorTipo(String pNombreRutaSinHost) {
		configClasificadores();
		String vRuta = host + "/clasificador/tipoClasificador/" + pNombreRutaSinHost;
		List<ClasificadorDto> vResultado = new ArrayList<ClasificadorDto>();

		try {
			TypeReference<List<ClasificadorDto>> listTypeRef = new TypeReference<List<ClasificadorDto>>() {
			};
			Response response = ejecutarGetSinParametros(vRuta);

			if (response.code() == StatusCodes.OK) {
			
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<ClasificadorDto>();
		}

		return vResultado;
	}

	/**
	 * Objetivo: Recuperar Clasificador por Id . Creado por: Carmen Rosa Silva.
	 * Fecha: 10/09/2018
	 */
	public ClasificadorDto recuperarClasificadorPorId(int pClasificadorId) {
		configClasificadores();
		ClasificadorDto vResultado = new ClasificadorDto();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pClasificadorId);
			String ruta = host + "/clasificador/id/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pClasificadorId", pClasificadorId + "");

			TypeReference<ClasificadorDto> listTypeRef = new TypeReference<ClasificadorDto>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.OK) {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ClasificadorDto();
		}

		return vResultado;
	}
	/**
	 * @autor ronald.quispe
	 * @descripcion obtencion de clasificador por tipo y codigo
	 * @return  ClasificadorDto
	 * @fecha 20/05/2020
	 */
	public ClasificadorDto obtenerClasificadorPorTipoCodigo(String ptipoClasificador,String pCodigoClasificador) {
		configClasificadores();
		String vRuta = host + "/clasificador/tipoCodigoClasificador/" + ptipoClasificador+"/"+pCodigoClasificador;
		ClasificadorDto vResultado = new ClasificadorDto();

		try {
			TypeReference<ClasificadorDto> listTypeRef = new TypeReference<ClasificadorDto>() {
			};
			Response response = ejecutarGetSinParametros(vRuta);

			if (response.code() == StatusCodes.OK) {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ClasificadorDto();
		}

		return vResultado;
	}
	/**
	 * @autor ronald.quispe
	 * @descripcion obtencion de clasificador por grupo clasificador
	 * @return  List<ClasificadorDto>
	 * @fecha 05/08/2020
	 */
	 public List<ClasificadorDto> obtenerListaClasificadoresPorGrupo(Integer pGrupoId) {
		 	configClasificadores();		    
		    String vRuta = host + "/clasificador/porGrupo/" + pGrupoId;
			List<ClasificadorDto> vResultado = new ArrayList<ClasificadorDto>();

			try {
				TypeReference<List<ClasificadorDto>> listTypeRef = new TypeReference<List<ClasificadorDto>>() {
				};
				Response response = ejecutarGetSinParametros(vRuta);

				if (response.code() == StatusCodes.OK) {
					vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
				}
			} catch (Exception e) {
				e.printStackTrace();
				vResultado = new ArrayList<ClasificadorDto>();
			}

			return vResultado;
		  }

}
