package kr.co.daou.sdev.altong.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

@Converter(autoApply = true)
public class BooleanAttributeConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean isTrue) {
    	if (isTrue == null) {
    		return null;
    	}
        return isTrue ? "1" : "0";
    }

    @Override
    public Boolean convertToEntityAttribute(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		return StringUtils.equals(str, "1");
    }
}
