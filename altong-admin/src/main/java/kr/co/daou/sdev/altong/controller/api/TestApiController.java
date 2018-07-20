package kr.co.daou.sdev.altong.controller.api;

import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TestApiController {

	/**
	 * 테스트 API
	 * @return JSON
	 */
	@GetMapping(value = "/api/test")
	public JsonResult test() {

		List<String> pringVersions = Arrays.asList("1.0", "2.0", "2.5", "3.0", "3.1", "3.2", "4.0", "4.1", "4.2", "5.0");

		Map<String, Object> resObj = new HashMap<>();
		resObj.put("springVersions", pringVersions);
		resObj.put("currentVersion", "5.0.0");

		return new JsonResult(resObj);
	}

	/**
	 * 테스트 API - 500에러
	 * @return JSON
	 */
	@GetMapping(value = "/api/test500Error")
	public JsonResult test500Error() {

		throw new RuntimeException("알 수 없는 에러");
	}

	/**
	 * FAIL 테스트 API
	 * @return JSON
	 */
	@GetMapping(value = "/api/fail")
	public JsonResult testFail(@RequestParam(name = "isSuccess", required = false) Boolean isSuccess) {

		if (isSuccess == null) {
			throw new AltongException.InvalidArgumentException("파라미터가 입력되지 않았습니다.");
		}

		if (!isSuccess) {
			throw new AltongException("테스트에 실패했습니다.");
		}

		return new JsonResult("테스트에 성공하셨습니다.");
	}
}