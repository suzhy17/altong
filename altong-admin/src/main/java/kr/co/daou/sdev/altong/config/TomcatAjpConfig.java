package kr.co.daou.sdev.altong.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author suzhy
 */
@Configuration
public class TomcatAjpConfig {

	@Resource
	private Environment env;

	@Profile({"dev", "prod"})
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {

		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		if (env.getProperty("tomcat.ajp.enabled", Boolean.class)) {
			Connector ajpConnector = new Connector("AJP/1.3");
			ajpConnector.setPort(env.getProperty("tomcat.ajp.port", Integer.class));
			ajpConnector.setSecure(false);
			ajpConnector.setAllowTrace(false);
			ajpConnector.setScheme("http");
			tomcat.addAdditionalTomcatConnectors(ajpConnector);
		}

		return tomcat;
	}
}
