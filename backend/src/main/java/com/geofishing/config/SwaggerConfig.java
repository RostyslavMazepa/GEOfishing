package com.geofishing.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;


@Configuration
@EnableSwagger2
@PropertySource(value = "classpath:application.yml")
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths((com.google.common.base.Predicate<String>) getPaths())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(getApiInfo())
                ;


    }


    private ApiInfo getApiInfo() {

        Contact contact = new Contact("Rodion Yashchuk", "http://geosportfishing.com", "yashchuk@gmail.com");

        return new ApiInfoBuilder()
                .title("GeoFishing Api")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build()
                ;

    }


    private Predicate<String> getPaths() {
        return ((Predicate<String>)
                Predicates.not(PathSelectors.ant("/actuator/**"))::apply)
                .and(Predicates.not(PathSelectors.regex("/error"))::apply)
                .and(Predicates.not(PathSelectors.regex("/oauth/authorize"))::apply)
                .and(Predicates.not(PathSelectors.regex("/oauth/confirm_access"))::apply)
                .and(Predicates.not(PathSelectors.regex("/oauth/error"))::apply);

    }
}
