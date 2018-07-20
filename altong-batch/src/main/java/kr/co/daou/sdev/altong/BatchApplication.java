package kr.co.daou.sdev.altong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "kr.co.daou.sdev.altong")
@PropertySource(value = "config-lib-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@EnableScheduling
@EnableAsync
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
