package bo.gob.sin.sre.fac.frvcc.jsf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.*;
import bo.gob.sin.sre.fac.frvcc.jsf.util.*;
import org.primefaces.context.RequestContext;
import bo.gob.sin.sen.enco.controller.MensajesBean;
import bo.gob.sin.sre.fac.frvcc.jsf.model.ContextoUsuarioModel;
import bo.gob.sin.sre.fac.frvcc.jsf.model.RegistroComprasNotasModel;
import bo.gob.sin.sre.fac.frvcc.jsf.parametros.ParametrosFRVCC;
import bo.gob.sin.str.cmsj.mapl.dto.StrMensajeAplicacionDto;

@ManagedBean(name = "registroComprasNotasController")
@ViewScoped
public class RegistroComprasNotasController implements Serializable {
	private static String FORMATO_FECHA = "dd/MM/yyyy";

	@ManagedProperty(value = "#{contextoUsuarioModel}")
	private ContextoUsuarioModel contextoUsuarioModel;

	@ManagedProperty(value = "#{registroComprasNotasModel}")
	private RegistroComprasNotasModel registroComprasNotasModel;

	@ManagedProperty(value = "#{mensajesBean}")
	private MensajesBean mensajesBean;

	@ManagedProperty(value = "#{clientesRestController}")
	private ClientesRestController clientesRestController;

	private Set<Long> nitAutoCompletado = new HashSet<>();
	private Set<String> numeroAutorizacionAutoCompleatado = new HashSet<>();
	private LinkedHashMap<String, Serializable> filtros;

	public void init() {
		agregarCompraVaciaInicio();
		//cargarListaNitAutocompletado();
	}

	public Set<Long> getNitAutoCompletado() {
		return nitAutoCompletado;
	}

	public void setNitAutoCompletado(Set<Long> nitAutoCompletado) {
		this.nitAutoCompletado = nitAutoCompletado;
	}

	public void agregarCompraVacia() {
		this.registroComprasNotasModel.addMiFactura(new ComprasNotasCvDto(null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, contextoUsuarioModel.getTipoNitCi(),
				contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(),
				contextoUsuarioModel.getNombreRazonSocial(), ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null));
	}

	public void agregarCompraVaciaInicio() {
		this.registroComprasNotasModel
				.addMiFactura(new ComprasNotasCvDto(UUID.randomUUID().toString(), null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null, null, contextoUsuarioModel.getTipoNitCi(),
						contextoUsuarioModel.getNitCi(), contextoUsuarioModel.getComplementoDocumentoIdentidad(),
						contextoUsuarioModel.getNombreRazonSocial(), ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, null,
						null, null, null, null, null, null, null, null, null, null, null, null, null, null));
	}

	public ComprasNotasCvDto crearCompraVacia() {
		ComprasNotasCvDto compraNueva = new ComprasNotasCvDto(UUID.randomUUID().toString(), null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null,
				contextoUsuarioModel.getTipoNitCi(), contextoUsuarioModel.getNitCi(),
				contextoUsuarioModel.getComplementoDocumentoIdentidad(), contextoUsuarioModel.getNombreRazonSocial(),
				ParametrosFRVCC.ORIGEN_COMPRA_APLICACION, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null);
		return compraNueva;

	}

	
	private void cargarListaNitAutocompletado() {

		if (nitAutoCompletado.size() == 0) {

			FiltroCriteria filtros = new FiltroCriteria();
			filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, contextoUsuarioModel.getNitCi());
			filtros.adicionar("estadoId", OperadorCriteria.IGUAL, "AC");
			List<ComprasCvDto> vListaFacturas;
			RespuestaComprasDto vRespuesta = clientesRestController.getClienteRestCompras().obtenerCompras(filtros.getFiltros());
			if (vRespuesta.isOk()) {
				vListaFacturas = vRespuesta.getComprasResponse();
			} else {
				vListaFacturas = new ArrayList<>();
				// mensajesBean.addMensajes(vRespuesta);
			}

			if (vListaFacturas.size() > 0) {
				nitAutoCompletado.addAll(vListaFacturas.stream().map(ComprasCvDto::getNitProveedor).collect(Collectors.toList()));
				numeroAutorizacionAutoCompleatado.addAll(vListaFacturas.stream().map(ComprasCvDto::getCodigoAutorizacion).collect(Collectors.toList()));
			}
		}

	}

//	public void eliminarCompra(ActionEvent actionEvent) {
//
//		ComprasCvDto vCompra = (ComprasCvDto) actionEvent.getComponent().getAttributes().get("compra");
//		ResultadoGenericoDto<String> resp = clientesRestController.getClienteRestCompras().eliminarCompra(vCompra);
//		mensajesBean.addMensajes(resp);
//
//	}

//	public void actulizarCompra(ComprasCvDto pComprasCvDto) {
//		if (pComprasCvDto == null) {
//			RequestContext.getCurrentInstance().execute("toastr.error('Error la compra no tiene valores', 'Error')");
//			return;
//		}
//		ResultadoGenericoDto<String> vRespuesta = this.clientesRestController.getClienteRestCompras().modificarCompra(pComprasCvDto);
//		mensajesBean.addMensajes(vRespuesta);
//	}
	
	public void onLimpiar() {
		this.registroComprasNotasModel.clear();
	}

	public void guardarCompra(ActionEvent actionEvent) {
		ComprasNotasCvDto vCompraNota = (ComprasNotasCvDto) actionEvent.getComponent().getAttributes().get("compra");
		String idCompra = "";
		if (vCompraNota == null) {
			RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
			return;
		}
		
		FiltroCriteria filtrosCompras = obtenerFiltroCriteriaCompra(vCompraNota);
		 List<ComprasCvDto> vListaCompras = new ArrayList<ComprasCvDto>();
         
         RespuestaComprasDto vRespuestaLista = this.clientesRestController.getClienteRestCompras().obtenerCompras(filtrosCompras.getFiltros());
         vListaCompras = vRespuestaLista.getComprasResponse();
         
         
		if (vListaCompras.isEmpty()) {
			String vId = UUID.randomUUID().toString();
			boolean vRespuestaCompra = guardarCompra(generarCompraNueva(vId,vCompraNota), vId);
			if (vRespuestaCompra) {
				idCompra=vId;
			}	
		}
		if (vListaCompras.size() == 1) {
			idCompra=vListaCompras.get(0).getId();
		}
		
		if (!idCompra.equals("")) {
			vCompraNota.setId(UUID.randomUUID().toString());
			vCompraNota.setCompraId(idCompra);
			
			ResultadoGenericoDto<String> vRespuesta = null;
			if (vCompraNota.isOk()) {
				vRespuesta = this.clientesRestController.getClienteRestComprasNotas().modificarCompraNota(vCompraNota);
			} else {
				vRespuesta = this.clientesRestController.getClienteRestComprasNotas().registrarCompraNota(vCompraNota);
			}
			if (vRespuesta.isOk()) {
				mensajesBean.addMensajes(vRespuesta);
				this.registroComprasNotasModel.limpiarErrores();
				this.nitAutoCompletado.add(vCompraNota.getNitProveedor());
				if (!vCompraNota.isOk()) {
					vCompraNota.setOk(true);
					agregarCompraVaciaInicio();
					onLimpiar();
				}
			} else {
				mensajesBean.addMensajes(vRespuesta);
				RequestContext.getCurrentInstance().execute("toastr.error(Error al guardar su Registro de factura nota credito y debito', 'Error')");
				if (!vRespuesta.getMensajes().isEmpty()) {
					List<StrMensajeAplicacionDto> vMensajesLista = vRespuesta.getMensajes();
					List<ErrorDto> errores = new ArrayList<>();
					for (StrMensajeAplicacionDto msj : vMensajesLista) {
						errores.add(new ErrorDto(msj.getDescripcion(),
								String.valueOf(registroComprasNotasModel.getMisFacturas().indexOf(vCompraNota)),
								msj.getDescripcionUi(), "El " + msj.getDescripcionUi() + " es invalido."));
					}
					RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
					this.registroComprasNotasModel.setErrores(errores);
				}
			}
			
			vCompraNota.setId(null);
			vCompraNota.setCompraId(null);
		} else {
			RequestContext.getCurrentInstance().execute("toastr.error('Error al registrar sus Compras Notas de Credito y Debito', 'Error')");
		}	
	}
	
	private FiltroCriteria obtenerFiltroCriteriaCompra(ComprasNotasCvDto pCompra) {
		FiltroCriteria filtros = new FiltroCriteria();
//		filtros.adicionar("fechaFactura", OperadorCriteria.IGUAL, pCompra.getFechaFacturaOriginal());
		filtros.adicionar("numeroFactura", OperadorCriteria.IGUAL, pCompra.getNumeroFacturaOriginal());
		filtros.adicionar("codigoAutorizacion", OperadorCriteria.IGUAL,pCompra.getCodigoAutorizacionOriginal());
		filtros.adicionar("tipoDocumentoCliente", OperadorCriteria.IGUAL, pCompra.getTipoDocumentoCliente());
		filtros.adicionar("nitProveedor", OperadorCriteria.IGUAL, pCompra.getNitProveedor());
		filtros.adicionar("numeroDocumentoCliente", OperadorCriteria.IGUAL, pCompra.getNumeroDocumentoCliente());
		filtros.adicionar("estadoId", OperadorCriteria.IGUAL, ParametrosFRVCC.ESTADO_ID);
//		if (pCompra.getCodigoControlOriginal()==null) {
//			filtros.adicionar("codigoControl", OperadorCriteria.IGUAL, "");
//		}else {
//			filtros.adicionar("codigoControl", OperadorCriteria.IGUAL, pCompra.getCodigoControlOriginal());
//		}
		return filtros;
	}
	
	private ComprasCvDto generarCompraNueva(String vId, ComprasNotasCvDto pCompraNota) {
		ComprasCvDto vCompra = new ComprasCvDto();
		vCompra.setId(vId);
		vCompra.setFechaFactura(pCompraNota.getFechaFacturaOriginal());
		vCompra.setNitProveedor(pCompraNota.getNitProveedor());
		vCompra.setNumeroFactura(pCompraNota.getNumeroFacturaOriginal());
		vCompra.setCodigoAutorizacion(pCompraNota.getCodigoAutorizacionOriginal());
		vCompra.setImporteBaseCf(pCompraNota.getImporteTotalOriginal());
		if (pCompraNota.getCodigoControlOriginal()==null) {
			vCompra.setCodigoControl("");
		}else {
			vCompra.setCodigoControl(pCompraNota.getCodigoControlOriginal());
		}
		vCompra.setNombreCliente(pCompraNota.getNombreCliente());
		vCompra.setTipoDocumentoCliente(pCompraNota.getTipoDocumentoCliente());
		vCompra.setNumeroDocumentoCliente(pCompraNota.getNumeroDocumentoCliente());
		vCompra.setComplementoDocumentoCliente(pCompraNota.getComplementoDocumentoCliente());
		vCompra.setOrigenId(pCompraNota.getOrigenId());
		
		
		vCompra.setEstadoCompraId(ParametrosFRVCC.ESTADO_COMPRA_NOTA_CREDITO_DEBITO);
		vCompra.setImporteTotalCompra(vCompra.getImporteBaseCf());
		return vCompra;
	}

	public boolean guardarCompra(ComprasCvDto pCompraNueva,String pCompraId) {
		boolean vResultado=false;
		ResultadoGenericoDto<String> vRespuesta = new ResultadoGenericoDto<String>();
		ComprasCvDto vCompraNueva = pCompraNueva;
		if (vCompraNueva == null) {
			RequestContext.getCurrentInstance().execute("toastr.error('No tiene facturas para su Registro de Compras', 'Error')");
			vResultado=false;
		}else {
//			vCompraNueva.setId(UUID.randomUUID().toString());
			vRespuesta = this.clientesRestController.getClienteRestCompras().registrarCompra(vCompraNueva);
			if (vRespuesta.isOk()) {
				vResultado=true;
			} else {
				vResultado=false;
				mensajesBean.addMensajes(vRespuesta);
			}
		}
		return vResultado;
	}

	public RegistroComprasNotasModel getRegistroComprasNotasModel() {
		return registroComprasNotasModel;
	}

	public void setRegistroComprasNotasModel(RegistroComprasNotasModel registroComprasNotasModel) {
		this.registroComprasNotasModel = registroComprasNotasModel;
	}

	public LinkedHashMap<String, Serializable> getFiltros() {
		return filtros;
	}

	public void setFiltros(LinkedHashMap<String, Serializable> filtros) {
		this.filtros = filtros;
	}

	public ClientesRestController getClientesRestController() {
		return clientesRestController;
	}

	public void setClientesRestController(ClientesRestController clientesRestController) {
		this.clientesRestController = clientesRestController;
	}

	public ContextoUsuarioModel getContextoUsuarioModel() {
		return contextoUsuarioModel;
	}

	public void setContextoUsuarioModel(ContextoUsuarioModel contextoUsuarioModel) {
		this.contextoUsuarioModel = contextoUsuarioModel;
	}

	public MensajesBean getMensajesBean() {
		return mensajesBean;
	}

	public void setMensajesBean(MensajesBean mensajesBean) {
		this.mensajesBean = mensajesBean;
	}

	public Set<String> getNumeroAutorizacionAutoCompleatado() {
		return numeroAutorizacionAutoCompleatado;
	}

	public void setNumeroAutorizacionAutoCompleatado(Set<String> numeroAutorizacionAutoCompleatado) {
		this.numeroAutorizacionAutoCompleatado = numeroAutorizacionAutoCompleatado;
	}

}
