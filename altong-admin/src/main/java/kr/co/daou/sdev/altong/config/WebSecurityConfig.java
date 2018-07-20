package kr.co.daou.sdev.altong.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kr.co.daou.sdev.altong.security.CustomAuthenticationFailureHandler;
import kr.co.daou.sdev.altong.security.CustomAuthenticationSuccessHandler;
import kr.co.daou.sdev.altong.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AdminUserService adminUserService;

	@Autowired
	private Environment env;
	
	@Bean
	public ShaPasswordEncoder shaPasswordEncoder() {
		return new ShaPasswordEncoder(512);
	}

	@Bean
	public ReflectionSaltSource saltSource() {
		ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		reflectionSaltSource.setUserPropertyToUse("salt");
		return reflectionSaltSource;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setSaltSource(saltSource());
		provider.setUserDetailsService(adminUserService);
		provider.setPasswordEncoder(shaPasswordEncoder());
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/static/**", "/webjars/**"
				,"/v2/api-docs/**","/swagger.json", "/swagger-ui.html");
	}
	
	
    private static final String[] AUTH_WHITELIST = {

            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// properties 설정이 h2일 경우 h2 콘솔을 사용가능하도록 한다.
		if(StringUtils.equals(env.getProperty("spring.profiles.active"), "h2")) {
			log.debug("h2 database use : use h2 console -> Websecurity setting ");
			http.authorizeRequests().antMatchers("/console/**").permitAll().and().csrf().disable().headers().frameOptions().disable();	
		}

		// 권한 문제 수정 
		// 참고 : https://github.com/springfox/springfox/issues/2093
        http.authorizeRequests()
        .antMatchers(AUTH_WHITELIST).permitAll()
       ;
		
	
		http
			.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login-process").permitAll()
				.passwordParameter("password")
				.usernameParameter("userId")
				.successHandler(customAuthenticationSuccessHandler())
				.failureHandler(customAuthenticationFailureHandler())
				.and()
			.logout()
				.logoutSuccessUrl("/login")
				.and()
			.authorizeRequests()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/**").access("hasAuthority('ROLE_USER')")
				.and()
			.csrf()
				.disable();
		// TODO csrf 활성화
		

	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}
