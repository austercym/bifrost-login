package com.orwellg.bifrost.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.orwellg.bifrost.party.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());                                       
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Bifrost Login API",
          "Midtier api for Party.", 
          "API TOS", 
          "Terms of service", 
          new Contact("ContactName", "www.example.com", "myeaddress@orwellg.com"),
          "License of API", "API license URL", Collections.emptyList());
   }
}