package backend.backendbase.common.result;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

@AllArgsConstructor
@RequiredArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("200","success"),
    ERROR_SYSTEM("500","error.system"),
    UNAUTHORIZED("1000","error.badCredentials"),
    ERROR_EXISTS("409","error.existed"),
    TOO_MANY_REQUESTS("429","error.rateLimit"),
    NOT_FOUND("404","error.recordNotFound"),
    ;

    private String code;
    private String msg;
    private static MessageSource messageSource;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return messageSource != null
                ? messageSource.getMessage(msg, null, LocaleContextHolder.getLocale())
                : msg; // if messageSource is not initialized
    }

    public static void setMessageSource(MessageSource ms) {
        messageSource = ms;
    }

}
