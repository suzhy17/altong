package kr.co.daou.sdev.altong.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class TextFormatter {

	public static String currency(String txt) {
		return txt;
	}
	public static String phoneNumber(String txt) {
		return txt;
	}

	public static String datetime(String txt, String inFormat, String outFormat) {
		return txt;
	}

	public static String datetime(LocalDateTime txt, String outFormat) {
		if (txt == null) {
			return "";
		}
		return txt.format(DateTimeFormatter.ofPattern(outFormat));
	}
}
