package com.xyz.atmEmulator.bankServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${server.url}")
    private String url;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Collections.singleton("http"))
                .host(url)
                .select().apis(RequestHandlerSelectors.basePackage("com.xyz.atmEmulator.bankServer"))
                //.paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("BANK REST API")
                .description("REST API for BANK Project")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact( new Contact("XYZ", "https://xyz.com/about/", "hajjari@gmail.com"))
                .build();
    }
}
