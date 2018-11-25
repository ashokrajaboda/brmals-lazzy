package com.brmals.application.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact("Ashok Raja Boda","www.brmals.com","brmals.live.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder().contact(DEFAULT_CONTACT)
            .description("Awesome API Description")
            .title("Awesome API Title")
            .license("BRMALS Groups")
            .licenseUrl("mailto:brmals@brmals.com")
            .version("0.0.1")
            .build();

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList(MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.brmals.application.api.rest")).build()
                //.produces(DEFAULT_PRODUCES_AND_CONSUMES)
                //.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .apiInfo(DEFAULT_API_INFO);
    }
}
