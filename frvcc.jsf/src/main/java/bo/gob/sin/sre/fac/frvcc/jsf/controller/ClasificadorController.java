package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import bo.gob.sin.sre.fac.frvcc.jsf.service.ServiciosClasificadoresRest;
import bo.gob.sin.str.cps.clas.dto.ClasificadorDto;



@ManagedBean(name = "clasificadorController")
@ApplicationScoped
public class ClasificadorController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ClasificadorDto> vListClasificadoresEstadoUsoCompra=new ArrayList<ClasificadorDto>();
	private List<ClasificadorDto> vListClasificadoresEstadoCompra=new ArrayList<ClasificadorDto>();
	private List<ClasificadorDto> vListaClasificadoresTipoDocumentoCompra=new ArrayList<ClasificadorDto>();
	private List<ClasificadorDto> vListaClasificadoresImpuestoUsoCompra=new ArrayList<ClasificadorDto>();
	private List<ClasificadorDto> vListClasificadoresEstadoFormulario=new ArrayList<ClasificadorDto>();
	private List<ClasificadorDto> vListaClasificadoresTiposObservacion=new ArrayList<>();
	private List<ClasificadorDto> vListaClasificadoresTiposCompra=new ArrayList<>();
	private List<ClasificadorDto> vListaClasificadoresEstadosLibros=new ArrayList<>();
	private transient ServiciosClasificadoresRest clienteClasificadores=new ServiciosClasificadoresRest();
	
	@PostConstruct
	public void init() {
		vListClasificadoresEstadoUsoCompra=clienteClasificadores.obtenerClasificadoresPorTipo("estado_uso_id_vc");
		vListClasificadoresEstadoCompra=clienteClasificadores.obtenerClasificadoresPorTipo("estado_compra_id_vc");
		vListClasificadoresEstadoFormulario=clienteClasificadores.obtenerClasificadoresPorTipo("estado_formulario_id_vc");
		vListaClasificadoresTipoDocumentoCompra=clienteClasificadores.obtenerClasificadoresPorTipo("tipo_documento_id_vc");
		vListaClasificadoresImpuestoUsoCompra=clienteClasificadores.obtenerClasificadoresPorTipo("impuesto_uso_id_vc");
		vListaClasificadoresTiposObservacion=clienteClasificadores.obtenerClasificadoresPorTipo("tipo_observacion_id_vc");
		vListaClasificadoresTiposCompra=clienteClasificadores.obtenerClasificadoresPorTipo("tipo_compra_id_vc");
		vListaClasificadoresEstadosLibros=clienteClasificadores.obtenerClasificadoresPorTipo("estado_libro_id_vc");
		System.err.println("CLASIFICADORES!!!!!");

	}
	
	public String descripcionEstadoUsoCompraByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListClasificadoresEstadoUsoCompra.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	
	public String descripcionEstadoCompraByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListClasificadoresEstadoCompra.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public String descripcionEstadoFormularioByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListClasificadoresEstadoFormulario.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public String descripcionTipoDocumentoCompraByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListaClasificadoresTipoDocumentoCompra.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public String descripcionImpuestoCompraByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListaClasificadoresImpuestoUsoCompra.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public String descripcionTipoObsByCodigo(String pCodigoclasificador)
	{
		if(pCodigoclasificador!=null && !pCodigoclasificador.equals("")){
			Optional<ClasificadorDto> clasificador= vListaClasificadoresTiposObservacion.stream()
					.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
					.findFirst();
			return clasificador.get().getDescripcion();
		}
		return "";
	}
	public String descripcionTipoCompraByCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListaClasificadoresTiposCompra.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public String descripcionEstadoLibrobyCodigo(String pCodigoclasificador)
	{
		Optional<ClasificadorDto> clasificador= vListaClasificadoresEstadosLibros.stream()
				.filter(s -> pCodigoclasificador.equals(s.getCodigoClasificador()))
		        .findFirst();
		return clasificador.get().getDescripcion();
	}
	public List<ClasificadorDto> getvListClasificadoresEstadoUsoCompra() {
		return vListClasificadoresEstadoUsoCompra;
	}

	public void setvListClasificadoresEstadoUsoCompra(List<ClasificadorDto> vListClasificadoresEstadoUsoCompra) {
		this.vListClasificadoresEstadoUsoCompra = vListClasificadoresEstadoUsoCompra;
	}

	public List<ClasificadorDto> getvListClasificadoresEstadoCompra() {
		return vListClasificadoresEstadoCompra;
	}

	public void setvListClasificadoresEstadoCompra(List<ClasificadorDto> vListClasificadoresEstadoCompra) {
		this.vListClasificadoresEstadoCompra = vListClasificadoresEstadoCompra;
	}

	public List<ClasificadorDto> getvListClasificadoresEstadoFormulario() {
		return vListClasificadoresEstadoFormulario;
	}

	public void setvListClasificadoresEstadoFormulario(List<ClasificadorDto> vListClasificadoresEstadoFormulario) {
		this.vListClasificadoresEstadoFormulario = vListClasificadoresEstadoFormulario;
	}

	public List<ClasificadorDto> getvListaClasificadoresTipoDocumentoCompra() {
		return vListaClasificadoresTipoDocumentoCompra;
	}

	public void setvListaClasificadoresTipoDocumentoCompra(List<ClasificadorDto> vListaClasificadoresTipoDocumentoCompra) {
		this.vListaClasificadoresTipoDocumentoCompra = vListaClasificadoresTipoDocumentoCompra;
	}

	public List<ClasificadorDto> getvListaClasificadoresImpuestoUsoCompra() {
		return vListaClasificadoresImpuestoUsoCompra;
	}

	public void setvListaClasificadoresImpuestoUsoCompra(List<ClasificadorDto> vListaClasificadoresImpuestoUsoCompra) {
		this.vListaClasificadoresImpuestoUsoCompra = vListaClasificadoresImpuestoUsoCompra;
	}

	public List<ClasificadorDto> getvListaClasificadoresTiposObservacion() {
		return vListaClasificadoresTiposObservacion;
	}

	public void setvListaClasificadoresTiposObservacion(List<ClasificadorDto> vListaClasificadoresTiposObservacion) {
		this.vListaClasificadoresTiposObservacion = vListaClasificadoresTiposObservacion;
	}

	public List<ClasificadorDto> getvListaClasificadoresTiposCompra() {
		return vListaClasificadoresTiposCompra;
	}

	public void setvListaClasificadoresTiposCompra(List<ClasificadorDto> vListaClasificadoresTiposCompra) {
		this.vListaClasificadoresTiposCompra = vListaClasificadoresTiposCompra;
	}
	

}
