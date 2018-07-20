package kr.co.daou.sdev.altong.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginResultType {
	BAD_CREDENTIALS("사용자가 존재하지 않거나 비밀번호가 틀렸습니다."),
	LOCKED("비밀번호가 5회이상 틀렸기때문에 계정이 잠겼습니다."),
	UNKNOWN("사용자가 존재하지 않거나 비밀번호가 틀렸습니다.");

	private String message;
}
