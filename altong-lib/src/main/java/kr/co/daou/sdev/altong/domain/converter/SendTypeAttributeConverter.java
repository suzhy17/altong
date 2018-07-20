package kr.co.daou.sdev.altong.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

import kr.co.daou.sdev.altong.domain.project.SendType;

@Converter(autoApply = true)
public class SendTypeAttributeConverter implements AttributeConverter<SendType, String> {

    @Override
    public String convertToDatabaseColumn(SendType sendType) {
    	if (sendType == null) {
    		return null;
    	}
        return sendType.getValue();
    }

    @Override
    public SendType convertToEntityAttribute(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		for (SendType sendType : SendType.values()) {
			if (str.equals(sendType.getValue())) {
				return sendType;
			}
		}

		return null;
    }
}
