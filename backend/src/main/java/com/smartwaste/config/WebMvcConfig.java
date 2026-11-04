package com.smartwaste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 * 
 * Configures Spring MVC static resource mapping and WebJars resolution.
 * Ensures Bootstrap resources (/webjars/bootstrap/**) and local assets
 * (/css/**, /js/**, /images/**) are served efficiently without blocking controllers.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /webjars/** to classpath resources bundled inside Maven dependency jars (e.g. Bootstrap)
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false);

        // Map root-relative static asset requests to classpath:/static/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }
}
