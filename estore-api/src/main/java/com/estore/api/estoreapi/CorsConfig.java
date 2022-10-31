package com.estore.api.estoreapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This file provides Spring Security configuration in relation with CORs, allows for cross domain communication which is
 * crucial as Spring backend is the APIs that will be used by Angular Frontend
 * 
 * {@link @Configuration} is a spring annotation that indicates that this class has @Bean definition methods
 * Spring container will process this class to create Spring Beans
 * 
 * @author Team E
 */
@Configuration
public class CorsConfig {
    private static final String[] CRUDMethods = { "POST", "GET", "PUT", "DELETE"};

    /**
     * an implemented WebMvcConfigurer that implements addCorsMappings which contains the CORS
     * configurations
     * 
     * @return {@linkplain WebMvcConfigurer}  
     * 
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            /**
             * Modifies the CorsRegistry object which assists check which kinds of cross origin requests are allowed
             * Here we allow CORS requests from any origin to any endpoint with all the HTTP methods, Origins and Headers allowed
             * 
             * @param {@linkplain CorsRegistry registry}  
             * 
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(CRUDMethods[0], CRUDMethods[1], CRUDMethods[2], CRUDMethods[3])
                        .allowedHeaders("*")
                        .allowedOriginPatterns("*")
                        .allowCredentials(true);
            }
        };
    }
}
