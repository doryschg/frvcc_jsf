package bo.gob.sin.sre.fac.frvcc.jsf.service;

import bo.gob.sin.sen.enco.model.Json;

import bo.gob.sin.str.cps.dpto.dto.AlcaldiaDto;
import bo.gob.sin.str.cps.dpto.dto.DepartamentoDto;
import bo.gob.sin.str.cps.dpto.dto.ProvinciaDto;
import io.undertow.util.StatusCodes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicioParametricaDepartamentoRestclient extends PeticionesRest implements Serializable
{
  private static final Logger LOG = LoggerFactory.getLogger(ServicioParametricaDepartamentoRestclient.class);
  
  	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}
  
	public ServicioParametricaDepartamentoRestclient(String host, OkHttpClient client) {
		super();
		LOG.info("Ingresando constructor ServicioFormulariosRestclient host={}, client={}", host, client);
		this.host = host;
		this.client = client;
		LOG.info("Saliendo constructor ServicioFormulariosRestclient");
	}
	public ServicioParametricaDepartamentoRestclient()
	{
		
	}
  
	/**
	 * @author ronald.quispe
	 * @descripción obtencion de departamentos todos
	 * @return lista dto de tipo DepartamentoDto
	 * @fecha 20/05/2020
	 */
	public List<DepartamentoDto> getListaDepartamento() {
		configParametricasDepartamento();
		String vRuta = host + "/departamento/listarTodos";
		List<DepartamentoDto> vResultado = new ArrayList<DepartamentoDto>();

		try {
		    TypeReference<List<DepartamentoDto>> listTypeRef = new TypeReference<List<DepartamentoDto>>() {
		      
		      };
			Response response = ejecutarGetSinParametros(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<DepartamentoDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<DepartamentoDto>();
		}

		return vResultado;
	}
	/**
	 * @author ronald.quispe
	 * @descripción obtencion de departamentos todos activos
	 * @return lista dto de tipo DepartamentoDto
	 * @fecha 20/05/2020
	 */
  public List<DepartamentoDto> getListaDepartamentoActivos() {
   
    configParametricasDepartamento();
	String vRuta = host + "/departamento/listarActivos";
	List<DepartamentoDto> vResultado = new ArrayList<DepartamentoDto>();

	try {
	    TypeReference<List<DepartamentoDto>> listTypeRef = new TypeReference<List<DepartamentoDto>>() {
	      
	      };
		Response response = ejecutarGetSinParametros(vRuta);

		if (response.code() == StatusCodes.NOT_FOUND) {
			vResultado = new ArrayList<DepartamentoDto>();
		} else {
			vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
		}
	} catch (Exception e) {
		e.printStackTrace();
		vResultado = new ArrayList<DepartamentoDto>();
	}

	return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de departamento por id
	 * @return dto de tipo DepartamentoDto
	 * @fecha 20/05/2020
	 */
  public DepartamentoDto getDepartamentoPorId(short pDepartamentoId) {
    configParametricasDepartamento();
    DepartamentoDto vResultado = new DepartamentoDto();
	try {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(pDepartamentoId);
		String ruta = host + "/departamento/recuperarPorId/";

		Map<String, String> map = new HashMap<String, String>();
		map.put("pDepartamentoId", pDepartamentoId + "");

		TypeReference<DepartamentoDto> listTypeRef = new TypeReference<DepartamentoDto>() {
		};
		Response response = ejecutarGetConParametros(ruta, map);
		if (response.code() == StatusCodes.NOT_FOUND) {
			vResultado = new DepartamentoDto();
		} else {
			vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
		}
	} catch (Exception e) {
		e.printStackTrace();
		vResultado = new DepartamentoDto();
	}

	return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de departamentos por nombre
	 * @return lista dto de tipo DepartamentoDto
	 * @fecha 20/05/2020
	 */
  public List<DepartamentoDto> getListaDepartamentoPorNombre(String pNombreDepartamento) {
    configParametricasDepartamento();
    List<DepartamentoDto> vResultado = new ArrayList<DepartamentoDto>();
	try {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(pNombreDepartamento);
		String ruta = host + "/departamento/nombre/";

		Map<String, String> map = new HashMap<String, String>();
		map.put("pNombreDepartamento", pNombreDepartamento + "");

		TypeReference<List<DepartamentoDto>> listTypeRef = new TypeReference<List<DepartamentoDto>>() {
		};
		Response response = ejecutarGetConParametros(ruta, map);
		if (response.code() == StatusCodes.NOT_FOUND) {
			vResultado = new ArrayList<DepartamentoDto>();
		} else {
			vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
		}
	} catch (Exception e) {
		e.printStackTrace();
		vResultado = new ArrayList<DepartamentoDto>();
	}

	return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de ProvinciaDto por nombre
	 * @return lista dto de tipo ProvinciaDto
	 * @fecha 20/05/2020
	 */
  public List<ProvinciaDto> getListaProvinciaPorNombre(String pNombre) {
	  configParametricasDepartamento();
	    List<ProvinciaDto> vResultado = new ArrayList<ProvinciaDto>();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pNombre);
			String ruta = host + "/provincia/nombre/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pNombre", pNombre + "");

			TypeReference<List<ProvinciaDto>> listTypeRef = new TypeReference<List<ProvinciaDto>>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<ProvinciaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<ProvinciaDto>();
		}

		return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de ProvinciaDto por id
	 * @return dto de tipo ProvinciaDto
	 * @fecha 20/05/2020
	 */
  public ProvinciaDto getProvinciaPorId(short pProvinciaId) {
    configParametricasDepartamento();
    ProvinciaDto vResultado = new ProvinciaDto();
	try {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(pProvinciaId);
		String ruta = host + "/provincia/recuperarPorId/";

		Map<String, String> map = new HashMap<String, String>();
		map.put("pProvinciaId", pProvinciaId + "");

		TypeReference<ProvinciaDto> listTypeRef = new TypeReference<ProvinciaDto>() {
		};
		Response response = ejecutarGetConParametros(ruta, map);
		if (response.code() == StatusCodes.NOT_FOUND) {
			vResultado = new ProvinciaDto();
		} else {
			vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
		}
	} catch (Exception e) {
		e.printStackTrace();
		vResultado = new ProvinciaDto();
	}

	return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de lista de ProvinciaDto activas
	 * @return lista dto de tipo ProvinciaDto
	 * @fecha 20/05/2020
	 */
  public List<ProvinciaDto> getListaProvinciaActivas() {
    
    configParametricasDepartamento();
    List<ProvinciaDto> vResultado = new ArrayList<ProvinciaDto>();
	try {
		
		String ruta = host + "/provincia/listarActivas/";

		TypeReference<List<ProvinciaDto>> listTypeRef = new TypeReference<List<ProvinciaDto>>() {
		};
		Response response = ejecutarGetSinParametros(ruta);
		if (response.code() == StatusCodes.NOT_FOUND) {
			vResultado = new ArrayList<ProvinciaDto>();
		} else {
			vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
		}
	} catch (Exception e) {
		e.printStackTrace();
		vResultado = new ArrayList<ProvinciaDto>();
	}

	return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de lista de ProvinciaDto todos
	 * @return lista dto de tipo ProvinciaDto
	 * @fecha 20/05/2020
	 */
  public List<ProvinciaDto> getListaProvincia() {
		configParametricasDepartamento();
		String vRuta = host + "/provincia/listarTodos";
		List<ProvinciaDto> vResultado = new ArrayList<ProvinciaDto>();

		try {
		    TypeReference<List<ProvinciaDto>> listTypeRef = new TypeReference<List<ProvinciaDto>>() {
		      
		      };
			Response response = ejecutarGetSinParametros(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<ProvinciaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<ProvinciaDto>();
		}

		return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de lista de ProvinciaDto por departamentoId
	 * @return lista dto de tipo ProvinciaDto
	 * @fecha 20/05/2020
	 */
  public List<ProvinciaDto> getListaProvinciaPorDepartamentoId(short pDepartamentoId) {
	  configParametricasDepartamento();
	    List<ProvinciaDto> vResultado = new ArrayList<ProvinciaDto>();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pDepartamentoId);
			String ruta = host + "/provincia/recuperarPorDptoId/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pDepartamentoId", pDepartamentoId + "");

			TypeReference<List<ProvinciaDto>> listTypeRef = new TypeReference<List<ProvinciaDto>>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<ProvinciaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<ProvinciaDto>();
		}

		return vResultado;
  }
  /**
	 * @author ronald.quispe
	 * @descripción obtencion de lista de AlcaldiaDto todos
	 * @return lista dto de tipo AlcaldiaDto
	 * @fecha 20/05/2020
	 */
  public List<AlcaldiaDto> getListaAlcaldia() {
	  configParametricasDepartamento();
		String vRuta = host + "/alcaldia/listarTodos";
		List<AlcaldiaDto> vResultado = new ArrayList<AlcaldiaDto>();

		try {
		    TypeReference<List<AlcaldiaDto>> listTypeRef = new TypeReference<List<AlcaldiaDto>>() {
		      
		      };
			Response response = ejecutarGetSinParametros(vRuta);

			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<AlcaldiaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<AlcaldiaDto>();
		}

		return vResultado;
  }
  /**
 	 * @author ronald.quispe
 	 * @descripción obtencion de lista de AlcaldiaDto activas
 	 * @return lista dto de tipo AlcaldiaDto
 	 * @fecha 20/05/2020
 	 */
  public List<AlcaldiaDto> getListaAlcaldiaActivas() {
	  configParametricasDepartamento();
	    List<AlcaldiaDto> vResultado = new ArrayList<AlcaldiaDto>();
		try {
			
			String ruta = host + "/alcaldia/listarActivas/";

			TypeReference<List<AlcaldiaDto>> listTypeRef = new TypeReference<List<AlcaldiaDto>>() {
			};
			Response response = ejecutarGetSinParametros(ruta);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<AlcaldiaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<AlcaldiaDto>();
		}

		return vResultado;
  }
  /**
 	 * @author ronald.quispe
 	 * @descripción obtencion de lista de AlcaldiaDto por nombre
 	 * @return lista dto de tipo AlcaldiaDto
 	 * @fecha 20/05/2020
 	 */
  public List<AlcaldiaDto> getListaAlcaldiaPorNombre(String pNombre) {
	  configParametricasDepartamento();
	    List<AlcaldiaDto> vResultado = new ArrayList<AlcaldiaDto>();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pNombre);
			String ruta = host + "/alcaldia/nombre/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pNombre", pNombre + "");

			TypeReference<List<AlcaldiaDto>> listTypeRef = new TypeReference<List<AlcaldiaDto>>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<AlcaldiaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<AlcaldiaDto>();
		}

		return vResultado;
  }
  /**
 	 * @author ronald.quispe
 	 * @descripción obtencion de AlcaldiaDto por id
 	 * @return  dto de tipo AlcaldiaDto
 	 * @fecha 20/05/2020
 	 */
  public AlcaldiaDto getAlcaldiaPorId(Integer pAlcaldiaId) {
	  configParametricasDepartamento();
	  AlcaldiaDto vResultado = new AlcaldiaDto();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pAlcaldiaId);
			String ruta = host + "/alcaldia/recuperarPorId/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pAlcaldiaId", pAlcaldiaId + "");

			TypeReference<AlcaldiaDto> listTypeRef = new TypeReference<AlcaldiaDto>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new AlcaldiaDto();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new AlcaldiaDto();
		}

		return vResultado;
  }
  /**
 	 * @author ronald.quispe
 	 * @descripción obtencion de lista de AlcaldiaDto por provincia id
 	 * @return lista dto de tipo AlcaldiaDto
 	 * @fecha 20/05/2020
 	 */ 
  public List<AlcaldiaDto> getListaAlcaldiaPorProvinciaId(short pProvinciaId) {
	  configParametricasDepartamento();
	    List<AlcaldiaDto> vResultado = new ArrayList<AlcaldiaDto>();
		try {
			String json = "";
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(pProvinciaId);
			String ruta = host + "/alcaldia/recuperarPorProvinciaId/";

			Map<String, String> map = new HashMap<String, String>();
			map.put("pProvinciaId", pProvinciaId + "");

			TypeReference<List<AlcaldiaDto>> listTypeRef = new TypeReference<List<AlcaldiaDto>>() {
			};
			Response response = ejecutarGetConParametros(ruta, map);
			if (response.code() == StatusCodes.NOT_FOUND) {
				vResultado = new ArrayList<AlcaldiaDto>();
			} else {
				vResultado = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef);
			}
		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<AlcaldiaDto>();
		}

		return vResultado;
  }
}

