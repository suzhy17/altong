package kr.co.daou.sdev.altong.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kr.co.daou.sdev.altong.exception.AltongException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.daou.sdev.altong.exception.UnityAlertException;
import kr.co.daou.sdev.altong.result.JsonResult;
import kr.co.daou.sdev.altong.result.JsonResultType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class JsonControllerAdvice {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public JsonResult exceptionHandler(Exception exception, HttpServletResponse response) {

		log.error("--- JsonControllerAdvice :: Exception ---", exception);

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new JsonResult(JsonResultType.ERROR);
	}

	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({AccessDeniedException.class})
	public JsonResult accessDeniedExceptionHandler(Exception exception, HttpServletResponse response) {

		log.info("--- JsonControllerAdvice :: AccessDeniedException ---");

		return new JsonResult(JsonResultType.ERROR, "접근 권한이 없습니다.");
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MissingServletRequestParameterException.class})
	public JsonResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception, HttpServletResponse response) {

		log.info("--- JsonControllerAdvice :: MissingServletRequestParameterException ---");

		return new JsonResult(JsonResultType.ERROR, "파라미터[" + exception.getParameterName() + "]가 누락되었습니다.");
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BindException.class})
	public JsonResult bindExceptionHandler(BindException exception, BindingResult bindingResult) {

		log.info("--- JsonControllerAdvice :: bindExceptionHandler ---");

		List<ObjectError> errors = bindingResult.getAllErrors();
		String errMsg = errors.stream().findFirst().get().getDefaultMessage();
		return new JsonResult(JsonResultType.ERROR, errMsg);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnityAlertException.class)
	public JsonResult unityAlertExceptionHandler(UnityAlertException exception) {

		log.info("--- JsonControllerAdvice :: UnityAlertException ---");
		log.info(exception.toString());

		return new JsonResult(JsonResultType.FAIL, exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AltongException.class)
	public JsonResult altonExceptionHandler(AltongException exception) {

		log.info("--- JsonControllerAdvice :: AltonException ---");
		log.info(exception.toString());

		return new JsonResult(JsonResultType.FAIL, exception.getMessage());
	}
}
