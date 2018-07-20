package kr.co.daou.sdev.altong.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import kr.co.daou.sdev.altong.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private AdminUserService adminUserService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		log.debug(exception.getMessage());

		// 로그인 실패 건수 업데이트
		String userId = request.getParameter("userId");
		adminUserService.addLoginFailCount(userId);

		// 방문 기록 : 로그인 실패
		// LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), request.getParameter("id"), request.getRequestURI(), "N", "");

		LoginResultType loginResult;
		// 스프링시큐리티에서 UsernameNotFoundException은 BadCredentialsException 으로 변경되어 넘어온다.
		if (exception instanceof BadCredentialsException) {
			loginResult = LoginResultType.BAD_CREDENTIALS;
		} else if (exception instanceof LockedException) {
			loginResult = LoginResultType.LOCKED;
		} else {
			loginResult = LoginResultType.UNKNOWN;
		}

		// 에러 메시지 저장
		request.setAttribute("userId", userId);
		request.setAttribute("loginResult", loginResult);
		request.getRequestDispatcher("/login").forward(request, response);
	}
}
