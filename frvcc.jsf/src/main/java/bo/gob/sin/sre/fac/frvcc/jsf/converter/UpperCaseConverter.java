package bo.gob.sin.sre.fac.frvcc.jsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
@FacesConverter("upperCaseConverter")
public class UpperCaseConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
     
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value.toUpperCase();
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
     
}