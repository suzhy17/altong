package kr.co.daou.sdev.altong.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JsonResultType {
	SUCCESS("SUCCESS", "정상처리되었습니다."),
	FAIL("FAIL", "처리에 실패하였습니다."),
	ERROR("ERROR", "알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.");

	private String code;
	private String message;
}
