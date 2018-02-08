package com.geofishing.config;

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


@Configuration
@EnableSwagger2
@PropertySource(value = "classpath:application.properties")
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
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
                .build();

    }

//    @Bean
//    AlternateTypeRuleConvention jacksonSerializerConvention(TypeResolver resolver) {
//       return new JacksonSerializerConvention(resolver, "org.springframework.security.oauth2.common");
//
//    }
}
