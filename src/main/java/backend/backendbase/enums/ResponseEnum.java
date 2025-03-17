package backend.backendbase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(200,"success"),
    INTERNAL_SERVER_ERROR(500,"error.system"),
    UNAUTHORIZED(1000,"error.badCredentials"),
    ERROR_EXISTS(409,"error.existed"),
    TOO_MANY_REQUESTS(429,"error.rateLimit"),
    NOT_FOUND(404,"error.recordNotFound"),
    CONFLICT(409, "error.conflict"),
    ;

    @Getter
    private final int code;
    private final String message;
    @Setter
    private static MessageSource messageSource;

    public String getMessage() {
        return messageSource != null
                ? messageSource.getMessage(message, null, LocaleContextHolder.getLocale())
                : message;
    }

}
