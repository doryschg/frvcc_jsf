package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ConsultaResumenLCVDto;

@ManagedBean(name = "consultaResumenLCVModel")
@ViewScoped
public class ConsultaResumenLCVModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long nitEmpleador;
	private Integer anioPeriodo;
	
	private ArrayList<String> listaMeses = new ArrayList<String>();
	private List<ConsultaResumenLCVDto> listaRespuestaConsultaLCV = new ArrayList<>();

	@PostConstruct
	public void init() {
		nitEmpleador=null;
		anioPeriodo=null;
	}
	
	public void limpiar() {
		this.setNitEmpleador(null);
		this.setAnioPeriodo(null);
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

	public ArrayList<String> getListaMeses() {
		return listaMeses;
	}

	public void setListaMeses(ArrayList<String> listaMeses) {
		this.listaMeses = listaMeses;
	}

	public List<ConsultaResumenLCVDto> getListaRespuestaConsultaLCV() {
		return listaRespuestaConsultaLCV;
	}

	public void setListaRespuestaConsultaLCV(List<ConsultaResumenLCVDto> listaRespuestaConsultaLCV) {
		this.listaRespuestaConsultaLCV = listaRespuestaConsultaLCV;
	}

}

	
