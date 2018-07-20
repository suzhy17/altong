package kr.co.daou.sdev.altong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.co.daou.sdev.altong.exception.UnityAlertException;
import kr.co.daou.sdev.altong.result.JsonResult;
import kr.co.daou.sdev.altong.result.JsonResultType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class WebControllerAdvice {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public String exceptionHandler(Exception exception, HttpServletResponse response) {

		log.error("--- WebControllerAdvice  : Exception ---", exception);

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return "error";
	}

	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({AccessDeniedException.class})
	public String accessDeniedExceptionHandler(AccessDeniedException exception, HttpServletResponse response) {

		log.info("--- WebControllerAdvice : AccessDeniedException ---");

		return "error-405";
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MissingServletRequestParameterException.class})
	public String missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception, HttpServletResponse response) {

		log.info("--- WebControllerAdvice :: MissingServletRequestParameterException ---");

		return "error";
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnityAlertException.class)
	public String unityAlertExceptionHandler(UnityAlertException exception, HttpServletRequest request) {

		log.info("--- WebControllerAdvice :: UnityAlertException ---");
		log.info(exception.toString());

		request.setAttribute("result", new JsonResult(JsonResultType.FAIL, exception.getMessage()));

		return "error";
	}
}
