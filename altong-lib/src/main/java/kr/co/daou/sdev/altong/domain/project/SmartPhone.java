package kr.co.daou.sdev.altong.domain.project;

import kr.co.daou.sdev.altong.exception.AltongException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Embeddable
public class SmartPhone {
	@Column(columnDefinition = "CHAR(1)")
	private SmartPhoneType smartPhoneType;

	@Column(length = 200)
	private String pushNo;

	public SmartPhone(SmartPhoneType smartPhoneType, String pushNo) {
		if (smartPhoneType == null) {
			throw new AltongException.InvalidArgumentException("스마트폰 종류는 필수 값입니다.");
		}
		if (StringUtils.isBlank(pushNo)) {
			throw new AltongException.InvalidArgumentException("푸시 번호는 필수 값입니다.");
		}
		
		this.smartPhoneType = smartPhoneType;
		this.pushNo = pushNo;
	}

	@Override
	public String toString() {
		return "SmartPhone{" +
				"smartPhoneType=" + smartPhoneType +
				", pushNo='" + pushNo + '\'' +
				'}';
	}
}
