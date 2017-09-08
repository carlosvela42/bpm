package co.jp.nej.earth.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@Component
public class MessageConfig {
    private static final int CACHE_SECONDS = 5;
    // private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    private static final String MESSAGE_SOURCE = "classpath:messages";

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

}
