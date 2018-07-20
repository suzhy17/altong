package kr.co.daou.sdev.altong.domain.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlertStatusType {
	READY("1", "발송대기"),
	ING("2", "발송중"),
	REREADY("5", "재발송대기"),
	REING("6", "재발송중"),
	OK("7", "발송완료"),
	ERROR("9","에러")
	;

	private String value;
	private String name;
}
