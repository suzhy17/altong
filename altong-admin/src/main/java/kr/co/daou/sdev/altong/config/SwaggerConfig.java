package kr.co.daou.sdev.altong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.co.daou.sdev.altong.controller.api"))
                .paths(PathSelectors.ant("/api/*"))
                .build()
                .apiInfo(apiInfo());
    }
    
    
    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("ALTONG")
				.description("서비스개발부 통합 메시지 API")
				.termsOfServiceUrl("http://localhost:10080")
				.contact(new Contact("altong", "http://localhost:10080", "jeejon@daou.co.kr"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("2.0")
				.build();
	}


    //    @Bean
//    public UiConfiguration uiConfig() {
//        return new UiConfiguration(
//                "",// url
//                "none",       // docExpansion          => none | list
//                "alpha",      // apiSorter             => alpha
//                "schema",     // defaultModelRendering => schema
//                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
//                true,        // enableJsonEditor      => true | false
//                true);        // showRequestHeaders    => true | false
//    }

}