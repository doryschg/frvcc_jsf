package bo.gob.sin.sre.fac.frvcc.jsf.service;
import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RolDto;
import com.fasterxml.jackson.core.type.TypeReference;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiciosRolesRest extends PeticionesRest implements Serializable{
	private static final Logger LOG = LoggerFactory.getLogger(ServiciosRolesRest.class);

	public String getHost() {
		return host;
	}

	public OkHttpClient getClient() {
		return client;
	}

	public List<RolDto> obtenerRolesPorUsuario(Long pUsuarioId) {
		List<RolDto> vResultado=new ArrayList<>();
		configRoles();
		String vRuta = host + "/rest/rol/usuarioId/"+pUsuarioId;

		try {
			TypeReference<List<RolDto>> listTypeRef = new TypeReference<List<RolDto>>() {
			};
			Response response = ejecutarGetSinParametrosToken(vRuta);
			LOG.info("ruta roles {}",vRuta );
			LOG.info("respuesta roles {}", response);
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
				vResultado = new ArrayList<RolDto>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			vResultado = new ArrayList<RolDto>();
		}
		return vResultado;
	}
}
