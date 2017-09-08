package co.jp.nej.earth.config;


import org.springframework.context.annotation.Bean;

import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

@EnableWebMvc
class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final int CACHE_SECONDS = 5;
    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
/*
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(CACHE_SECONDS);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.JAPAN); // change this
        return localeResolver;
    }
    */

    @Bean(name = "freemarkerConfig")
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        // configurer.setTemplateLoaderPaths("/WEB-INF/views/","/WEB-INF/freemarker/");
        configurer.setTemplateLoaderPaths("/WEB-INF/views/");

        Properties settings = new Properties();
        settings.put("auto_import", "/common/standardPage.ftl as standard, spring.ftl as spring, "
                + "/common/component.ftl as component");
        //settings.put("auto_import", "/common/standardPage.ftl as standard");
        configurer.setFreemarkerSettings(settings);
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();

        viewResolver.setCache(false);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setRequestContextAttribute("rc");
        viewResolver.setContentType("text/html;charset=UTF-8");
        // viewResolver.setContentType("UTF-8");
        return viewResolver;
    }

    @Bean(name = "validator")
    public SmartValidator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
}