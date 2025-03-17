package backend.backendbase.exception;

import backend.backendbase.data.api.ApiResponse;
import backend.backendbase.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@AllArgsConstructor
@ResponseBody
@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception exception) {
        log.info("Error Handling Exception, message = {}", exception.getMessage());
        return ApiResponse.error(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResponse handleBadCredentialsException(BadCredentialsException exception) {
        log.info("Error Handling Bad Credentials, message = {}", exception.getMessage());
        return ApiResponse.error(ResponseEnum.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.info("Error Handling DataIntegrityViolationException", exception);
        return ApiResponse.error(ResponseEnum.CONFLICT);
    }

    @ExceptionHandler(value = { RateLimitExceededException.class })
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    public ApiResponse handleRateLimitExceededException(RateLimitExceededException exception) {
        log.info("Error Handling RateLimitExceededException, message = {}", exception.getMessage());
        return ApiResponse.error(ResponseEnum.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = { RecordNotFoundException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiResponse handleRecordNotFoundException(RecordNotFoundException exception) {
        log.info("Error Handling RecordNotFoundException, message = {}", exception.getMessage());
        return ApiResponse.error(ResponseEnum.NOT_FOUND);
    }

}
