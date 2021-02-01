package bo.gob.sin.sre.fac.frvcc.jsf.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

@FacesConverter(value = LocalDateConverter.ID)
public class LocalDateConverter extends DateTimeConverter {
	public static final String ID = "bo.gob.sin.sre.fac.frvcc.jsf.util.LocalDateConverter";

	public LocalDateConverter() {
		super();
//		this.setTimeZone(TimeZone.getDefault());
//		this.setPattern("dd/MM/yyyy");
	}

//	@Override
//	public Object getAsObject1(FacesContext facesContext, UIComponent uiComponent, String value) {
//	
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate localDate = LocalDate.parse(value, formatter);
//		if (localDate == null) {
//			return null;
//		}
//
//		return localDate.atStartOfDay();
//	}	

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		
		if (value == null) {
			return null;
		}
		
		Object o = super.getAsObject(facesContext, uiComponent, value);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(value, formatter);
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		o = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		if (o == null) {
			return null;
		}

		if (o instanceof Date) {
			Instant instant = Instant.ofEpochMilli(((Date) o).getTime());
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
//			return LocalDate.parse(value, buildParser(uiComponent.getAttributes().getOrDefault("pattern", "dd/MM/yyyy").toString()));
		} else {
			throw new IllegalArgumentException(String
					.format("value=%s could not be converted to a LocalDate, result super.getAsObject=%s", value, o));
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return super.getAsString(facesContext, uiComponent, value);
		}

		if (value instanceof LocalDate) {
			LocalDate lDate = (LocalDate) value;
			Instant instant = lDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			return super.getAsString(facesContext, uiComponent, date);
		} else {
			throw new IllegalArgumentException(String.format("value=%s is not a instanceof LocalDate", value));
		}
	}
	private static DateTimeFormatter buildParser(String pattern)
    {
        DateTimeFormatterBuilder dtf = new DateTimeFormatterBuilder().parseLenient();
        dtf.appendOptional(DateTimeFormatter.ofPattern("M/dd/yy"))
                .appendOptional(DateTimeFormatter.ofPattern("M/dd/yy HH:mm:ss"));
        if(StringUtils.isNotBlank(pattern))
        {
            dtf.appendOptional(DateTimeFormatter.ofPattern(pattern));
        }
        return dtf.appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy"))
                .toFormatter();
    } 
}