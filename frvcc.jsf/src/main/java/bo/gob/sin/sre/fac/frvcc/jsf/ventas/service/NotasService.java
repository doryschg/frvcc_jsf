package bo.gob.sin.sre.fac.frvcc.jsf.ventas.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class NotasService {

	@Inject
	 PeticionesRest config;
	
	public String SaveNota() {
	
		return "Implementar ingresar registro";
	}
	
	public String removeNota() {
		
		return "Implementar remover registro";
	}
	
	public String updateNota() {
		
		return "Implementar update registro";
	}
	
}
