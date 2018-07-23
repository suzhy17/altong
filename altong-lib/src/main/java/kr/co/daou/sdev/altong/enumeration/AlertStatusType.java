package kr.co.daou.sdev.altong.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlertStatusType {
	READY("1", "발송대기"),
	ING("2", "발송중"),
	REREADY("5", "재발송대기"),
	REING("6", "재발송중"),
	OK("7", "발송완료"),
	ERROR("9","에러")
	;

	private final String value;
	private final String name;
}
