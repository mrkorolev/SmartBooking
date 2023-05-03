package org.clinic.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan({"org.clinic.config",
        "org.clinic.controller",
        "org.clinic.service"})
public class WebConfig implements ApplicationContextAware, WebMvcConfigurer {

    private ApplicationContext applicationContext;


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");              // where to look for templates
        templateResolver.setSuffix(".html");                        //
        templateResolver.setTemplateMode(TemplateMode.HTML);        // view language we are using

        return templateResolver;
    }

//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }

    // Thymeleaf engine initializer
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new Java8TimeDialect());                  // adding Java8Dialect for dates to the engine
        templateEngine.addDialect(new SpringSecurityDialect());             // SpringSecurity dialect - интеграция thymeleaf с SS
//        templateEngine.setTemplateEngineMessageSource(messageSource());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    // Context is field-injected, not via constructor
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // Uses engine to configure the viewresolver
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        registry.viewResolver(resolver);
        resolver.setTemplateEngine(templateEngine());
    }

    // Setting up resources, as well as front end library storage (webjars)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/sign-in").setViewName("sign-in");
    }
}
