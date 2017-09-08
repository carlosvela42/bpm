package co.jp.nej.earth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class EMessageResource {

    @Autowired
    private MessageSource messageSource;

    public String get(String errorCode) {
        return messageSource.getMessage(errorCode, new Object[]{}, Locale.JAPAN);
    }

    public String get(String errorCode, String[] args) {
        if (args == null) {
            return messageSource.getMessage(errorCode, null, Locale.JAPAN);
        }

        String[] params = new String[args.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = messageSource.getMessage(args[i], new String[]{}, Locale.JAPAN);
        }

        return messageSource.getMessage(errorCode, params, Locale.JAPAN);
    }
}
