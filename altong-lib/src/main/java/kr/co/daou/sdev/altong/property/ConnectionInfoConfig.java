package kr.co.daou.sdev.altong.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

//@Component   사용하지 않으므로 컨테이너에 올리지 않음
//@PropertySource(value = "config-lib-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class ConnectionInfoConfig {

	@Getter
	@ToString
	@Component
	public class DaouOffice {
		@Value("${connectInfo.DAOU_OFFICE.host}")
		private String host;

		@Value("${connectInfo.DAOU_OFFICE.port}")
		private int port;

		@Value("${connectInfo.DAOU_OFFICE.userId}")
		private String userId;

		@Value("${connectInfo.DAOU_OFFICE.password}")
		private String password;
	}

	@Getter
	@ToString
	@Component
	public class Email {
		@Value("${connectInfo.EMAIL.host}")
		private String host;

		@Value("${connectInfo.EMAIL.port}")
		private int port;

		@Value("${connectInfo.EMAIL.userId}")
		private String userId;

		@Value("${connectInfo.EMAIL.password}")
		private String password;
	}

	@Getter
	@ToString
	@Component
	public class Sms {
		@Value("${connectInfo.SMS.host}")
		private String host;

		@Value("${connectInfo.SMS.port}")
		private int port;

		@Value("${connectInfo.SMS.userId}")
		private String userId;

		@Value("${connectInfo.SMS.password}")
		private String password;
	}

	@Getter
	@ToString
	@Component
	public class Push {
		@Value("${connectInfo.PUSH.host}")
		private String host;

		@Value("${connectInfo.PUSH.port}")
		private int port;

		@Value("${connectInfo.PUSH.userId}")
		private String userId;

		@Value("${connectInfo.PUSH.password}")
		private String password;
	}
}
