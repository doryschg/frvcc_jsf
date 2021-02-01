package bo.gob.sin.sre.fac.frvcc.jsf.controller;
import bo.gob.sin.sre.fac.frvcc.jsf.service.*;



import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "clientesRestController")
@ApplicationScoped
public class ClientesRestController {
	
	private transient ServiciosClasificadoresRest clienteRestClasificadores;
	private transient ServiciosPadronRestClient clienteRestPadron;
	private transient ServiciosSfeRestclient clienteRestSfe;
	private transient ServicioParametricaDepartamentoRestclient clienteDepartamentos;
	private transient ServicioFormulariosRestclient clienteRestFormularios;
	private transient ServicioLibrosRestclient clienteRestLibros;
	private transient ServicioComprasRestClient clienteRestCompras;
	private transient ServicioComprasNotasRestClient clienteRestComprasNotas;
	private transient ServicioComprasEstandarRestClient clienteRestComprasEstandar;
	private transient ServicioSucursalUsuario clienteRestSucursalUsuario;
	private transient ServiciosRolesRest clienteRestRoles;
	private transient  ServiciosHistorialRestClient clienteRestHistoriales;
	private transient  ServiciosBeneficiarioRestClient clienteBeneficiarioRestClient;

	@PostConstruct
	public void init(){
		clienteRestClasificadores=new ServiciosClasificadoresRest();
		clienteRestPadron=new ServiciosPadronRestClient();
		clienteRestSfe=new ServiciosSfeRestclient();
		clienteDepartamentos=new ServicioParametricaDepartamentoRestclient();
		clienteRestFormularios=new ServicioFormulariosRestclient();
		clienteRestLibros=new ServicioLibrosRestclient();
		clienteRestCompras=new ServicioComprasRestClient();
		clienteRestComprasNotas=new ServicioComprasNotasRestClient();
		clienteRestComprasEstandar=new ServicioComprasEstandarRestClient();
		clienteRestSucursalUsuario=new ServicioSucursalUsuario();
		clienteRestRoles = new ServiciosRolesRest();
		clienteRestHistoriales=new ServiciosHistorialRestClient();
		clienteBeneficiarioRestClient = new ServiciosBeneficiarioRestClient();
	}

	public ServiciosBeneficiarioRestClient getClienteBeneficiarioRestClient() {
		return clienteBeneficiarioRestClient;
	}

	public ServiciosClasificadoresRest getClienteRestClasificadores() {
		return clienteRestClasificadores;
	}
	public ServiciosPadronRestClient getClienteRestPadron() {
		return clienteRestPadron;
	}
	public ServiciosSfeRestclient getClienteRestSfe() {
		return clienteRestSfe;
	}
	public ServicioParametricaDepartamentoRestclient getClienteDepartamentos() {
		return clienteDepartamentos;
	}
	public ServicioFormulariosRestclient getClienteRestFormularios() {
		return clienteRestFormularios;
	}
	public ServicioLibrosRestclient getClienteRestLibros() {
		return clienteRestLibros;
	}
	public ServicioComprasRestClient getClienteRestCompras() {
		return clienteRestCompras;
	}
	public ServicioComprasNotasRestClient getClienteRestComprasNotas() {
		return clienteRestComprasNotas;
	}
	public ServicioComprasEstandarRestClient getClienteRestComprasEstandar() {
		return clienteRestComprasEstandar;
	}
	public void setClienteRestComprasEstandar(ServicioComprasEstandarRestClient clienteRestComprasEstandar) {
		this.clienteRestComprasEstandar = clienteRestComprasEstandar;
	}
	public ServicioSucursalUsuario getClienteRestSucursalUsuario() {
		return clienteRestSucursalUsuario;
	}
	public ServiciosRolesRest getClienteRestRoles() {
		return clienteRestRoles;
	}

	public ServiciosHistorialRestClient getClienteRestHistoriales() {
		return clienteRestHistoriales;
	}



}
