//package com.geofishing.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//@Configuration
//@EnableWebMvc
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/rest_doc/api-doc", "/api-doc").setKeepQueryParams(true);
//        registry.addRedirectViewController("/rest_doc/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
//        registry.addRedirectViewController("/rest_doc/swagger-resources/configuration/security","/swagger-resources/configuration/security");
//        registry.addRedirectViewController("/rest_doc/swagger-resources", "/swagger-resources");
//    }
//
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/rest_doc/**").addResourceLocations("classpath:/META-INF/resources/");
//    }
//}
