package kr.co.daou.sdev.altong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@PropertySource(value = "config-lib-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@SpringBootApplication(scanBasePackages = "kr.co.daou.sdev.altong")
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

//		String hostName;
//		try {
//			hostName = InetAddress.getLocalHost().getHostName();
//		} catch (UnknownHostException e) {
//			hostName = "";
//			e.printStackTrace();
//		}
//		System.setProperty("adminLog.hostname", hostName);
//
//
//		log.info(System.getProperty("user.dir"));
//		System.setProperty("local.dir", System.getProperty("user.dir") + "\\");
//		log.info(System.getProperty("local.dir"));
//
		return application.sources(WebApplication.class);
	}
}
