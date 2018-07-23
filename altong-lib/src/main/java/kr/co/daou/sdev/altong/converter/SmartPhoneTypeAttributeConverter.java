package kr.co.daou.sdev.altong.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import kr.co.daou.sdev.altong.enumeration.SmartPhoneType;
import org.apache.commons.lang3.StringUtils;

@Converter(autoApply = true)
public class SmartPhoneTypeAttributeConverter implements AttributeConverter<SmartPhoneType, String> {

    @Override
    public String convertToDatabaseColumn(SmartPhoneType smartPhoneType) {
    	if (smartPhoneType == null) {
    		return null;
    	}
        return smartPhoneType.getValue();
    }

    @Override
    public SmartPhoneType convertToEntityAttribute(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		for (SmartPhoneType smartPhoneType : SmartPhoneType.values()) {
			if (str.equals(smartPhoneType.getValue())) {
				return smartPhoneType;
			}
		}

		return null;
    }
}
