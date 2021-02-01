package bo.gob.sin.sre.fac.frvcc.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("longConverter")
public class LongConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (value == null || value.equals("0")) {
			return null;
		}
		return Long.parseLong(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (value).toString();
	}
}

