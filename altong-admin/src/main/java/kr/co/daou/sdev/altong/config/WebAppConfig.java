package kr.co.daou.sdev.altong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.interceptor.ActionUrlLoggingInterceptor;

@Configuration
public class WebAppConfig {

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new ActionUrlLoggingInterceptor()).addPathPatterns("/**");
			}
		};
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public AdminUser adminUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (AdminUser) authentication.getPrincipal();
	}
}
