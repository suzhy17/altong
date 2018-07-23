package kr.co.daou.sdev.altong.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SendType {
	PUSH("P", "PUSH"),
	EMAIL("E", "EMAIL"),
	DAOU_OFFICE("D", "DAOU_OFFICE"),
	SMS("S", "SMS");

	private final String value;
	private final String name;
}
