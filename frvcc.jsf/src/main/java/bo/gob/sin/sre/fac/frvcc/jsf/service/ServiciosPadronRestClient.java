package bo.gob.sin.sre.fac.frvcc.jsf.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import bo.gob.sin.scn.empa.caco.dto.ContribuyenteDto;
import bo.gob.sin.scn.empa.caco.dto.DatosContribuyenteEscritorioDto;
import bo.gob.sin.scn.empa.capd.aper.dto.PersonaDto;
import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ServiciosPadronRestClient extends PeticionesRest {
	private static final Logger LOG = LoggerFactory.getLogger(ServiciosPadronRestClient.class);

	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}

	
	/**
	 * @autor ronald.quispe
	 * @descripcion obtencion de contribuyente por Nit
	 * @return ResultadoGenericoDto<ContribuyenteDto>
	 * @fecha 26/05/2020
	 */
	
	public ResultadoGenericoDto<ContribuyenteDto> obtenerDAtoscontribuyentePorNit(long pNit) {
		configFacturacionPadronContribuyentesQueryRest();
		ResultadoGenericoDto<ContribuyenteDto> resultado = new ResultadoGenericoDto<>();

		try {
			String ruta = host + "/rest/consultasContribuyente/obtenerContribuyentePorNitO/";
			Map<String, String> map = new HashMap<String, String>();
			map.put("pNit", pNit + "");

			TypeReference<ResultadoGenericoDto<ContribuyenteDto>> typeRef = new TypeReference<ResultadoGenericoDto<ContribuyenteDto>>() {
			};

			Response response = ejecutarGetConParametrosToken(ruta, map);

			if (response.code() == StatusCodes.NOT_FOUND) {
				resultado = new ResultadoGenericoDto<ContribuyenteDto>();
			} else {
				resultado = Json.serializer().fromInputStream(response.body().byteStream(), typeRef);
			}
		} catch (Exception e) {
			resultado =new ResultadoGenericoDto<ContribuyenteDto>();
		}

		return resultado;
	}
	
	/**
	 * @autor ronald.quispe
	 * @descripcion obtencion de contribuyente por ifc
	 * @return ResultadoGenericoDto<ContribuyenteDto>
	 * @fecha 3/08/2020
	 */
		
		public ResultadoGenericoDto<ContribuyenteDto> obtenerDatoscontribuyentePorIfc(long pPersonaId) {
			configFacturacionPadronContribuyentesQueryRest();
			ResultadoGenericoDto<ContribuyenteDto> resultado = new ResultadoGenericoDto<>();

			try {
				String ruta = host + "/rest/consultasContribuyente/obtenerContribuyentePorPersonaIdO/";
				Map<String, String> map = new HashMap<String, String>();
				map.put("pPersonaId", pPersonaId + "");

				TypeReference<ResultadoGenericoDto<ContribuyenteDto>> typeRef = new TypeReference<ResultadoGenericoDto<ContribuyenteDto>>() {
				};

				Response response = ejecutarGetConParametrosToken(ruta, map);

				if (response.code() == StatusCodes.NOT_FOUND) {
					resultado = new ResultadoGenericoDto<ContribuyenteDto>();
				} else {
					resultado = Json.serializer().fromInputStream(response.body().byteStream(), typeRef);
				}
			} catch (Exception e) {
				resultado =new ResultadoGenericoDto<ContribuyenteDto>();
			}

			return resultado;
		}
		
		/**
		 * @autor ronald.quispe
		 * @descripcion obtencion de datos de persona por ifc
		 * @return PersonaDto
		 * @fecha 3/08/2020
		 */
			
			public PersonaDto obtenerDatosPersonaPorIfc(long pPersonaId) {
				configFacturacionPadronPersonasQueryRest();
				PersonaDto resultado = new PersonaDto();

				try {
					String ruta = host + "/rest/consultaPersona/obtenerPersonaPorId/";
					Map<String, String> map = new HashMap<String, String>();
					map.put("pPersonaId", pPersonaId + "");

					TypeReference<PersonaDto> typeRef = new TypeReference<PersonaDto>() {
					};

					Response response = ejecutarGetConParametrosToken(ruta, map);

					if (response.code() == StatusCodes.NOT_FOUND) {
						resultado = new PersonaDto();
					} else {
						resultado = Json.serializer().fromInputStream(response.body().byteStream(), typeRef);
					}
				} catch (Exception e) {
					resultado =new PersonaDto();
				}

				return resultado;
			}
					
			public ResultadoGenericoDto<DatosContribuyenteEscritorioDto> consultaContribuyente(Long pNit) {
				configFacturacionPadronContribuyentesQueryRest();
				ResultadoGenericoDto<DatosContribuyenteEscritorioDto> resultado = new ResultadoGenericoDto<DatosContribuyenteEscritorioDto>();

				try {
					String ruta = host + "/rest/consultasContribuyente/obtenerDatosEscritorioXNit/";
					Map<String, String> map = new HashMap<String, String>();
					map.put("pNit", pNit + "");

					TypeReference<ResultadoGenericoDto<DatosContribuyenteEscritorioDto>> typeRef = new TypeReference<ResultadoGenericoDto<DatosContribuyenteEscritorioDto>>() {
					};

					Response response = ejecutarGetConParametrosToken(ruta, map);
					
					if (response.code() == StatusCodes.OK) {
						resultado = Json.serializer().fromInputStream(response.body().byteStream(), typeRef);
						
					} else {
						resultado = new ResultadoGenericoDto<DatosContribuyenteEscritorioDto>();
					
					}
				} catch (Exception e) {
					resultado =new ResultadoGenericoDto<DatosContribuyenteEscritorioDto>();
				}

				return resultado;
			}
}
