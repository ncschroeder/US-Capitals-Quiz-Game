package com.uscapitalsquizgame;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }
    
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer() {
//        var freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/");
//        return freeMarkerConfigurer;
//    }
}
