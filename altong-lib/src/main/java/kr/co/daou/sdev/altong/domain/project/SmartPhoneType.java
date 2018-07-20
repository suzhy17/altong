package kr.co.daou.sdev.altong.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmartPhoneType {
	ANDROID("A", "ANDROID"),
	IPHONE("I", "IPHONE");

	private String value;
	private String name;
}
