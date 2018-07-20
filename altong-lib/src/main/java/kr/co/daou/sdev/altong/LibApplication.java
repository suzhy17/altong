package kr.co.daou.sdev.altong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.co.daou.sdev.altong")
public class LibApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibApplication.class, args);
	}
}
