package backend.backendbase.exception;

import backend.backendbase.data.api.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Locale;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@ResponseBody
@ControllerAdvice
public class ExceptionHandleController {

    private MessageSource messageSource;

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception exception) {
        exception.printStackTrace();
        log.error("Error Handling Exception, message = {}", exception.getMessage());
        return new ApiResponse(500, getLocalizedMessage("error.system"));
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResponse handleBadCredentialsException(BadCredentialsException exception) {
        log.error("Error Handling Bad Credentials, message = {}", exception.getMessage());
        return new ApiResponse(1000, getLocalizedMessage("error.badCredentials"));
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("Error Handling DataIntegrityViolationException", exception);
        return new ApiResponse(HttpStatus.CONFLICT.value(), getLocalizedMessage("error.existed"));
    }

    @ExceptionHandler(value = { RateLimitExceededException.class })
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    public ApiResponse handleRateLimitExceededException(RateLimitExceededException exception) {
        log.error("Error Handling RateLimitExceededException, message = {}", exception.getMessage());
        return new ApiResponse(HttpStatus.TOO_MANY_REQUESTS.value(), getLocalizedMessage("error.rateLimit"));
    }

    @ExceptionHandler(value = { RecordNotFoundException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiResponse handleRecordNotFoundException(RecordNotFoundException exception) {
        log.error("Error Handling RecordNotFoundException, message = {}", exception.getMessage());
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), getLocalizedMessage("error.recordNotFound"));
    }

    private String getLocalizedMessage(String translationKey)
    {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(translationKey, null, locale);
    }

}
