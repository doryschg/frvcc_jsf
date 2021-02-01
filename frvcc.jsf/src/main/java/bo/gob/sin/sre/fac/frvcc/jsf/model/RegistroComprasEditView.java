package bo.gob.sin.sre.fac.frvcc.jsf.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import bo.gob.sin.sre.fac.frvcc.jsf.dto.ComprasCvDto;


@ManagedBean(name = "registroComprasEditView")
@ViewScoped
public class RegistroComprasEditView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2562316472368366153L;

	private List<ComprasCvDto> facturas;

	@ManagedProperty(value = "#{registroComprasModel}")
	private RegistroComprasModel registroComprasModel;

	@PostConstruct
	public void init() {
	}



	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

//		if (newValue != null && !newValue.equals(oldValue)) {
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura Modificada",
//					"Old: " + oldValue + ", New:" + newValue);
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//		}
	}
	
	
}
