package kr.co.daou.sdev.altong.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

import kr.co.daou.sdev.altong.domain.alert.AlertStatusType;
import kr.co.daou.sdev.altong.domain.project.SendType;

@Converter(autoApply = true)
public class AlertStatusTypeAttributeConverter implements AttributeConverter<AlertStatusType, String> {

    @Override
    public String convertToDatabaseColumn(AlertStatusType alertStatusType) {
    	if (alertStatusType == null) {
    		return null;
    	}
        return alertStatusType.getValue();
    }

    @Override
    public AlertStatusType convertToEntityAttribute(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		for (AlertStatusType alertStatusType : AlertStatusType.values()) {
			if (str.equals(alertStatusType.getValue())) {
				return alertStatusType;
			}
		}

		return null;
    }
}
