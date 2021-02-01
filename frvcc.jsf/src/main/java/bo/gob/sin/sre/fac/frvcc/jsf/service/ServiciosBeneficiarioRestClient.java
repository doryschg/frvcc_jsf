package bo.gob.sin.sre.fac.frvcc.jsf.service;

import bo.gob.sin.scn.empa.caco.dto.ContribuyenteDto;
import bo.gob.sin.sen.enco.model.Json;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.RespuestaLibrosDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ResultadoGenericoDto;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.beneficiario.BeneficiarioPersonaRivDto;
import com.fasterxml.jackson.core.type.TypeReference;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Response;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ServiciosBeneficiarioRestClient extends PeticionesRest implements Serializable{

    public ServiciosBeneficiarioRestClient(String host, OkHttpClient client) {
        super();
        this.host = host;
        this.client = client;
    }

    public ServiciosBeneficiarioRestClient() {
    }

    public BeneficiarioPersonaRivDto getBeneficiario(Long pPersonaId) {
        configBeneficiarioQuery();

        String vRuta = host + "/rest/consultaBeneficiario/obtenerBeneficiarioPersonaPorPersonaId/"+pPersonaId;
        try {
            TypeReference<ResultadoGenericoDto<BeneficiarioPersonaRivDto>> listTypeRef =
                    new TypeReference<ResultadoGenericoDto<BeneficiarioPersonaRivDto>>() {
                    };


            TypeReference<ResultadoGenericoDto<BeneficiarioPersonaRivDto>> typeRef = new TypeReference<ResultadoGenericoDto<BeneficiarioPersonaRivDto>>() {
            };


            //System.out.println("imprime formularios................."+vRuta);
            Response response = ejecutarGetSinParametrosToken(vRuta);
            //System.out.println("imprime formularios................."+response);

            if (response.code() == StatusCodes.OK) {

                BeneficiarioPersonaRivDto respuesta = Json.serializer().fromInputStream(response.body().byteStream(), listTypeRef).getResultadoObjeto();

                return respuesta;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
