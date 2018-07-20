package kr.co.daou.sdev.altong.exception;

import kr.co.daou.sdev.altong.result.JsonResultType;

public class UnityAlertException extends RuntimeException {

	private static final long serialVersionUID = 5947253546989123459L;

	private String code;

	public String getCode() {
		return code;
	}

	public UnityAlertException() {
		super();
	}

	public UnityAlertException(String message) {
		super(message);
	}

	public UnityAlertException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnityAlertException(Throwable cause) {
		super(cause);
	}

	public UnityAlertException(String code, String message) {
		super(message);
		this.code = code;
	}

	public UnityAlertException(JsonResultType resultCodeMessage) {
		super(resultCodeMessage.getMessage());
		this.code = resultCodeMessage.getCode();
	}
}
