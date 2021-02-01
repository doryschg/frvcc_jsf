package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.FormularioCvDto;

@ManagedBean(name = "consultaFormulario110Model")
@ViewScoped

public class ConsultaFormulario110Model implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long nitEmpleador;
	private Integer anioPeriodo;
	private Integer mesPeriodo;
	private String tipoFormularioId;
	
	private ArrayList<String> listaMeses = new ArrayList<String>();
	private List<FormularioCvDto> listaMesesConsulta = new ArrayList<>();

	@PostConstruct
	public void init() {
		nitEmpleador=null;
		anioPeriodo=null;
	}
	
	public void limpiar() {
		this.setNitEmpleador(null);
		this.setAnioPeriodo(null);
		this.setMesPeriodo(null);
		this.setTipoFormularioId(null);
	}
	
	public Long getNitEmpleador() {
		return nitEmpleador;
	}

	public void setNitEmpleador(Long nitEmpleador) {
		this.nitEmpleador = nitEmpleador;
	}

	public Integer getAnioPeriodo() {
		return anioPeriodo;
	}

	public void setAnioPeriodo(Integer anioPeriodo) {
		this.anioPeriodo = anioPeriodo;
	}

	public String getTipoFormularioId() {
		return tipoFormularioId;
	}

	public void setTipoFormularioId(String tipoFormularioId) {
		this.tipoFormularioId = tipoFormularioId;
	}

	public ArrayList<String> getListaMeses() {
		return listaMeses;
	}

	public void setListaMeses(ArrayList<String> listaMeses) {
		this.listaMeses = listaMeses;
	}

	public List<FormularioCvDto> getListaMesesConsulta() {
		return listaMesesConsulta;
	}

	public void setListaMesesConsulta(List<FormularioCvDto> listaMesesConsulta) {
		this.listaMesesConsulta = listaMesesConsulta;
	}

	public Integer getMesPeriodo() {
		return mesPeriodo;
	}

	public void setMesPeriodo(Integer mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}
	
}

	
