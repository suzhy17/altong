package kr.co.daou.sdev.altong.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 결과 객체를 담아 Json View에 전달하는 역할
 * @author suzhy
 */
@Getter
@ToString
public class JsonResult {

	private String resCd;
	private String resMsg;
	private Map<String, Object> resObj = new HashMap<>();

	private void initialize(JsonResultType result, String resMsg, Map<String, Object> resObj) {
		resCd = result.getCode();
		if (resMsg == null) {
			this.resMsg = result.getMessage();
		} else {
			this.resMsg = resMsg;
		}
		this.resObj = resObj;
	}

	public JsonResult() {
		this.initialize(JsonResultType.SUCCESS, null, null);
	}

	public JsonResult(JsonResultType result) {
		this.initialize(result, null, null);
	}

	public JsonResult(String resMsg) {
		this.initialize(JsonResultType.SUCCESS, resMsg, null);
	}

	public JsonResult(Map<String, Object> resObj) {
		this.initialize(JsonResultType.SUCCESS, null, resObj);
	}

	public JsonResult(JsonResultType result, String resMsg) {
		this.initialize(result, resMsg, null);
	}

	public JsonResult(JsonResultType result, Map<String, Object> resObj) {
		this.initialize(result, null, resObj);
	}

	public JsonResult(JsonResultType result, String resMsg, Map<String, Object> resObj) {
		this.initialize(result, resMsg, resObj);
	}

	public JsonResult addResObj(String key, Object obj) {
	    this.resObj.put(key, obj);
	    return this;
    }

	/**
	 * 객체를 Json 문자열로 변경 (로그 출력용)
	 * @return
	 */
	public String toJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
