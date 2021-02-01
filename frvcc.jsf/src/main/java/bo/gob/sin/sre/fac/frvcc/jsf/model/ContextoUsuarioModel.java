package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import bo.gob.sin.sau.ausu.dto.UsuarioContextoDto;
import bo.gob.sin.scn.empa.caco.dto.ContribuyenteDto;
import bo.gob.sin.scn.empa.capd.aper.dto.PersonaDto;
import bo.gob.sin.sen.enco.model.ContextoJSF;
import bo.gob.sin.sre.fac.frvcc.jsf.dto.ContribuyenteNewtonDto;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioFormulariosRestclient;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServicioLibrosRestclient;
import bo.gob.sin.sre.fac.frvcc.jsf.service.ServiciosPadronRestClient;
import org.jfree.util.Log;


@ManagedBean(name = "contextoUsuarioModel")
@SessionScoped
public class ContextoUsuarioModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private ContextoJSF contexto;
	private UsuarioContextoDto usuario;
	private String numeroDocumentoIdentidad="";
	private String nombreRazonSocial="";
	private String tipoDocumentoIdentidad="";
	private Integer codigoAdministracion=0;
	private String descripcionAdministracion="";
	private String complementoDocumentoIdentidad="";
	private String lugarExpedicionDocumentoIdentidad="";
	private String nitCi;
	private String tipoNitCi;
	private ContribuyenteDto contribuyente=null;
	private PersonaDto persona=null;
	private String esNewton="N";
	
	

	private transient ServiciosPadronRestClient vServiciosPadron=new ServiciosPadronRestClient();
	private transient ServicioLibrosRestclient vServicioLibro=new ServicioLibrosRestclient();

	@PostConstruct
	public void init() {
		contexto = new ContextoJSF();
		usuario = new UsuarioContextoDto();

		if (contexto.getUsuario() != null) {
			usuario = contexto.getUsuario();
			if (contexto.getUsuario(). getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_CONTRIBUYENTE)) {
				contribuyente=vServiciosPadron.obtenerDatoscontribuyentePorIfc(contexto.getUsuario().getPersonaId()).getResultadoObjeto();
				if(contribuyente!=null) {
					if(contribuyente.getPersonaNatural()!=null)
					{
						if(contribuyente.getPersonaNatural().getPersona()!=null)
						{
							numeroDocumentoIdentidad=contribuyente.getPersonaNatural().getPersona().getNumeroDocumento().toString()!=null?contribuyente.getPersonaNatural().getPersona().getNumeroDocumento().toString():"";
							
							tipoDocumentoIdentidad=contribuyente.getPersonaNatural().getPersona().getTipoDocumentoIdentidadId()!=null?contribuyente.getPersonaNatural().getPersona().getTipoDocumentoIdentidadId().toString():"";
							complementoDocumentoIdentidad=contribuyente.getPersonaNatural().getPersona().getCodigoComplementario()!=null?contribuyente.getPersonaNatural().getPersona().getCodigoComplementario():"";
							lugarExpedicionDocumentoIdentidad=contribuyente.getPersonaNatural().getPersona().getLugarExpedicionId()!=null?contribuyente.getPersonaNatural().getPersona().getLugarExpedicionId().toString():"";
						}
						
					}
					else
					{
						if(contribuyente.getPersonaJuridica().getPersona()!=null) {
							numeroDocumentoIdentidad=contribuyente.getPersonaJuridica().getPersona().getNumeroDocumento().toString()!=null?contribuyente.getPersonaJuridica().getPersona().getNumeroDocumento().toString():"";
							tipoDocumentoIdentidad=contribuyente.getPersonaJuridica().getPersona().getTipoDocumentoIdentidadId()!=null?contribuyente.getPersonaJuridica().getPersona().getTipoDocumentoIdentidadId().toString():"";
							complementoDocumentoIdentidad=contribuyente.getPersonaJuridica().getPersona().getCodigoComplementario()!=null?contribuyente.getPersonaJuridica().getPersona().getCodigoComplementario():"";
							lugarExpedicionDocumentoIdentidad=contribuyente.getPersonaJuridica().getPersona().getLugarExpedicionId()!=null?contribuyente.getPersonaJuridica().getPersona().getLugarExpedicionId().toString():"";
						}
						
					}
					nombreRazonSocial=contribuyente.getNombreRazonSocial();
					ContribuyenteNewtonDto vRespuesta=vServicioLibro.verificaContribuyenteNewton(usuario.getPersonaId());
					if(vRespuesta.isOk())
					{
						esNewton=vRespuesta.getEsNewton();
					}
					
					
				}
				
				codigoAdministracion=contexto.getUsuario().getDependenciaId()!=null?contexto.getUsuario().getDependenciaId():0;
				descripcionAdministracion=contexto.getUsuario().getDescripcionDependencia()!=null?contexto.getUsuario().getDescripcionDependencia():"";
				nitCi=contexto.getUsuario().getNit().toString();
				tipoNitCi=ParametrosFRVCC.TIPO_DOC_CLIENTE_COMPRA_NIT;
				Log.info("Datos Usuario Logueado: "+nombreRazonSocial+" "+codigoAdministracion+" "+nitCi+" "+tipoNitCi);
				
			}
			if (contexto.getUsuario().getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_FUNCIONARIO)) {

			}
			if (contexto.getUsuario().getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_DEPENDIENTE)) {
				persona=vServiciosPadron.obtenerDatosPersonaPorIfc(contexto.getUsuario().getPersonaId());
				
				if(persona!=null)
				{
					numeroDocumentoIdentidad=persona.getNumeroDocumento();
					tipoDocumentoIdentidad=persona.getTipoDocumentoIdentidadId()!=null?
							persona.getTipoDocumentoIdentidadId().toString():"";
					
					complementoDocumentoIdentidad=persona.getCodigoComplementario()!=null?
							persona.getCodigoComplementario():"";
					lugarExpedicionDocumentoIdentidad=persona.getLugarExpedicionId()!=null?
							persona.getLugarExpedicionId().toString():"";
							
				}
				
						codigoAdministracion=contexto.getUsuario().getOficinaId()!=null?contexto.getUsuario().getOficinaId():0;
						nombreRazonSocial=contexto.getUsuario().getNombreCompleto();
						nitCi=contexto.getUsuario().getNumeroDocumento();
						tipoNitCi=ParametrosFRVCC.TIPO_DOC_CLIENTE_COMPRA_CI;
			}
			if (contexto.getUsuario().getTipoUsuario().equals(ParametrosFRVCC.TIPO_USUARIO_BENEFICIARIO)) {
				persona=vServiciosPadron.obtenerDatosPersonaPorIfc(contexto.getUsuario().getPersonaId());

				if(persona!=null)
				{
					numeroDocumentoIdentidad=persona.getNumeroDocumento();
					tipoDocumentoIdentidad=persona.getTipoDocumentoIdentidadId()!=null?
							persona.getTipoDocumentoIdentidadId().toString():"";

					complementoDocumentoIdentidad=persona.getCodigoComplementario()!=null?
							persona.getCodigoComplementario():"";
					lugarExpedicionDocumentoIdentidad=persona.getLugarExpedicionId()!=null?
							persona.getLugarExpedicionId().toString():"";

				}

				codigoAdministracion=contexto.getUsuario().getOficinaId()!=null?contexto.getUsuario().getOficinaId():0;
				nombreRazonSocial=contexto.getUsuario().getNombreCompleto();
				nitCi=contexto.getUsuario().getNumeroDocumento();
				tipoNitCi=ParametrosFRVCC.TIPO_DOC_CLIENTE_COMPRA_CI;
			}
			
		} else {
			usuario = contexto.getUsuario();
			this.RedireccionarIndex();
		}
	}


	public UsuarioContextoDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioContextoDto usuario) {
		this.usuario = usuario;
	}

	public void RedireccionarIndex() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.faces");
		} catch (Exception e) {
		}
	}

	public void RedireccionarMensajesErroresPagina() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/errorValidacion.faces");
		} catch (Exception e) {
		}
	}
	public ContextoJSF getContexto() {
		return contexto;
	}
	public void setContexto(ContextoJSF contexto) {
		this.contexto = contexto;
	}
	
	public String getNumeroDocumentoIdentidad() {
		return numeroDocumentoIdentidad;
	}


	public void setNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
		this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
	}


	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}


	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}


	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}


	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}


	public Integer getCodigoAdministracion() {
		return codigoAdministracion;
	}


	public void setCodigoAdministracion(Integer codigoAdministracion) {
		this.codigoAdministracion = codigoAdministracion;
	}


	public String getComplementoDocumentoIdentidad() {
		return complementoDocumentoIdentidad;
	}


	public void setComplementoDocumentoIdentidad(String complementoDocumentoIdentidad) {
		this.complementoDocumentoIdentidad = complementoDocumentoIdentidad;
	}


	public String getLugarExpedicionDocumentoIdentidad() {
		return lugarExpedicionDocumentoIdentidad;
	}


	public void setLugarExpedicionDocumentoIdentidad(String lugarExpedicionDocumentoIdentidad) {
		this.lugarExpedicionDocumentoIdentidad = lugarExpedicionDocumentoIdentidad;
	}


	public String getNitCi() {
		return nitCi;
	}


	public void setNitCi(String nitCi) {
		this.nitCi = nitCi;
	}


	public String getTipoNitCi() {
		return tipoNitCi;
	}


	public void setTipoNitCi(String tipoNitCi) {
		this.tipoNitCi = tipoNitCi;
	}


	public ContribuyenteDto getContribuyente() {
		return contribuyente;
	}
	public void setContribuyente(ContribuyenteDto contribuyente) {
		this.contribuyente = contribuyente;
	}
	public PersonaDto getPersona() {
		return persona;
	}
	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}


	public String getEsNewton() {
		return esNewton;
	}


	public void setEsNewton(String esNewton) {
		this.esNewton = esNewton;
	}


	public ServiciosPadronRestClient getvServiciosPadron() {
		return vServiciosPadron;
	}


	public void setvServiciosPadron(ServiciosPadronRestClient vServiciosPadron) {
		this.vServiciosPadron = vServiciosPadron;
	}


	public ServicioLibrosRestclient getvServicioLibro() {
		return vServicioLibro;
	}


	public void setvServicioLibro(ServicioLibrosRestclient vServicioLibro) {
		this.vServicioLibro = vServicioLibro;
	}


	public String getDescripcionAdministracion() {
		return descripcionAdministracion;
	}


	public void setDescripcionAdministracion(String descripcionAdministracion) {
		this.descripcionAdministracion = descripcionAdministracion;
	}
	

}