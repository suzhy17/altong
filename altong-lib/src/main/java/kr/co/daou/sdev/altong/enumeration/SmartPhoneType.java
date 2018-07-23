package kr.co.daou.sdev.altong.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmartPhoneType {
	ANDROID("A", "ANDROID"),
	IPHONE("I", "IPHONE");

	private final String value;
	private final String name;
}