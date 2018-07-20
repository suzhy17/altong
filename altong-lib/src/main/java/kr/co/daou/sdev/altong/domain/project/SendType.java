package kr.co.daou.sdev.altong.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SendType {
	PUSH("P", "PUSH"),
	EMAIL("E", "EMAIL"),
	DAOU_OFFICE("D", "DAOU_OFFICE"),
	SMS("S", "SMS");

	private String value;
	private String name;
}
